package br.com.tiagods.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PessoaJuridica implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String razao;
	private String cnpj;
	private String im;
	private String ie;
	private String responsavel;
	private String apelido;
	
	/**
	 * @return the razao
	 */
	public String getRazao() {
		return razao;
	}
	/**
	 * @param razao the razao to set
	 */
	public void setRazao(String razao) {
		this.razao = razao;
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
	 * @return the im
	 */
	public String getIm() {
		return im;
	}
	/**
	 * @param im the im to set
	 */
	public void setIm(String im) {
		this.im = im;
	}
	/**
	 * @return the ie
	 */
	public String getIe() {
		return ie;
	}
	/**
	 * @param ie the ie to set
	 */
	public void setIe(String ie) {
		this.ie = ie;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	/**
	 * @return the apelido
	 */
	public String getApelido() {
		return apelido;
	}
	/**
	 * @param apelido the apelido to set
	 */
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	
}
