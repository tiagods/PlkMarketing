package br.com.tiagods.model;

import java.util.Date;
import java.util.Set;

import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class TarefaDao {
	public Set<Tarefa> getList(){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		Set<Tarefa> list = (Set<Tarefa>) session.createQuery("from Tarefa").getResultList();
		factory.closeSession(session);
		return list;
	}
	public int getQuantidade(Usuario usuario, Date dataInicio, Date dataFinal){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		String hql = "FROM Tarefa as t where t.dataEvento between "
				+ ":dataInicial "
				+ "and :dataFim "
				+ "and t.atendente = :atendente";
		int quant = session.createQuery(hql)
				.setParameter("dataInicial", dataInicio)
				.setParameter("dataFim", dataFinal)
				.setParameter("atendente", usuario.getId()).getMaxResults();
		factory.closeSession(session);
		return quant;
	}
	public void cadastrarTarefa(Usuario usuario){
		
	}
}
