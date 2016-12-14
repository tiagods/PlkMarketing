package br.com.tiagods.modelDAO;

import java.util.List;

import org.hibernate.Session;

public class NegocioDAO implements InterfaceDAO {

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
	public List listar(Class classe, Session session) {
		return session.createQuery("from "+classe.getSimpleName()).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		return session.get(classe, id);
	}

}
