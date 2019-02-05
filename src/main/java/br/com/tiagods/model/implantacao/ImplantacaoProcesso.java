package br.com.tiagods.model.implantacao;

import br.com.tiagods.model.Cliente;

import java.util.HashSet;
import java.util.Set;

public class ImplantacaoProcesso {
    private Long id;
    private Cliente cliente;
    //muitos para muitos
    private Set<ImplantacaoProcessoEtapa> etapas = new HashSet<>();


}
