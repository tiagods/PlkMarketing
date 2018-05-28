package br.com.tiagods.repository.helpers;

import java.util.List;

import javax.persistence.EntityManager;

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
