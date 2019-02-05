package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.negocio.NegocioOrigem;

public interface NegocioOrigemDAO {
	NegocioOrigem save(NegocioOrigem e);
    void remove(NegocioOrigem e);
    List<NegocioOrigem> getAll();
    NegocioOrigem findById(Long id);
}
