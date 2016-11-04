package br.com.tiagods.model;

import java.util.Set;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class UsuarioDao {
	
	public Set<Usuario> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Set<Usuario> list = (Set<Usuario>)session.createQuery("from Usuario").getResultList();
		factory.closeSession(session);
		return list;
	}
	
	
}
