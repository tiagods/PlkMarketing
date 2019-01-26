package br.com.tiagods.migracao.protocolo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Tiago
 */
public class ProtocoloItemBean implements Serializable{
    private int id;
    private String nome;
    private int quantidade;
    private String detalhe;
    private Date dataEntrada;
    private Date dataSaida;
    private int clienteId;
    private int protocoloEntrada;
    //mudar para int
    private int protocoloSaida;
    private int passivelDevolucao;
    private int entregue;
    private int item_responsavel_id;
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
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
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the detalhe
     */
    public String getDetalhe() {
        return detalhe;
    }

    /**
     * @param detalhe the detalhe to set
     */
    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    /**
     * @return the dataEntrada
     */
    public Date getDataEntrada() {
        return dataEntrada;
    }

    /**
     * @param dataEntrada the dataEntrada to set
     */
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    /**
     * @return the dataSaida
     */
    public Date getDataSaida() {
        return dataSaida;
    }

    /**
     * @param dataSaida the dataSaida to set
     */
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    /**
     * @return the clienteId
     */
    public int getClienteId() {
        return clienteId;
    }

    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * @return the protocoloEntrada
     */
    public int getProtocoloEntrada() {
        return protocoloEntrada;
    }

    /**
     * @param protocoloEntrada the protocoloEntrada to set
     */
    public void setProtocoloEntrada(int protocoloEntrada) {
        this.protocoloEntrada = protocoloEntrada;
    }

    /**
     * @return the protocoloSaida
     */
    public int getProtocoloSaida() {
        return protocoloSaida;
    }

    /**
     * @param protocoloSaida the protocoloSaida to set
     */
    public void setProtocoloSaida(int protocoloSaida) {
        this.protocoloSaida = protocoloSaida;
    }

    /**
     * @return the passivelDevolucao
     */
    public int getPassivelDevolucao() {
        return passivelDevolucao;
    }

    /**
     * @param passivelDevolucao the passivelDevolucao to set
     */
    public void setPassivelDevolucao(int passivelDevolucao) {
        this.passivelDevolucao = passivelDevolucao;
    }

    /**
     * @return the entregue
     */
    public int getEntregue() {
        return entregue;
    }

    /**
     * @param entregue the entregue to set
     */
    public void setEntregue(int entregue) {
        this.entregue = entregue;
    }

    /**
     * @return the item_responsavel_id
     */
    public int getItem_responsavel_id() {
        return item_responsavel_id;
    }

    /**
     * @param item_responsavel_id the item_responsavel_id to set
     */
    public void setItem_responsavel_id(int item_responsavel_id) {
        this.item_responsavel_id = item_responsavel_id;
    }
     
}
