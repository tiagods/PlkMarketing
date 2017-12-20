package br.com.tiagods.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecundaryDatabaseConfig {
	Logger log = LoggerFactory.getLogger(SecundaryDatabaseConfig.class);
	private static SecundaryDatabaseConfig instance;
	private static Properties props = null;
	
//	public static SecundaryDatabaseConfig getInstance() {
//		if(instance == null)
//			instance = new SecundaryDatabaseConfig();
//		return instance;
//	}
//	private SecundaryDatabaseConfig() {
//		props = new Properties();
//		fileLoad();
//	}
//	private void fileLoad() {
//		try {
//			InputStream stream = getClass().getResource("/credentials/database_other.properties").openStream();
//			props.load(stream);
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null,"Falha ao carregar o arquivo de configurações do Banco de Dados - Atualizador");
//		}
//	}
//	public String getValue(String key) {
//		return props.getProperty(key);
//	}
}
