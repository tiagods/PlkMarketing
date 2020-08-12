package br.com.tiagods.repository;

import br.com.tiagods.model.negocio.NegocioServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegociosServicos extends JpaRepository<NegocioServico,Long> {
}
