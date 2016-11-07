package br.com.tiagods.model;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class PessoaDao {
	public Pessoa getById(int cod){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Pessoa pessoa = session.find(Pessoa.class, cod);
		session.getTransaction().commit();
		session.close();
		return pessoa;
	}
}
