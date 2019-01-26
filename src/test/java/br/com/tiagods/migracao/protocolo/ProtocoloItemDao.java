/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.migracao.protocolo;

import br.com.tiagods.migracao.ConexaoStatement;
import br.com.tiagods.migracao.ConfigTables;
import br.com.tiagods.migracao.InterfaceDao;
import br.com.tiagods.model.ProtocoloItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Tiago
 */
public class ProtocoloItemDao implements InterfaceDao,ConfigTables {
@Override
    public Connection pegarConexao() {
        return new ConexaoStatement().getConnetion();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar(Object o) {
    Connection con = pegarConexao();
        try {
            PreparedStatement ps = con.prepareStatement("delete from " + PROTOCOLO_ITEM + " where "
                    + PROTOCOLO_ITEM_item_id+ "=?");
            ps.setInt(1, ((ProtocoloItemBean) o).getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            fecharConexao(con);
        }}

    @Override
    public boolean atualizar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inserir(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public boolean inserirOuAtualizar(List<ProtocoloItemBean> items, long key) {
        Connection con = pegarConexao();
        try{
        if (!items.isEmpty()) {
            PreparedStatement ps;
            for (ProtocoloItemBean item : items) {
                if (item.getId()==0) {
                    String sql = "insert into "
                            + PROTOCOLO_ITEM
                            + " ("
                            + PROTOCOLO_ITEM_item_nome + ","
                            + PROTOCOLO_ITEM_item_quantidade + ","
                            + PROTOCOLO_ITEM_item_detalhe + ","
                            + PROTOCOLO_ITEM_item_prot_entrada_id + ","
                            + PROTOCOLO_ITEM_item_data_entrada + ","
                            + PROTOCOLO_ITEM_item_cliente_id + ") "
                            + "values (?,?,?,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, item.getNome());
                    ps.setInt(2, item.getQuantidade());
                    ps.setString(3, item.getDetalhe());
                    ps.setLong(4, key);
                    ps.setDate(5, new java.sql.Date(new Date().getTime()));
                    ps.setInt(6, item.getClienteId());
                } else {
                    String sql = "update "
                            + PROTOCOLO_ITEM + " set "
                            + PROTOCOLO_ITEM_item_nome + "=?,"
                            + PROTOCOLO_ITEM_item_quantidade + "=?,"
                            + PROTOCOLO_ITEM_item_detalhe + "=?,"
                            + PROTOCOLO_ITEM_item_cliente_id + "=? "
                            + "where "
                            + PROTOCOLO_ITEM_item_id+"=?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, item.getNome());
                    ps.setInt(2, item.getQuantidade());
                    ps.setString(3, item.getDetalhe());
                    ps.setInt(4, item.getClienteId());
                    ps.setInt(5, item.getId());
                }
                ps.executeUpdate();
            }
        }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            fecharConexao(con);
        }
        return true;
    }
    public Set<ProtocoloItemBean> receber(String sql) {
        Connection con = pegarConexao();
        Set<ProtocoloItemBean> lista = new HashSet<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProtocoloItemBean p = new ProtocoloItemBean();
                p.setId(rs.getInt(PROTOCOLO_ITEM_item_id));
                p.setNome(rs.getString(PROTOCOLO_ITEM_item_nome));
                p.setQuantidade(rs.getInt(PROTOCOLO_ITEM_item_quantidade));
                p.setDetalhe(rs.getString(PROTOCOLO_ITEM_item_detalhe));
                p.setDataEntrada(rs.getDate(PROTOCOLO_ITEM_item_data_entrada));
                p.setDataSaida(rs.getDate(PROTOCOLO_ITEM_item_data_saida));
                p.setClienteId(rs.getInt(PROTOCOLO_ITEM_item_cliente_id));
                p.setProtocoloEntrada(rs.getInt(PROTOCOLO_ITEM_item_prot_entrada_id));
                p.setProtocoloSaida(rs.getString(PROTOCOLO_ITEM_item_prot_saida_id)==null?0:
                        rs.getInt(PROTOCOLO_ITEM_item_prot_saida_id));
                p.setPassivelDevolucao(rs.getInt(PROTOCOLO_ITEM_item_passivel_devolucao));
                p.setEntregue(rs.getInt(PROTOCOLO_ITEM_item_entregue));
                p.setItem_responsavel_id(rs.getInt(PROTOCOLO_ITEM_item_responsavel_id));
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
    public List<ProtocoloItemBean> receberLista(String sql) {
        Connection con = pegarConexao();
        List<ProtocoloItemBean> lista = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProtocoloItemBean p = new ProtocoloItemBean();
                p.setId(rs.getInt(PROTOCOLO_ITEM_item_id));
                p.setNome(rs.getString(PROTOCOLO_ITEM_item_nome));
                p.setQuantidade(rs.getInt(PROTOCOLO_ITEM_item_quantidade));
                p.setDetalhe(rs.getString(PROTOCOLO_ITEM_item_detalhe));
                p.setDataEntrada(rs.getDate(PROTOCOLO_ITEM_item_data_entrada));
                p.setDataSaida(rs.getDate(PROTOCOLO_ITEM_item_data_saida));
                p.setClienteId(rs.getInt(PROTOCOLO_ITEM_item_cliente_id));
                p.setProtocoloEntrada(rs.getInt(PROTOCOLO_ITEM_item_prot_entrada_id));
                p.setProtocoloSaida(rs.getString(PROTOCOLO_ITEM_item_prot_saida_id)==null?0:
                        rs.getInt(PROTOCOLO_ITEM_item_prot_saida_id));
                p.setPassivelDevolucao(rs.getInt(PROTOCOLO_ITEM_item_passivel_devolucao));
                p.setEntregue(rs.getInt(PROTOCOLO_ITEM_item_entregue));
                p.setItem_responsavel_id(rs.getInt(PROTOCOLO_ITEM_item_responsavel_id));
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
