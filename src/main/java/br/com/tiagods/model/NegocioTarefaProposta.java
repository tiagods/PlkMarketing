package br.com.tiagods.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.tiagods.modelcollections.NegocioProposta;

@Entity
@DiscriminatorValue(value = "proposta")
public class NegocioTarefaProposta extends NegocioTarefa{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="proposta_id")
	NegocioProposta proposta;	
	
	public NegocioTarefaProposta() {}
	public NegocioTarefaProposta(Long id) {
		setId(id);
	}

	@Override
	public String toString() {
		String nome = proposta!=null?proposta.getNome():"";
		return nome;
	}

	/**
	 * @return the proposta
	 */
	public NegocioProposta getProposta() {
		return proposta;
	}

	/**
	 * @param proposta the proposta to set
	 */
	public void setProposta(NegocioProposta proposta) {
		this.proposta = proposta;
	}
	
}
