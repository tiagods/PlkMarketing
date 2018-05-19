package br.com.tiagods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Usuario implements AbstractEntity,Serializable{
	public enum UsuarioNivel {
		ADMIN("Admin","O administrador é tem permissão sobre todo o sistema"),
		GERENTE("Gerente","O gerente tem permissão para excluir e alterar registros, cadastrar usuários, relatórios de vendas"),
		OPERADOR("Operador","O operador não pode: excluir registros, criar ou alterar contas, ver relatórios financeiros");
		private String descricao;
		private String nome;
		UsuarioNivel(String nome,String descricao) {
			this.nome = nome;
			this.descricao = descricao;
		}
		public String getDescricao() {
			return this.descricao;
		}
		public String getNome() {return nome;}
		@Override
		public String toString() {
			return this.nome;
		}
	}
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USU_COD")
	private Long id;
	@Column(name="USU_LOGIN")
	private String login="";
	@Column(name="USU_SENHA")
	private String senha="";
	@Column(name="USU_ULTIMOACESSO")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar ultimoAcesso;
	@Transient
	//@Enumerated(EnumType.STRING)
	private UsuarioNivel nivel;
	
	@Transient
	private int totalTarefas=0;
	@Transient
	private int totalTarefasPendentes=0;
	@Transient
	private int totalEmpresas=0;
	@Transient
	private int totalPessoas=0;
	@Transient
	private int totalNegocios=0;
	
	@ManyToOne 
	@JoinColumn(name="USU_DEPARTAMENTO_COD")
	private UsuarioDepartamento departamento;
	@ManyToOne 
	@JoinColumn(name="USU_FUNCAO_COD")
	private Funcao funcao;
	@Column(name="USU_TOTALVENDAS")
	private BigDecimal totalVendas;
	@Column(name="USU_ATIVO")
	private int ativo=1;
	
	//init Pessoa
	@Column(name="USU_NOME")
	
	private String nome="";
	private String telefone="";
	private String celular="";
	private String email="";
	private String site="";
	private String cep="";
	private String endereco="";
	private String numero="";
	private String bairro="";
	private String complemento="";
	
	@ManyToOne
	@JoinColumn(name="cidade_id")
	private Cidade cidade;
	@Enumerated(value =EnumType.STRING)
	private Cidade.Estado estado;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao")
	private Calendar criadoEm;

	@ManyToOne(fetch=FetchType.LAZY)
	//@JoinColumn(name = "criado_por_id")
	private Usuario criadoPor;
	
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
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}
	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	/**
	 * @return the ultimoAcesso
	 */
	public Calendar getUltimoAcesso() {
		return ultimoAcesso;
	}
	/**
	 * @param ultimoAcesso the ultimoAcesso to set
	 */
	public void setUltimoAcesso(Calendar ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}
	/**
	 * @return the nivel
	 */
	public UsuarioNivel getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(UsuarioNivel nivel) {
		this.nivel = nivel;
	}
	/**
	 * @return the totalTarefas
	 */
	public int getTotalTarefas() {
		return totalTarefas;
	}
	/**
	 * @param totalTarefas the totalTarefas to set
	 */
	public void setTotalTarefas(int totalTarefas) {
		this.totalTarefas = totalTarefas;
	}
	/**
	 * @return the totalTarefasPendentes
	 */
	public int getTotalTarefasPendentes() {
		return totalTarefasPendentes;
	}
	/**
	 * @param totalTarefasPendentes the totalTarefasPendentes to set
	 */
	public void setTotalTarefasPendentes(int totalTarefasPendentes) {
		this.totalTarefasPendentes = totalTarefasPendentes;
	}
	/**
	 * @return the totalEmpresas
	 */
	public int getTotalEmpresas() {
		return totalEmpresas;
	}
	/**
	 * @param totalEmpresas the totalEmpresas to set
	 */
	public void setTotalEmpresas(int totalEmpresas) {
		this.totalEmpresas = totalEmpresas;
	}
	/**
	 * @return the totalPessoas
	 */
	public int getTotalPessoas() {
		return totalPessoas;
	}
	/**
	 * @param totalPessoas the totalPessoas to set
	 */
	public void setTotalPessoas(int totalPessoas) {
		this.totalPessoas = totalPessoas;
	}
	/**
	 * @return the totalNegocios
	 */
	public int getTotalNegocios() {
		return totalNegocios;
	}
	/**
	 * @param totalNegocios the totalNegocios to set
	 */
	public void setTotalNegocios(int totalNegocios) {
		this.totalNegocios = totalNegocios;
	}
	/**
	 * @return the departamento
	 */
	public UsuarioDepartamento getDepartamento() {
		return departamento;
	}
	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(UsuarioDepartamento departamento) {
		this.departamento = departamento;
	}
	/**
	 * @return the funcao
	 */
	public Funcao getFuncao() {
		return funcao;
	}
	/**
	 * @param funcao the funcao to set
	 */
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	/**
	 * @return the totalVendas
	 */
	public BigDecimal getTotalVendas() {
		return totalVendas;
	}
	/**
	 * @param totalVendas the totalVendas to set
	 */
	public void setTotalVendas(BigDecimal totalVendas) {
		this.totalVendas = totalVendas;
	}
	/**
	 * @return the ativo
	 */
	public int getAtivo() {
		return ativo;
	}
	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(int ativo) {
		this.ativo = ativo;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.login;
	}
}
