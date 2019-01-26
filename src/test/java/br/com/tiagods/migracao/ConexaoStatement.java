package br.com.tiagods.migracao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *Essa classe vai abrir e fechar conexao com o banco de dados
 * @author User
 */
        
public class ConexaoStatement{
//vamos abrir a conexao
    
    private String driver = "com.mysql.jdbc.Driver";
    
    private String url="jdbc:mysql://localhost:3306/clientev1";
    private String user = "root";
    private String password = "root";
    
    private static ConexaoStatement instance;
    public static ConexaoStatement getInstance(){
        if(instance==null)
            instance=new ConexaoStatement();
        return instance;
    }
    public Connection getConnetion() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException erro) {
            JOptionPane.showMessageDialog(null, "Erro! Sem comunicação com o servidor e banco de dados!");
            throw new RuntimeException(erro);
        }
    }
}