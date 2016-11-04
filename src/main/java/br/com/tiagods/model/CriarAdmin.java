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
		session.getTransaction().commit();
		if(users==0){
			Departamento departamento = new Departamento();
			departamento.setNome("Tecnologia");
			int dps = session.createQuery("from Departamento as d where d.nome"
					+ "= :nomeDepartamento ")
					.setParameter("nomeDepartamento", departamento.getNome()).getFirstResult();
			session.getTransaction().commit();
			if(dps>0){
				departamento = (Departamento) session.createQuery("from Departamento as d where d.nome"
						+ "= :nomeDepartamento ")
						.setParameter("nomeDepartamento", departamento.getNome()).getSingleResult();
				session.getTransaction().commit();
			}
			else{
				session.save(departamento);
				session.getTransaction().commit();
			}
			
			Funcao funcao = new Funcao();
			funcao.setNome("Analista");
			
			String hql = "from Funcao as d where d.nome= :nomeFuncao";
			int fun = session.createQuery(hql)
					.setParameter("nomeFuncao", funcao.getNome()).getFirstResult();
			session.getTransaction().commit();
			
			if(fun>0){
				funcao = (Funcao) session.createQuery("from Funcao as d where d.nome"
						+ "= :nomeFuncao ")
						.setParameter("nomeFuncao", funcao.getNome()).getSingleResult();
				session.getTransaction().commit();
			}
			else{
				session.save(funcao);
				session.getTransaction().commit();
			}
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
				session.beginTransaction();
				session.save(usuario);
				session.getTransaction().commit();
			}catch(HibernateException e){
				e.printStackTrace();
				session.getTransaction().rollback();
			}	
		}
		else{
			usuario = session.find(Usuario.class, 1);
			session.getTransaction().commit();
		}
		session.close();
	}
	public void criarUsuario(){
		
	}
	public void criarDepartamento(){
		
	}
	
	public Usuario getUsuario(){
		return usuario;
	}
}
