package br.com.tiagods.repository.helpers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.modelcollections.NegocioProposta.TipoEtapa;
import br.com.tiagods.modelcollections.NegocioProposta.TipoStatus;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.interfaces.NegocioPropostaDAO;


public class NegocioPropostaImpl extends AbstractRepository<NegocioProposta, Long> implements NegocioPropostaDAO{
	public NegocioPropostaImpl(EntityManager manager) {
		super(manager);
	}
	@Override
	public NegocioProposta findById(Long id) {
		Query query = getEntityManager().createQuery("from NegocioProposta as a "
                + "LEFT JOIN FETCH a.tarefas LEFT JOIN FETCH a.servicosContratados LEFT JOIN FETCH a.documentos "
                + "where a.id=:id");
        query.setParameter("id", id);
        return (NegocioProposta)query.getSingleResult();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<NegocioProposta> filtrar(TipoStatus status, TipoEtapa etapa, NegocioCategoria categoria, NegocioNivel nivel,
			NegocioOrigem origem, NegocioServico servico, Usuario atendente, LocalDate dataInicial, LocalDate dataFinal,
			String dataFiltro, String ordenacao, String pesquisa) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(NegocioProposta.class);
		if(!status.equals(TipoStatus.STATUS)) criteria.add(Restrictions.eq("tipoStatus",status));
		if(!etapa.equals(TipoEtapa.ETAPA)) criteria.add(Restrictions.eq("tipoEtapa", etapa));
		if(categoria!=null && categoria.getId()!=-1L) criteria.add(Restrictions.eq("categoria",categoria));
		if(nivel!=null && nivel.getId()!=-1L) criteria.add(Restrictions.eq("nivel",nivel));
		if(origem!=null && origem.getId()!=-1L) criteria.add(Restrictions.eq("origem",origem));
		if(servico!=null && servico.getId()!=-1L) criteria.add(Restrictions.eq("servico", servico));
		if(atendente!=null && atendente.getId()!=-1L) criteria.add(Restrictions.eq("atendente",atendente));
		if(dataInicial!=null && dataFinal!=null) criteria.add(Restrictions.between(dataFiltro, 
				GregorianCalendar.from(dataInicial.atStartOfDay(ZoneId.systemDefault())), GregorianCalendar.from(dataFinal.atStartOfDay(ZoneId.systemDefault()))));
		if(pesquisa.length()>0) {
			Criterion or1 = Restrictions.ilike("nome", pesquisa, MatchMode.ANYWHERE);
			try {
				Long value = Long.parseLong(pesquisa);
				Criterion or2 = Restrictions.eq("id",value);
				Disjunction disjunction = Restrictions.disjunction();
				disjunction.add(or1);
				disjunction.add(or2);
				criteria.add(disjunction);
			}catch(Exception e) {
				criteria.add(or1);
			}
		}
		criteria.addOrder(Order.desc(ordenacao));
		return criteria.list();
	}
}
