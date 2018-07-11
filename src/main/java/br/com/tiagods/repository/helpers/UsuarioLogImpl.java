package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.UsuarioLog;
import br.com.tiagods.repository.AbstractRepository;

public class UsuarioLogImpl extends AbstractRepository<UsuarioLog, Long>{

	public UsuarioLogImpl(EntityManager manager) {
		super(manager);
	}

	
}
