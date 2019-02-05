package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.negocio.NegocioCategoria;

public interface NegocioCategoriaDAO {
	NegocioCategoria save(NegocioCategoria e);
    void remove(NegocioCategoria e);
    List<NegocioCategoria> getAll();
    NegocioCategoria findById(Long id);
}
