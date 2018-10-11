package br.com.tiagods.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.tiagods.modelcollections.NegocioEmpresa;
import br.com.tiagods.modelcollections.NegocioPessoa;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.modelcollections.NegocioProspeccao;
@Entity
@Table(name="tarefa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tarefa_type")
public abstract class NegocioTarefa implements AbstractEntity,Serializable{
	private static final long serialVersionUID = 1L;
	public enum TipoTarefa{
		REUNIAO("Reunião"), PROPOSTA("Proposta"), 
		TELEFONE("Telefone"),EMAIL("E-Mail"),WHATSAPP ("WhatsApp");
		private String descricao;
		TipoTarefa(String descricao){
			this.descricao=descricao;
		}
		public String getDescricao() {
			return descricao;
		}
		@Override
		public String toString() {
			return getDescricao();
		}
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TAR_COD")
	private Long id;
	@Column(name="TAR_NOME")
	private String descricao="";
	@Column(name="TAR_DATAEVENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataEvento;
	@Column(name="TAR_CRIADOEM")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar criadoEm;
	@ManyToOne
	@JoinColumn(name="TAR_CRIADOPOR_COD")
	private Usuario criadoPor;
	@ManyToOne
	@JoinColumn(name="TAR_ATENDENTE_COD")
	private Usuario atendente;
	
	@Column(name="tipo")
	@Enumerated(value=EnumType.STRING)
	private TipoTarefa tipoTarefa;
	//excluir
	@Column(name="TAR_CLASSE")
	private String classe="";
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TAR_PESSOA_COD")
	private NegocioPessoa pessoa;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TAR_EMPRESA_COD")
	private NegocioEmpresa empresa;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TAR_NEGOCIO_COD")
	private NegocioProposta negocio;
	
	@ManyToOne
	@JoinColumn(name="TAR_PROSPECCAO_COD")
	private NegocioProspeccao prospeccao;

	@Column(name="TAR_FORMULARIO")
	private String formulario;

	@Column(name="TAR_FINALIZADO")
	private int finalizado=0;
	@Column(name="TAR_ALERTAENVIADO")
	private int alertaEnviado=0;
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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the dataEvento
	 */
	public Calendar getDataEvento() {
		return dataEvento;
	}
	/**
	 * @param dataEvento the dataEvento to set
	 */
	public void setDataEvento(Calendar dataEvento) {
		this.dataEvento = dataEvento;
	}
	/**
	 * @return the classe
	 */
	public String getClasse() {
		return classe;
	}
	/**
	 * @param classe the classe to set
	 */
	public void setClasse(String classe) {
		this.classe = classe;
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
	 * @return the tipoTarefa
	 */
	public TipoTarefa getTipoTarefa() {
		return tipoTarefa;
	}
	/**
	 * @param tipoTarefa the tipoTarefa to set
	 */
	public void setTipoTarefa(TipoTarefa tipoTarefa) {
		this.tipoTarefa = tipoTarefa;
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
	 * @return the pessoa
	 */
	public NegocioPessoa getPessoa() {
		return pessoa;
	}
	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(NegocioPessoa pessoa) {
		this.pessoa = pessoa;
	}
	/**
	 * @return the empresa
	 */
	public NegocioEmpresa getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(NegocioEmpresa empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the negocio
	 */
	public NegocioProposta getNegocio() {
		return negocio;
	}
	/**
	 * @param negocio the negocio to set
	 */
	public void setNegocio(NegocioProposta negocio) {
		this.negocio = negocio;
	}
	/**
	 * @return the prospeccao
	 */
	public NegocioProspeccao getProspeccao() {
		return prospeccao;
	}
	/**
	 * @param prospeccao the prospeccao to set
	 */
	public void setProspeccao(NegocioProspeccao prospeccao) {
		this.prospeccao = prospeccao;
	}
	/**
	 * @return the finalizado
	 */
	public int getFinalizado() {
		return finalizado;
	}
	/**
	 * @param finalizado the finalizado to set
	 */
	public void setFinalizado(int finalizado) {
		this.finalizado = finalizado;
	}
	/**
	 * @return the alertaEnviado
	 */
	public int getAlertaEnviado() {
		return alertaEnviado;
	}
	/**
	 * @param alertaEnviado the alertaEnviado to set
	 */
	public void setAlertaEnviado(int alertaEnviado) {
		this.alertaEnviado = alertaEnviado;
	}

	public String getFormulario() {
		return formulario;
	}
	public void setFormulario(String formulario) {
		this.formulario = formulario;
	}

	@Override
	public String toString() {
		return "";
	}
}
