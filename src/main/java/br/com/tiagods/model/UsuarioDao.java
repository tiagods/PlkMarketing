package br.com.tiagods.model;

import java.util.List;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class UsuarioDao {
	
	public List<Usuario> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		List<Usuario> list = (List<Usuario>)session.createQuery("from Usuario").getResultList();
		factory.closeSession(session);
		return list;
	}
	
	
}
