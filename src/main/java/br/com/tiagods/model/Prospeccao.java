package br.com.tiagods.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Prospeccao implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 352778741628193878L;
	private int id;
	private String nome;
	private String responsavel;
	private String departamento;
	private PfPj pfpj;
	private int conviteParaEventos=0;
	private int material=0;
	private int newsletter=0;
	private ProspeccaoTipoContato tipoContato;
	private String endereco;
	private Set<Lista> listas = new HashSet<>();
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
	 * @return the responsavel
	 */
	public String getResponsavel() {
		return responsavel;
	}
	/**
	 * @param responsavel the responsavel to set
	 */
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}
	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	/**
	 * @return the pfpj
	 */
	public PfPj getPfpj() {
		return pfpj;
	}
	/**
	 * @param pfpj the pfpj to set
	 */
	public void setPfpj(PfPj pfpj) {
		this.pfpj = pfpj;
	}
	/**
	 * @return the apresentacoes
	 */
	public int getConviteParaEventos() {
		return conviteParaEventos;
	}
	/**
	 * @param apresentacoes the apresentacoes to set
	 */
	public void setConviteParaEventos(int conviteParaEventos) {
		this.conviteParaEventos = conviteParaEventos;
	}
	/**
	 * @return the material
	 */
	public int getMaterial() {
		return material;
	}
	/**
	 * @param material the material to set
	 */
	public void setMaterial(int material) {
		this.material = material;
	}
	/**
	 * @return the newsletter
	 */
	public int getNewsletter() {
		return newsletter;
	}
	/**
	 * @param newsletter the newsletter to set
	 */
	public void setNewsletter(int newsletter) {
		this.newsletter = newsletter;
	}
	/**
	 * @return the tipoContato
	 */
	public ProspeccaoTipoContato getTipoContato() {
		return tipoContato;
	}
	/**
	 * @param tipoContato the tipoContato to set
	 */
	public void setTipoContato(ProspeccaoTipoContato tipoContato) {
		this.tipoContato = tipoContato;
	}
	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}
	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	/**
	 * @return the listas
	 */
	public Set<Lista> getListas() {
		return listas;
	}
	/**
	 * @param listas the listas to set
	 */
	public void setListas(Set<Lista> listas) {
		this.listas = listas;
	}


}
