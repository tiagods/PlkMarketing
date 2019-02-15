package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ImplantacaoProcessoDAO;

import javax.persistence.EntityManager;

public class ImplantacaoProcessosImpl extends AbstractRepository<ImplantacaoProcesso,Long> implements ImplantacaoProcessoDAO {
    public ImplantacaoProcessosImpl(EntityManager manager) {
        super(manager);
    }
}
