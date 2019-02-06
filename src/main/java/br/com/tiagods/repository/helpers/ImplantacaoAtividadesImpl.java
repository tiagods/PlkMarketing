package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ImplantacaoAtividadeDAO;

import javax.persistence.EntityManager;

public class ImplantacaoAtividadesImpl extends AbstractRepository<ImplantacaoAtividade,Long> implements ImplantacaoAtividadeDAO {
    public ImplantacaoAtividadesImpl(EntityManager manager) {
        super(manager);
    }
}
