package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.negocio.Franquia;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class FranquiasImpl {

    @PersistenceContext
    EntityManager manager;

    private FranquiasImpl(){}

    public List<Franquia> filtrar(String nome, Franquia.Tipo tipo, Usuario atendente) {
        Criteria criteria = manager.unwrap(Session.class).createCriteria(Franquia.class);
        if(nome!=null && nome.trim().length()>0) {
            criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
        }
        if(!tipo.equals(Franquia.Tipo.TODOS)) {
            criteria.add(Restrictions.eq("tipo", tipo));
        }
//		if(atendente!=null && atendente.getId()!=-1L) {
//			criteria.add(Restrictions.eq("atendente", atendente));
//		}
        return criteria.list();
    }
}
