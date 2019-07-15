package br.com.tiagods;

import br.com.tiagods.controller.PersistenciaController;
import br.com.tiagods.controller.UtilsController;
import br.com.tiagods.model.implantacao.ImplantacaoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.repository.helpers.ImplantacaoProcessoEtapasImpl;
import javafx.scene.control.Alert;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class TesteEtapas extends UtilsController{
    public static void main(String[] args){
        new TesteEtapas().chamada();
    }
    private void chamada(){
        try{
            loadFactory();
            ImplantacaoProcessoEtapasImpl etapas = new ImplantacaoProcessoEtapasImpl(getManager());
            List<ImplantacaoProcessoEtapa> result = etapas.filtrar(null,null,null,null);

            for(ImplantacaoProcessoEtapa ip : result) {
                ImplantacaoEtapa.Etapa etapa = ip.getEtapa().getEtapa();
                ImplantacaoEtapa.Etapa nextEtapa = null;
                Optional<ImplantacaoEtapa.Etapa> re = Arrays.asList(ImplantacaoEtapa.Etapa.values()).stream().filter(c->c.getValor() == etapa.getValor()+1).findAny();
                if(re.isPresent()) {
                    System.out.println("Encontrado  proxima etapa = " + re.get());
                    nextEtapa = re.get();
                }
                if (nextEtapa != null) {
                    List<ImplantacaoProcessoEtapa> list = etapas.filtrar(null, ip.getProcesso(), ip.getEtapa().getAtividade(), nextEtapa);
                    if (!list.isEmpty() && list.size() == 1) {
                        ImplantacaoProcessoEtapa processoEtapa = list.get(0);
                        processoEtapa.setStatus(ImplantacaoProcessoEtapa.Status.ABERTO);
                        processoEtapa.setDataLiberacao(Calendar.getInstance());
                        processoEtapa.setDataAtualizacao(Calendar.getInstance());
                        System.out.println("Encontrado " + list.size() + " etapas proximas = " + processoEtapa.getId());
                        final ImplantacaoProcessoEtapa pe = etapas.save(processoEtapa);
                    } else if (list.size() > 1) {
                        System.out.println("Foram encontradas mais etapas para o mesmo processo e atividade! Etapa=" + nextEtapa + ";processo=" + ip.getProcesso().getId() + ";atividade=" + ip.getEtapa().getAtividade());
                    }
                }
            }
        }catch (Exception e){
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao filtrar","Falha ao filtrar registros da tabela de processos",e,true);
        }finally {
            close();
        }
    }
}
