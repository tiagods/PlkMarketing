package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ImplantacaoProcessoDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ImplantacaoProcessosImpl extends AbstractRepository<ImplantacaoProcesso,Long> implements ImplantacaoProcessoDAO {
    public ImplantacaoProcessosImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public ImplantacaoProcesso findById(Long id) {
        Query query = getEntityManager().createQuery("from ImplantacaoProcesso as a "
                + "LEFT JOIN FETCH a.etapas "
                + "where a.id=:id");
        query.setParameter("id", id);
        return (ImplantacaoProcesso) query.getSingleResult();
    }
}
