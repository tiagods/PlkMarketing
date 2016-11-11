package br.com.tiagods.factory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateFactory {
	public static SessionFactory fabrica;
	static{
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try{
			fabrica = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}catch(Exception e){
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
			fabrica = null;
		}
	}
	public static Session getSession() throws HibernateException{
		return fabrica.openSession();
		
	}	
}