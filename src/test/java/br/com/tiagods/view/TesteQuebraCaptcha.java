package br.com.tiagods.view;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
public class TesteQuebraCaptcha {

	public static void main(String[] args) throws Exception{
		  	DefaultHttpClient cliente = new DefaultHttpClient();
	        // Adicionando um sistema de redireção
	        cliente.setRedirectStrategy(new LaxRedirectStrategy());
	        // Mantendo a conexão sempre ativa
	        cliente.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
	        // Criando o container de cookies
	        CookieStore cookie = new BasicCookieStore();
	        // Criando o contexto de conexão
	        HttpContext contexto = new BasicHttpContext();
	        // Adicionando o coockie store no contexto de conexão
	        contexto.setAttribute(ClientContext.COOKIE_STORE, cookie);
	        // Criando o método de acesso
	        HttpGet requisição1 = new HttpGet("http://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/Cnpjreva_Solicitacao2.asp");
	        // Resposta
	        HttpResponse resposta = cliente.execute(requisição1, contexto);
	        // Escrever informações
	        System.out.println("Status Line: " + resposta.getStatusLine());
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	        // Buscando a entidade
	        HttpEntity entidade = resposta.getEntity();
	        // Escrever informações
	        System.out.println("Encoding: " + entidade.getContentEncoding());
	        System.out.println("Tamanho: " + entidade.getContentLength());
	        System.out.println("Tipo: " + entidade.getContentType());
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	        // Baixar o stream
	        InputStream entrada = entidade.getContent();
	        // Cria um stream de leitura
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entrada, "LATIN1"));
	        // Cria o receptor de linha
	        String linha;
	        // Cria o acumulador
	        String acumulador = "";
	        // Para cada linha
	        while ((linha = bufferedReader.readLine()) != null) {
	            // Escreva
	            System.out.println(linha);
	            acumulador += "\n" + linha;
	        }
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	        acumulador = acumulador.substring(acumulador.indexOf("/scripts/captcha/Telerik.Web.UI.WebResource.axd?"));
	        acumulador = acumulador.substring(0, acumulador.indexOf("'"));
	        System.out.println(acumulador.replaceAll("amp;", ""));
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	        HttpGet requisição2 = new HttpGet("http://www.receita.fazenda.gov.br"+acumulador.replaceAll("amp;", "").replaceAll("cah", "rca"));
	        // Resposta
	        resposta = cliente.execute(requisição2, contexto);
	        // Escrever informações
	        System.out.println("Status Line: " + resposta.getStatusLine());
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	        // Buscando a entidade
	        entidade = resposta.getEntity();
	        // Escrever informações
	        System.out.println("Encoding: " + entidade.getContentEncoding());
	        System.out.println("Tamanho: " + entidade.getContentLength());
	        System.out.println("Tipo: " + entidade.getContentType());
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	        // Baixar o stream
	        entrada = entidade.getContent();
	        ByteArrayOutputStream out;
	        try (InputStream in = new BufferedInputStream(entrada)) {
	            out = new ByteArrayOutputStream();
	            byte[] buf = new byte[1024];
	            int n = 0;
	            while (-1 != (n = in.read(buf))) {
	                out.write(buf, 0, n);
	            }
	            out.close();
	        }
	        byte[] response = out.toByteArray();
	        try (FileOutputStream fos = new FileOutputStream("captcha.jpeg")) {
	            fos.write(response);
	        }
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	        String captcha = JOptionPane.showInputDialog("Entre com o captcha:");
	        // Criando o método de acesso
	        HttpPost requisição3 = new HttpPost("http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/valida.asp");
	        // Lista de parâmetros
	        List<NameValuePair> nameValuePairs = new ArrayList<>();
	        // Adicionando os parâmetros
	        /*
	        nameValuePairs.add(new BasicNameValuePair("origem", "comprovante"));
	        nameValuePairs.add(new BasicNameValuePair("cnpj", "37444452000172"));
	        nameValuePairs.add(new BasicNameValuePair("cnpj", captcha));
	        nameValuePairs.add(new BasicNameValuePair("submit1", "Consultar"));
	        nameValuePairs.add(new BasicNameValuePair("search_type", "cnpj"));
	        */
	        
	        nameValuePairs.add(new BasicNameValuePair("origem", "comprovante"));
	        nameValuePairs.add(new BasicNameValuePair("search_type", "cnpj"));
	        nameValuePairs.add(new BasicNameValuePair("cnpj", "04110391000191"));
	        nameValuePairs.add(new BasicNameValuePair("captcha", captcha));
	        nameValuePairs.add(new BasicNameValuePair("captchaAudio", ""));
	        nameValuePairs.add(new BasicNameValuePair("submit1", "Consultar"));
	        //nameValuePairs.add(new BasicNameValuePair("viewstate", viewstate));
	        // Encapsulando
	        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
	        // A adição dos parâmetros
	        requisição3.setEntity(urlEncodedFormEntity);
	        // Resposta
	        resposta = cliente.execute(requisição3, contexto);
	        // Escrever informações
	        System.out.println("Status Line: " + resposta.getStatusLine());
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	        // Buscando a entidade
	        entidade = resposta.getEntity();
	        // Escrever informações
	        System.out.println("Encoding: " + entidade.getContentEncoding());
	        System.out.println("Tamanho: " + entidade.getContentLength());
	        System.out.println("Tipo: " + entidade.getContentType());
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	        // Baixar o stream
	        entrada = entidade.getContent();
	        // Cria um stream de leitura
	        bufferedReader = new BufferedReader(new InputStreamReader(entrada));
	        // Para cada linha
	        while ((linha = bufferedReader.readLine()) != null) {
	            // Escreva
	            System.out.println(linha);
	        }
	        // Separador
	        System.out.println();
	        System.out.println("---------------------------------------------------------");
	        System.out.println();
	}

}
