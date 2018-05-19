package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.Categoria;

public interface CategoriaDAO {
	Categoria save(Categoria e);
    void remove(Categoria e);
    List<Categoria> getAll();
    Categoria findById(Long id);
}
