package br.com.tiagods.modelcollections;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import br.com.tiagods.model.Categoria;
import br.com.tiagods.model.Nivel;
import br.com.tiagods.model.Origem;
import br.com.tiagods.model.Servico;
import br.com.tiagods.model.Usuario;

//@MappedSuperclass
@Embeddable
public class NegocioPadrao implements Serializable{
	private static final long serialVersionUID = 1L;
	@ManyToOne
	private Origem origem;
	@ManyToOne
	private Usuario atendente;
	//private String detalhesOrigem;
	//private String resumo;
	//private String apresentacao;
	
	@ManyToOne
	private Servico servico;
	@ManyToOne
	//@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	@ManyToOne
	//@JoinColumn(name = "nivel_id")
	private Nivel nivel;
	private String departamento;
	
	/**
	 * @return the origem
	 */
	public Origem getOrigem() {
		return origem;
	}
	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(Origem origem) {
		this.origem = origem;
	}
	/**
	 * @return the atendente
	 */
	public Usuario getAtendente() {
		return atendente;
	}
	/**
	 * @param atendente the atendente to set
	 */
	public void setAtendente(Usuario atendente) {
		this.atendente = atendente;
	}
	/**
	 * @return the servico
	 */
	public Servico getServico() {
		return servico;
	}
	/**
	 * @param servico the servico to set
	 */
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the nivel
	 */
	public Nivel getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	
	
	
}
