package br.com.tiagods.services;

import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.*;
import br.com.tiagods.repository.ImplantacaoProcessosEtapas;
import br.com.tiagods.util.ExcelGenericoUtil;
import br.com.tiagods.util.JavaFxUtil;
import br.com.tiagods.util.MyFileUtil;
import br.com.tiagods.util.alerta.AlertaModel;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;

@Service
public class AlertaImplantacao extends AlertaModel {

    @Autowired
    ImplantacaoProcessosEtapas etapas;

    public File gerarExcel( ImplantacaoProcesso processo,
                            Departamento departamento,
                            ImplantacaoAtividade atividade,
                            ImplantacaoEtapa.Etapa etapa,
                            boolean exibirHistorico) throws Exception{

        File export = MyFileUtil.salvarTemp("xls");
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
        return export;
    }

    public File gerarHtml(
            ImplantacaoProcesso processo,
            Departamento departamento,
            ImplantacaoAtividade atividade,
            ImplantacaoEtapa.Etapa etapa,
            boolean exibirHistorico) throws Exception{

            Map<ImplantacaoProcessoEtapa, List<ImplantacaoProcessoEtapaStatus>> map =
                    montarMap(processo,departamento,atividade,etapa,exibirHistorico);
            String value = montarMensagem(map,new ArrayList<>(),new ArrayList<>(),exibirHistorico);
            File htmlFile = MyFileUtil.salvarTemp("html");
            FileWriter fileWriter = new FileWriter(htmlFile);
            fileWriter.write(value);
            fileWriter.close();
            return htmlFile;
    }

    public  Map<ImplantacaoProcessoEtapa, List<ImplantacaoProcessoEtapaStatus>> montarMap(
            ImplantacaoProcesso processo,
            Departamento departamento,
            ImplantacaoAtividade atividade,
            ImplantacaoEtapa.Etapa etapa,
            boolean exibirHistorico){

        try {
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
                    pe = etapas.getOne(pe.getId());
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
            JavaFxUtil.alert(Alert.AlertType.ERROR,"Erro","","",e,true);
            return null;
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
                builder.append("       <p><span style=\"color:").append(linhasTabelaFonteColor).append(";font-size:12px;\">").append(c.getCriadoPor().getNomeResumido()).append("</span><p>");
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
