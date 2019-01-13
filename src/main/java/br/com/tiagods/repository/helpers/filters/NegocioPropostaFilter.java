package br.com.tiagods.repository.helpers.filters;

import br.com.tiagods.model.*;
import br.com.tiagods.model.NegocioProposta;

import java.time.LocalDate;

public class NegocioPropostaFilter {
    NegocioProposta.TipoStatus status = NegocioProposta.TipoStatus.STATUS;
    NegocioProposta.TipoEtapa etapa = NegocioProposta.TipoEtapa.ETAPA;
    NegocioCategoria categoria;
    NegocioNivel nivel;
    NegocioOrigem origem;
    NegocioServico servico;
    Usuario atendente;
    LocalDate dataInicial;
    LocalDate dataFinal;
    String dataFiltro;
    String ordenacao;
    String pesquisa;

    public NegocioProposta.TipoStatus getStatus() {
        return status;
    }

    public void setStatus(NegocioProposta.TipoStatus status) {
        this.status = status;
    }

    public NegocioProposta.TipoEtapa getEtapa() {
        return etapa;
    }

    public void setEtapa(NegocioProposta.TipoEtapa etapa) {
        this.etapa = etapa;
    }

    public NegocioCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(NegocioCategoria categoria) {
        this.categoria = categoria;
    }

    public NegocioNivel getNivel() {
        return nivel;
    }

    public void setNivel(NegocioNivel nivel) {
        this.nivel = nivel;
    }

    public NegocioOrigem getOrigem() {
        return origem;
    }

    public void setOrigem(NegocioOrigem origem) {
        this.origem = origem;
    }

    public NegocioServico getServico() {
        return servico;
    }

    public void setServico(NegocioServico servico) {
        this.servico = servico;
    }

    public Usuario getAtendente() {
        return atendente;
    }

    public void setAtendente(Usuario atendente) {
        this.atendente = atendente;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getDataFiltro() {
        return dataFiltro;
    }

    public void setDataFiltro(String dataFiltro) {
        this.dataFiltro = dataFiltro;
    }

    public String getOrdenacao() {
        return ordenacao;
    }

    public void setOrdenacao(String ordenacao) {
        this.ordenacao = ordenacao;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }
}
