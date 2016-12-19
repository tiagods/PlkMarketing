package br.com.tiagods.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Negocio implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome="";
	private Date dataInicio;
	private Date dataFim;
	private String classe="";
	private Date criadoEm;
	private Usuario criadoPor;
	private Status status;
	private Etapa etapa;
	private Usuario atendente;
	private String andamento="";
	private Date contato;
	private Date envioProposta;
	private Date followUp;
	private Date fechamento;
	private Set<Tarefa> tarefas = new LinkedHashSet<>();
	private Empresa empresa;
	private Pessoa pessoa;
	private Set<ServicoContratado> servicosContratados = new LinkedHashSet<>();
	private BigDecimal honorario;
	private Origem origem;
	private PfPj pessoaFisicaOrJuridica;
	
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
	public Date getDataInicio() {
		return dataInicio;
	}
	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	/**
	 * @return the dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}
	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	/**
	 * @return the honorario
	 */
	public BigDecimal getHonorario() {
		return honorario;
	}
	/**
	 * @param honorario the honorario to set
	 */
	public void setHonorario(BigDecimal honorario) {
		this.honorario = honorario;
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
	 * @return the etapa
	 */
	public Etapa getEtapa() {
		return etapa;
	}
	/**
	 * @param etapa the etapa to set
	 */
	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
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
	public enum Andamento{
		Contato, EnvioProposta, FollowUp,Fechamento;
	}
	/**
	 * @return the andamento
	 */
	public String getAndamento() {
		return andamento;
	}
	/**
	 * @param andamento the andamento to set
	 */
	public void setAndamento(String andamento) {
		this.andamento = andamento;
	}
	/**
	 * @return the contato
	 */
	public Date getContato() {
		return contato;
	}
	/**
	 * @param contato the contato to set
	 */
	public void setContato(Date contato) {
		this.contato = contato;
	}
	/**
	 * @return the envioProposta
	 */
	public Date getEnvioProposta() {
		return envioProposta;
	}
	/**
	 * @param envioProposta the envioProposta to set
	 */
	public void setEnvioProposta(Date envioProposta) {
		this.envioProposta = envioProposta;
	}
	/**
	 * @return the followUp
	 */
	public Date getFollowUp() {
		return followUp;
	}
	/**
	 * @param followUp the followUp to set
	 */
	public void setFollowUp(Date followUp) {
		this.followUp = followUp;
	}
	/**
	 * @return the fechamento
	 */
	public Date getFechamento() {
		return fechamento;
	}
	/**
	 * @param fechamento the fechamento to set
	 */
	public void setFechamento(Date fechamento) {
		this.fechamento = fechamento;
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
	 * @return the empresa
	 */
	public Empresa getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the pessoa
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}
	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	/**
	 * @return the servicosContratados
	 */
	public Set<ServicoContratado> getServicosContratados() {
		return servicosContratados;
	}
	/**
	 * @param servicosContratados the servicosContratados to set
	 */
	public void setServicosContratados(Set<ServicoContratado> servicosContratados) {
		this.servicosContratados = servicosContratados;
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
	 * @return the pessoaFisicaOrJuridica
	 */
	public PfPj getPessoaFisicaOrJuridica() {
		return pessoaFisicaOrJuridica;
	}
	/**
	 * @param pessoaFisicaOrJuridica the pessoaFisicaOrJuridica to set
	 */
	public void setPessoaFisicaOrJuridica(PfPj pessoaFisicaOrJuridica) {
		this.pessoaFisicaOrJuridica = pessoaFisicaOrJuridica;
	}
		
}
