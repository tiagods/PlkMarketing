package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.repository.AbstractRepository;

public class NegocioPropostaImpl extends AbstractRepository<NegocioProposta, Long>{
	public NegocioPropostaImpl(EntityManager manager) {
		super(manager);
	}
}
