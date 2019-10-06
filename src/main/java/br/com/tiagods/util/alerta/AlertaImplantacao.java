package br.com.tiagods.util.alerta;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.*;
import br.com.tiagods.repository.helpers.ImplantacaoProcessoEtapasImpl;
import br.com.tiagods.util.ExcelGenericoUtil;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;

public class AlertaImplantacao extends AlertaModel {

    public void excel(){
        try {
            FXMLLoader loader = new FXMLLoader(FXMLEnum.PROGRESS_SAMPLE.getLocalizacao());
            Alert progress = new Alert(Alert.AlertType.INFORMATION);
            progress.setHeaderText("");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(loader.load());
            progress.setDialogPane(dialogPane);
            Stage sta = (Stage) dialogPane.getScene().getWindow();
            Task<Void> run = new Task<Void>() {
                {
                    setOnFailed(a ->sta.close());
                    setOnSucceeded(a ->sta.close());
                    setOnCancelled(a ->sta.close());
                }
                @Override
                protected Void call() {
                    Platform.runLater(() -> sta.show());
                    try {

                    } catch (Exception e1) {
                        Platform.runLater(() -> alert(Alert.AlertType.ERROR, "Erro", "", "Erro ao criar a planilha ", e1, true));
                    } finally {
                        close();
                    }
                    return null;
                }
            };
            Thread thread = new Thread(run);
            thread.start();
            sta.setOnCloseRequest(ae -> {
                try {
                    thread.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }catch (IOException e){
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o progresso", "O arquivo nao foi localizado",null,false);
        }
    }

    public void gerarExcel( ImplantacaoProcesso processo,
                            Departamento departamento,
                            ImplantacaoAtividade atividade,
                            ImplantacaoEtapa.Etapa etapa,
                            boolean exibirHistorico) throws Exception{

        try {
            File export = salvarTemp("xls");
            Map<ImplantacaoProcessoEtapa, List<ImplantacaoProcessoEtapaStatus>> map = montarMap(processo,departamento,atividade,etapa,exibirHistorico);

            ArrayList<ArrayList> listaImpressao = new ArrayList<>();
            Integer[] colunasLenght = new Integer[]
                    {12,10,6,22,15,10,20,20,30};
            String[] cabecalho = new String[]
                    {"Prazo","Status","Id Cliente","Nome","Departamento","Etapa","Atividade","O que fazer?",
                            exibirHistorico?"Historico da Etapa":"Historico das atividades anteriores"};

            listaImpressao.add(new ArrayList<>());
            for (String c : cabecalho) {
                listaImpressao.get(0).add(c);
            }
            List<ImplantacaoProcessoEtapa> etapaList = map.keySet().stream().collect(Collectors.toList());

            for (int i = 1; i <= map.size(); i++) {
                listaImpressao.add(new ArrayList<String>());

                ImplantacaoProcessoEtapa pe = etapaList.get(i - 1);

                if(pe.getDataAtualizacao()!=null) {
                    Calendar prazo = pe.getDataAtualizacao();
                    if (prazo == null) prazo = Calendar.getInstance();
                    prazo.add(Calendar.DAY_OF_MONTH, pe.getEtapa().getTempo());
                    listaImpressao.get(i).add(new SimpleDateFormat("dd/MM/yyyy").format(prazo.getTime()));
                }
                else
                    listaImpressao.get(i).add("");

                listaImpressao.get(i).add(pe.getStatus());
                listaImpressao.get(i).add(pe.getProcesso().getCliente().getId());
                listaImpressao.get(i).add(pe.getProcesso().getCliente().getNomeFormatado());
                listaImpressao.get(i).add(pe.getEtapa().getDepartamento());
                listaImpressao.get(i).add(pe.getEtapa().getEtapa());
                listaImpressao.get(i).add(pe.getEtapa().getAtividade());
                listaImpressao.get(i).add(pe.getEtapa().getDescricao());

                Iterator<ImplantacaoProcessoEtapaStatus> iterator = map.get(pe).iterator();
                StringBuilder builder = new StringBuilder();
                while(iterator.hasNext()){
                    ImplantacaoProcessoEtapaStatus status = iterator.next();
                    builder.append(sdf.format(status.getCriadoEm().getTime()))
                            .append("-").append(status.getCriadoPor().getNomeResumido())
                            .append("-").append(status.getDescricao()).append(" \n");
                }
                listaImpressao.get(i).add(builder.toString());
            }
            ExcelGenericoUtil planilha = new ExcelGenericoUtil(export.getAbsolutePath(), listaImpressao, colunasLenght);
            planilha.gerarExcel();
            Desktop.getDesktop().open(export);
            salvarLog(getManager(), "Implantacao - Relatorios", "Exportar", "Exportou relatorio xls");
            //Platform.runLater(() -> alert(Alert.AlertType.INFORMATION, "Sucesso", "Relatorio gerado com sucesso", "", null, false));
        } catch (Exception e) {
            //Platform.runLater(() -> alert(Alert.AlertType.ERROR, "Erro", "", "Erro ao criar a planilha ", e, true));
        }
    }

    public void gerarHtml(
            ImplantacaoProcesso processo,
            Departamento departamento,
            ImplantacaoAtividade atividade,
            ImplantacaoEtapa.Etapa etapa,
            boolean exibirHistorico) throws Exception{
        try {
            Map<ImplantacaoProcessoEtapa, List<ImplantacaoProcessoEtapaStatus>> map =
                    montarMap(processo,departamento,atividade,etapa,exibirHistorico);
            String value = montarMensagem(map,new ArrayList<>(),new ArrayList<>(),exibirHistorico);
            renderizar(value);
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","","",e,true);
        } finally {
            close();
        }
    }

    public  Map<ImplantacaoProcessoEtapa, List<ImplantacaoProcessoEtapaStatus>> montarMap(
            ImplantacaoProcesso processo,
            Departamento departamento,
            ImplantacaoAtividade atividade,
            ImplantacaoEtapa.Etapa etapa,
            boolean exibirHistorico){

        try {
            loadFactory();
            ImplantacaoProcessoEtapasImpl etapas = new ImplantacaoProcessoEtapasImpl(getManager());
            Map<ImplantacaoProcessoEtapa, List<ImplantacaoProcessoEtapaStatus>> map = new LinkedHashMap<>();
            ImplantacaoProcessoEtapa.Status ieStatus = null;
            List<ImplantacaoProcessoEtapa> etapasDoProcesso = etapas.filtrar(
                    departamento,
                    processo,
                    atividade,
                    etapa,
                    ieStatus,
                    exibirHistorico);
            Comparator<ImplantacaoProcessoEtapa> comparator =
                    comparingLong((ImplantacaoProcessoEtapa c) ->c.getProcesso().getCliente().getId())
                            .thenComparing(d->d.getEtapa().getAtividade().getNome())
                            .thenComparingInt(c->c.getEtapa().getEtapa().getValor());
            Collections.sort(etapasDoProcesso,comparator);

            for(ImplantacaoProcessoEtapa c : etapasDoProcesso){
                ImplantacaoProcessoEtapa pe = c;

                List<ImplantacaoProcessoEtapaStatus> status = new ArrayList<>();
                List<ImplantacaoProcessoEtapa> etapaList = new ArrayList<>();
                if(exibirHistorico) {
                    etapaList = etapas.filtrar(null,
                            pe.getProcesso(),
                            pe.getEtapa().getAtividade(),
                            null, null,false);
                    status = organizarList(etapaList);
                }
                else{
                    pe = etapas.findById(pe.getId());
                    etapaList.add(pe);
                    status.addAll(organizarList(etapaList));
                }
                Calendar prazo = pe.getDataAtualizacao();
                if (prazo == null) prazo = Calendar.getInstance();
                prazo.add(Calendar.DAY_OF_MONTH, pe.getEtapa().getTempo());
                map.put(pe, status);
            }
            return map;
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR,"Erro","","",e,true);
            return null;
        } finally {
            close();
        }
    }

    public List<ImplantacaoProcessoEtapaStatus> organizarList(List<ImplantacaoProcessoEtapa> list){
        return list.stream()
                .map(ImplantacaoProcessoEtapa::getHistorico)
                .flatMap(a -> a.stream()
                        .sorted(Comparator
                                .comparing(ImplantacaoProcessoEtapaStatus::getCriadoEm)))
                .collect(Collectors.toList());
    }


    public static void main(String[] args) throws Exception {

        AlertaImplantacao p = new AlertaImplantacao();
        p.gerarHtml(new ImplantacaoProcesso(9L),
                null,
                null, null, false);
        /*
        EntityManager manager = JPAConfig.getInstance().createManager();
        ImplantacaoProcessoEtapasImpl etapas = new ImplantacaoProcessoEtapasImpl(manager);

        Map<ImplantacaoProcessoEtapa, List<ImplantacaoProcessoEtapaStatus>> map = new LinkedHashMap<>();

        Usuario usuario = new UsuariosImpl(manager).findById(1L);

        List<String> cabecalho = Arrays.asList("Sistema Controle de Processos/Implanta&ccedil;&atilde;o",
                "Ol&aacute;;",
                usuario != null ? usuario.getNome() : "" + " acabou de concluir uma Etapa de Implanta&ccedil;&atilde;o",
                "Voc&ecirc; foi designado para concluir a proxima Etapa, abaixo uma descri&ccedil;&atilde;o:");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<String> rodape = Arrays.asList("N&atilde;o se esque&ccedil;a de concluir a tarefa at&eacute; " + sdf.format(Calendar.getInstance().getTime()));

        ImplantacaoProcesso processo = new ImplantacaoProcesso(9L);

        ImplantacaoProcessoEtapa.Status ieStatus = null;

        List<ImplantacaoProcessoEtapa> etapasDoProcesso = etapas.filtrarMultProcessos(null, processo, null, null, ieStatus);
        Comparator<ImplantacaoProcessoEtapa> comparator =
                comparingLong((ImplantacaoProcessoEtapa c) ->c.getProcesso().getCliente().getId())
                        .thenComparing(d->d.getEtapa().getAtividade().getNome())
                        .thenComparingInt(c->c.getEtapa().getEtapa().getValor());
        Collections.sort(etapasDoProcesso,comparator);

        for(ImplantacaoProcessoEtapa c : etapasDoProcesso){
            ImplantacaoProcessoEtapa pe = c;

            List<ImplantacaoProcessoEtapa> list = etapas.filtrarMultProcessos(null, pe.getProcesso(), pe.getEtapa().getAtividade(), null, null);
            List<ImplantacaoProcessoEtapaStatus> status = organizarList(list);

            Calendar prazo = pe.getDataAtualizacao();
            if (prazo == null) prazo = Calendar.getInstance();
            prazo.add(Calendar.DAY_OF_MONTH, pe.getEtapa().getTempo());

            map.put(pe, status);
        }
        String value = p.montarMensagem(map,cabecalho,rodape);
        p.renderizar(value);
        manager.close();
        */
    }


    private String cabecalhoFundoColor = "35, 14, 153";
    private String linhasTabelaFundoColor= "255, 255, 255";

    private String linhasTabelaFonteColorIdVencido= "255, 0, 0";
    private String linhasTabelaFonteColorIdNoPrazo= "35, 14, 153";
    private String linhasTabelaFonteColor= "0, 0 ,0";

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public String montarMensagem(Map<ImplantacaoProcessoEtapa
            ,List<ImplantacaoProcessoEtapaStatus>> map,
                                 List<String> msgCabecalho,
                                 List<String> msgRodape,
                                 boolean exibirHistoricoAnterior) {
        StringBuilder builder = new StringBuilder();

        builder.append(cabecalho(msgCabecalho));

        builder.append("<div style=\"text-align: center;\">")
                .append("   <table align=\"left\" border=\"2\" cellpadding=\"2\" cellspacing=\"0\" style=\"width: 100%\">")
                .append("       <thead>")
                .append("           <tr>")

                .append("           <th scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append(");\">")
                .append("               <span style=\"color:#ffffff;\">Prazo</span></th>")

                .append("           <th scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append(");\">")
                .append("               <span style=\"color:#ffffff;\">Status</span></th>")

                .append("           <th colspan=\"2\" scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append(");\">")
                .append("               <span style=\"color:#ffffff;\">Cliente</span></th>")

