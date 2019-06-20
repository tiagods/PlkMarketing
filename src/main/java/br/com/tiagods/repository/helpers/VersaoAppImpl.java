package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.VersaoApp;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.VersaoAppDAO;

import javax.persistence.EntityManager;

public class VersaoAppImpl extends AbstractRepository<VersaoApp, Long> implements VersaoAppDAO {
    public VersaoAppImpl(EntityManager manager) {
        super(manager);
    }


}
