package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.UsuarioDepartamento;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.UsuarioDepartamentoDAO;

import javax.persistence.EntityManager;
import java.util.List;

public class UsuariosDepartamentosImpl extends AbstractRepository<UsuarioDepartamento,Long> implements UsuarioDepartamentoDAO {
    public UsuariosDepartamentosImpl(EntityManager manager) {
        super(manager);
    }
}
