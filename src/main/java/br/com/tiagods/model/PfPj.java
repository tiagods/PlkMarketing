package br.com.tiagods.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class PfPj extends Endereco implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private int telefone;
	private int celular;
	private String email;
	private String site;
	private Date criadoEm;
	private Usuario criadoPor;
	private Origem origem;
	private Nivel nivel;
	private Usuario atendente;
	private Set<Negocio> negocios = new LinkedHashSet<Negocio>();
	private Set<Tarefa> tarefas = new LinkedHashSet<Tarefa>();
	private Servico servico;
	private ServicoAgregado servicoAgregado;
	private Categoria categoria;

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
	public int getTelefone() {
		return telefone;
	}
	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}
	/**
	 * @return the celular
	 */
	public int getCelular() {
		return celular;
	}
	/**
	 * @param celular the celular to set
	 */
	public void setCelular(int celular) {
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
	 * @return the nivel
	 */
	public Nivel getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
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
	 * @return the negocios
	 */
	public Set<Negocio> getNegocios() {
		return negocios;
	}
	/**
	 * @param negocios the negocios to set
	 */
	public void setNegocios(Set<Negocio> negocios) {
		this.negocios = negocios;
	}
	/**
	 * @return the tarefas
	 */
	public Set<Tarefa> getTarefas() {
		return tarefas;
	}
	/**
	 * @param tarefas the tarefas to set
	 */
	public void setTarefas(Set<Tarefa> tarefas) {
		this.tarefas = tarefas;
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
	 * @return the servicoAgregado
	 */
	public ServicoAgregado getServicoAgregado() {
		return servicoAgregado;
	}
	/**
	 * @param servicoAgregado the servicoAgregado to set
	 */
	public void setServicoAgregado(ServicoAgregado servicoAgregado) {
		this.servicoAgregado = servicoAgregado;
	}
	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
