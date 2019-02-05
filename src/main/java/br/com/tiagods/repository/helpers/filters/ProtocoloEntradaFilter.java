package br.com.tiagods.repository.helpers.filters;

import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.model.Usuario;

import java.time.LocalDate;

public class ProtocoloEntradaFilter {
    private ProtocoloEntrada.StatusRecebimento recebimento = ProtocoloEntrada.StatusRecebimento.ABERTO;
    private ProtocoloEntrada.StatusDevolucao devolucao = ProtocoloEntrada.StatusDevolucao.NAO;
    private ProtocoloEntrada.Classificacao classificacao = ProtocoloEntrada.Classificacao.USUARIO;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private Usuario usuario = UsuarioLogado.getInstance().getUsuario();
    private String pesquisa = "";

    public ProtocoloEntrada.StatusRecebimento getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(ProtocoloEntrada.StatusRecebimento recebimento) {
        this.recebimento = recebimento;
    }

    public ProtocoloEntrada.StatusDevolucao getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(ProtocoloEntrada.StatusDevolucao devolucao) {
        this.devolucao = devolucao;
    }

    public ProtocoloEntrada.Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(ProtocoloEntrada.Classificacao classificacao) {
        this.classificacao = classificacao;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }
}
