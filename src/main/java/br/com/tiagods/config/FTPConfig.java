package br.com.tiagods.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPConfig {
	Logger log = LoggerFactory.getLogger(FTPConfig.class);
	private static FTPConfig instance;
	private static Properties props = null;
	
//	public static FTPConfig getInstance() {
//		if(instance == null)
//			instance = new FTPConfig();
//		return instance;
//	}
//	private FTPConfig() {
//		props = new Properties();
//		fileLoad();
//	}
//	private void fileLoad() {
//		try {
//			InputStream stream = getClass().getResource("/credentials/ftp.properties").openStream();
//			props.load(stream);
//		} catch (IOException e) {
//			throw new RuntimeException("Falha ao carregar o arquivo de configurações do FTP");
//		}
//	}
//	public String getValue(String key) {
//		return props.getProperty(key);
//	}
}
