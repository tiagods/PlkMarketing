/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.migracao.usuario;

import br.com.tiagods.migracao.ConexaoStatement;
import br.com.tiagods.migracao.ConfigTables;
import br.com.tiagods.migracao.InterfaceDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 *
 * @author Tiago
 */
public class UsuarioDao implements InterfaceDao,ConfigTables {
    public UsuarioMysql autentica(String usuario, String senha){
        Connection con = pegarConexao();
        try{
            String sql = "select * from "+LOGIN+" Where "+LOGIN_Usuario+" like ?"
                        + " and "+LOGIN_Senha+" like ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, usuario);
                ps.setString(2, senha);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    UsuarioMysql novoUsuario = new UsuarioMysql();
                    novoUsuario.setCod(rs.getInt(LOGIN_cod));
                    novoUsuario.setUsuario(rs.getString(LOGIN_Usuario));
                    novoUsuario.setSenha(rs.getString(LOGIN_Senha));
                    novoUsuario.setDepartamento(rs.getString(LOGIN_Departamento));
                    novoUsuario.setEmail(rs.getString(LOGIN_Email));
                    novoUsuario.setNome(rs.getString(LOGIN_Nome));
                    novoUsuario.setNivel(rs.getInt(LOGIN_Nivel));
                    novoUsuario.setAtivo(rs.getInt(LOGIN_Ativo));
                    return novoUsuario;
                } 
        }catch(SQLException ex){
            return null;
        }finally{fecharConexao(con);}
        return null;
    }

    @Override
    public Connection pegarConexao() {
        return ConexaoStatement.getInstance().getConnetion();
    }

    @Override
    public void fecharConexao(Connection con) {
        try{if(con!=null)con.close();
        }catch(SQLException e){}
    }
    @Override
    public Object retornaClasse(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//
    @Override
    public List<Object> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public List usuariosAtivos(){
        Connection con = pegarConexao();
        try{
            List lista = new ArrayList<>();
            String sql = "select * from "+LOGIN+" where "+LOGIN_Ativo+"=1";
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    UsuarioMysql novoUsuario = new UsuarioMysql();
                    novoUsuario.setCod(rs.getInt(LOGIN_cod));
                    novoUsuario.setUsuario(rs.getString(LOGIN_Usuario));
                    novoUsuario.setSenha(rs.getString(LOGIN_Senha));
                    novoUsuario.setDepartamento(rs.getString(LOGIN_Departamento));
                    novoUsuario.setEmail(rs.getString(LOGIN_Email));
                    novoUsuario.setNome(rs.getString(LOGIN_Nome));
                    novoUsuario.setNivel(rs.getInt(LOGIN_Nivel));
                    novoUsuario.setAtivo(rs.getInt(LOGIN_Ativo));
                    lista.add(novoUsuario);
                } 
                return lista;
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }finally{fecharConexao(con);}
    }

    @Override
    public boolean atualizar(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inserir(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public Map<String, Integer> protocoloEntrada(UsuarioMysql u){
        Map<String,Integer> map = new HashMap<>();
        Connection con = pegarConexao();
        try{
            String sql = "select l.usuario," +
            "(select count(d1.cod) from documentos_recebidos as d1 where d1.para_quem_id=l.cod and d1.Recebido='N') as 'Não Recebidos'," +
            "(select count(d2.cod) from documentos_recebidos as d2 where d2.para_quem_id=l.cod and d2.Recebido='S' and d2.passivel_devolucao=1 and d2.devolvido=0) as 'Não Devolvidos'," +
            "(select count(d3.cod) from documentos_recebidos as d3 where d3.para_quem_id=l.cod and d3.Recebido='S' " +
            "		and d3.passivel_devolucao=1 and d3.devolvido=1 or d3.para_quem_id=l.cod and d3.Recebido='S' and d3.passivel_devolucao=0) as 'Concluido'" +
            " from login as l where l.cod=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, u.getCod());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put("Não Recebido", rs.getInt(2));
                map.put("Não Devolvido", rs.getInt(3));
                map.put("Concluido", rs.getInt(4));
            }
            return map;
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }finally{fecharConexao(con);}
    }
    public Set<Map<String,Integer>> protocoloEntradaPizza(){
        String sql=""
                + "select l.usuario,\n" +
                    "	(select count(d1.cod) from documentos_recebidos as d1 where d1.para_quem_id=l.cod and d1.Recebido='N') as 'Não Recebidos',\n" +
                    "	(select count(d2.cod) from documentos_recebidos as d2 where d2.para_quem_id=l.cod and d2.Recebido='S' and d2.passivel_devolucao=1 and d2.devolvido=0) as 'Não Devolvido',\n" +
                    "	(select count(d3.cod) from documentos_recebidos as d3 where d3.para_quem_id=l.cod and d3.Recebido='S' \n" +
                    "		and d3.passivel_devolucao=1 and d3.devolvido=1 or d3.para_quem_id=l.cod and d3.Recebido='S' and d3.passivel_devolucao=0) as 'Concluido'\n" +
                    "from login as l where l.Ativo=1"
                + "";
        return null;
    }
    public List<UsuarioMysql> listarTodos(){
        Connection con = pegarConexao();
        try{
            List lista = new ArrayList<>();
            String sql = "select * from "+LOGIN;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                UsuarioMysql novoUsuario = new UsuarioMysql();
                novoUsuario.setCod(rs.getInt(LOGIN_cod));
                novoUsuario.setUsuario(rs.getString(LOGIN_Usuario));
                novoUsuario.setSenha(rs.getString(LOGIN_Senha));
                novoUsuario.setDepartamento(rs.getString(LOGIN_Departamento));
                novoUsuario.setEmail(rs.getString(LOGIN_Email));
                novoUsuario.setNome(rs.getString(LOGIN_Nome));
                novoUsuario.setNivel(rs.getInt(LOGIN_Nivel));
                novoUsuario.setAtivo(rs.getInt(LOGIN_Ativo));
                lista.add(novoUsuario);
            }
            return lista;
        }catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }finally{fecharConexao(con);}
    }
}