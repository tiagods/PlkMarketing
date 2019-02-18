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
public class ImplantacaoProcessoEtapa implements AbstractEntity,Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="processoEtapa",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    private Set<ImplantacaoProcessoEtapaStatus> status = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private ImplantacaoProcesso processo;

    @Embedded
    private ImplantacaoEtapa etapa = new ImplantacaoEtapa();

    public ImplantacaoProcessoEtapa(){}

    public ImplantacaoProcessoEtapa(ImplantacaoEtapa implantacaoEtapa,ImplantacaoProcesso processo){
        this.processo=processo;
        this.etapa=implantacaoEtapa;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImplantacaoEtapa getEtapa() {
        return etapa;
    }

    public void setEtapa(ImplantacaoEtapa etapa) {
        this.etapa = etapa;
    }

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

}
