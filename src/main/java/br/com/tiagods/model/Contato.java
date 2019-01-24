package br.com.tiagods.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import br.com.tiagods.config.init.UsuarioLogado;

@Entity
public class Contato extends Pessoa implements AbstractEntity,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	public enum PessoaTipo {
		CONTATO("Tipo"),EMPRESA("Empresa"),PESSOA("Pessoa");
		private String descricao;
		PessoaTipo(String descricao) {
			this.descricao=descricao;
		}
		public String getDescricao() {
			return descricao;
		}
		@Override
		public String toString() {
			return getDescricao();
		}
	}
	public enum ContatoTipo{
		CONTATO("Prospeccao/Sondagem"),
		PROSPECCAO("Prospecção"),SONDAGEM("Sondagem");
		private String descricao;
		private ContatoTipo(String descricao) {
			this.descricao=descricao;
		}
		public String getDescricao() {
			return descricao;
		}
		@Override
		public String toString() {
			return getDescricao();
		}
	}

	@Embedded
	private PessoaFisica fisico;
	@Embedded
	private PessoaJuridica juridico;
	@Enumerated(value = EnumType.STRING)
	@Column(name="pessoa_tipo")
	private PessoaTipo pessoaTipo = PessoaTipo.PESSOA;
	
	@Enumerated(value= EnumType.STRING)
	@Column(name="contato_tipo")
	private ContatoTipo contatoTipo = ContatoTipo.SONDAGEM;
	
	@ManyToOne
	@JoinColumn(name = "ultimo_negocio_id")
	private NegocioProposta ultimoNegocio;
		
	@ManyToOne
	@JoinColumn(name = "origem_id")
	private NegocioOrigem origem;

	@ManyToOne
	@JoinColumn(name = "atendente_id")
	private Usuario atendente;
	
	@Column(name="detalhes_origem")
	private String detalhesOrigem;
	
	private String resumo;
	
	private String apresentacao;
	
	@ManyToOne
	@JoinColumn(name = "servico_id")
	private NegocioServico servico;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private NegocioCategoria categoria;

	@ManyToOne
	@JoinColumn(name = "nivel_id")
	private NegocioNivel nivel;
	
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="contato_lista",
            joinColumns = { @JoinColumn(name = "contato_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "lista_id", referencedColumnName = "id") })
	private Set<NegocioLista> listas = new HashSet<>();
	
	private boolean material = false;
	private boolean convite = false;
	private boolean newsletter = false;

	@ManyToOne
	@JoinColumn(name="mala_direta_id")
	private NegocioMalaDireta malaDireta;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="negocioContato",cascade=CascadeType.ALL)
	private Set<NegocioProposta> negocios = new LinkedHashSet<>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="contato",cascade=CascadeType.ALL)
	private Set<NegocioTarefaContato> tarefas = new LinkedHashSet<>();

	public Contato() {
	}
	public Contato(long id) {
		this.id=id;
	}
	
	@PrePersist
	void prePersist() {
		setCriadoPor(UsuarioLogado.getInstance().getUsuario());
		setCriadoEm(Calendar.getInstance());
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the fisico
	 */
	public PessoaFisica getFisico() {
		return fisico;
	}
	/**
	 * @param fisico the fisico to set
	 */
	public void setFisico(PessoaFisica fisico) {
		this.fisico = fisico;
	}
	/**
	 * @return the juridico
	 */
	public PessoaJuridica getJuridico() {
		return juridico;
	}
	/**
	 * @param juridico the juridico to set
	 */
	public void setJuridico(PessoaJuridica juridico) {
		this.juridico = juridico;
	}
	
	/**
	 * @return the pessoaTipo
	 */
	public PessoaTipo getPessoaTipo() {
		return pessoaTipo;
	}
	/**
	 * @param pessoaTipo the pessoaTipo to set
	 */
	public void setPessoaTipo(PessoaTipo pessoaTipo) {
		this.pessoaTipo = pessoaTipo;
	}
	/**
	 * @return the contatoTipo
	 */
	public ContatoTipo getContatoTipo() {
		return contatoTipo;
	}
	/**
	 * @param contatoTipo the contatoTipo to set
	 */
	public void setContatoTipo(ContatoTipo contatoTipo) {
		this.contatoTipo = contatoTipo;
	}
	/**
	 * @return the ultimoNegocio
	 */
	public NegocioProposta getUltimoNegocio() {
		return ultimoNegocio;
	}
	/**
	 * @param ultimoNegocio the ultimoNegocio to set
	 */
	public void setUltimoNegocio(NegocioProposta ultimoNegocio) {
		this.ultimoNegocio = ultimoNegocio;
	}
	/**
	 * @return the origem
	 */
	public NegocioOrigem getOrigem() {
		return origem;
	}
	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(NegocioOrigem origem) {
		this.origem = origem;
	}
	/**
	 * @return the atendente
	 */
	public Usuario getAtendente() {
		return atendente;
	}
	/**
	 * @param atendente the atendente to set
	 */
	public void setAtendente(Usuario atendente) {
		this.atendente = atendente;
	}
	/**
	 * @return the detalhesOrigem
	 */
	public String getDetalhesOrigem() {
		return detalhesOrigem;
	}
	/**
	 * @param detalhesOrigem the detalhesOrigem to set
	 */
	public void setDetalhesOrigem(String detalhesOrigem) {
		this.detalhesOrigem = detalhesOrigem;
	}
	/**
	 * @return the resumo
	 */
	public String getResumo() {
		return resumo;
	}
	/**
	 * @param resumo the resumo to set
	 */
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	/**
	 * @return the apresentacao
	 */
	public String getApresentacao() {
		return apresentacao;
	}
	/**
	 * @param apresentacao the apresentacao to set
	 */
	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}
	/**
	 * @return the servico
	 */
	public NegocioServico getServico() {
		return servico;
	}
	/**
	 * @param servico the servico to set
	 */
	public void setServico(NegocioServico servico) {
		this.servico = servico;
	}
	/**
	 * @return the categoria
	 */
	public NegocioCategoria getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(NegocioCategoria categoria) {
		this.categoria = categoria;
	}
	
	/**
	 * @return the lista
	 */
	
	/**
	 * @return the nivel
	 */
	public NegocioNivel getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(NegocioNivel nivel) {
		this.nivel = nivel;
	}
	
	/**
	 * @return the negocios
	 */
	public Set<NegocioProposta> getNegocios() {
		return negocios;
	}
	/**
	 * @param negocios the negocios to set
	 */
	public void setNegocios(Set<NegocioProposta> negocios) {
		this.negocios = negocios;
	}
	/**
	 * @return the tarefas
	 */
	public Set<NegocioTarefaContato> getTarefas() {
		return tarefas;
	}
	
	/**
	 * @return the listas
	 */
	public Set<NegocioLista> getListas() {
		return listas;
	}
	/**
	 * @param listas the listas to set
	 */
	public void setListas(Set<NegocioLista> listas) {
		this.listas = listas;
	}
	/**
	 * @return the material
	 */
	public boolean isMaterial() {
		return material;
	}
	/**
	 * @param material the material to set
	 */
	public void setMaterial(boolean material) {
		this.material = material;
	}
	/**
	 * @return the convite
	 */
	public boolean isConvite() {
		return convite;
	}
	/**
	 * @param convite the convite to set
	 */
	public void setConvite(boolean convite) {
		this.convite = convite;
	}
	/**
	 * @return the newsletter
	 */
	public boolean isNewsletter() {
		return newsletter;
	}
	/**
	 * @param newsletter the newsletter to set
	 */
	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}
	/**
	 * @return the malaDireta
	 */
	public NegocioMalaDireta getMalaDireta() {
		return malaDireta;
	}
	/**
	 * @param malaDireta the malaDireta to set
	 */
	public void setMalaDireta(NegocioMalaDireta malaDireta) {
		this.malaDireta = malaDireta;
	}
	/**
	 * @param tarefas the tarefas to set
	 */
	public void setTarefas(Set<NegocioTarefaContato> tarefas) {
		this.tarefas = tarefas;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contato other = (Contato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		String newName = getNome();
		if(pessoaTipo.equals(PessoaTipo.EMPRESA))
			newName +=" || "+ juridico.getResponsavel()+" || "+juridico.getRazao();
		// TODO Auto-generated method stub
		return newName;
	}
}
