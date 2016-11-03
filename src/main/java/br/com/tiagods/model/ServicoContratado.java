package br.com.tiagods.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ServicoContratado implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private BigDecimal valor;
	private ServicoAgregado servicosAgregados;
	
	/**
	 * @return the servicoAgregado
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param servicoAgregado the servicoAgregado to set
	 */
	public void setId(int id) {
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
	
}
