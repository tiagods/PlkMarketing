package br.com.tiagods.model;

import java.util.List;

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
	public List<Negocio> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		List<Negocio> lista = (List<Negocio>)session.createQuery("from Negocio").getResultList();//incluir paramentro se pesquisa caso negocio esteja fechado
		session.getTransaction().commit();
		session.close();
		return lista;
	}
}
