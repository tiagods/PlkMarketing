package br.com.tiagods.model.implantacao;

import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "imp_pro_etapa")
public class ImplantacaoProcessoEtapa extends ImplantacaoEtapa{

    @OneToMany(mappedBy="processoEtapa",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    private Set<ImplantacaoProcessoEtapaStatus> status = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private ImplantacaoProcesso processo;

    public Set<ImplantacaoProcessoEtapaStatus> getStatus() {
        return status;
    }

    public void setStatus(Set<ImplantacaoProcessoEtapaStatus> status) {
        this.status = status;
    }

    public ImplantacaoProcesso getProcesso() {
        return processo;
    }

    public void setProcesso(ImplantacaoProcesso processo) {
        this.processo = processo;
    }

    @PrePersist
    void prePersist(){
        setCriadoEm(Calendar.getInstance());
        setCriadoPor(UsuarioLogado.getInstance().getUsuario());
    }
}
