package br.com.tiagods.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class UsuarioDao {
	
	@SuppressWarnings("unchecked")
	public List<Usuario> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		List<Usuario> list = (List<Usuario>)session.createQuery("from Usuario").getResultList();
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return list;
	}
	//listar todos os usuarios ativos
	public Usuario getUsuario(String acesso){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Usuario usuario = (Usuario)session.createQuery("from Usuario u where u.login=:loginName").setParameter("loginName", acesso).getSingleResult();
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			
		}finally{
			session.close();
		}
		return usuario;
		
	}
	
	
}
