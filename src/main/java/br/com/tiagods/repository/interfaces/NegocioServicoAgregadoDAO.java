package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.ServicoAgregado;

public interface NegocioServicoAgregadoDAO {
	ServicoAgregado save(ServicoAgregado e);
    void remove(ServicoAgregado e);
    List<ServicoAgregado> getAll();
    ServicoAgregado	 findById(Long id);
}
