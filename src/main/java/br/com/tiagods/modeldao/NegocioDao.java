package br.com.tiagods.modeldao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import br.com.tiagods.model.Negocio;
import br.com.tiagods.view.interfaces.InterfaceDao;

public class NegocioDao implements InterfaceDao {

	@Override
	public boolean salvar(Object classe, Session session) {
		try{
			session.saveOrUpdate(classe);
			session.getTransaction().commit();
			return true;
		}catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean excluir(Object object, Session session) {
		try{
			session.delete(object);
			session.getTransaction().commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
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
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Negocio> filtrar(List<Criterion> criterios, Session session){
		Criteria criteria = session.createCriteria(Negocio.class);
		criterios.forEach(c->{
			criteria.add(c);
		});
		return (List<Negocio>)criteria.list();
	}

}
