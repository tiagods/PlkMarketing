/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.migracao.protocolo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Tiago
 */
public class ProtocoloSaidaBean implements Serializable{
    private int id;
    private String empresaNome;
    private int cliente;
    private int usuarioId;
    private String funcionarioPara;
    private String setorPara;
    private String observacao;
    private Date data;
    private String comprovante;
    private int protocoloEntradaId;
    private Set<ProtocoloItemBean> items = new HashSet();

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
     * @return the empresaNome
     */
    public String getEmpresaNome() {
        return empresaNome;
    }

    /**
     * @param empresaNome the empresaNome to set
     */
    public void setEmpresaNome(String empresaNome) {
        this.empresaNome = empresaNome;
    }

    /**
     * @return the cliente
     */
    public int getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the usuarioId
     */
    public int getUsuarioId() {
        return usuarioId;
    }

    /**
     * @param usuarioId the usuarioId to set
     */
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * @return the funcionarioPara
     */
    public String getFuncionarioPara() {
        return funcionarioPara;
    }

    /**
     * @param funcionarioPara the funcionarioPara to set
     */
    public void setFuncionarioPara(String funcionarioPara) {
        this.funcionarioPara = funcionarioPara;
    }

    /**
     * @return the setorPara
     */
    public String getSetorPara() {
        return setorPara;
    }

    /**
     * @param setorPara the setorPara to set
     */
    public void setSetorPara(String setorPara) {
        this.setorPara = setorPara;
    }

    /**
     * @return the observacao
     */
    public String getObservacao() {
        return observacao;
    }

    /**
     * @param observacao the observacao to set
     */
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the comprovante
     */
    public String getComprovante() {
        return comprovante;
    }

    /**
     * @param comprovante the comprovante to set
     */
    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    /**
     * @return the protocoloEntradaId
     */
    public int getProtocoloEntradaId() {
        return protocoloEntradaId;
    }

    /**
     * @param protocoloEntradaId the protocoloEntradaId to set
     */
    public void setProtocoloEntradaId(int protocoloEntradaId) {
        this.protocoloEntradaId = protocoloEntradaId;
    }
    /**
     * @return the items
     */
    public Set<ProtocoloItemBean> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(Set<ProtocoloItemBean> items) {
        this.items = items;
    }
    
}
