package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.Categoria;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.CategoriaDAO;

public class CategoriasImpl extends AbstractRepository<Categoria, Long> implements CategoriaDAO{
	public CategoriasImpl(EntityManager manager) {
		super(manager);
	}
}
