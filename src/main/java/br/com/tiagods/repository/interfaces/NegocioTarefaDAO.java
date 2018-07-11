package br.com.tiagods.repository.interfaces;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.Paginacao;
import javafx.util.Pair;

public interface NegocioTarefaDAO {
	NegocioTarefa save(NegocioTarefa e);
    void remove(NegocioTarefa e);
    List<NegocioTarefa> getAll();
    NegocioTarefa findById(Long id);
	int getQuantidade(Usuario usuario, Calendar dataInicio, Calendar dataFinal, int status);
	Pair<List<NegocioTarefa>, Paginacao> filtrar(Paginacao paginacao, int finalizado, Usuario usuario,
			Calendar dataEventoInicial, Calendar dataEventoFinal, Set<TipoTarefa> tipoTarefas);
}
