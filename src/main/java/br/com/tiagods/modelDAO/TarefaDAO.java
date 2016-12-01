package br.com.tiagods.modelDAO;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.Usuario;

public class TarefaDAO implements InterfaceDAO{
	//Quantidade de tarefas de acordo com o usuario
	public int getQuantidade(Usuario usuario, Date dataInicio, Date dataFinal, Session session){
		if(session.getTransaction().getStatus()==TransactionStatus.NOT_ACTIVE)
			session.beginTransaction();
		String hql = "FROM Tarefa as t where t.dataEvento between "
				+ ":dataInicial and :dataFim "
				+ "and t.atendente = :atendente";
		return session.createQuery(hql)
				.setParameter("dataInicial", dataInicio)
				.setParameter("dataFim", dataFinal)
				.setParameter("atendente", usuario).getResultList().size();
	}
	@Override
	public boolean salvar(Object classe,Session session) {
		try{
			session.save(classe);
			session.getTransaction().commit();
			return true;
		}catch (Exception e) {
			session.getTransaction().rollback();
		}
		return false;
	}
	@Override
	public boolean excluir(Object object,Session session) {
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
}
