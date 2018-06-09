package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.NegocioMalaDireta;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioMalaDiretaDAO;

public class NegociosMalaDiretaImpl extends AbstractRepository<NegocioMalaDireta, Long> implements NegocioMalaDiretaDAO{

	public NegociosMalaDiretaImpl(EntityManager manager) {
		super(manager);
	}

}
