package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioTarefaProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosTarefasPropostas extends JpaRepository<NegocioTarefaProposta, Long> {
}
