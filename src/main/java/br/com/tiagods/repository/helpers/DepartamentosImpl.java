package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.Departamento;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.UsuarioDepartamentoDAO;

import javax.persistence.EntityManager;

public class DepartamentosImpl extends AbstractRepository<Departamento,Long> implements UsuarioDepartamentoDAO {
    public DepartamentosImpl(EntityManager manager) {
        super(manager);
    }
}
