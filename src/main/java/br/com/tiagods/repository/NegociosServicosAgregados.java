package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.ServicoAgregado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosServicosAgregados extends JpaRepository<ServicoAgregado, Long> {
}
