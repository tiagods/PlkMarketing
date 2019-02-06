package br.com.tiagods.model.implantacao;

import br.com.tiagods.config.init.UsuarioLogado;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "imp_pac_etapa")
public class ImplantacaoPacoteEtapa extends ImplantacaoEtapa {
    @ManyToOne
    @JoinColumn(name = "pacote_id")
    private ImplantacaoPacote pacote;

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
