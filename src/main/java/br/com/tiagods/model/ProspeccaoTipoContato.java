package br.com.tiagods.model;

import java.io.Serializable;

public class ProspeccaoTipoContato implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6128803021370214234L;
	private int id;
	private int nome;
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
	public int getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
	 */
	public void setNome(int nome) {
		this.nome = nome;
	}
	
	
}
