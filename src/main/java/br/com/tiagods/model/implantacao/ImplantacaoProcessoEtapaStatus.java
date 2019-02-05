package br.com.tiagods.model.implantacao;

import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.Usuario;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

public class ImplantacaoProcessoEtapaStatus implements AbstractEntity,Serializable {
    private Long id;
    private ImplantacaoProcessoEtapa etapa;
    private Usuario criadoPor;
    private Calendar criadoEm;
    private boolean status = true;
    private String descricao;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImplantacaoProcessoEtapa getEtapa() {
        return etapa;
    }

    public void setEtapa(ImplantacaoProcessoEtapa etapa) {
        this.etapa = etapa;
    }

    public Usuario getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Usuario criadoPor) {
        this.criadoPor = criadoPor;
    }

    public Calendar getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Calendar criadoEm) {
        this.criadoEm = criadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImplantacaoProcessoEtapaStatus that = (ImplantacaoProcessoEtapaStatus) o;
        return Objects.equals(id, that.id);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
