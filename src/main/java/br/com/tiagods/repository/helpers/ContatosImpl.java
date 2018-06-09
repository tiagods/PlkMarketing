package br.com.tiagods.repository.helpers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.model.Contato;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.ContatoDAO;

public class ContatosImpl extends AbstractRepository<Contato, Long> implements ContatoDAO{
	
	
	public ContatosImpl(EntityManager manager) {
		super(manager);
	}
	
	@Override
	public Contato findById(Long id) {
		Query query = getEntityManager().createQuery("from Contato as a "
	                + "LEFT JOIN FETCH a.tarefas LEFT JOIN FETCH a.negocios LEFT JOIN FETCH a.listas "
	                + "where a.id=:id");
	        query.setParameter("id", id);
	        Contato a = (Contato)query.getSingleResult();
		return a;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Contato> filtrar(String nome, NegocioCategoria categoria, NegocioNivel nivel, NegocioOrigem origem,NegocioServico servico) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Contato.class);
		if(nome.equals("")) {
			
		}
		if(categoria!=null && categoria.getId()!=-1L) criteria.add(Restrictions.eq("categoria", categoria));
		if(nivel!=null && nivel.getId()!=-1L) criteria.add(Restrictions.eq("nivel", nivel));
		if(origem!=null && origem.getId()!=-1L) criteria.add(Restrictions.eq("origem", origem));
		if(servico!=null && servico.getId()!=-1L) criteria.add(Restrictions.eq("servico", servico));
		return criteria.list();
	}
	
}
