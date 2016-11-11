package br.com.tiagods.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class ServicoDao {
	public Servico getById(int cod){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Servico servico = session.find(Servico.class, cod);
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return servico;
	}
	@SuppressWarnings("unchecked")
	public List<Servico> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		List<Servico> lista = (List<Servico>)session.createQuery("from Servico").getResultList();
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
