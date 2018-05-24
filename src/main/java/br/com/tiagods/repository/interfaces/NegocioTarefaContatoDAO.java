package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.NegocioTarefaContato;

public interface NegocioTarefaContatoDAO {
	NegocioTarefaContato save(NegocioTarefaContato e);
    void remove(NegocioTarefaContato e);
    List<NegocioTarefaContato> getAll();
    NegocioTarefaContato findById(Long id);
}
