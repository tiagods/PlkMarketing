package br.com.tiagods.modelDAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import br.com.tiagods.model.Pessoa;

public class PessoaDAO implements InterfaceDAO {

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
			session.delete((Pessoa)object);
			session.getTransaction().commit();
			return true;
		}catch (HibernateException e) {
			session.getTransaction().rollback();
		}
		return false;
	}

	@Override
	public List listar(Object object, Session session) {
		return session.createQuery("from "+object).getResultList();
	}

	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		Object o = session.get(classe, id);
		return o;
	}

}
