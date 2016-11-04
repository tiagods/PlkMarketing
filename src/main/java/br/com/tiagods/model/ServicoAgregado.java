package br.com.tiagods.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class ServicoAgregado implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private Set<ServicoContratado> servicosContratados = new LinkedHashSet<ServicoContratado>();
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
}
