package br.com.tiagods.repository;

import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImplantacaoAtividades extends JpaRepository<ImplantacaoAtividade,Long> {
}
