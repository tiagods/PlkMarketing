package br.com.tiagods.modeldao;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import br.com.tiagods.factory.HibernateFactory;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.view.interfaces.InterfaceDao;

public class TarefaDao implements InterfaceDao{
	//Quantidade de tarefas de acordo com o usuario
	public int getQuantidade(Usuario usuario, Date dataInicio, Date dataFinal, Session session){
		if(session.getTransaction().getStatus()==TransactionStatus.NOT_ACTIVE)
			session.beginTransaction();
		String hql = "FROM Tarefa as t where t.dataEvento between "
				+ ":dataInicial and :dataFim "
				+ "and t.atendente = :atendente and t.finalizado=:andamento";
		return session.createQuery(hql)
				.setParameter("dataInicial", dataInicio)
				.setParameter("dataFim", dataFinal)
				.setParameter("andamento", 0)
				.setParameter("atendente", usuario).getResultList().size();
	}
	@Override
	public boolean salvar(Object classe,Session session) {
		try{
			session.saveOrUpdate(classe);
			session.getTransaction().commit();
			return true;
		}catch (Exception e) {
			session.getTransaction().rollback();
			return false;
		}
	}
	@Override
	public boolean excluir(Object object,Session session) {
		try{
			session.delete(object);
			session.getTransaction().commit();
			return true;
		}catch (Exception e) {
			e.getMessage();
			return false;
		}
	}
	@Override
	public List listar(Class classe, Session session) {
		return session.createQuery("from "+classe.getSimpleName()).getResultList();
	}
	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		return session.get(classe, id);
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Tarefa> filtrar(List<Criterion> criterios, Session session){
		Criteria criteria  = session.createCriteria(Tarefa.class).addOrder(Order.desc("dataEvento"));
		if(!criterios.isEmpty()){
			criterios.forEach(c->{
				criteria.add(c);
			});
		}
		return criteria.list();
	}
}
