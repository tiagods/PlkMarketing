package br.com.tiagods.modelcollections;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.tiagods.model.AbstractEntity;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.Contato;
import br.com.tiagods.model.NegocioDocumento;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.ServicoContratado;
import br.com.tiagods.model.Usuario;

@Entity
@Table(name="negocio")
public class NegocioProposta implements AbstractEntity,Serializable{
	public enum TipoEtapa{
		ETAPA(-1,"Etapa"),CONTATO(1,"Contato"),PROPOSTA(2,"Envio de Proposta"),
		FOLLOWUP(3,"Follow-up"),FECHAMENTO(4,"Fechamento"),INDEFINIDA(5,"Indefinida");
		
		private int index;
		private String descricao;
		
		private TipoEtapa(int index, String descricao) {
			this.index=index;
			this.descricao=descricao;
		}
		
		public String getDescricao() {
			return descricao;
		}
		public int getIndex() {
			return index;
		}
		@Override
		public String toString() {
			return getDescricao();
		}
	}
	public enum TipoStatus{
		STATUS(-1,"Status"),ANDAMENTO(1,"Em Andamento"),GANHO(2,"Ganho"),PERDIDO(3,"Perdido"),SEMMOVIMENTO(4,"Sem Movimento");
		
		private int index;
		private String descricao;
		
		private TipoStatus(int index, String descricao) {
			this.index=index;
			this.descricao=descricao;
		}
		
		public String getDescricao() {
			return descricao;
		}
		public int getIndex() {
			return index;
		}
		@Override
		public String toString() {
			return getDescricao();
		}
	}
	public enum MotivoPerda{
		DESISTENCIA("Desistencia"),INDEFINIDO("Indefinido"),
		PRAZO("Prazo"),PRECO("Preço"),SERVICO("Serviço");
		
		private String descricao;
		
		private MotivoPerda(String descricao) {
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NEG_COD")
	private Long id;
	
	@Column(name="NEG_CLASSE")
	private String classe="";
	
	@Column(name="NEG_NOME")
	private String nome="";
	
	@Column(name="NEG_DATAINICIO")
	@Temporal(TemporalType.DATE)
	private Calendar dataInicio;
	
	@Column(name="NEG_DATAFIM")
	@Temporal(TemporalType.DATE)
	private Calendar dataFim;
	
	@ManyToOne
	@JoinColumn(name="NEG_STA_COD")
	private NegocioStatus status;
	
	@ManyToOne
	@JoinColumn(name="NEG_ETA_COD")
	private NegocioEtapa etapa;
	
	@Column(name="NEG_ANDCONTATO")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar contato;
	
	@Column(name="NEG_ANDENVIOPROPOSTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar envioProposta;
	
	@Column(name="NEG_ANDFOLLOWUP")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar followUp;
	
	@Column(name="NEG_ANDFECHAMENTO")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fechamento;
	
	@Column(name="NEG_ANDINDEFINIDA")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar indefinida;
	
	@Column(name="NEG_DATAFINALIZACAO")
	@Temporal(TemporalType.DATE)
	private Calendar dataFinalizacao;
	
	@ManyToOne
	@JoinColumn(name="NEG_EMPRESA_COD")
	private NegocioEmpresa empresa;
	
	@ManyToOne
	@JoinColumn(name="NEG_PESSOA_COD")
	private NegocioPessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name="NEG_PROSPECCAO_COD")
	private NegocioProspeccao prospeccao;
	
	@Column(name="NEG_HONORARIO")
	private BigDecimal honorario=new BigDecimal(0.00);
	
	@Column(name="NEG_DESCRICAO",columnDefinition="text")
	private String descricao;
	
	@Column(name="NEG_MOTIVOPERDA")
	private MotivoPerda motivoPerda;
	
	@Column(name="NEG_DETALHESPERDA",columnDefinition="text")
	private String detalhesPerda="";
	
	@Temporal(TemporalType.DATE)
	@Column(name="NEG_DATAPERDA")
	private Calendar dataPerda;
	
	@OneToMany(fetch=FetchType.LAZY)
	private Set<NegocioTarefa> tarefas = new LinkedHashSet<>();
	@Transient
	private Set<ServicoContratado> servicosContratados = new LinkedHashSet<>();
	@Transient
	private Set<NegocioDocumento> documentos = new LinkedHashSet<>();
	
	@Column(name="NEG_CRIADOEM")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar criadoEm;
	
	@ManyToOne
	@JoinColumn(name="NEG_CRIADOPOR_COD")
	private Usuario criadoPor;
	
	@ManyToOne
	@JoinColumn(name = "NEG_ORIGEM_COD")
	private NegocioOrigem origem;
	@ManyToOne
	@JoinColumn(name = "NEG_ATENDENTE_COD")
	private Usuario atendente;
	
	@ManyToOne
	@JoinColumn(name = "NEG_SERVICO_COD")
	private NegocioServico servico;
	@ManyToOne
	//@JoinColumn(name = "categoria_id")
	@JoinColumn(name = "NEG_CATEGORIA_COD")
	private NegocioCategoria categoria;
	@ManyToOne
	//@JoinColumn(name = "nivel_id")
	@JoinColumn(name = "NEG_NIVEL_COD")
	private NegocioNivel nivel;
	
	@ManyToOne
	@JoinColumn(name = "contato_id")
	private Contato negocioContato;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="tipo_etapa")
	private TipoEtapa tipoEtapa;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="tipo_status")
	private TipoStatus tipoStatus;
	
