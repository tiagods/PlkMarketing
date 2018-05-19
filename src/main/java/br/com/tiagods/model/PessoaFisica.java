package br.com.tiagods.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PessoaFisica implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rg;
	private String cpf;
	private String aniversario;
	
	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}
	/**
	 * @param rg the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}
	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}
	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	/**
	 * @return the aniversario
	 */
	public String getAniversario() {
		return aniversario;
	}
	/**
	 * @param aniversario the aniversario to set
	 */
	public void setAniversario(String aniversario) {
		this.aniversario = aniversario;
	}


}
