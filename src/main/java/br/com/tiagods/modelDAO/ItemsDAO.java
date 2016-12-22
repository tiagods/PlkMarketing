package br.com.tiagods.modelDAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public class ItemsDAO {
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public List items(Class classe, Session session, Criterion criterion, Order order){
		return session.createCriteria(classe).add(criterion).addOrder(order).list();
	}
}
