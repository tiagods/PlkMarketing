package br.com.tiagods.model;

import java.util.Set;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class FuncionarioDao {
	
	public Set<Funcionario> getLista(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Set<Funcionario> list = (Set<Funcionario>)session.createQuery("from Funcionario").getResultList();
		factory.closeSession(session);
		return list;
	}
	
}
