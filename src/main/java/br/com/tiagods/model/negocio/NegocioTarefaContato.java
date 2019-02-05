package br.com.tiagods.model.negocio;

import br.com.tiagods.model.NegocioTarefa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "contato")
public class NegocioTarefaContato extends NegocioTarefa {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="contato_id")
	private Contato contato;
	
	public NegocioTarefaContato() {}
	public NegocioTarefaContato(Long id) {
		setId(id);
	}
	/**
	 * @return the contato
	 */
	public Contato getContato() {
		return contato;
	}
	/**
	 * @param contato the contato to set
	 */
	public void setContato(Contato contato) {
		this.contato = contato;
	}

	@Override
	public String toString() {
		String nome = contato!=null?contato.getNome():"";
		return nome;
	}
}
