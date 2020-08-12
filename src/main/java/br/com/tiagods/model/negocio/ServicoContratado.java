package br.com.tiagods.model.negocio;

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

@Entity
@Table(name="SERVICO_CONTRATADO")
public class ServicoContratado implements Serializable{
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
	
	@ManyToOne
	@JoinColumn(name="SER_CON_NEGOCIO_COD")
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
		ServicoContratado other = (ServicoContratado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
