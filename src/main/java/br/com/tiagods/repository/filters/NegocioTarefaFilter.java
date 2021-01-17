package br.com.tiagods.repository.filters;

import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.Usuario;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class NegocioTarefaFilter {
    private int finalizado = -1;
    private Usuario usuario;
    private Calendar dataEventoInicial;
    private Calendar dataEventoFinal;
    private Set<NegocioTarefa.TipoTarefa> tipoTarefas = new HashSet<>();
    private Usuario atendente;

    public int getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(int finalizado) {
        this.finalizado = finalizado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Calendar getDataEventoInicial() {
        return dataEventoInicial;
    }

    public void setDataEventoInicial(Calendar dataEventoInicial) {
        this.dataEventoInicial = dataEventoInicial;
    }

    public Calendar getDataEventoFinal() {
        return dataEventoFinal;
    }

    public void setDataEventoFinal(Calendar dataEventoFinal) {
        this.dataEventoFinal = dataEventoFinal;
    }

    public Set<NegocioTarefa.TipoTarefa> getTipoTarefas() {
        return tipoTarefas;
    }

    public void setTipoTarefas(Set<NegocioTarefa.TipoTarefa> tipoTarefas) {
        this.tipoTarefas = tipoTarefas;
    }

    public Usuario getAtendente() {
        return atendente;
    }

    public void setAtendente(Usuario atendente) {
        this.atendente = atendente;
    }
}
