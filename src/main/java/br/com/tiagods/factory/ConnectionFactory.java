package br.com.tiagods.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.tiagods.config.SecundaryDatabaseConfig;

public class ConnectionFactory {
	
	public static String TABLENAME="VERSAO_APP";
	
	public Connection getConnection(){
		try{
			SecundaryDatabaseConfig s = SecundaryDatabaseConfig.getInstance();
			Class.forName(s.getValue("classForName"));
			return DriverManager.getConnection(s.getValue("url"), s.getValue("user"), s.getValue("password"));
		}catch(ClassNotFoundException e){
			return null;
		}catch(SQLException e){
			return null;
		}
	}
}
