package br.com.tiagods.modelcollections;

import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.Usuario;

import java.io.Serializable;
import java.util.Calendar;

public class ImplantacaoDocumento implements AbstractEntity,Serializable {
    private Long id;
    private String nome;
    private String descricao;
    private Calendar criadoEm;
    private Usuario criadoPor;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Calendar getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Calendar criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Usuario getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Usuario criadoPor) {
        this.criadoPor = criadoPor;
    }
}
