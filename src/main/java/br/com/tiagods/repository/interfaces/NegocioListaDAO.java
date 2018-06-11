package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.NegocioLista;

public interface NegocioListaDAO {
	NegocioLista save(NegocioLista e);
    void remove(NegocioLista e);
    List<NegocioLista> getAll();
    NegocioLista findById(Long id);
}
