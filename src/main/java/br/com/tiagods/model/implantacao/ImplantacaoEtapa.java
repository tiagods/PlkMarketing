package br.com.tiagods.model.implantacao;

import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.UsuarioDepartamento;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public abstract class ImplantacaoEtapa implements Serializable,AbstractEntity {
    public enum Etapa{
        PRIMEIRA(1),SEGUNDA(2),TERCEIRA(3);
        //QUARTA(4),QUINTA(5),SEXTA(6),SETIMA(7),OITAVA(8),NONA(9),DECIMA(10)
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

    private String descricao;

    private int tempo = 15;
    private boolean status = false;
    @ManyToOne
    @JoinColumn(name = "atividade_id")
    private ImplantacaoAtividade atividade;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "criado_em")
    private Calendar criadoEm;
    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private Usuario criadoPor;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImplantacaoEtapa that = (ImplantacaoEtapa) o;
        return etapa == that.etapa &&
                Objects.equals(atividade, that.atividade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etapa, atividade);
    }
}
