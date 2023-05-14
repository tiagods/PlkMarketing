package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioNivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosNiveis extends JpaRepository<NegocioNivel, Long> {
}
