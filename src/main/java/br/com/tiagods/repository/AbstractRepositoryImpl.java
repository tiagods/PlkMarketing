package br.com.tiagods.repository;

import br.com.tiagods.repository.interfaces.Paginacao;
import javafx.util.Pair;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class AbstractRepositoryImpl {

    @PersistenceContext
    EntityManager manager;

    public Session getSession() {
        return manager
                .getEntityManagerFactory()
                .createEntityManager()
                .unwrap(Session.class);
    }

    public Pair<List, Paginacao> filterWithPagination(Class<?> entityClass, Paginacao page, Criteria criteria, List<Criterion> criterios) {
        if(page!=null) {
            criteria.setFirstResult(page.getPrimeiroRegistro());
            criteria.setMaxResults(page.getLimitePorPagina());
        }
        List firstPage = criteria.list();
        Criteria criteria2 = getSession()
                .createCriteria(entityClass);
        if(page!=null) page.setTotalRegistros(countResult(criteria2,criterios));
        return new Pair<>(firstPage, page);
    }

    public long countResult(Criteria criteria, List<Criterion> criterios){
        criterios.forEach(c-> criteria.add(c));
        criteria.setProjection(Projections.rowCount());
        return (Long)criteria.uniqueResult();
    }
}
