package br.com.tiagods.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.tiagods.config.SecundaryDatabaseConfig;

public class ConnectionFactory {
	public Connection getConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://54.207.2.225/negocios", "negocios", "negocios");
		}catch(ClassNotFoundException e){
			return null;
		}catch(SQLException e){
			return null;
		}
	}
	
}
