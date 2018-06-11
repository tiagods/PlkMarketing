package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.NegocioLista;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.repository.Paginacao;
import javafx.util.Pair;

public interface NegocioPropostaDAO {
	NegocioProposta save(NegocioProposta e);
    void remover(NegocioProposta e);
    List<NegocioProposta> getAll();
    Pair<List<NegocioProposta>,Paginacao> getAll(Paginacao page);
    NegocioLista findById(Long id);
}
