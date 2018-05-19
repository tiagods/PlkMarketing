package br.com.tiagods.modelcollections;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.tiagods.model.AbstractEntity;

@Entity
@Table(name="SERVICO_CONTRATADO")
public class ServicoContratado implements AbstractEntity,Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SER_CON_COD")
	private Long id;
	@Column(name="SER_CON_VALOR")
	private BigDecimal valor;
	@ManyToOne
	@JoinColumn(name="SER_CON_SERVICOAGREGADO_COD")
	private ServicoAgregado servicosAgregados;
	/*
	@ManyToOne
	@JoinColumn(name="SER_CON_NEGOCIO_COD")
	*/
	@Transient
	private NegocioProposta negocios;
	
	/**
	 * @return the servicoAgregado
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param servicoAgregado the servicoAgregado to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the valor
	 */
	public BigDecimal getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	/**
	 * @return the servicosAgregados
	 */
	public ServicoAgregado getServicosAgregados() {
		return servicosAgregados;
	}
	/**
	 * @param servicosAgregados the servicosAgregados to set
	 */
	public void setServicosAgregados(ServicoAgregado servicosAgregados) {
		this.servicosAgregados = servicosAgregados;
	}
	/**
	 * @return the negocios
	 */
	public NegocioProposta getNegocios() {
		return negocios;
	}
	/**
	 * @param negocios the negocios to set
	 */
	public void setNegocios(NegocioProposta negocios) {
		this.negocios = negocios;
	}
	
}
