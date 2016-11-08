package br.com.tiagods.model;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import br.com.tiagods.factory.HibernateFactory;

public class PessoaDao {
	//recuperar objeto pelo id
	public Pessoa getById(int cod){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Pessoa pessoa = session.find(Pessoa.class, cod);
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return pessoa;
	}
	//recuperar lista de pessoas
	@SuppressWarnings("unchecked")
	public List<Pessoa> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		List<Pessoa> lista = (List<Pessoa>)session.createQuery("from Pessoa").getResultList();
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
