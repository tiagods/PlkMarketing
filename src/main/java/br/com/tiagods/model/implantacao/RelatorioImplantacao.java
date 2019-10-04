package br.com.tiagods.model.implantacao;

import br.com.tiagods.model.Cliente;
import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapaStatus;

import java.util.*;

public class RelatorioImplantacao {

    private List<Calendar> data = new LinkedList<>();
    private List<ImplantacaoProcessoEtapa.Status> status = new LinkedList<>();
    private Cliente cliente;
    private List<Departamento> departamentoList = new LinkedList<>();
    private ImplantacaoAtividade atividade;
    private List<String> detalhes = new LinkedList<>();
    private Set<ImplantacaoProcessoEtapaStatus> etapaStatusSet = new TreeSet<>();


    public List<Calendar> getData() {
        return data;
    }

    public void setData(List<Calendar> data) {
        this.data = data;
    }

    public List<ImplantacaoProcessoEtapa.Status> getStatus() {
        return status;
    }

    public void setStatus(List<ImplantacaoProcessoEtapa.Status> status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Departamento> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<Departamento> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public ImplantacaoAtividade getAtividade() {
        return atividade;
    }

    public void setAtividade(ImplantacaoAtividade atividade) {
        this.atividade = atividade;
    }

    public List<String> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(List<String> detalhes) {
        this.detalhes = detalhes;
    }

    public Set<ImplantacaoProcessoEtapaStatus> getEtapaStatusSet() {
        return etapaStatusSet;
    }

    public void setEtapaStatusSet(Set<ImplantacaoProcessoEtapaStatus> etapaStatusSet) {
        this.etapaStatusSet = etapaStatusSet;
    }
}
