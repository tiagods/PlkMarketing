package br.com.tiagods.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class CategoriaDao {
	public Categoria getById(int cod){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Categoria categoria = session.find(Categoria.class, cod);
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return categoria;
	}
	@SuppressWarnings("unchecked")
	public List<Categoria> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		List<Categoria> lista = (List<Categoria>)session.createQuery("from Categoria").getResultList();
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
