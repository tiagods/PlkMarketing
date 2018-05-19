package br.com.tiagods.modelcollections;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.Usuario;

@Entity
public class Lista implements AbstractEntity,Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LIS_COD")
	private Long id;
	@Column(name="LIS_NOME")
	private String nome;
	@Column(name="LIS_DETALHES")
	private String detalhes;
	@Column(name="LIS_CRIADOEM")
	private Calendar criadoEm;
	@JoinColumn(name="LIS_CRIADOPOR_COD")
	private Usuario criadoPor;
	/*
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="PROSPECCAO_REL_LISTA",
            joinColumns = { @JoinColumn(name = "LIS_REL_COD", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "PRO_REL_COD", referencedColumnName = "id") })
    */
	@Transient
	private Set<NegocioProspeccao> prospects = new HashSet<>();
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
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
	 * @return the detalhes
	 */
	public String getDetalhes() {
		return detalhes;
	}
	/**
	 * @param detalhes the detalhes to set
	 */
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	/**
	 * @return the criadoEm
	 */
	public Calendar getCriadoEm() {
		return criadoEm;
	}
	/**
	 * @param criadoEm the criadoEm to set
	 */
	public void setCriadoEm(Calendar criadoEm) {
		this.criadoEm = criadoEm;
	}
	/**
	 * @return the criadoPor
	 */
	public Usuario getCriadoPor() {
		return criadoPor;
	}
	/**
	 * @param criadoPor the criadoPor to set
	 */
	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
	}
	/**
	 * @return the prospects
	 */
	public Set<NegocioProspeccao> getProspects() {
		return prospects;
	}
	/**
	 * @param prospects the prospects to set
	 */
	public void setProspects(Set<NegocioProspeccao> prospects) {
		this.prospects = prospects;
	}

}
