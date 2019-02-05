package br.com.tiagods.repository.interfaces;

import java.util.List;

import br.com.tiagods.model.negocio.ServicoContratado;

public interface NegocioServicoContratadoDAO {
	ServicoContratado save(ServicoContratado e);
    void remove(ServicoContratado e);
    List<ServicoContratado> getAll();
    ServicoContratado findById(Long id);
}
