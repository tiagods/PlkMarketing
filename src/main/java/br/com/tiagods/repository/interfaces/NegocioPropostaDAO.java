package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.NegocioProposta;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.filters.NegocioPropostaFilter;
import javafx.util.Pair;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

public interface NegocioPropostaDAO {
	NegocioProposta save(NegocioProposta e);
    void remove(NegocioProposta e);
    List<NegocioProposta> getAll();
    Pair<List<NegocioProposta>,Paginacao> getAll(Paginacao page);
    NegocioProposta findById(Long id);

    long count(NegocioPropostaFilter filter);

    Pair<List<NegocioProposta>, Paginacao> filtrar(Paginacao paginacao, NegocioPropostaFilter f);
    Criteria filtrar(NegocioPropostaFilter f, List<Criterion> criterios);
}
