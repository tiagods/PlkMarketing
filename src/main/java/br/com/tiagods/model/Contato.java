package br.com.tiagods.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import br.com.tiagods.modelcollections.NegocioProposta;

@Entity
public class Contato extends Pessoa implements AbstractEntity,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	public enum PessoaTipo {
		EMPRESA("Empresa"), PESSOA("Pessoa");

		private String descricao;
		PessoaTipo(String descricao) {
			this.descricao=descricao;
		}
		public String getDescricao() {
			return descricao;
		}
	}
	public enum ContatoTipo{
		GENERICO("Generico"),PROSPECCAO("Prospecção"),SONDAGEM("Sondagem");
		private String descricao;
		private ContatoTipo(String descricao) {
			this.descricao=descricao;
		}
		public String getDescricao() {
			return descricao;
		}
	}
	@Embedded
	private PessoaFisica fisico;
	@Embedded
	private PessoaJuridica juridico;
	@Enumerated(value= EnumType.STRING)
	private PessoaTipo tipo;
	
	@ManyToOne
	@JoinColumn(name = "ultimo_negocio_id")
	private NegocioProposta ultimoNegocio;
		
	@ManyToOne
	@JoinColumn(name = "origem_id")
	private NegocioOrigem origem;
	@ManyToOne
	@JoinColumn(name = "atendente_id")
	private Usuario atendente;
	
	@Column(name="detalhes_origem")
	private String detalhesOrigem;
	
	private String resumo;
	
	private String apresentacao;
	
	@ManyToOne
	@JoinColumn(name = "servico_id")
	private NegocioServico servico;
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private NegocioCategoria categoria;
	@ManyToOne
	@JoinColumn(name = "nivel_id")
	private NegocioNivel nivel;
	
	@Transient
	private Set<NegocioProposta> negocios = new LinkedHashSet<>();
	@Transient
	private Set<NegocioTarefaContato> tarefas = new LinkedHashSet<>();
	public Contato() {
		// TODO Auto-generated constructor stub
	}
	public Contato(long id) {
		this.id=id;
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
	 * @return the fisico
	 */
	public PessoaFisica getFisico() {
		return fisico;
	}
	/**
	 * @param fisico the fisico to set
	 */
	public void setFisico(PessoaFisica fisico) {
		this.fisico = fisico;
	}
	/**
	 * @return the juridico
	 */
	public PessoaJuridica getJuridico() {
		return juridico;
	}
	/**
	 * @param juridico the juridico to set
	 */
	public void setJuridico(PessoaJuridica juridico) {
		this.juridico = juridico;
	}
	/**
	 * @return the tipo
	 */
	public PessoaTipo getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(PessoaTipo tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the ultimoNegocio
	 */
	public NegocioProposta getUltimoNegocio() {
		return ultimoNegocio;
	}
	/**
	 * @param ultimoNegocio the ultimoNegocio to set
	 */
	public void setUltimoNegocio(NegocioProposta ultimoNegocio) {
		this.ultimoNegocio = ultimoNegocio;
	}
	/**
	 * @return the origem
	 */
	public NegocioOrigem getOrigem() {
		return origem;
	}
	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(NegocioOrigem origem) {
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
	 * @return the detalhesOrigem
	 */
	public String getDetalhesOrigem() {
		return detalhesOrigem;
	}
	/**
	 * @param detalhesOrigem the detalhesOrigem to set
	 */
	public void setDetalhesOrigem(String detalhesOrigem) {
		this.detalhesOrigem = detalhesOrigem;
	}
	/**
	 * @return the resumo
	 */
	public String getResumo() {
		return resumo;
	}
	/**
	 * @param resumo the resumo to set
	 */
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	/**
	 * @return the apresentacao
	 */
	public String getApresentacao() {
		return apresentacao;
	}
	/**
	 * @param apresentacao the apresentacao to set
	 */
	public void setApresentacao(String apresentacao) {
		this.apresentacao = apresentacao;
	}
	/**
	 * @return the servico
	 */
	public NegocioServico getServico() {
		return servico;
	}
	/**
	 * @param servico the servico to set
	 */
	public void setServico(NegocioServico servico) {
		this.servico = servico;
	}
	/**
	 * @return the categoria
	 */
	public NegocioCategoria getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(NegocioCategoria categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the nivel
	 */
	public NegocioNivel getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(NegocioNivel nivel) {
		this.nivel = nivel;
	}
	
	/**
	 * @return the negocios
	 */
	public Set<NegocioProposta> getNegocios() {
		return negocios;
	}
	/**
	 * @param negocios the negocios to set
	 */
	public void setNegocios(Set<NegocioProposta> negocios) {
		this.negocios = negocios;
	}
	/**
	 * @return the tarefas
	 */
	public Set<NegocioTarefaContato> getTarefas() {
		return tarefas;
	}
	/**
	 * @param tarefas the tarefas to set
	 */
	public void setTarefas(Set<NegocioTarefaContato> tarefas) {
		this.tarefas = tarefas;
	}
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
		Contato other = (Contato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		String newName = getNome();
		if(tipo.equals(PessoaTipo.EMPRESA))
			newName +=" || "+ juridico.getResponsavel()+" || "+juridico.getRazao();
		// TODO Auto-generated method stub
		return newName;
	}
}
