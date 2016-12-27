package br.com.tiagods.modelDAO;

import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class GenericDAO implements InterfaceDAO{

	@Override
	public boolean salvar(Object classe, Session session) {
		try{
			session.saveOrUpdate(classe);
			session.getTransaction().commit();
			
			return true;
		}catch (Exception e) {
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, "Não foi salvo!\n"+e.getMessage());
			return false;
		}
	}

	@Override
	public boolean excluir(Object object, Session session) {
		try{
			session.delete(object);
			session.getTransaction().commit();
			JOptionPane.showMessageDialog(br.com.tiagods.view.MenuView.jDBody, "Excluido com sucesso!");
			return true;
		}catch (HibernateException e) {
			session.getTransaction().rollback();
		}
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
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public List items(Class classe, Session session, List<Criterion> criterion, Order order){
		Criteria criteria = session.createCriteria(classe);
		for(Criterion c : criterion){
			criteria.add(c);
		}
		criteria.addOrder(order);
		return criteria.list();
	}
}
