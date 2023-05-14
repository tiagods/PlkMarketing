package br.com.tiagods.config;

import br.com.tiagods.config.enums.PropsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailConfig extends PropsConfig{
	Logger log = LoggerFactory.getLogger(MailConfig.class);
	private static MailConfig instance;
	public static MailConfig getInstance() {
		if(instance == null)
			instance = new MailConfig();
		return instance;
	}
	private MailConfig() {
		super(PropsEnum.MAIL);
	}
}
