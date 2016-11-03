package br.com.tiagods.model;

public class Cidade {
	private int id;
	private String nome;
	private String estado;
	private int idExtra;
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
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the codExtra
	 */
	public int getCodExtra() {
		return idExtra;
	}
	/**
	 * @param codExtra the codExtra to set
	 */
	public void setCodExtra(int idExtra) {
		this.idExtra = idExtra;
	}
	
}
