package br.com.tiagods.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class Pessoa extends PfPj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private String cpf;
	private String dataNascimento;
	private PfPj pessoaFisica;
	private Endereco endereco;
	
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
	 * @return the dataNascimento
	 */
	public String getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	/**
	 * @return the pessoaFisica
	 */
	public PfPj getPessoaFisica() {
		return pessoaFisica;
	}

	/**
	 * @param pessoaFisica the pessoaFisica to set
	 */
	public void setPessoaFisica(PfPj pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
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
}
