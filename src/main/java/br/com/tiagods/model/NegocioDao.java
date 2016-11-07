package br.com.tiagods.model;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class NegocioDao {
	public Negocio getById(int cod){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Negocio negocio = session.find(Negocio.class, cod);
		session.getTransaction().commit();
		session.close();
		return negocio;
	}
}
