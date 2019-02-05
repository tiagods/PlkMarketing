package br.com.tiagods.repository.interfaces;

import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.filters.ProtocoloEntradaFilter;
import javafx.util.Pair;

import java.util.List;

public interface ProtocoloEntradaDAO {
    ProtocoloEntrada save(ProtocoloEntrada p);
    void remove(ProtocoloEntrada p);
    List<ProtocoloEntrada> getAll();

    Pair<List<ProtocoloEntrada>,Paginacao> filtrar(Paginacao paginacao, ProtocoloEntradaFilter filter);
}
