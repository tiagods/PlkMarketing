package br.com.tiagods.repository.interfaces;

import java.time.LocalDate;
import java.util.List;

import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.modelcollections.NegocioProposta.TipoEtapa;
import br.com.tiagods.modelcollections.NegocioProposta.TipoStatus;
import br.com.tiagods.repository.Paginacao;
import javafx.util.Pair;

public interface NegocioPropostaDAO {
	NegocioProposta save(NegocioProposta e);
    void remove(NegocioProposta e);
    List<NegocioProposta> getAll();
    Pair<List<NegocioProposta>,Paginacao> getAll(Paginacao page);
    NegocioProposta findById(Long id);
	Pair<List<NegocioProposta>, Paginacao> filtrar(Paginacao Paginacao,TipoStatus status, TipoEtapa etapa, NegocioCategoria categoria, NegocioNivel nivel,
			NegocioOrigem origem, NegocioServico servico, Usuario atendente, LocalDate dataInicial, LocalDate dataFinal,
			String dataFiltro, String ordenacao, String pesquisa);
	
}