	public NegocioProposta() {
	}
	
	public NegocioProposta(long id) {
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
	 * @return the dataInicio
	 */
	public Calendar getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(Calendar dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataFim
	 */
	public Calendar getDataFim() {
		return dataFim;
	}

	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(Calendar dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return the status
	 */
	public NegocioStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(NegocioStatus status) {
		this.status = status;
	}

	/**
	 * @return the etapa
	 */
	public NegocioEtapa getEtapa() {
		return etapa;
	}

	/**
	 * @param etapa the etapa to set
	 */
	public void setEtapa(NegocioEtapa etapa) {
		this.etapa = etapa;
	}

	/**
	 * @return the contato
	 */
	public Calendar getContato() {
		return contato;
	}

	/**
	 * @param contato the contato to set
	 */
	public void setContato(Calendar contato) {
		this.contato = contato;
	}

	/**
	 * @return the envioProposta
	 */
	public Calendar getEnvioProposta() {
		return envioProposta;
	}

	/**
	 * @param envioProposta the envioProposta to set
	 */
	public void setEnvioProposta(Calendar envioProposta) {
		this.envioProposta = envioProposta;
	}

	/**
	 * @return the followUp
	 */
	public Calendar getFollowUp() {
		return followUp;
	}

	/**
	 * @param followUp the followUp to set
	 */
	public void setFollowUp(Calendar followUp) {
		this.followUp = followUp;
	}

	/**
	 * @return the fechamento
	 */
	public Calendar getFechamento() {
		return fechamento;
	}

	/**
	 * @param fechamento the fechamento to set
	 */
	public void setFechamento(Calendar fechamento) {
		this.fechamento = fechamento;
	}

	/**
	 * @return the indefinida
	 */
	public Calendar getIndefinida() {
		return indefinida;
	}

	/**
	 * @param indefinida the indefinida to set
	 */
	public void setIndefinida(Calendar indefinida) {
		this.indefinida = indefinida;
	}

	/**
	 * @return the dataFinalizacao
	 */
	public Calendar getDataFinalizacao() {
		return dataFinalizacao;
	}

	/**
	 * @param dataFinalizacao the dataFinalizacao to set
	 */
	public void setDataFinalizacao(Calendar dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
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
	 * @return the honorario
	 */
	public BigDecimal getHonorario() {
		return honorario;
	}

	/**
	 * @param honorario the honorario to set
	 */
	public void setHonorario(BigDecimal honorario) {
		this.honorario = honorario;
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
	 * @return the motivoPerda
	 */
	public MotivoPerda getMotivoPerda() {
		return motivoPerda;
	}

	/**
	 * @param motivoPerda the motivoPerda to set
	 */
	public void setMotivoPerda(MotivoPerda motivoPerda) {
		this.motivoPerda = motivoPerda;
	}

	/**
	 * @return the detalhesPerda
	 */
	public String getDetalhesPerda() {
		return detalhesPerda;
	}

	/**
	 * @param detalhesPerda the detalhesPerda to set
	 */
	public void setDetalhesPerda(String detalhesPerda) {
		this.detalhesPerda = detalhesPerda;
	}

	/**
	 * @return the dataPerda
	 */
	public Calendar getDataPerda() {
		return dataPerda;
	}

	/**
	 * @param dataPerda the dataPerda to set
	 */
	public void setDataPerda(Calendar dataPerda) {
		this.dataPerda = dataPerda;
	}

	/**
	 * @return the tarefas
	 */
	public Set<NegocioTarefa> getTarefas() {
		return tarefas;
	}

	/**
	 * @param tarefas the tarefas to set
	 */
	public void setTarefas(Set<NegocioTarefa> tarefas) {
		this.tarefas = tarefas;
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

	/**
	 * @return the documentos
	 */
	public Set<NegocioDocumento> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(Set<NegocioDocumento> documentos) {
		this.documentos = documentos;
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
	 * @return the padrao
	 */
	
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
	 * @return the negocioContato
	 */
	public Contato getNegocioContato() {
		return negocioContato;
	}

	/**
	 * @param negocioContato the negocioContato to set
	 */
	public void setNegocioContato(Contato negocioContato) {
		this.negocioContato = negocioContato;
	}

	/**
	 * @return the tipoEtapa
	 */
	public TipoEtapa getTipoEtapa() {
		return tipoEtapa;
	}

	/**
	 * @param tipoEtapa the tipoEtapa to set
	 */
	public void setTipoEtapa(TipoEtapa tipoEtapa) {
		this.tipoEtapa = tipoEtapa;
	}

	/**
	 * @return the tipoStatus
	 */
	public TipoStatus getTipoStatus() {
		return tipoStatus;
	}

	/**
	 * @param tipoStatus the tipoStatus to set
	 */
	public void setTipoStatus(TipoStatus tipoStatus) {
		this.tipoStatus = tipoStatus;
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
		NegocioProposta other = (NegocioProposta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return nome;
	}	
}
