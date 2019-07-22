package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapaStatus;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ImplantacaoProcessoEtapaStatusDAO;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class ImplantacaoProcessoEtapaStatusImpl extends AbstractRepository<ImplantacaoProcessoEtapaStatus,Long> implements ImplantacaoProcessoEtapaStatusDAO {

    public ImplantacaoProcessoEtapaStatusImpl(EntityManager manager) {
        super(manager);
    }
}
