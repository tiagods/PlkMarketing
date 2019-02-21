package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.implantacao.ImplantacaoPacote;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ImplantacaoPacoteDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ImplantacaoPacotesImpl extends AbstractRepository<ImplantacaoPacote,Long> implements ImplantacaoPacoteDAO {

    public ImplantacaoPacotesImpl(EntityManager manager) {
        super(manager);
    }

    @Override
    public ImplantacaoPacote findById(Long id) {
        Query query = getEntityManager().createQuery("from ImplantacaoPacote as a "
                + "LEFT JOIN FETCH a.etapas "
                + "where a.id=:id");
        query.setParameter("id", id);
        return (ImplantacaoPacote)query.getSingleResult();
    }

    @Override
    public ImplantacaoPacote findByNome(String nome) {
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(ImplantacaoPacote.class);
        criteria.add(Restrictions.ilike("nome", nome));
        return (ImplantacaoPacote) criteria.uniqueResult();
    }
}
