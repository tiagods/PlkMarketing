package br.com.tiagods.modelcollections;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.PessoaJuridica;
import br.com.tiagods.model.ProspeccaoTipoContato;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.Usuario;

@Entity
@Table(name = "prospeccao")
public class NegocioProspeccao implements AbstractEntity,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRO_COD")
	private Long id;
	@Column(name = "PRO_CONVITE_EVENTOS")
	private int conviteParaEventos = 0;
	@Column(name = "PRO_MATERIAL")
	private int material = 0;
	@Column(name = "PRO_NEWSLETTER")
	private int newsletter = 0;
	
	@Column(name="PRO_ENDERECO")
	private String enderecoCompleto;//deletar
	
	@ManyToOne
	@JoinColumn(name="PRO_TIPO_CONTATO")
	private ProspeccaoTipoContato tipoContato;	
	
	@Column(name = "PRO_ULT_NEGOCIO_COD")
	private NegocioProposta ultimoNegocio;


	@AttributeOverrides({
		@AttributeOverride(name = "responsavel", column = @Column(name = "PRO_RESPONSAVEL"))
		})
	@Embedded
	private PessoaJuridica pessoaJuridica;
	
	//@Embedded
	//private Pessoa pessoa;

	//@Embedded
	//private NegocioPadrao padrao;
	@Column(name = "PRO_ORIGEM_DETALHES")
	private String detalhesOrigem;
	@Column(name = "PRO_RESUMO")
	private String resumo;
	@Column(name = "PRO_APRESENTACAO")
	private String apresentacao;
	
	@ManyToOne
	@JoinColumn(name = "PRO_ORIGEM_COD")
	private Origem origem;
	@ManyToOne
	@JoinColumn(name = "PRO_ATENDENTE_COD")
	private Usuario atendente;
	
	@ManyToOne
	@JoinColumn(name = "PRO_SERVICO_COD")
	private Servico servico;
	
	/*
	@ManyToOne
	//@JoinColumn(name = "categoria_id")
	@JoinColumn(name = "PRO_CATEGORIA_COD")
	private NegocioCategoria categoria;
	@ManyToOne
	//@JoinColumn(name = "nivel_id")
	@JoinColumn(name = "PRO_NIVEL_COD")
	private NegocioNivel nivel;
	*/
	//@Embedded
	//private Pessoa pessoa;
	
	@Column(name = "PRO_NOME")
	private String nome;
	
	@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "data_criacao")
	@Column(name = "PRO_CRIADOEM")
	private Calendar criadoEm;
	@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name = "criado_por_id")
	@JoinColumn(name = "PRO_CRIADOPOR_COD")
	private Usuario criadoPor;
	
	@Transient
	private Set<Lista> listas = new HashSet<>();
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
	 * @return the conviteParaEventos
	 */
	public int getConviteParaEventos() {
		return conviteParaEventos;
	}

	/**
	 * @param conviteParaEventos the conviteParaEventos to set
	 */
	public void setConviteParaEventos(int conviteParaEventos) {
		this.conviteParaEventos = conviteParaEventos;
	}

	/**
	 * @return the material
	 */
	public int getMaterial() {
		return material;
	}

	/**
	 * @param material the material to set
	 */
	public void setMaterial(int material) {
		this.material = material;
	}

	/**
	 * @return the newsletter
	 */
	public int getNewsletter() {
		return newsletter;
	}

	/**
	 * @param newsletter the newsletter to set
	 */
	public void setNewsletter(int newsletter) {
		this.newsletter = newsletter;
	}

	/**
	 * @return the tipoContato
	 */
	public ProspeccaoTipoContato getTipoContato() {
		return tipoContato;
	}

	/**
	 * @param tipoContato the tipoContato to set
	 */
	public void setTipoContato(ProspeccaoTipoContato tipoContato) {
		this.tipoContato = tipoContato;
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
	 * @return the pessoaJuridica
	 */
	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	/**
	 * @param pessoaJuridica the pessoaJuridica to set
	 */
	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
	/**
	 * @return the listas
	 */
	public Set<Lista> getListas() {
		return listas;
	}

	/**
	 * @param listas the listas to set
	 */
	public void setListas(Set<Lista> listas) {
		this.listas = listas;
	}
	
	/**
	 * @return the enderecoCompleto
	 */
	public String getEnderecoCompleto() {
		return enderecoCompleto;
	}

	/**
	 * @param enderecoCompleto the enderecoCompleto to set
	 */
	public void setEnderecoCompleto(String enderecoCompleto) {
		this.enderecoCompleto = enderecoCompleto;
	}

	/**
	 * @return the origem
	 */
	public Origem getOrigem() {
		return origem;
	}

	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(Origem origem) {
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
	 * @return the servico
	 */
	public Servico getServico() {
		return servico;
	}

	/**
	 * @param servico the servico to set
	 */
	public void setServico(Servico servico) {
		this.servico = servico;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the criadoEm
	 */
	public Calendar getCriadoEm() {
		return criadoEm;
	}

	/**
	 * @param criadoEm the criadoEm to set
	 */
	public void setCriadoEm(Calendar criadoEm) {
		this.criadoEm = criadoEm;
	}

	/**
	 * @return the criadoPor
	 */
	public Usuario getCriadoPor() {
		return criadoPor;
	}

	/**
	 * @param criadoPor the criadoPor to set
	 */
	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
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
		NegocioProspeccao other = (NegocioProspeccao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
	}

}
