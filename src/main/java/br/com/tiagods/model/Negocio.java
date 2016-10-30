package br.com.tiagods.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

public class Negocio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private double honorario;
	private double servicoAgregado;
	private String classe;
	private LocalDateTime criadoEm;
	private Usuario criadoPor;
	private Status status;
	private Usuario atendente;
	private Set<Tarefa> tarefas = new LinkedHashSet<Tarefa>();
	private Set<Empresa> empresas = new LinkedHashSet<Empresa>();
	private Set<Pessoa> pessoas = new LinkedHashSet<Pessoa>();
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
	 * @return the dataInicio
	 */
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	/**
	 * @return the dataFim
	 */
	public LocalDate getDataFim() {
		return dataFim;
	}
	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	/**
	 * @return the honorario
	 */
	public double getHonorario() {
		return honorario;
	}
	/**
	 * @param honorario the honorario to set
	 */
	public void setHonorario(double honorario) {
		this.honorario = honorario;
	}
	/**
	 * @return the servicoAgregado
	 */
	public double getServicoAgregado() {
		return servicoAgregado;
	}
	/**
	 * @param servicoAgregado the servicoAgregado to set
	 */
	public void setServicoAgregado(double servicoAgregado) {
		this.servicoAgregado = servicoAgregado;
	}
	/**
	 * @return the classe
	 */
	public String getClasse() {
		return classe;
	}
	/**
	 * @param classe the classe to set
	 */
	public void setClasse(String classe) {
		this.classe = classe;
	}
	/**
	 * @return the criadoEm
	 */
	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}
	/**
	 * @param criadoEm the criadoEm to set
	 */
	public void setCriadoEm(LocalDateTime criadoEm) {
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
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
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
	 * @return the empresas
	 */
	public Set<Empresa> getEmpresas() {
		return empresas;
	}
	/**
	 * @param empresas the empresas to set
	 */
	public void setEmpresas(Set<Empresa> empresas) {
		this.empresas = empresas;
	}
	/**
	 * @return the pessoas
	 */
	public Set<Pessoa> getPessoas() {
		return pessoas;
	}
	/**
	 * @param pessoas the pessoas to set
	 */
	public void setPessoas(Set<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}	
		
}
