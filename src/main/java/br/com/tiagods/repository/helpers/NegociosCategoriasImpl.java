package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.negocio.NegocioCategoria;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioCategoriaDAO;

public class NegociosCategoriasImpl extends AbstractRepository<NegocioCategoria, Long> implements NegocioCategoriaDAO{
	public NegociosCategoriasImpl(EntityManager manager) {
		super(manager);
	}
}
