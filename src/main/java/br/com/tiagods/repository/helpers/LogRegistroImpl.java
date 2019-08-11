package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.LogRegistro;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.LogRegistroDAO;

import javax.persistence.EntityManager;

public class LogRegistroImpl extends AbstractRepository<LogRegistro,Long> implements LogRegistroDAO {

    public LogRegistroImpl(EntityManager manager) {
        super(manager);
    }


}
