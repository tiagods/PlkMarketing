package br.com.tiagods.model;

import java.util.Date;
import java.util.Set;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class TarefaDao {
	public Set<Tarefa> getList(){
//		HibernateFactory factory = new HibernateFactory();
//		Session session = factory.getSession();
//		Set<Tarefa> list = (Set<Tarefa>) session.createQuery("from Tarefa").getResultList();
//		factory.closeSession(session);
//		return list;
		return null;
	}
	public int getQuantidade(Usuario usuario){
//		HibernateFactory factory = new HibernateFactory();
//		Session session = factory.getSession();
//		String hql = "FROM Tarefa as c where c.DATA_INICIO "
//				+ "= :dayNow "
//				+ "and c.DATA_FIM = :dayNow "
//				+"and c.ATENDENTE = :atendente";
//		int quant = session.createQuery(hql)
//				.setParameter("dayNow", new Date()).setParameter("atendente", usuario.getId()).getMaxResults();
//		factory.closeSession(session);
//		return quant;
		return 0;
	}
}
