package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.ServicoContratado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosServicosContratados extends JpaRepository<ServicoContratado, Long> {
}
