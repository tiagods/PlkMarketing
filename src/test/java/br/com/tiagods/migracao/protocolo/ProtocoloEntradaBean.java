/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiagods.migracao.protocolo;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Tiago
 */
public class ProtocoloEntradaBean implements Serializable{
private int id;
private Date dataRecebimento;
private String quemEntregou;
private String empresa;
private String apelido;
private String historico;
private String paraQuem;
private String quemRecebeu;
private Date dataFuncionarioRecebeu;
private String observacao;
private String departamento;
private Time hora;
private String alerta;
private String recebido;
private int paraQuemId;
private int quemRecebeuId;
private int passivelDevolucao;
private int devolvido;
private Date devolverAte;
private int adiado;
private String adiadoMotivo;
private Set<ProtocoloItemBean> items = new HashSet<>();

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
     * @return the dataRecebimento
     */
    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    /**
     * @param dataRecebimento the dataRecebimento to set
     */
    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    /**
     * @return the quemEntregou
     */
    public String getQuemEntregou() {
        return quemEntregou;
    }

    /**
     * @param quemEntregou the quemEntregou to set
     */
    public void setQuemEntregou(String quemEntregou) {
        this.quemEntregou = quemEntregou;
    }

    /**
     * @return the empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * @return the apelido
     */
    public String getApelido() {
        return apelido;
    }

    /**
     * @param apelido the apelido to set
     */
    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    /**
     * @return the historico
     */
    public String getHistorico() {
        return historico;
    }

    /**
     * @param historico the historico to set
     */
    public void setHistorico(String historico) {
        this.historico = historico;
    }

    /**
     * @return the paraQuem
     */
    public String getParaQuem() {
        return paraQuem;
    }

    /**
     * @param paraQuem the paraQuem to set
     */
    public void setParaQuem(String paraQuem) {
        this.paraQuem = paraQuem;
    }

    /**
     * @return the quemRecebeu
     */
    public String getQuemRecebeu() {
        return quemRecebeu;
    }

    /**
     * @param quemRecebeu the quemRecebeu to set
     */
    public void setQuemRecebeu(String quemRecebeu) {
        this.quemRecebeu = quemRecebeu;
    }

    /**
     * @return the dataFuncionarioRecebeu
     */
    public Date getDataFuncionarioRecebeu() {
        return dataFuncionarioRecebeu;
    }

    /**
     * @param dataFuncionarioRecebeu the dataFuncionarioRecebeu to set
     */
    public void setDataFuncionarioRecebeu(Date dataFuncionarioRecebeu) {
        this.dataFuncionarioRecebeu = dataFuncionarioRecebeu;
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
     * @return the departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the hora
     */
    public Time getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(Time hora) {
        this.hora = hora;
    }

    /**
     * @return the alerta
     */
    public String getAlerta() {
        return alerta;
    }

    /**
     * @param alerta the alerta to set
     */
    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    /**
     * @return the recebido
     */
    public String getRecebido() {
        return recebido;
    }

    /**
     * @param recebido the recebido to set
     */
    public void setRecebido(String recebido) {
        this.recebido = recebido;
    }

    /**
     * @return the paraQuemId
     */
    public int getParaQuemId() {
        return paraQuemId;
    }

    /**
     * @param paraQuemId the paraQuemId to set
     */
    public void setParaQuemId(int paraQuemId) {
        this.paraQuemId = paraQuemId;
    }

    /**
     * @return the quemRecebeuId
     */
    public int getQuemRecebeuId() {
        return quemRecebeuId;
    }

    /**
     * @param quemRecebeuId the quemRecebeuId to set
     */
    public void setQuemRecebeuId(int quemRecebeuId) {
        this.quemRecebeuId = quemRecebeuId;
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
     * @return the devolvido
     */
    public int getDevolvido() {
        return devolvido;
    }

    /**
     * @param devolvido the devolvido to set
     */
    public void setDevolvido(int devolvido) {
        this.devolvido = devolvido;
    }

    /**
     * @return the devolverAte
     */
    public Date getDevolverAte() {
        return devolverAte;
    }

    /**
     * @param devolverAte the devolverAte to set
     */
    public void setDevolverAte(Date devolverAte) {
        this.devolverAte = devolverAte;
    }

    /**
     * @return the adiado
     */
    public int getAdiado() {
        return adiado;
    }

    /**
     * @param adiado the adiado to set
     */
    public void setAdiado(int adiado) {
        this.adiado = adiado;
    }

    /**
     * @return the adiadoMotivo
     */
    public String getAdiadoMotivo() {
        return adiadoMotivo;
    }

    /**
     * @param adiadoMotivo the adiadoMotivo to set
     */
    public void setAdiadoMotivo(String adiadoMotivo) {
        this.adiadoMotivo = adiadoMotivo;
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
