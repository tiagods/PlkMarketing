package br.com.tiagods.model;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class TarefaDao {
	//listar todas as tarefas
	@SuppressWarnings("unchecked")
	public List<Tarefa> getList(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		List<Tarefa> list = (List<Tarefa>) session.createQuery("from Tarefa")
				.getResultList();
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return list;
	}
	//Quantidade de tarefas de acordo com o usuario
	public int getQuantidade(Usuario usuario, Date dataInicio, Date dataFinal){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		String hql = "FROM Tarefa as t where t.dataEvento between "
				+ ":dataInicial and :dataFim "
				+ "and t.atendente = :atendente";
		int quant = session.createQuery(hql)
				.setParameter("dataInicial", dataInicio)
				.setParameter("dataFim", dataFinal)
				.setParameter("atendente", usuario).getResultList().size();
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return quant;
	}
	//salvar tarefa
	public boolean salvarTarefa(Usuario usuario, Tarefa tarefa){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		try{
			session.saveOrUpdate(tarefa);
			session.getTransaction().commit();
		return true;
		}catch(HibernateException e){
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}finally{
			session.close();
		}
	}
}
