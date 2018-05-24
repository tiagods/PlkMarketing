package br.com.tiagods.modelcollections;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.PessoaJuridica;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.Usuario;

@Entity
@Table(name="empresa")
public class NegocioEmpresa implements AbstractEntity, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMP_COD")
	private Long id;

	@Column(name = "EMP_APELIDO_COD")
	private String apelido;

	@ManyToOne
	@JoinColumn(name = "EMP_ULT_NEGOCIO_COD")
	private NegocioProposta ultimoNegocio;

	@Transient
	private Set<NegocioPessoa> pessoas = new LinkedHashSet<>();
	@Transient
	private Set<NegocioProposta> negocios = new LinkedHashSet<>();
	@Transient
	private Set<NegocioTarefa> tarefas = new LinkedHashSet<>();

	@AttributeOverrides({ @AttributeOverride(name = "razao", column = @Column(name = "EMP_RAZAO_COD")),
			@AttributeOverride(name = "cnpj", column = @Column(name = "EMP_CNPJ"))
	})
	@Embedded
	private PessoaJuridica pessoaJuridica;
	//@Embedded
	//private NegocioPadrao padrao;

	@ManyToOne
	@JoinColumn(name = "EMP_ORIGEM_COD")
	private Origem origem;
	@ManyToOne
	@JoinColumn(name = "EMP_ATENDENTE_COD")
	private Usuario atendente;
	//private String detalhesOrigem;
	//private String resumo;
	//private String apresentacao;
	@ManyToOne
	@JoinColumn(name = "EMP_SERVICO_COD")
	private Servico servico;
	@ManyToOne
	//@JoinColumn(name = "categoria_id")
	@JoinColumn(name = "EMP_CATEGORIA_COD")
	private NegocioCategoria categoria;
	@ManyToOne
	//@JoinColumn(name = "nivel_id")
	@JoinColumn(name = "EMP_NIVEL_COD")
	private NegocioNivel nivel;
	private String departamento;
	//@Embedded
	//private Pessoa pessoa;
	
	@Column(name = "EMP_NOME")
	private String nome;
	@Column(name = "EMP_TELEFONE")
	private String telefone;
	@Column(name = "EMP_CELULAR")
	private String celular;
	@Column(name = "EMP_EMAIL")
	private String email;
	@Column(name = "EMP_SITE")
	private String site;
	@Column(name = "EMP_END_CEP")
	private String cep;
	@Column(name = "EMP_END_NOME")
	private String endereco;
	@Column(name = "EMP_END_NUMERO")
	private String numero;
	@Column(name = "EMP_END_BAIRRO")
	private String bairro;
	@Column(name = "EMP_END_COMPLEMENTO")
	private String complemento;
	@ManyToOne
	//@JoinColumn(name="cidade_id")
	@JoinColumn(name="EMP_CIDADE_COD")
	private Cidade cidade;
	@Enumerated(value =EnumType.STRING)
	private Cidade.Estado estado;
	@Temporal(TemporalType.TIMESTAMP)
	//@Column(name = "data_criacao")
	@Column(name = "EMP_CRIADOEM")
	private Calendar criadoEm;
	@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name = "criado_por_id")
	@JoinColumn(name = "EMP_CRIADOPOR_COD")
	private Usuario criadoPor;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the apelido
	 */
	public String getApelido() {
		return apelido;
	}

	/**
	 * @param apelido
	 *            the apelido to set
	 */
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	/**
	 * @return the pessoaJuridica
	 */
	public PessoaJuridica getPessoaJuridica() {
		return pessoaJuridica;
	}

	/**
	 * @param pessoaJuridica
	 *            the pessoaJuridica to set
	 */
	public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}

	/**
	 * @return the ultimoNegocio
	 */
	public NegocioProposta getUltimoNegocio() {
		return ultimoNegocio;
	}

	/**
	 * @param ultimoNegocio
	 *            the ultimoNegocio to set
	 */
	public void setUltimoNegocio(NegocioProposta ultimoNegocio) {
		this.ultimoNegocio = ultimoNegocio;
	}

	/**
	 * @return the pessoas
	 */
	public Set<NegocioPessoa> getPessoas() {
		return pessoas;
	}

	/**
	 * @param pessoas
	 *            the pessoas to set
	 */
	public void setPessoas(Set<NegocioPessoa> pessoas) {
		this.pessoas = pessoas;
	}

	/**
	 * @return the negocios
	 */
	public Set<NegocioProposta> getNegocios() {
		return negocios;
	}

	/**
	 * @param negocios
	 *            the negocios to set
	 */
	public void setNegocios(Set<NegocioProposta> negocios) {
		this.negocios = negocios;
	}

	/**
	 * @return the tarefas
	 */
	public Set<NegocioTarefa> getTarefas() {
		return tarefas;
	}

	/**
	 * @param tarefas
	 *            the tarefas to set
	 */
	public void setTarefas(Set<NegocioTarefa> tarefas) {
		this.tarefas = tarefas;
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
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
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
	 * @return the telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the celular
	 */
	public String getCelular() {
		return celular;
	}

	/**
	 * @param celular the celular to set
	 */
	public void setCelular(String celular) {
		this.celular = celular;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the site
	 */
	public String getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(String site) {
		this.site = site;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * @return the cidade
	 */
	public Cidade getCidade() {
		return cidade;
	}

	/**
	 * @param cidade the cidade to set
	 */
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the estado
	 */
	public Cidade.Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Cidade.Estado estado) {
		this.estado = estado;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		NegocioEmpresa other = (NegocioEmpresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
