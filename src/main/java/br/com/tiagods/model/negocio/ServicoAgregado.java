package br.com.tiagods.model.negocio;

import br.com.tiagods.model.AbstractEntity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="servicosAgregados")
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
	@Override
	public String toString() {
		return this.nome;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicoAgregado other = (ServicoAgregado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
