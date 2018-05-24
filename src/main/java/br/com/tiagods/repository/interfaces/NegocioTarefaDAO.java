package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.NegocioTarefa;

public interface NegocioTarefaDAO {
	NegocioTarefa save(NegocioTarefa e);
    void remove(NegocioTarefa e);
    List<NegocioTarefa> getAll();
    NegocioTarefa findById(Long id);
}
