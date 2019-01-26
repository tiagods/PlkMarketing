package br.com.tiagods.repository.interfaces;

import br.com.tiagods.model.ProtocoloEntrada;

import java.util.List;

public interface ProtocoloEntradaDAO {
    ProtocoloEntrada save(ProtocoloEntrada p);
    void remove(ProtocoloEntrada p);
    List<ProtocoloEntrada> getAll();

}
