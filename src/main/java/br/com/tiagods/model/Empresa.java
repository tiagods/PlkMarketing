package br.com.tiagods.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Empresa implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private Endereco endereco;
	private PfPj pessoaJuridica;
	private String cnpj;
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
	/**
	 * @return the endereco
	 */
	public Endereco getEndereco() {
		return endereco;
	}
	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	/**
	 * @return the pessoaJuridica
	 */
	public PfPj getPessoaJuridica() {
		return pessoaJuridica;
	}
	/**
	 * @param pessoaJuridica the pessoaJuridica to set
	 */
	public void setPessoaJuridica(PfPj pessoaJuridica) {
		this.pessoaJuridica = pessoaJuridica;
	}
	
}
