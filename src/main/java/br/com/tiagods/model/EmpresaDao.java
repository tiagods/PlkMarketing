package br.com.tiagods.model;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class EmpresaDao {
	public Empresa getById(int cod){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Empresa empresa = session.find(Empresa.class, cod);
		session.getTransaction().commit();
		session.close();
		return empresa;
	}
}
