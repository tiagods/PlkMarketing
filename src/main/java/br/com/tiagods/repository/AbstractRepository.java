package br.com.tiagods.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.tiagods.model.AbstractEntity;

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
	public Entity findByNome(String nome) {
		Query query = getEntityManager().createQuery("SELECT o FROM " + entityClass.getName() + " o where o.nome=:nome");
		query.setParameter("nome", nome);
		return (Entity) query.getSingleResult();
	}
	
	protected EntityManager getEntityManager() {
		return this.em;
	}
}
