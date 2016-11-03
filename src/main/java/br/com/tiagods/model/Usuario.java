package br.com.tiagods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String login;
	private String nome;
	private String senha;
	private String email;
	private Date criadoEm;
	private Usuario criadoPor;
	private Date ultimoAcesso;
	private int totalTarefas;
	private int totalTarefasPendentes;
	private int totalEmpresas;
	private int totalPessoas;
	private int totalNegocios;
	private Departamento departamento;
	private Funcao funcao;
	private BigDecimal totalVendas;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
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
	 * @return the criadoEm
	 */
	public Date getCriadoEm() {
		return criadoEm;
	}
	/**
	 * @param criadoEm the criadoEm to set
	 */
	public void setCriadoEm(Date criadoEm) {
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
	 * @return the ultimoAcesso
	 */
	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}
	/**
	 * @param ultimoAcesso the ultimoAcesso to set
	 */
	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
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
	public Departamento getDepartamento() {
		return departamento;
	}
	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(Departamento departamento) {
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
	 * @return the totalNegocio
	 */
	public int getTotalNegocio() {
		return totalNegocios;
	}
	/**
	 * @param totalNegocio the totalNegocio to set
	 */
	public void setTotalNegocio(int totalNegocio) {
		this.totalNegocios = totalNegocio;
	}
	/**
	 * @return the vendas
	 */
	public BigDecimal getVendas() {
		return totalVendas;
	}
	/**
	 * @param vendas the vendas to set
	 */
	public void setVendas(BigDecimal vendas) {
		this.totalVendas = vendas;
	}
		
}
