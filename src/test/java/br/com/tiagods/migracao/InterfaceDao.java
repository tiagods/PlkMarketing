/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.migracao;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author Tiago
 */
public interface InterfaceDao {
    public Connection pegarConexao();
    public void fecharConexao(Connection con);
    public Object retornaClasse(int id);

    public List listar();

    public boolean deletar(Object o);

    public boolean atualizar(Object o);

    public boolean inserir(Object o);
}
