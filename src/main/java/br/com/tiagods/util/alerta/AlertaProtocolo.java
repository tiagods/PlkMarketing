package br.com.tiagods.util.alerta;

import br.com.tiagods.config.init.JPAConfig;
import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.repository.helpers.ProtocolosEntradasImpl;
import br.com.tiagods.services.SendEmail;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class AlertaProtocolo extends AlertaModel {
    private String normalizer(String valor){
        return Normalizer.normalize(valor, Normalizer.Form.NFD)
            .replaceAll("[^\\p{ASCII}]", "");
    }

    public static void main(String[] args) throws IOException{
        AlertaProtocolo p = new AlertaProtocolo();
        String value = p.montarMensagem("7353","2010",
                p.normalizer("TSGD SERVIÇOS MÉDICOS"),
                Arrays.asList("Certificado Digital A1"),Arrays.asList("9"),
                Arrays.asList("certificado digital + leitor"),"Tiago Dias");
    }

    //usando o boolean trySucess, significa tentar enviar o alerta consecutivas vezes ate resultar em sucesso, se nao informado a tentativa sera unica
    public void programarEnvioDocumentoRecebido(ProtocoloEntrada p, boolean trySucess) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                final SendEmail aviso = new SendEmail();
                List<String> itemNome = new ArrayList<>();
                List<String> qtde = new ArrayList<>();
                List<String> detalhes = new ArrayList<>();
                p.getItems().forEach(item -> {
                    itemNome.add(String.valueOf(item.getNome()));
                    qtde.add(String.valueOf(item.getQuantidade()));
                    detalhes.add(String.valueOf(item.getDetalhes()));
                });
                String clienteId = p.getCliente()!=null?""+p.getCliente().getId():"";
                String clienteNome = p.getCliente()!=null?p.getCliente().getNome():"";
                String novoId = clienteId.length() >= 4 ? clienteId
                        : (clienteId.length() == 1 ? "000" + clienteId.length() : (clienteId.length() == 2 ? "00" + clienteId.length() : "0" + clienteId.length()));
                String[] spEmpresa = clienteNome.split(" ");
                String novoNome = spEmpresa.length>=3?spEmpresa[0]+" "+spEmpresa[1]+" "+spEmpresa[2]:clienteNome;

                String mensagem = montarMensagem(p.getId() + "", novoId, novoNome, itemNome,
                        qtde, detalhes, p.getParaQuem().getNome());

                String email = p.getParaQuem().getEmail();
                //String email = "tiago.dias@prolinkcontabil.com.br";

                if (aviso.enviaAlerta("recepccao@prolinkcontabil.com.br","Protocolos \\ Prolink Contabil", Arrays.asList(email), novoId+" - Protocolo de Entrada N." +p.getId(), mensagem,true)) {
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Enviado com sucesso!");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Email enviado para:\n"+p.getParaQuem()+" sob o protocolo nº: "+p.getId()+" foi entregue com sucesso!");
//                    alert.showAndWait();
                    JOptionPane.showMessageDialog(null, "Email enviado para:\n"+p.getParaQuem()+" sob o protocolo nº: "+p.getId()+" foi entregue com sucesso!");

                    boolean enviar = !p.isAlerta();
                    if(enviar) {
                        EntityManager manager = null;
                        try {
                            manager = JPAConfig.getInstance().createManager();
                            ProtocolosEntradasImpl impl = new ProtocolosEntradasImpl(manager);
                            p.setAlerta(true);
                            impl.save(p);
                        } catch (Exception e) {
                        } finally {
                            if(manager!=null) manager.close();
                        }
                    }
                    timer.cancel();
                }
                else if(!trySucess){
                    JOptionPane.showMessageDialog(null, "Não foi possivel entregar o comunicado do protocolo "+p.getId()+" \nTalvez o servidor de e-mail esteja temporiaramnte disponivel, tente mais tarde!");
                    timer.cancel();
                }
            }

        }, 1000,5*60000);
    }

    private String cabecalhoFundoColor = "230, 92, 23";
    private String linhasTabelaFundoColor= "255, 255, 255";
    private String linhasTabelaFonteColorId= "255, 0, 0";
    private String linhasTabelaFonteColor= "0, 0 ,0";

    private SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat hora = new SimpleDateFormat("HH:mm");

    private String montarMensagem(String protocolo,
                                   String clienteId,
                                   String clienteNome,
                                   List<String> itemNome,
                                   List<String> qtde,
                                   List<String> descricao,
                                   String user) {
        StringBuilder builder = new StringBuilder();
        List<String> mensagems1 = Arrays.asList(
                "Ol&aacute "+user+";",
                "Existe(m) novo(s) documento(s) para voc&ecirc;.",
                "&Eacute; necess&aacute;rio que voc&ecirc; informe se os documentos devem ou n&atilde;o serem devolvidos atrav&eacute;s do protocolo de entrada.");
        builder.append(cabecalho(mensagems1));

        builder.append("<div style=\"text-align: center;\">")
                .append("   <table align=\"left\" border=\"2\" cellpadding=\"2\" cellspacing=\"0\" style=\"width: 100%\">")
                .append("       <thead>")
                .append("           <tr>")
                .append("           <th scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append("); width: 10%;\">")
                .append("               <span style=\"color:#ffffff;\">Protocolo de &nbsp;Entrada</span></th>")
                .append("           <th scope=\"row\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append("); width: 10%;\">")
                .append("               <span style=\"color:#ffffff;\">Data</span></th>")
                .append("           <th colspan=\"2\" scope=\"col\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append("); width: 10%;\">")
                .append("	            <span style=\"color:#ffffff;\">Refer&ecirc;ncia</span></th>")
                .append("	        <th scope=\"col\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append("); width: 10%;\">")
                .append("	            <span style=\"color:#ffffff;\">Tipo</span></th>")
                .append("	        <th scope=\"col\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append("); width: 10%;\">")
                .append("	            <span style=\"color:#ffffff;\">Qtde</span></th>")
                .append("	        <th scope=\"col\" style=\"background-color: rgb(").append(cabecalhoFundoColor).append("); width: 10%;\">")
                .append("	            <span style=\"color:#ffffff;\">Descri&ccedil;&atilde;o</span></th>")
                .append("	        </tr>")
                .append("       </thead>")
                ///linhas da tabela
                .append("   <tbody>")
                .append("	    <tr>")
                .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append("); width: 10%;\">")
                .append("	            <span style=\"color:rgb(").append(linhasTabelaFonteColorId).append("); font-size: 14px;\">")
                .append(protocolo).append("</span></th>")
                .append("	        <th scope=\"row\" style=\"background-color: rgb(").append(linhasTabelaFundoColor).append("); width: 10%;\">")
                .append("	            <span style=\"color:rgb(").append(linhasTabelaFonteColor).append("); font-size: 14px;\">")
                .append(data.format(new Date())).append(" &agrave;s ").append(hora.format(new Date()))
                .append("               </span></th>")
                .append("	        <th style=\"background-color: rgb(").append(linhasTabelaFundoColor).append("); width: 10%;\">")
                .append("	            <span style=\"font-size:14px; color: rgb(").append(linhasTabelaFonteColor).append(");\">")
                .append(clienteId).append("</span></th>")
                .append("	        <th style=\"background-color: rgb(").append(linhasTabelaFundoColor).append("); width: 20%;\">")
                .append("	            <span style=\"font-size:14px; color: rgb(").append(linhasTabelaFonteColor).append(");\">")
                .append(clienteNome).append("</span></th>")
                .append("	        <th style=\"background-color: rgb(").append(linhasTabelaFundoColor).append("); width: 10%;\">");
        for(String item : itemNome) {
            builder.append("	        <p><span style=\"color:").append(linhasTabelaFonteColor).append(";font-size:14px;\"><strong>")
                    .append(item).append("</strong></span><p>");
        }
        builder.append("            </th>")
                .append("	        <th style=\"background-color: rgb(").append(linhasTabelaFundoColor).append("); width: 10%;\">");
        for(String qtd : qtde) {
            builder.append("            <p><span style=\"color: rgb(").append(linhasTabelaFonteColor).append(");font-size:14px;\"><strong>")
                    .append(qtd).append("</strong></span><p>");
        }
        builder.append("            </th>")
                .append("	        <th style=\"background-color: rgb(").append(linhasTabelaFundoColor).append("); width: 40%;\">");
        for(String des : descricao) {
            builder.append("<p>	<span style=\"color: rgb(").append(linhasTabelaFonteColor).append(");font-size: 14px;\">")
                    .append(des).append("</span></p>");
        }
        builder.append("       </th>")
                .append("	</tr>")
                .append("	</tbody>")
                .append("</table>")
                //fim da tabela
                .append("</div>");

        List<String> mensagems = Arrays.asList("Preciso que valide o recebimento pelo sistema Controle de Processos,",
                        "Se notar algo errado, use a op&ccedil;&atilde;o Contestar ou encaminhe para outra pessoa.");
        builder.append(rodape(mensagems));
        return builder.toString();
    }

}