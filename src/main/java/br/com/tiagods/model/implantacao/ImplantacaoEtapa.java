package br.com.tiagods.model.implantacao;

import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.UsuarioDepartamento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public abstract class ImplantacaoEtapa implements Serializable,AbstractEntity {
    public enum Etapa{
        PRIMEIRA(1),SEGUNDA(2),TERCEIRA(3);

        private int valor;

        Etapa(Integer valor){
            this.valor=valor;
        }

        public int getValor() {
            return valor;
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Etapa etapa = Etapa.PRIMEIRA;
    @Transient
    private Usuario responsavel;
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private UsuarioDepartamento departamento;
    private int tempo = 15;
    private boolean status = false;
    @ManyToOne
    @JoinColumn(name = "atividade_id")
    private ImplantacaoAtividade atividade;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public Usuario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Usuario responsavel) {
        this.responsavel = responsavel;
    }

    public UsuarioDepartamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(UsuarioDepartamento departamento) {
        this.departamento = departamento;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ImplantacaoAtividade getAtividade() {
        return atividade;
    }
    public void setAtividade(ImplantacaoAtividade atividade) {
        this.atividade = atividade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImplantacaoEtapa that = (ImplantacaoEtapa) o;
        return Objects.equals(id, that.id) &&
                etapa == that.etapa &&
                Objects.equals(departamento, that.departamento) &&
                Objects.equals(atividade, that.atividade);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, etapa, departamento, atividade);
    }
}
