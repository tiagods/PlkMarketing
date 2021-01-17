package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.repository.AbstractRepositoryImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class ImplantacaoProcessosImpl  {

    @PersistenceContext
    EntityManager manager;
    @Autowired
    AbstractRepositoryImpl abstractRepository;

    public Optional<ImplantacaoProcesso> findById(Long id) {
        Query query = manager.createQuery(
                "SELECT a FROM ImplantacaoProcesso as a "+
                        "LEFT JOIN FETCH a.etapas "+
                        "where a.id=:id");
        query.setParameter("id", id);
        return Optional.ofNullable((ImplantacaoProcesso) query.getSingleResult());
    }

    public List<ImplantacaoProcesso> listarAtivos(boolean finalizado){
        Criteria criteria = abstractRepository
                .getSession()
                .createCriteria(ImplantacaoProcesso.class);
        if(!finalizado)
            criteria.add(Restrictions.eq("finalizado",false));
        return criteria.list();
    }
}
