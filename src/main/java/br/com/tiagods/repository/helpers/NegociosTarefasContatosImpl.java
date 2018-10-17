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
}
