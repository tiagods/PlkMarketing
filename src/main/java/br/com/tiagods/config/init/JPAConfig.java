package br.com.tiagods.config.init;

import br.com.tiagods.config.PropsConfig;
import br.com.tiagods.config.enums.PropsEnum;
import br.com.tiagods.config.enums.PropsInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

public class JPAConfig{
	private static final String PERSISTENCE_UNIT_NAME = "negocios";
	private static JPAConfig instance;

	private static EntityManagerFactory factory;

	static {
		Map<String,String> anotacao = DataBaseConfig.getInstance().getMap();
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,anotacao);
	}

	public static JPAConfig getInstance() {
		if(instance==null)
			instance = new JPAConfig();
		return instance;
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

	public EntityManager createManager(){
		return factory.createEntityManager();
	}
}
