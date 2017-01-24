package br.com.tiagods.modeldao;

import java.awt.Image;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import br.com.tiagods.view.MenuView;
import br.com.tiagods.view.SubmeterErroView;
import br.com.tiagods.view.interfaces.InterfaceDao;

public class GenericDao implements InterfaceDao{

	@Override
	public boolean salvar(Object classe, Session session) {
		try{
			session.saveOrUpdate(classe);
			session.getTransaction().commit();
			return true;
		}catch (Exception e) {
			
			return false;
		}
	}

	@Override
	public boolean excluir(Object object, Session session) {
		try{
			session.delete(object);
			session.getTransaction().commit();
			return true;
		}catch (HibernateException e) {
			session.getTransaction().rollback();
			//relatarErro(object.getClass().getSimpleName(),"Excluir",e.getMessage());
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
		if(!criterion.isEmpty()){
			for(Criterion c : criterion){
				criteria.add(c);
			}
		}
		criteria.addOrder(order);
		return criteria.list();
	}
	private void relatarErro(String classe, String tipo, String erro){
		CapturarTela cap = new CapturarTela();
		cap.gerarFoto();
		java.io.File file = cap.getFile();
		String message = classe+"\n"+tipo+"\n"+erro;
		new SubmeterErroView(message,file);
		
	}
}
