package br.com.tiagods.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name = "versao_app")
public class VersaoApp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String versao;
    private String detalhes;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar historico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Calendar getHistorico() {
        return historico;
    }

    public void setHistorico(Calendar historico) {
        this.historico = historico;
    }

    @Override
    public String toString() {
        return this.versao;
    }
}
