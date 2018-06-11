package br.com.tiagods.modelcollections;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.tiagods.model.AbstractEntity;

@Entity
@Table(name="NEGOCIO_STATUS")
public class NegocioStatus implements AbstractEntity,Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NEG_STA_COD")
	private Long id;
	@Column(name="NEG_STA_NOME")
	private String nome;
	
	public NegocioStatus() {}
	
	public NegocioStatus(long id, String nome) {
		this.id=id;
		this.nome=nome;
	}
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

}
