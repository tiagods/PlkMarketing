package br.com.tiagods.controller;

import java.util.List;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelDAO.CriarAdmin;

@SuppressWarnings("unchecked")
public class UsuarioLogado {
	Usuario usuario;
	
	static UsuarioLogado instance;
	
	public static UsuarioLogado getInstance(){
		if(instance==null){
			instance = new UsuarioLogado();
		}
		return instance;
	}
	public UsuarioLogado(){
		Session session = HibernateFactory.getSession();
		session.beginTransaction();
		List<Usuario> users = session.createQuery("from Usuario").getResultList();
		if(users.isEmpty()){
			CriarAdmin.getInstance().gerarDefault();
			usuario = CriarAdmin.getInstance().getUsuario();
		}	
		else{
			usuario = users.get(0);
		}
		session.close();
	}
	public void setUsuario(Usuario usuario){
		this.usuario=usuario;
	}
	public Usuario getUsuario(){
		return usuario;
	}
}
