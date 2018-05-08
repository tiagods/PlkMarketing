package br.com.tiagods.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecundaryDatabaseConfig extends PropsConfig{
	Logger log = LoggerFactory.getLogger(SecundaryDatabaseConfig.class);
	private static SecundaryDatabaseConfig instance;
	
	public static SecundaryDatabaseConfig getInstance() {
		if(instance == null)
			instance = new SecundaryDatabaseConfig();
		return instance;
	}
	private SecundaryDatabaseConfig() {
		super(PropsEnum.DB);
	}
}
