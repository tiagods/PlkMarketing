package br.com.tiagods.model.implantacao;

import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.Cliente;
import br.com.tiagods.model.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="imp_processo")
public class ImplantacaoProcesso implements AbstractEntity, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    //muitos para muitos
    @ManyToOne
    @JoinColumn(name = "criado_por_id")
    private Usuario criadoPor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "criado_em")
    private Calendar criadoEm;

    @OneToMany(mappedBy="processo",cascade=CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    private Set<ImplantacaoProcessoEtapa> etapas = new HashSet<>();

    @PrePersist
    void prePersist(){
        setCriadoEm(Calendar.getInstance());
        setCriadoPor(UsuarioLogado.getInstance().getUsuario());
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public Set<ImplantacaoProcessoEtapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(Set<ImplantacaoProcessoEtapa> etapas) {
        this.etapas = etapas;
    }
}
