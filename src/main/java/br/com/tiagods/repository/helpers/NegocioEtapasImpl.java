package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.modelcollections.NegocioEtapa;
import br.com.tiagods.repository.AbstractRepository;

public class NegocioEtapasImpl extends AbstractRepository<NegocioEtapa, Long>{
	public NegocioEtapasImpl(EntityManager manager) {
		super(manager);
	}
}
