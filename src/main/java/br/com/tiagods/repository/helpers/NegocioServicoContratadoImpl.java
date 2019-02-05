package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.negocio.ServicoContratado;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioServicoContratadoDAO;

public class NegocioServicoContratadoImpl extends AbstractRepository<ServicoContratado, Long> implements NegocioServicoContratadoDAO{
	public NegocioServicoContratadoImpl(EntityManager manager) {
		super(manager);
	}

}
