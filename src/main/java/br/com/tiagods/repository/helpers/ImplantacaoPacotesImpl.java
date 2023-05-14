package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.implantacao.ImplantacaoPacote;
import br.com.tiagods.repository.AbstractRepositoryImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Service
public class ImplantacaoPacotesImpl {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    AbstractRepositoryImpl abstractRepository;

    public Optional<ImplantacaoPacote> findById(Long id) {
        Query query = manager.createQuery(
                "SELECT a FROM ImplantacaoPacote as a " +
                        "LEFT JOIN FETCH a.etapas " +
                        "WHERE a.id=:id");
        query.setParameter("id", id);
        return Optional.ofNullable((ImplantacaoPacote)query.getSingleResult());
    }

    public Optional<ImplantacaoPacote> findByNome(String nome) {
        Criteria criteria = abstractRepository
                .getSession()
                .createCriteria(ImplantacaoPacote.class);
        criteria.add(Restrictions.ilike("nome", nome));
        return Optional.ofNullable((ImplantacaoPacote) criteria.uniqueResult());
    }
}
