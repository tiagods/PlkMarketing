package br.com.tiagods.repository.interfaces;

import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapaStatus;

import java.util.List;

public interface ImplantacaoProcessoEtapaStatusDAO {
    ImplantacaoProcessoEtapaStatus save(ImplantacaoProcessoEtapaStatus i);
    void remove(ImplantacaoProcessoEtapaStatus i);
    List<ImplantacaoProcessoEtapaStatus> getAll();
}
