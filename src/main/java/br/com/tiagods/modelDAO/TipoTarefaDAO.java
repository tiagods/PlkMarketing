package br.com.tiagods.modelDAO;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.Usuario;

public class TipoTarefaDAO implements InterfaceDAO{

	@Override
	public boolean salvar(Object classe,Session session) {
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
