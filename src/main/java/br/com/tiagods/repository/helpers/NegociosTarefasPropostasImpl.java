package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.NegocioTarefaProposta;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioTarefaPropostaDAO;

public class NegociosTarefasPropostasImpl extends AbstractRepository<NegocioTarefaProposta, Long> implements NegocioTarefaPropostaDAO{

	public NegociosTarefasPropostasImpl(EntityManager manager) {
		super(manager);
	}	
}
