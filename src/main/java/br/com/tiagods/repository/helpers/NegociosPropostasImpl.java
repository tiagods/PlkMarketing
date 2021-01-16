package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.negocio.NegocioProposta;
import br.com.tiagods.model.negocio.NegocioProposta.TipoEtapa;
import br.com.tiagods.model.negocio.NegocioProposta.TipoStatus;
import br.com.tiagods.repository.interfaces.AbstractRepositoryImpl;
import br.com.tiagods.repository.interfaces.Paginacao;
import br.com.tiagods.repository.helpers.filters.NegocioPropostaFilter;
import javafx.util.Pair;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class NegociosPropostasImpl {

	@PersistenceContext
	EntityManager manager;

	@Autowired
	AbstractRepositoryImpl abstractRepository;

	private NegociosPropostasImpl(){}

	public NegocioProposta findById(Long id) {
		Query query = manager.createQuery(
				"SELECT a FROM NegocioProposta as a "
                + "LEFT JOIN FETCH a.tarefas LEFT JOIN FETCH a.servicosContratados LEFT JOIN FETCH a.documentos "
                + "where a.id=:id");
        query.setParameter("id", id);
        return (NegocioProposta)query.getSingleResult();
	}

	public long count(NegocioPropostaFilter filter){
		List<Criterion> criterios = new ArrayList<>();
		Criteria criteria = filtrar(filter,criterios);
		criteria.setProjection(Projections.rowCount());
		return (long)criteria.uniqueResult();
	}

	public Pair<List<NegocioProposta>,Paginacao> filtrar(Paginacao paginacao, NegocioPropostaFilter filter) {
		List<Criterion> criterios = new ArrayList<>();
		Criteria criteria = filtrar(filter, criterios);
		Pair<List, Paginacao> pair = abstractRepository.filterWithPagination(NegocioProposta.class, paginacao, criteria, criterios);
		return new Pair<>(pair.getKey(), pair.getValue());
	}

	public Criteria filtrar(NegocioPropostaFilter f, List<Criterion> criterios){
		Criteria criteria = manager.unwrap(Session.class).createCriteria(NegocioProposta.class);
		if(!f.getStatus().equals(TipoStatus.STATUS)) criterios.add(Restrictions.eq("tipoStatus",f.getStatus()));
		if(!f.getEtapa().equals(TipoEtapa.ETAPA)) criterios.add(Restrictions.eq("tipoEtapa", f.getEtapa()));
		if(f.getCategoria()!=null && f.getCategoria().getId()!=-1L) criterios.add(Restrictions.eq("categoria",f.getCategoria()));
		if(f.getNivel()!=null && f.getNivel().getId()!=-1L) criterios.add(Restrictions.eq("nivel",f.getNivel()));
		if(f.getOrigem()!=null && f.getOrigem().getId()!=-1L) criterios.add(Restrictions.eq("origem",f.getOrigem()));
		if(f.getServico()!=null && f.getServico().getId()!=-1L) criterios.add(Restrictions.eq("servico", f.getServico()));
		if(f.getAtendente()!=null && f.getAtendente().getId()!=-1L) criterios.add(Restrictions.eq("atendente",f.getAtendente()));
		if(f.getDataFiltro()!=null && f.getDataInicial()!=null && f.getDataFinal()!=null) criterios.add(Restrictions.between(f.getDataFiltro(),
				GregorianCalendar.from(f.getDataInicial().atStartOfDay(ZoneId.systemDefault())), GregorianCalendar.from(f.getDataFinal().atStartOfDay(ZoneId.systemDefault()))));
		if(f.getPesquisa()!=null && f.getPesquisa().length()>0) {
			Criterion or1 = Restrictions.ilike("nome", f.getPesquisa(), MatchMode.ANYWHERE);
			try {
				Long value = Long.parseLong(f.getPesquisa());
				Criterion or2 = Restrictions.eq("id",value);
				Disjunction disjunction = Restrictions.disjunction();
				disjunction.add(or1);
				disjunction.add(or2);
				criterios.add(disjunction);
			}catch(Exception e) {
				criterios.add(or1);
			}
		}
		criterios.forEach(c->criteria.add(c));
		if(f.getOrdenacao()!=null) criteria.addOrder(Order.desc(f.getOrdenacao()));
		return criteria;
	}
}
