package br.com.tiagods.repository.interfaces;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.model.Usuario;

public interface NegocioTarefaDAO {
	NegocioTarefa save(NegocioTarefa e);
    void remove(NegocioTarefa e);
    List<NegocioTarefa> getAll();
    NegocioTarefa findById(Long id);
	int getQuantidade(Usuario usuario, Calendar dataInicio, Calendar dataFinal, int status);
	List<NegocioTarefa> filtrar(int aberto, Usuario usuario, Calendar dataEventoInicial, Calendar dataEventoFinal,
			Set<TipoTarefa> tipoTarefas);
}
