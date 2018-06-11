package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.NegocioLista;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioListaDAO;

public class NegociosListasImpl extends AbstractRepository<NegocioLista, Long> implements NegocioListaDAO{

	public NegociosListasImpl(EntityManager manager) {
		super(manager);
	}
	
}
