package br.com.tiagods.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.com.tiagods.factory.HibernateFactory;

public class TipoTarefaDao {

	public TipoTarefa getTipoTarefa(String tarefa){
		HibernateFactory factory = new HibernateFactory();
		Session session = factory.getSession();
		TipoTarefa tipoTarefa = (TipoTarefa)session.createQuery("from TipoTarefa t where t.nome=:tarefaNome")
				.setParameter("tarefaNome", tarefa)
				.getSingleResult();
		try{
			session.getTransaction().commit();
		}catch(HibernateException e){
			session.getTransaction().rollback();
		}finally{
			session.close();
		}
		return tipoTarefa;
		
	}
}
