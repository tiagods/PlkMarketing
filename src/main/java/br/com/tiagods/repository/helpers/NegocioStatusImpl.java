package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.modelcollections.NegocioStatus;
import br.com.tiagods.repository.AbstractRepository;

public class NegocioStatusImpl extends AbstractRepository<NegocioStatus, Long>{
	public NegocioStatusImpl(EntityManager manager) {
		super(manager);
	}
}
