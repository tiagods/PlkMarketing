package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.negocio.NegocioServico;

public interface NegocioServicoDAO {
	NegocioServico save(NegocioServico e);
    void remove(NegocioServico e);
    List<NegocioServico> getAll();
    NegocioServico findById(Long id);
}
