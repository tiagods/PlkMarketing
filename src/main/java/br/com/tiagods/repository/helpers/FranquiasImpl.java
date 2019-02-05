package br.com.tiagods.repository.helpers;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.model.negocio.Franquia;
import br.com.tiagods.model.negocio.Franquia.Tipo;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.FranquiaDAO;

public class FranquiasImpl extends AbstractRepository<Franquia, Long> implements FranquiaDAO{

	public FranquiasImpl(EntityManager manager) {
		super(manager);
	}

	@Override
	public List<Franquia> filtrar(String nome, Tipo tipo, Usuario atendente) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Franquia.class);
		if(nome!=null && nome.trim().length()>0) {
			criteria.add(Restrictions.ilike("nome", nome,MatchMode.ANYWHERE));
		}
		if(!tipo.equals(Tipo.TODOS)) {
			criteria.add(Restrictions.eq("tipo", tipo));
		}
//		if(atendente!=null && atendente.getId()!=-1L) {
//			criteria.add(Restrictions.eq("atendente", atendente));
//		}
		return criteria.list();
	}

}
