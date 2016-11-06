package br.com.tiagods.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class CriarAdmin {
	
	Usuario usuario;
	Departamento departamento;
	Funcao funcao;
	
	static CriarAdmin instance;
	public static CriarAdmin getInstance(){
		if(instance==null){
			instance = new CriarAdmin();
		}
		return instance;
	}
	public Usuario getUsuario(){
		return usuario;
	}
	
	public CriarAdmin(){
		Session session = new HibernateFactory().getSession();
		List users = session.createQuery("from Usuario").getResultList();
		if(users.isEmpty()){
			criarDepartamento();
			criarFuncao();
			criarUsuario();
		}
		else{
			usuario = (Usuario)users.get(0);
		}
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
	
}
