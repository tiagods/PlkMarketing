package br.com.tiagods.util.alerta;

import br.com.tiagods.config.init.JPAConfig;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapaStatus;
import br.com.tiagods.repository.helpers.ImplantacaoProcessoEtapasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingLong;

public class AlertaImplantacao extends AlertaModel{

    public void gerarHtml(ImplantacaoProcesso processo){

    }

    public static void main(String[] args) throws IOException {

        AlertaImplantacao p = new AlertaImplantacao();
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

//        ImplantacaoProcessoEtapa.Status ieStatus = ImplantacaoProcessoEtapa.Status.ABERTO;
        ImplantacaoProcessoEtapa.Status ieStatus = null;

        List<ImplantacaoProcessoEtapa> etapasDoProcesso = etapas.filtrar(null, processo, null, null, ieStatus);
        Comparator<ImplantacaoProcessoEtapa> comparator =
                comparingLong((ImplantacaoProcessoEtapa c) ->c.getProcesso().getCliente().getId())
                        .thenComparing(d->d.getEtapa().getAtividade().getNome())
                        .thenComparingInt(c->c.getEtapa().getEtapa().getValor());
        Collections.sort(etapasDoProcesso,comparator);

        for(ImplantacaoProcessoEtapa c : etapasDoProcesso){

            //ImplantacaoProcessoEtapa pe = etapas.findById(356L);

            ImplantacaoProcessoEtapa pe = c;

            List<ImplantacaoProcessoEtapa> list = etapas.filtrar(null, pe.getProcesso(), pe.getEtapa().getAtividade(), null, null);

            /*
            Comparator<ImplantacaoProcessoEtapa> compare2 =
                    Comparator.comparing(c2->c2.getEtapa().getAtividade().getNome());
            Collections.sort(list, compare2
                    .thenComparingInt(c3->c3.getEtapa().getEtapa().getValor()));
            */
            List<ImplantacaoProcessoEtapaStatus> status = p.organizarLista(list);

            Calendar prazo = pe.getDataAtualizacao();
            if (prazo == null) prazo = Calendar.getInstance();
            prazo.add(Calendar.DAY_OF_MONTH, pe.getEtapa().getTempo());

            map.put(pe, status);
        }
        String value = p.montarMensagem(map,cabecalho,rodape);

        p.renderizar(value);

        manager.close();
    }


    private String cabecalhoFundoColor = "35, 14, 153";
    private String linhasTabelaFundoColor= "255, 255, 255";

    private String linhasTabelaFonteColorIdVencido= "255, 0, 0";
    private String linhasTabelaFonteColorIdNoPrazo= "35, 14, 153";
    private String linhasTabelaFonteColor= "0, 0 ,0";

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List<ImplantacaoProcessoEtapaStatus> organizarLista(List<ImplantacaoProcessoEtapa> list){
        //pegando sets dos objetos e reunindo em um unico list
        List<ImplantacaoProcessoEtapaStatus> result = list.stream()
                .map(ImplantacaoProcessoEtapa::getHistorico)
                .flatMap(c -> c.stream()).collect(Collectors.toList());

        Collections.sort(result, Comparator.comparing(ImplantacaoProcessoEtapaStatus::getCriadoEm));
        return result;
    }

    public String montarMensagem(Map<ImplantacaoProcessoEtapa,List<ImplantacaoProcessoEtapaStatus>> map,List<String> msgCabecalho, List<String> msgRodape) {
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
                .append("               <span style=\"color:#ffffff;\">Historico das atividades anteriores</span></th>")
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
