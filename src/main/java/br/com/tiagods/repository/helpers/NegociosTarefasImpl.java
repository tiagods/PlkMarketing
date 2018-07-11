package br.com.tiagods.repository.helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

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
	//Quantidade de tarefas de acordo com o usuario
	public int getQuantidade(Usuario usuario, Calendar dataInicio, Calendar dataFinal, int status) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(NegocioTarefa.class);
		if(usuario!=null) criteria.add(Restrictions.eq("atendente", usuario));
		if(dataInicio!=null && dataFinal!=null) criteria.add(Restrictions.between("dataEvento", dataInicio, dataFinal));
		if(status!=-1) criteria.add(Restrictions.eq("finalizado", status));
		criteria.setMaxResults(100);
		return criteria.list().size();
	}
	@Override
	public Pair<List<NegocioTarefa>, Paginacao> filtrar(Paginacao paginacao, int finalizado, Usuario usuario, Calendar dataEventoInicial, Calendar dataEventoFinal, Set<TipoTarefa> tipoTarefas) {
		Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(NegocioTarefa.class);
		
		List<Criterion> criterios = new ArrayList<>();
		if(usuario!=null && usuario.getId()!=-1L) criterios.add(Restrictions.eq("atendente", usuario));
		if(dataEventoInicial!=null && dataEventoFinal!=null) criterios.add(Restrictions.between("dataEvento", dataEventoInicial, dataEventoFinal));
		if(finalizado!=-1) criterios.add(Restrictions.eq("finalizado", finalizado));
		if(!tipoTarefas.isEmpty()) criterios.add(Restrictions.in("tipoTarefa", tipoTarefas.toArray()));
		
		criterios.forEach(c->criteria.add(c));
		
		return super.filterPagination(paginacao, criteria, criterios);
	}
}
