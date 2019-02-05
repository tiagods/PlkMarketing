package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.negocio.NegocioMalaDireta;

public interface NegocioMalaDiretaDAO {
    List<NegocioMalaDireta> getAll();
}
