package br.com.tiagods.repository.helpers;

import javax.persistence.EntityManager;

import br.com.tiagods.model.negocio.ServicoAgregado;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioServicoAgregadoDAO;

public class NegocioServicoAgregadoImpl extends AbstractRepository<ServicoAgregado, Long> implements NegocioServicoAgregadoDAO{
	public NegocioServicoAgregadoImpl(EntityManager manager) {
		super(manager);
	}
}
