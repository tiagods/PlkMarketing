package br.com.tiagods.repository;

import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.repository.helpers.filters.NegocioTarefaFilter;
import br.com.tiagods.repository.interfaces.Paginacao;
import javafx.util.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegociosTarefas extends JpaRepository<NegocioTarefa, Long> {
    long getQuantidade(NegocioTarefaFilter filter);
    Pair<List<NegocioTarefa>, Paginacao> filtrar(Paginacao paginacao, NegocioTarefaFilter filter);
}
