package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioOrigem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosOrigens extends JpaRepository<NegocioOrigem, Long> {
}
