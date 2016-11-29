package br.com.tiagods.modelDAO;

import java.util.List;

import org.hibernate.Session;

import br.com.tiagods.model.Usuario;

public class UsuarioDAO implements InterfaceDAO{
	public Usuario getLogin(String login, Session session){
		Usuario usuario = (Usuario) session.createQuery("from Usuario u where u.login=:loginName")
				.setParameter("loginName", login)
				.getSingleResult();
		return usuario;
	}

	@Override
	public boolean salvar(Object classe, Session session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excluir(Object object, Session session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List listar(Object object, Session session) {
		return session.createQuery("from "+object).getResultList();
	}

	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
