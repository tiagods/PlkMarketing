package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioOrigemDAO;

public class NegocioOrigensImpl extends AbstractRepository<NegocioOrigem, Long> implements NegocioOrigemDAO{
	public NegocioOrigensImpl(EntityManager manager) {
		super(manager);
	}
}
