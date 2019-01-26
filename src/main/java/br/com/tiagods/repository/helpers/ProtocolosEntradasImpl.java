package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.ProtocoloEntrada;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ProtocoloEntradaDAO;

import javax.persistence.EntityManager;

public class ProtocolosEntradasImpl extends AbstractRepository<ProtocoloEntrada,Long> implements ProtocoloEntradaDAO {
    public ProtocolosEntradasImpl(EntityManager manager) {
        super(manager);
    }
}
