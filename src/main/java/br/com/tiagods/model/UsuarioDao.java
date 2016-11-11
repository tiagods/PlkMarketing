package br.com.tiagods.model;

import org.hibernate.Session;

public class UsuarioDao {
	public Usuario getUsuario(String login, Session session){
		Usuario usuario = (Usuario) session.createQuery("from Usuario u where u.login=:loginName")
				.setParameter("loginName", login)
				.getSingleResult();
		return usuario;
	}
	
	
}
