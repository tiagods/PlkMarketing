package br.com.tiagods.repository.interfaces;

import br.com.tiagods.model.implantacao.ImplantacaoPacote;

import java.util.List;

public interface ImplantacaoPacoteDAO {
    ImplantacaoPacote save(ImplantacaoPacote i);
    void remove(ImplantacaoPacote i);
    List<ImplantacaoPacote> getAll();
    ImplantacaoPacote findByNome(String nome);
}
