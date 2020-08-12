package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosPropostas extends JpaRepository<NegocioProposta, Long> {
}
