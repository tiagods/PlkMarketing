package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioMalaDireta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosMalaDiretas extends JpaRepository<NegocioMalaDireta, Long> {
}
