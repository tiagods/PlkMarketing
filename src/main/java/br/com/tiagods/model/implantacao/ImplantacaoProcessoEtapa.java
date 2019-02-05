package br.com.tiagods.model.implantacao;

import java.util.HashSet;
import java.util.Set;

public class ImplantacaoProcessoEtapa extends ImplantacaoEtapa {
    //um para muitos
    private Set<ImplantacaoProcessoEtapaStatus> status = new HashSet<>();

    public Set<ImplantacaoProcessoEtapaStatus> getStatus() {
        return status;
    }

    public void setStatus(Set<ImplantacaoProcessoEtapaStatus> status) {
        this.status = status;
    }
}
