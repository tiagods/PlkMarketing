package br.com.tiagods.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailConfig {
	Logger log = LoggerFactory.getLogger(MailConfig.class);
	private static MailConfig instance;
	private static Properties props = null;
	
	public static MailConfig getInstance() {
		if(instance == null)
			instance = new MailConfig();
		return instance;
	}
	private MailConfig() {
		props = new Properties();
		fileLoad();
	}
	private void fileLoad() {
		try {
			InputStream stream = getClass().getResource("/credentials/mail.properties").openStream();
			props.load(stream);
		} catch (IOException e) {
			throw new RuntimeException("Falha ao carregar o arquivo de configurações do E-Mail");
		}
	}
	public String getValue(String key) {
		return props.getProperty(key);
	}
}
