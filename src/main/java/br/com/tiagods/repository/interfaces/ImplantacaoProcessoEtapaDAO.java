package br.com.tiagods.repository.interfaces;

import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;

import java.util.List;

public interface ImplantacaoProcessoEtapaDAO {
    ImplantacaoProcessoEtapa save(ImplantacaoProcessoEtapa i);
    void remove(ImplantacaoProcessoEtapa i);
    List<ImplantacaoProcessoEtapa> getAll();
}
