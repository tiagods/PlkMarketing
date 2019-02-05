package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.negocio.NegocioTarefaContato;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioTarefaContatoDAO;

public class NegociosTarefasContatosImpl extends AbstractRepository<NegocioTarefaContato, Long> implements NegocioTarefaContatoDAO {

	public NegociosTarefasContatosImpl(EntityManager manager) {
		super(manager);
	}
}
