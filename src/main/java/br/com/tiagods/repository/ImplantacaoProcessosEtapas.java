package br.com.tiagods.repository;

import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import br.com.tiagods.model.implantacao.ImplantacaoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImplantacaoProcessosEtapas extends JpaRepository<ImplantacaoProcessoEtapa, Long> {
    List<ImplantacaoProcessoEtapa> filtrar(Departamento departamento, ImplantacaoProcesso processo, ImplantacaoAtividade atividade,
                                           ImplantacaoEtapa.Etapa etapa, ImplantacaoProcessoEtapa.Status status, boolean exibirHistorico);
}
