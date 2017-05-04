package br.com.tiagods.modeldao;

import java.util.List;

import org.hibernate.Session;

import br.com.tiagods.model.Usuario;
import br.com.tiagods.view.interfaces.InterfaceDao;

public class UsuarioDao implements InterfaceDao{
	public Usuario getLogin(String login, Session session){
		return (Usuario) session.createQuery("from Usuario u where u.login=:loginName")
				.setParameter("loginName", login)
				.getSingleResult();
	}
	@Override
	public boolean salvar(Object classe, Session session) {
		return false;
	}

	@Override
	public boolean excluir(Object object, Session session) {
		return false;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List listar(Class classe, Session session) {
		return session.createQuery("from "+classe.getSimpleName()).getResultList();
	}
	
	
	
	
}

