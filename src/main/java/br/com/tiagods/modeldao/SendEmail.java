package br.com.tiagods.modeldao;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

public class SendEmail {
	
	private String errorMessage="";
	
    public boolean enviaAlerta(String conta, String titulo, String mensagem){
    MailConfig cf = MailConfig.getInstance();
    	
    HtmlEmail email = new HtmlEmail();
    email.setHostName("email-ssl.com.br");
    email.setSmtpPort(587);
    email.setAuthenticator( new DefaultAuthenticator("suporte.ti@prolinkcontabil.com.br","") );
    try {
        email.setFrom("suporte.ti@prolinkcontabil.com.br","Suporte Prolink");
        email.setSubject( titulo );
        email.setHtmlMsg( mensagem );
        email.addTo(conta);
        email.send();
        return true;
    } catch (Exception e) {
    	errorMessage =e.getMessage();
    	e.printStackTrace();
        return false;
    } 
    }
    public String getErroMessage(){
    	return this.errorMessage;
    }
}
