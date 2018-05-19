package br.com.tiagods.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.tiagods.config.enums.PropsEnum;

public class FTPConfig extends PropsConfig{
	Logger log = LoggerFactory.getLogger(FTPConfig.class);
	private static FTPConfig instance;
	public static FTPConfig getInstance() {
		if(instance == null)
			instance = new FTPConfig();
		return instance;
	}
	private FTPConfig() {
		super(PropsEnum.FTP);
	}
}
