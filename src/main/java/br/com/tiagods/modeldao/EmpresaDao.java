package br.com.tiagods.modeldao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.annotations.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.model.Pessoa;
import br.com.tiagods.view.interfaces.InterfaceDao;

public class EmpresaDao implements InterfaceDao {

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
		try{
			session.delete(object);
			session.getTransaction().commit();
			return true;
		}catch (HibernateException e) {
			session.getTransaction().rollback();
		}
		return false;
	}

	@Override
	public List listar(Class classe, Session session) {
		return session.createQuery("from "+classe.getSimpleName()+" e order by e.id ").getResultList();
	}

	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		return session.get(classe, id);
	}
	
}