                .append("           <th scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append(");\">")
                .append("               <span style=\"color:#ffffff;\">Departamento</span></th>")

                .append("           <th scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append(");\">")
                .append("               <span style=\"color:#ffffff;\">Etapa</span></th>")

                .append("           <th scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append(");\">")
                .append("               <span style=\"color:#ffffff;\">Atividade</span></th>")

                .append("           <th scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append(");\">")
                .append("               <span style=\"color:#ffffff;\">O que fazer? (Sua tarefa)</span></th>")

                .append("           <th colspan=\"3\" scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append(");\">")
                .append("               <span style=\"color:#ffffff;\">").append(exibirHistoricoAnterior?"Historico das atividades anteriores":"Historico da Etapa").append("</span></th>")
                .append("	        </tr>")
                .append("       </thead>")

                ///linhas da tabela
                .append("   <tbody>");

        for (Map.Entry<ImplantacaoProcessoEtapa, List<ImplantacaoProcessoEtapaStatus>> pe : map.entrySet()){
            ImplantacaoProcessoEtapa etapa = pe.getKey();
            List<ImplantacaoProcessoEtapaStatus> status = pe.getValue();

            Calendar prazo = etapa.getDataAtualizacao();
            if(prazo!=null)
                prazo.add(Calendar.DAY_OF_MONTH, etapa.getEtapa().getTempo());

            ImplantacaoProcessoEtapa.Vencido vencido = etapa.getVencido();

            String corData = (vencido == ImplantacaoProcessoEtapa.Vencido.VENCIDO || vencido == ImplantacaoProcessoEtapa.Vencido.VENCE_HOJE) ? linhasTabelaFonteColorIdVencido : linhasTabelaFonteColorIdNoPrazo;

            builder.append("	    <tr>")
                    .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">")
                    .append("	            <span style=\"color:rgb(").append(corData).append("); font-size: 14px;\">")
                    .append(prazo==null?"":sdf.format(prazo.getTime())).append("</span></th>")

                    .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">")
                    .append("	            <span style=\"color:rgb(").append(linhasTabelaFonteColor).append("); font-size: 14px;\">")
                    .append(etapa.getStatus())
                    .append("               </span></th>")

                    .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">")
                    .append("	            <span style=\"color:rgb(").append(linhasTabelaFonteColor).append("); font-size: 12px;\">")
                    .append(etapa.getProcesso().getCliente().getIdFormatado())
                    .append("               </span></th>")

                    .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">")
                    .append("	            <span style=\"color:rgb(").append(linhasTabelaFonteColor).append("); font-size: 12px;\">")
                    .append(etapa.getProcesso().getCliente().getNomeFormatado())
                    .append("               </span></th>")

                    .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">")
                    .append("	            <span style=\"color:rgb(").append(linhasTabelaFonteColor).append("); font-size: 14px;\">")
                    .append(etapa.getEtapa().getDepartamento().getNome())
                    .append("               </span></th>")

                    .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">")
                    .append("	            <span style=\"color:rgb(").append(linhasTabelaFonteColor).append("); font-size: 14px;\">")
                    .append(etapa.getEtapa().getEtapa())
                    .append("               </span></th>")

                    .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">")
                    .append("	            <span style=\"color:rgb(").append(linhasTabelaFonteColor).append("); font-size: 14px;\">")
                    .append(etapa.getEtapa().getAtividade().getNome())
                    .append("               </span></th>")

                    .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">")
                    .append("	            <span style=\"color:rgb(").append(linhasTabelaFonteColor).append("); font-size: 14px;\">")
                    .append(etapa.getEtapa().getDescricao())
                    .append("               </span></th>");
            builder.append("	        <th style=\"background-color: rgb(").append(linhasTabelaFundoColor).append("); \">");
            status.forEach(c -> {
                builder.append("       <p><span style=\"color:").append(linhasTabelaFonteColor).append(";font-size:12;\">").append(c.getCriadoEm() == null ? "" : sdf.format(c.getCriadoEm().getTime())).append("</span><p>");
            });
            builder.append("       </th>");

            builder.append("       <th style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">");
            status.forEach(c -> {
                builder.append("       <p><span style=\"color:").append(linhasTabelaFonteColor).append(";font-size:12px;\">").append(c.getCriadoPor().getLogin()).append("</span><p>");
            });
            builder.append("       </th>");

            builder.append("	        <th style=\"background-color: rgb(").append(linhasTabelaFundoColor).append(");\">");
            status.forEach(c -> {
                builder.append("       <p><span style=\"color:").append(linhasTabelaFonteColor).append(";font-size:12px;\">").append(c.getDescricao()).append("</span><p>");
            });
            builder.append("       </th>");

            builder.append("	</tr>");
        }

                builder.append("	</tbody>")
                .append("</table>")
                //fim da tabela
                .append("</div>");

        builder.append(rodape(msgRodape));
        return builder.toString();
    }


}
