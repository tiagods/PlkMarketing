package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.negocio.NegocioCategoria;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioCategoriaDAO;

public class NegocioCategoriasImpl extends AbstractRepository<NegocioCategoria, Long> implements NegocioCategoriaDAO{
	public NegocioCategoriasImpl(EntityManager manager) {
		super(manager);
	}
}
