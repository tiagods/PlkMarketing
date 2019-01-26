/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.migracao.protocolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import br.com.tiagods.migracao.ConexaoStatement;
import br.com.tiagods.migracao.ConfigTables;
import br.com.tiagods.migracao.InterfaceDao;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Tiago
 */
public class ProtocoloDao implements InterfaceDao, ConfigTables {

    private long key=0;
    
    public long getKey(){
        return this.key;
    }
    
    @Override
    public Connection pegarConexao() {
        return ConexaoStatement.getInstance().getConnetion();
    }

    @Override
    public void fecharConexao(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
        }
    }

    @Override
    public Object retornaClasse(int id) {
        String sql = "select d.*, "
                + "(select c." + CLIENTE_nome + " from " + CLIENTE + " as c where d." + DOCUMENTOS_RECEBIDOS_ID + "=c." + CLIENTE_id + ") AS EmpresaNome, "
                + "(select l." + LOGIN_Usuario + " from " + LOGIN + " as l where d." + DOCUMENTOS_RECEBIDOS_para_quem_id + "=l." + LOGIN_cod + ") as ParaQuemNome,"
                + "(select l." + LOGIN_Usuario + " from " + LOGIN + " as l where d." + DOCUMENTOS_RECEBIDOS_quem_recebeu_id + "=l." + LOGIN_cod + ") as QuemRecebeuNome"
                + " from " + DOCUMENTOS_RECEBIDOS + " as d "
                +" where " + DOCUMENTOS_RECEBIDOS_cod + "=" + id;
        List lista = receberLista(sql);
        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }
    }

    @Override
    public List listar() {
        String sql = "select d.*, "
                + "(select c." + CLIENTE_nome + " from " + CLIENTE + " as c where d." + DOCUMENTOS_RECEBIDOS_ID + "=c." + CLIENTE_id + ") AS EmpresaNome, "
                + "(select l." + LOGIN_Usuario + " from " + LOGIN + " as l where d." + DOCUMENTOS_RECEBIDOS_para_quem_id + "=l." + LOGIN_cod + ") as ParaQuemNome,"
                + "(select l." + LOGIN_Usuario + " from " + LOGIN + " as l where d." + DOCUMENTOS_RECEBIDOS_quem_recebeu_id + "=l." + LOGIN_cod + ") as QuemRecebeuNome"
                + " from " + DOCUMENTOS_RECEBIDOS + " as d "
                + " where d." + DOCUMENTOS_RECEBIDOS_Recebido + "='N' order by "+DOCUMENTOS_RECEBIDOS_cod+" desc";
        return receberLista(sql);
    }

    @Override
    public boolean deletar(Object o) {
        Connection con = pegarConexao();
        try {
            PreparedStatement ps = con.prepareStatement("delete from " + DOCUMENTOS_RECEBIDOS + " where "
                    + DOCUMENTOS_RECEBIDOS_cod + "=?");
            ps.setInt(1, ((ProtocoloEntradaBean) o).getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            fecharConexao(con);
        }
    }

    @Override
    public boolean atualizar(Object o) {
        Connection con = pegarConexao();
        try {
            ProtocoloEntradaBean p = (ProtocoloEntradaBean) o;
            String sql = "update "+DOCUMENTOS_RECEBIDOS+" set "
                    + DOCUMENTOS_RECEBIDOS_Quem_Entregou+"=?,"
                    + DOCUMENTOS_RECEBIDOS_ID+"=?,"
                    + DOCUMENTOS_RECEBIDOS_Departamento+"=?,"
                    + DOCUMENTOS_RECEBIDOS_para_quem_id+"=?,"
                    + DOCUMENTOS_RECEBIDOS_Observacao+"=?,"
                    + DOCUMENTOS_RECEBIDOS_Recebido+"=?,"
                    + DOCUMENTOS_RECEBIDOS_Data_Funcionario_Recebeu+"=?,"
                    + DOCUMENTOS_RECEBIDOS_quem_recebeu_id+"=?,"
                    + DOCUMENTOS_RECEBIDOS_passivel_devolucao+"=?,"
                    + DOCUMENTOS_RECEBIDOS_devolver_ate+"=?,"
                    + DOCUMENTOS_RECEBIDOS_devolvido+"=?,"
                    + DOCUMENTOS_RECEBIDOS_adiado+"=?,"
                    + DOCUMENTOS_RECEBIDOS_adiado_motivo+"=? "
                    + "where "
                    + DOCUMENTOS_RECEBIDOS_cod+"=?";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getQuemEntregou());
            ps.setString(2, p.getApelido());
            ps.setString(3, p.getDepartamento());
            ps.setInt(4, p.getParaQuemId());
            ps.setString(5, p.getObservacao());
            ps.setString(6, p.getRecebido());
            ps.setString(7, p.getDataFuncionarioRecebeu()==null?null:sdf.format(p.getDataFuncionarioRecebeu()));
            ps.setInt(8, p.getQuemRecebeuId());
            ps.setInt(9, p.getPassivelDevolucao());
            ps.setString(10, p.getDevolverAte()==null?null:sdf.format(p.getDevolverAte()));
            ps.setInt(11, p.getDevolvido());
            ps.setInt(12, p.getAdiado());
            ps.setString(13, p.getAdiadoMotivo());
            ps.setInt(14, p.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro ao inserir os dados");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao inserir os dados -> Descricao:\n" + e);
            return false;
        }finally {
            fecharConexao(con);
        }
    }

    @Override
    public boolean inserir(Object o) {
        Connection con = pegarConexao();
        try {
            Date date = new Date();
            ProtocoloEntradaBean p = (ProtocoloEntradaBean) o;
            String sql = "insert into "+DOCUMENTOS_RECEBIDOS
                    + "("+DOCUMENTOS_RECEBIDOS_Data_Recebimento+", "
                    +DOCUMENTOS_RECEBIDOS_Hora+", "
                    +DOCUMENTOS_RECEBIDOS_Quem_Entregou+", "
                    +DOCUMENTOS_RECEBIDOS_ID+", "
                    +DOCUMENTOS_RECEBIDOS_Departamento+", "
                    +DOCUMENTOS_RECEBIDOS_Recebido+", "
                    +DOCUMENTOS_RECEBIDOS_para_quem_id+", "
                    +DOCUMENTOS_RECEBIDOS_Observacao+", "
                    +DOCUMENTOS_RECEBIDOS_Alerta+", "
                    +DOCUMENTOS_RECEBIDOS_adiado+", "
                    +DOCUMENTOS_RECEBIDOS_adiado_motivo+") values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new java.sql.Date(date.getTime()));
            ps.setTime(2, p.getHora());
            ps.setString(3, p.getQuemEntregou());
            ps.setString(4, p.getApelido());
            ps.setString(5, p.getDepartamento());
            ps.setString(6, p.getRecebido());
            ps.setInt(7, p.getParaQuemId());
            ps.setString(8, p.getObservacao());            
            ps.setString(9, p.getAlerta());
            ps.setInt(10, p.getAdiado());
            ps.setString(11, p.getAdiadoMotivo());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            ps.clearParameters();
            if (rs != null && rs.next()) {
                key = rs.getLong(1);
                Set<ProtocoloItemBean> items = p.getItems();
            }
            return true;
        } catch (SQLException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro ao inserir os dados");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao inserir os dados -> Descricao:\n" + e);
            return false;
        }finally {
            fecharConexao(con);
        }
    }
    public List filtrar(String sql) {
        return receberLista(sql+" order by "+DOCUMENTOS_RECEBIDOS_cod+" asc");
    }

    public List receberLista(String sql) {
        Connection con = pegarConexao();
        List lista = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProtocoloEntradaBean p = new ProtocoloEntradaBean();
                p.setId(rs.getInt(DOCUMENTOS_RECEBIDOS_cod));
                p.setDataRecebimento(rs.getDate(DOCUMENTOS_RECEBIDOS_Data_Recebimento));
                p.setQuemEntregou(rs.getString(DOCUMENTOS_RECEBIDOS_Quem_Entregou));
                p.setEmpresa(rs.getString("Empresa"));
                p.setApelido(rs.getString(DOCUMENTOS_RECEBIDOS_ID));
                p.setHistorico(rs.getString(DOCUMENTOS_RECEBIDOS_Historico));
                p.setParaQuem(rs.getString("Para_Quem"));
                p.setQuemRecebeu(rs.getString("Quem_recebeu"));
                p.setDataFuncionarioRecebeu(rs.getDate(DOCUMENTOS_RECEBIDOS_Data_Funcionario_Recebeu));
                p.setObservacao(rs.getString(DOCUMENTOS_RECEBIDOS_Observacao));
                p.setDepartamento(rs.getString(DOCUMENTOS_RECEBIDOS_Departamento));
                p.setHora(rs.getTime(DOCUMENTOS_RECEBIDOS_Hora));
                p.setAlerta(rs.getString(DOCUMENTOS_RECEBIDOS_Alerta));
                p.setRecebido(rs.getString(DOCUMENTOS_RECEBIDOS_Recebido));
                p.setParaQuemId(rs.getInt(DOCUMENTOS_RECEBIDOS_para_quem_id));
                p.setQuemRecebeuId(rs.getString(DOCUMENTOS_RECEBIDOS_quem_recebeu_id)
                        == null ? 0 : rs.getInt(DOCUMENTOS_RECEBIDOS_quem_recebeu_id));
                p.setPassivelDevolucao(rs.getInt(DOCUMENTOS_RECEBIDOS_passivel_devolucao));
                p.setDevolvido(rs.getInt(DOCUMENTOS_RECEBIDOS_devolvido));
                p.setDevolverAte(rs.getDate(DOCUMENTOS_RECEBIDOS_devolver_ate));
                p.setAdiado(rs.getInt(DOCUMENTOS_RECEBIDOS_adiado));
                p.setAdiadoMotivo(rs.getString(DOCUMENTOS_RECEBIDOS_adiado_motivo));
                ProtocoloItemDao itemDao = new ProtocoloItemDao();
                Set<ProtocoloItemBean> itens = itemDao.receber("select * from protocolo_item where item_prot_entrada_id="+p.getId());
                p.setItems(itens);
                lista.add(p);
            }
            return lista;
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
            return lista;
        } finally {
            fecharConexao(con);
        }
    }
}
