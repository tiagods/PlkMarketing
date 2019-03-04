package br.com.tiagods.repository.interfaces;

import br.com.tiagods.model.implantacao.ImplantacaoProcesso;

import java.util.List;

public interface ImplantacaoProcessoDAO {
    ImplantacaoProcesso save(ImplantacaoProcesso i);
    void remove(ImplantacaoProcesso i);
    List<ImplantacaoProcesso> getAll();
    ImplantacaoProcesso findByNome(String nome);
    List<ImplantacaoProcesso> listarAtivos(boolean finalizado);
}
