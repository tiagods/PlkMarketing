package br.com.tiagods.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Empresa extends PfPj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cnpj;
	private Set<Pessoa> pessoas = new LinkedHashSet<Pessoa>();
	/**
	 * @return the cnpj
	 */
	public String getCnpj() {
		return cnpj;
	}
	/**
	 * @param cnpj the cnpj to set
	 */
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
