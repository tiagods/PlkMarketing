package br.com.tiagods.repository;

import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImplantacaoProcessosEtapasStatus extends JpaRepository<ImplantacaoProcessoEtapaStatus,Long> {
}
