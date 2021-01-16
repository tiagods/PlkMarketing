package br.com.tiagods.repository;

import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.repository.helpers.filters.ProtocoloEntradaFilter;
import br.com.tiagods.repository.interfaces.Paginacao;
import javafx.util.Pair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtocolosEntradas extends JpaRepository<ProtocoloEntrada, Long> {
    Pair<List<ProtocoloEntrada>, Paginacao> filtrar(Paginacao paginacao, ProtocoloEntradaFilter protocoloEntradaFilter);
}
