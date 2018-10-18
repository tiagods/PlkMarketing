package br.com.tiagods.repository.helpers;

import java.time.ZoneId;
import java.util.*;

import javax.persistence.EntityManager;

import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.repository.helpers.filters.NegocioPropostaFilter;
import br.com.tiagods.repository.helpers.filters.NegocioTarefaFilter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.AbstractRepository;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.interfaces.NegocioTarefaDAO;
import javafx.util.Pair;

public class NegociosTarefasImpl extends AbstractRepository<NegocioTarefa, Long> implements NegocioTarefaDAO {

	public NegociosTarefasImpl(EntityManager manager) {
		super(manager);
	}	

	@Override
	public long getQuantidade(NegocioTarefaFilter filter) {
		List<Criterion> criterios = new ArrayList<>();
		Criteria criteria = filtrar(filter,criterios);
		criteria.setProjection(Projections.rowCount());
		return (long)criteria.uniqueResult();
	}
	@Override
	public Pair<List<NegocioTarefa>, Paginacao> filtrar(Paginacao paginacao, NegocioTarefaFilter filter) {
		List<Criterion> criterios = new ArrayList<>();
		Criteria criteria = filtrar(filter, criterios);
		return super.filterWithPagination(paginacao, criteria, criterios);
	}


	@Override
	public Criteria filtrar(NegocioTarefaFilter f, List<Criterion> criterios){
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(NegocioTarefa.class);
		if(f.getAtendente()!=null && f.getAtendente().getId()!=-1L) criterios.add(Restrictions.eq("atendente", f.getAtendente()));
		if(f.getDataEventoInicial()!=null && f.getDataEventoFinal()!=null) criterios.add(Restrictions.between("dataEvento", f.getDataEventoInicial(), f.getDataEventoFinal()));
		if(f.getFinalizado()!=-1) criterios.add(Restrictions.eq("finalizado", f.getFinalizado()));
		if(!f.getTipoTarefas().isEmpty()) criterios.add(Restrictions.in("tipoTarefa", f.getTipoTarefas().toArray()));
		criterios.forEach(c->criteria.add(c));
		return criteria;
	}
}
