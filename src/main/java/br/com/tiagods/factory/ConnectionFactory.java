package br.com.tiagods.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.tiagods.config.SecundaryDatabaseConfig;

public class ConnectionFactory {
	public Connection getConnection(){
		try{
			SecundaryDatabaseConfig cf = SecundaryDatabaseConfig.getInstance();
			Class.forName(cf.getValue("classForName"));
			return DriverManager.getConnection(
					cf.getValue("url"),
					cf.getValue("user"),
					cf.getValue("password")
			);
		}catch(ClassNotFoundException e){
			return null;
		}catch(SQLException e){
			return null;
		}
	}
	
}
