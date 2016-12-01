package br.com.tiagods.modeldao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class EmpresaDAO implements InterfaceDAO {

	@Override
	public boolean salvar(Object classe, Session session) {
		try{
			session.saveOrUpdate(classe);
			session.getTransaction().commit();
			return true;
		}catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return false;
	}

	@Override
	public boolean excluir(Object object, Session session) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List listar(Class classe, Session session) {
		return session.createQuery("from "+classe.getSimpleName()).getResultList();
	}

	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		return session.get(classe, id);
	}

}
