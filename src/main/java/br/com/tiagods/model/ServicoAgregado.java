package br.com.tiagods.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="SERVICO_AGREGADO")
public class ServicoAgregado implements AbstractEntity,Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SER_AGR_COD")
	private Long id;
	@Column(name="SER_AGR_NOME")
	private String nome;
	/*
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "SER_CON_SERVICOAGREGADO_COD")
	*/
	@Transient
	private Set<ServicoContratado> servicosContratados = new LinkedHashSet<>();
	
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
	 * @return the servicosContratados
	 */
	public Set<ServicoContratado> getServicosContratados() {
		return servicosContratados;
	}
	/**
	 * @param servicosContratados the servicosContratados to set
	 */
	public void setServicosContratados(Set<ServicoContratado> servicosContratados) {
		this.servicosContratados = servicosContratados;
	}
}
