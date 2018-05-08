package br.com.tiagods.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropsConfig implements PropsInterface{
	Logger log = LoggerFactory.getLogger(SecundaryDatabaseConfig.class);
	private static Properties props = null;
	
	public PropsConfig(PropsEnum propsEnum) {
		props = new Properties();
		fileLoad(propsEnum);
	}
	@Override
	public void fileLoad(PropsEnum propsEnum) {
		try {
			InputStream stream = getClass().getResource(propsEnum.getDescricao()).openStream();
			props.load(stream);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Falha ao carregar o arquivo de configurações do Banco de Dados - Atualizador");
		}
	}
	@Override
	public String getValue(String key) {
		return props.getProperty(key);
	}
}
