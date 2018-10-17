package br.com.tiagods.repository.helpers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.model.Contato;
import br.com.tiagods.model.Contato.ContatoTipo;
import br.com.tiagods.model.Contato.PessoaTipo;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioLista;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.interfaces.ContatoDAO;
import javafx.util.Pair;

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
		if(nome.length()>0) criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		if(categoria!=null && categoria.getId()!=-1L) criteria.add(Restrictions.eq("categoria", categoria));
		if(nivel!=null && nivel.getId()!=-1L) criteria.add(Restrictions.eq("nivel", nivel));
		if(origem!=null && origem.getId()!=-1L) criteria.add(Restrictions.eq("origem", origem));
		if(servico!=null && servico.getId()!=-1L) criteria.add(Restrictions.eq("servico", servico));
		criteria.addOrder(Order.desc("criadoEm"));
		return criteria.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Pair<List<Contato>,Paginacao> filtrar(Paginacao pagination, PessoaTipo pessoaTipo, ContatoTipo contatoTipo, NegocioLista lista, NegocioCategoria categoria,
			NegocioNivel nivel, NegocioOrigem origem, NegocioServico servico, Usuario usuario, String malaDireta, LocalDate inicio, LocalDate fim, String nome) {
		
		List<Criterion> criterios = new ArrayList<>();
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(Contato.class);
		if(!pessoaTipo.equals(PessoaTipo.CONTATO)) criterios.add(Restrictions.eq("pessoaTipo", pessoaTipo));
		if(!contatoTipo.equals(ContatoTipo.CONTATO)) criterios.add(Restrictions.eq("contatoTipo", contatoTipo));
		//if(lista!=null && lista.getId()!=-1L) criteria.add(Restrictions.eq("lista", lista));
		if(categoria!=null && categoria.getId()!=-1L) criterios.add(Restrictions.eq("categoria", categoria));
		if(nivel!=null && nivel.getId()!=-1L) criterios.add(Restrictions.eq("nivel", nivel));
		if(origem!=null && origem.getId()!=-1L) criterios.add(Restrictions.eq("origem", origem));
		if(servico!=null && servico.getId()!=-1L) criterios.add(Restrictions.eq("servico", servico));
		if(usuario!=null && usuario.getId()!=-1L) criterios.add(Restrictions.eq("atendente", usuario));
		if(!malaDireta.equals("Mala Direta")) {
			String vl = "convite";
			if(malaDireta.equals("Material"))
				vl="material";
			else if(malaDireta.equals("Newsletter"))
				vl="newsletter";
			criterios.add(Restrictions.eq(vl, true));
		}
		if(inicio!=null && fim!=null) criterios.add(Restrictions.between("criadoEm", 
				GregorianCalendar.from(inicio.atStartOfDay(ZoneId.systemDefault())), 
				GregorianCalendar.from(fim.atStartOfDay(ZoneId.systemDefault()))));
		if(nome.length()>0) criterios.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE));
		criterios.forEach(c-> criteria.add(c));
		criteria.addOrder(Order.desc("criadoEm"));

		return super.filterWithPagination(pagination, criteria, criterios);
	}
	
}
