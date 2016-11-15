package br.com.tiagods.modelDAO;

import org.hibernate.Session;

import br.com.tiagods.model.Usuario;

public class UsuarioDao {
	public Usuario getLogin(String login, Session session){
		Usuario usuario = (Usuario) session.createQuery("from Usuario u where u.login=:loginName")
				.setParameter("loginName", login)
				.getSingleResult();
		return usuario;
	}
	
	
}
