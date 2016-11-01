package br.com.tiagods.factory;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateFactory {
/*
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
		//returnando a lista de objetos da sessao
		@SuppressWarnings({"unchecked"})
		public List<Object> getList(Session session, Object object){
			return session.createQuery("from "+object).getResultList();
		}
		public Set<Object> getSetList(Session session, Object object){
			return (Set<Object>) session.createQuery("from "+object).getResultList();
		}
		//retornando apenas 1 objeto
		public Object getObject(Session session, Object object){
			return session.createQuery("from "+object).getFirstResult();
		}
		//salvar ou atualiza
		public String  saveOrUpdateSession(Session session, Object object){
			try{
				session.saveOrUpdate(object);
				return "Salvo";
			}catch(Exception e){
				return "Erro ao Salvar: "+e.getMessage();
			}
		}
		//fechando a sessao
		public void closeSession(Session session){
			session.getTransaction().commit();
			session.close();
	}
		*/
}

