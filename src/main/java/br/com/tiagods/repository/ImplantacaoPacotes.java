package br.com.tiagods.repository;

import br.com.tiagods.model.implantacao.ImplantacaoPacote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImplantacaoPacotes extends JpaRepository<ImplantacaoPacote, Long> {
}
