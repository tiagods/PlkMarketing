package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ImplantacaoProcessoDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
    @Override
    public List<ImplantacaoProcesso> listarAtivos(){
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(ImplantacaoProcesso.class);
        criteria.add(Restrictions.eq("finalizado",false));
        return criteria.list();
    }
}
