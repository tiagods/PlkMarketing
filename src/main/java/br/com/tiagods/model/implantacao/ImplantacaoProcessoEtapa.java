package br.com.tiagods.model.implantacao;

import br.com.tiagods.config.enums.IconsEnum;
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

    public enum Status{
        ABERTO("Pendente",IconsEnum.BUTTON_OK),
        CONCLUIDO("Concluido",IconsEnum.BUTTON_UP),
        AGUARDANDO_ANTERIOR("Aguardando Liberação",IconsEnum.BUTTON_DEADLINE);
        private String descricao;
        private IconsEnum icon;
        Status(String descricao,IconsEnum icon){
            this.descricao = descricao;
            this.icon=icon;
        }

        public IconsEnum getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            return this.descricao;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy="processoEtapa",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    private Set<ImplantacaoProcessoEtapaStatus> historico = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private ImplantacaoProcesso processo;

    @Embedded
    private ImplantacaoEtapa etapa = new ImplantacaoEtapa();

    @Enumerated(EnumType.STRING)
    private Status status = Status.AGUARDANDO_ANTERIOR;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_liberacao")
    private Calendar dataLiberacao;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_atualizacao")
    private Calendar dataAtualizacao;

    public ImplantacaoProcessoEtapa(){}

    public ImplantacaoProcessoEtapa(ImplantacaoEtapa implantacaoEtapa,ImplantacaoProcesso processo){
        this.processo=processo;
        this.etapa=implantacaoEtapa;
    }

    @PrePersist
    void prePersist(){
        if(etapa.getEtapa().equals(ImplantacaoEtapa.Etapa.PRIMEIRA)) {
            status = Status.ABERTO;
            setDataLiberacao(Calendar.getInstance());
            setDataAtualizacao(Calendar.getInstance());
        }
        etapa.setCriadoEm(Calendar.getInstance());
        etapa.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
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

    public Set<ImplantacaoProcessoEtapaStatus> getHistorico() { return historico; }

    public void setHistorico(Set<ImplantacaoProcessoEtapaStatus> historico) { this.historico = historico; }

    public ImplantacaoProcesso getProcesso() {
        return processo;
    }

    public void setProcesso(ImplantacaoProcesso processo) {
        this.processo = processo;
    }

    public void setStatus(Status status) { this.status = status; }

    public Status getStatus() { return status; }

    public Calendar getDataLiberacao() {
        return dataLiberacao;
    }

    public void setDataLiberacao(Calendar dataLiberacao) {
        this.dataLiberacao = dataLiberacao;
    }

    public Calendar getDataAtualizacao() { return dataAtualizacao; }

    public void setDataAtualizacao(Calendar dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
