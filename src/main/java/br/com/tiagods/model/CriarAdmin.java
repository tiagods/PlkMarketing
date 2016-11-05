package br.com.tiagods.model;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class CriarAdmin {
	
	Usuario usuario;
	Departamento departamento;
	Funcao funcao;
	
	public CriarAdmin(){
		Session session = new HibernateFactory().getSession();
		int users = session.createQuery("from Usuario").getFirstResult();
		if(users==0){
			criarDepartamento();
			criarFuncao();
			criarUsuario();
		}
		else{
			usuario = session.find(Usuario.class, 1);
			session.getTransaction().commit();
		}
		usuario = session.find(Usuario.class, 1);
		session.getTransaction().commit();
		session.close();
	}
	public void criarUsuario(){
		Session session = new HibernateFactory().getSession();
		usuario = new Usuario();
		usuario.setLogin("admin");
		usuario.setNome("Administrador");
		usuario.setSenha("admin");
		usuario.setEmail("suporte.ti@prolinkcontabil.com.br");
		usuario.setCriadoEm(new Date());
		usuario.setUltimoAcesso(new Date());
		usuario.setDepartamento(departamento);
		usuario.setFuncao(funcao);
		usuario.setTotalVendas(new BigDecimal("0.00"));
		try{
			session.save(usuario);
			session.getTransaction().commit();
		}catch(HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
		}	
		session.close();
	}
	public void criarDepartamento(){
		Session session = new HibernateFactory().getSession();
		departamento = new Departamento();
		departamento.setNome("Tecnologia");
		try{
			session.save(departamento);
			session.getTransaction().commit();
		}catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		session.close();
	}
	public void criarFuncao(){
		Session session = new HibernateFactory().getSession();
		funcao = new Funcao();
		funcao.setNome("Analista");
		try{
		session.save(funcao);
		session.getTransaction().commit();
		}catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		session.close();
	}
	public Usuario getUsuario(){
		Session session = new HibernateFactory().getSession();
		usuario = session.find(Usuario.class, 1);
		session.close();
		return usuario;
	}
}
