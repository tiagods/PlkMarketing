package br.com.tiagods.factory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateFactory {
	//recebendo a sessao
		public Session getSession(){
			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
			SessionFactory sessionFactory = null;
			try{
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			}catch(Exception e){
				StandardServiceRegistryBuilder.destroy(registry);
				e.printStackTrace();
			}
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			return session;
		}
		//fechando a sessao
//		public void closeSession(Session session){
//			session.getTransaction().commit();
//			session.close();
//		}
		
}

