package br.com.tiagods.repository.interfaces;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.filters.NegocioTarefaFilter;
import javafx.util.Pair;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

public interface NegocioTarefaDAO {
	NegocioTarefa save(NegocioTarefa e);
    void remove(NegocioTarefa e);
    List<NegocioTarefa> getAll();
    NegocioTarefa findById(Long id);
	long getQuantidade(NegocioTarefaFilter filter);
    Pair<List<NegocioTarefa>, Paginacao> filtrar(Paginacao paginacao, NegocioTarefaFilter filter);
    Criteria filtrar(NegocioTarefaFilter f, List<Criterion> criterios);
}
