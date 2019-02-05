package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.negocio.NegocioTarefaProposta;

public interface NegocioTarefaPropostaDAO {
	NegocioTarefaProposta save(NegocioTarefaProposta e);
    void remove(NegocioTarefaProposta e);
    List<NegocioTarefaProposta> getAll();
    NegocioTarefaProposta findById(Long id);
}
