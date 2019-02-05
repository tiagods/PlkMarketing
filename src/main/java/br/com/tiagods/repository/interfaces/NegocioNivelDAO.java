package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.negocio.NegocioNivel;

public interface NegocioNivelDAO {
	NegocioNivel save(NegocioNivel e);
    void remove(NegocioNivel e);
    List<NegocioNivel> getAll();
    NegocioNivel findById(Long id);
}
