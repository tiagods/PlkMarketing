package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.Tarefa;

public interface TarefaDAO {
	Tarefa save(Tarefa e);
    void remove(Tarefa e);
    List<Tarefa> getAll();
    Tarefa findById(Long id);
}
