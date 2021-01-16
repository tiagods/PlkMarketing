package br.com.tiagods.repository.helpers;

import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.repository.interfaces.AbstractRepositoryImpl;
import br.com.tiagods.repository.interfaces.Paginacao;
import br.com.tiagods.repository.helpers.filters.NegocioTarefaFilter;
import javafx.util.Pair;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class NegociosTarefasImpl {

	@PersistenceContext
	EntityManager manager;
	@Autowired
	AbstractRepositoryImpl abstractRepository;

	private NegociosTarefasImpl(){}

	public long getQuantidade(NegocioTarefaFilter filter) {
		List<Criterion> criterios = new ArrayList<>();
		Criteria criteria = filtrar(filter,criterios);
		criteria.setProjection(Projections.rowCount());
		return (long)criteria.uniqueResult();
	}

	public Pair<List<NegocioTarefa>, Paginacao> filtrar(Paginacao paginacao, NegocioTarefaFilter filter) {
		List<Criterion> criterios = new ArrayList<>();
		Criteria criteria = filtrar(filter, criterios);
		Pair<List, Paginacao> pair = abstractRepository.filterWithPagination(NegocioTarefa.class, paginacao, criteria, criterios);
		return new Pair<>((List<NegocioTarefa>)pair.getKey(),pair.getValue());
	}

	public Criteria filtrar(NegocioTarefaFilter f, List<Criterion> criterios){
		Criteria criteria = manager.unwrap(Session.class).createCriteria(NegocioTarefa.class);
		if(f.getAtendente()!=null && f.getAtendente().getId()!=-1L) criterios.add(Restrictions.eq("atendente", f.getAtendente()));
		if(f.getDataEventoInicial()!=null && f.getDataEventoFinal()!=null) criterios.add(Restrictions.between("dataEvento", f.getDataEventoInicial(), f.getDataEventoFinal()));
		if(f.getFinalizado()!=-1) criterios.add(Restrictions.eq("finalizado", f.getFinalizado()));
		if(!f.getTipoTarefas().isEmpty()) criterios.add(Restrictions.in("tipoTarefa", f.getTipoTarefas().toArray()));
		criterios.forEach(c->criteria.add(c));
		return criteria;
	}
}
