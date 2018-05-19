package br.com.tiagods.repository.helpers;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.model.Cidade;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.CidadeDAO;

public class CidadesImpl extends AbstractRepository<Cidade, Long> implements CidadeDAO {
	public CidadesImpl(EntityManager manager) {
		super(manager);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Cidade> findByEstado(Cidade.Estado estado) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Cidade.class);
		criteria.add(Restrictions.eq("estado", estado));
		return (List<Cidade>)criteria.list();
	}
}
