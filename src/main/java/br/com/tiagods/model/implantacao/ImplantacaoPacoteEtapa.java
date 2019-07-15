package com.prolink.processos.model.implantacao;

import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "imp_pac_etapa")
public class ImplantacaoPacoteEtapa extends ImplantacaoEtapa implements AbstractEntity,Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pacote_id")
    private ImplantacaoPacote pacote;

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    void prePersist(){
        setCriadoEm(Calendar.getInstance());
        setCriadoPor(UsuarioLogado.getInstance().getUsuario());
    }

    public ImplantacaoPacote getPacote() {
        return pacote;
    }

    public void setPacote(ImplantacaoPacote pacote) {
        this.pacote = pacote;
    }
}
