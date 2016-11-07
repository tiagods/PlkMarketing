package br.com.tiagods.model;

import java.util.List;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class OrigemDao {
	public Origem getById(int cod){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Origem origem = session.find(Origem.class, cod);
		session.getTransaction().commit();
		session.close();
		return origem;
	}
	public List<Origem> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		List<Origem> lista = (List<Origem>)session.createQuery("from Origem").getResultList();
		session.getTransaction().commit();
		session.close();
		return lista;
	}
}
