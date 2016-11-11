package br.com.tiagods.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class NivelDao {
	public Nivel getById(int cod){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Nivel nivel = session.find(Nivel.class, cod);
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return nivel;
	}
	@SuppressWarnings("unchecked")
	public List<Nivel> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		List<Nivel> lista = (List<Nivel>)session.createQuery("from Nivel").getResultList();
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return lista;
	}
}
