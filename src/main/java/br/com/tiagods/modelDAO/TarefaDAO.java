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
		int quant = session.createQuery(hql)
				.setParameter("dataInicial", dataInicio)
				.setParameter("dataFim", dataFinal)
				.setParameter("atendente", usuario).getResultList().size();
		return quant;
	}
	@Override
	public boolean salvar(Object classe,Session session) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean excluir(Session session, int id) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<Tarefa> listar(String classe, Session session) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object receberObjeto(Class classe, int id, Session session) {
		// TODO Auto-generated method stub
		return null;
	}
}
