package br.com.tiagods.repository.interfaces;

import br.com.tiagods.model.implantacao.ImplantacaoAtividade;

import java.util.List;

public interface ImplantacaoAtividadeDAO {
    ImplantacaoAtividade save(ImplantacaoAtividade e);
    void remove(ImplantacaoAtividade e);
    List<ImplantacaoAtividade> getAll();

}
