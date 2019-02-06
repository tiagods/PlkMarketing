package br.com.tiagods.repository;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;

import br.com.tiagods.model.AbstractEntity;
import javafx.util.Pair;

public abstract class AbstractRepository<Entity extends AbstractEntity, PK extends Number> {
	private EntityManager em;
	private Class<Entity> entityClass;

	@SuppressWarnings("unchecked")
	public AbstractRepository(EntityManager manager) {
		this.em = manager;
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<Entity>) genericSuperclass.getActualTypeArguments()[0];
	}
	public Entity save(Entity e) {
		em.getTransaction().begin();
		
		if (e.getId() != null) 
			e = em.merge(e);
		else 
			em.persist(e);
		
		em.getTransaction().commit();
		return e;
	}
	public void saveAll(List<Entity> list){
		em.getTransaction().begin();
		for(Entity e : list) {
			if (e.getId() != null)
				em.merge(e);
			else
				em.persist(e);
		}
		em.getTransaction().commit();
	}
	public void remove(Entity e) {
		em.getTransaction().begin();
		em.remove(e);
		em.getTransaction().commit();
	}
	
	public Entity findById(PK id) {
		return em.find(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Entity> getAll() {
		Query query = getEntityManager().createQuery("FROM " + entityClass.getName() + " o order by o.id");
		return (List<Entity>) query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public Pair<List<Entity>,Paginacao> getAll(Paginacao page){
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(entityClass);
		return filterWithPagination(page, criteria, new ArrayList<>());
	}
	public long countResult(Criteria criteria, List<Criterion> criterios){
		criterios.forEach(c-> criteria.add(c));
		criteria.setProjection(Projections.rowCount());
		return (Long)criteria.uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public Pair<List<Entity>,Paginacao> filterWithPagination(Paginacao page, Criteria criteria, List<Criterion> criterios) {
		if(page!=null) {
			criteria.setFirstResult(page.getPrimeiroRegistro());
			criteria.setMaxResults(page.getLimitePorPagina());
		}
		List<Entity> firstPage = (List<Entity>)criteria.list();

		Criteria criteria2 = getEntityManager().unwrap(Session.class).createCriteria(entityClass);
		if(page!=null) page.setTotalRegistros(countResult(criteria2,criterios));
		return new Pair<>(firstPage, page);
	}
	@SuppressWarnings("unchecked")
	public Entity findByNome(String nome) {
		Query query = getEntityManager().createQuery("SELECT o FROM " + entityClass.getName() + " o where o.nome=:nome");
		query.setParameter("nome", nome);
		return (Entity) query.getSingleResult();
	}
	
	protected EntityManager getEntityManager() {
		return this.em;
	}
}
