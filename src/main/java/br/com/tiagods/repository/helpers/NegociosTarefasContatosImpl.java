package br.com.tiagods.repository.helpers;

import java.util.Calendar;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.model.NegocioTarefaContato;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioTarefaContatoDAO;

public class NegociosTarefasContatosImpl extends AbstractRepository<NegocioTarefaContato, Long> implements NegocioTarefaContatoDAO {

	public NegociosTarefasContatosImpl(EntityManager manager) {
		super(manager);
	}	
	//Quantidade de tarefas de acordo com o usuario
	public int getQuantidade(Usuario usuario, Calendar dataInicio, Calendar dataFinal, int status) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(NegocioTarefaContato.class);
		if(usuario!=null) criteria.add(Restrictions.eq("atendente", usuario));
		if(dataInicio!=null && dataFinal!=null) criteria.add(Restrictions.between("dataEvento", dataInicio, dataFinal));
		if(status!=-1) criteria.add(Restrictions.eq("finalizado", status));
		return criteria.list().size();
	}
}
