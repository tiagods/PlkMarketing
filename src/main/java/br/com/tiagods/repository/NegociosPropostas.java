package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioProposta;
import br.com.tiagods.repository.filters.NegocioPropostaFilter;
import br.com.tiagods.repository.interfaces.Paginacao;
import javafx.util.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegociosPropostas extends JpaRepository<NegocioProposta, Long> {
    Pair<List<NegocioProposta>, Paginacao> filtrar(Paginacao paginacao, NegocioPropostaFilter filter);
    long count(NegocioPropostaFilter negocioPropostaFilter);
}
