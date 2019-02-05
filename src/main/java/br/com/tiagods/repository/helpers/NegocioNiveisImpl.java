package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.negocio.NegocioNivel;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioNivelDAO;

public class NegocioNiveisImpl extends AbstractRepository<NegocioNivel,Long> implements NegocioNivelDAO{
	public NegocioNiveisImpl(EntityManager manager) {
		super(manager);
	}
}
