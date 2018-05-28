package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioServicoDAO;

public class NegocioServicosImpl extends AbstractRepository<NegocioServico, Long> implements NegocioServicoDAO{

	public NegocioServicosImpl(EntityManager manager) {
		super(manager);
	}
	
}
