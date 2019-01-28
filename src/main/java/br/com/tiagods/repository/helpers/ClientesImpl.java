package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.Cliente;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ClienteDAO;

import javax.persistence.EntityManager;

public class ClientesImpl extends AbstractRepository<Cliente,Long> implements ClienteDAO {

    public ClientesImpl(EntityManager manager) {
        super(manager);
    }
}
