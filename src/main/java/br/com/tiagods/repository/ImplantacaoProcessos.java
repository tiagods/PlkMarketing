package br.com.tiagods.repository;

import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImplantacaoProcessos extends JpaRepository<ImplantacaoProcesso, Long> {
    List<ImplantacaoProcesso> listarAtivos(boolean selected);
}
