package br.com.tiagods.model;

import java.io.Serializable;
import java.util.Date;

public class Documento implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1020152610379969428L;
	private int id;
	private String nome;
	private String descricao;
	private Usuario usuario;
	private Date data;
	private String url;
	private Negocio negocio;
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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the negocio
	 */
	public Negocio getNegocio() {
		return negocio;
	}
	/**
	 * @param negocio the negocio to set
	 */
	public void setNegocio(Negocio negocio) {
		this.negocio = negocio;
	}
	
	
}
