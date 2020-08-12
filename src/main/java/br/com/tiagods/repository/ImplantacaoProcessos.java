package br.com.tiagods.repository;

import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImplantacaoProcessos extends JpaRepository<ImplantacaoProcesso, Long> {
}
