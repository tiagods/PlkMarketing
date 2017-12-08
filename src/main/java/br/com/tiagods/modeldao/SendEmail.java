package br.com.tiagods.modeldao;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import br.com.tiagods.config.MailConfig;

public class SendEmail {
	
	private String errorMessage="";
	
    public boolean enviaAlerta(String conta, String titulo, String mensagem){
    MailConfig cf = MailConfig.getInstance();
    	
    HtmlEmail email = new HtmlEmail();
    email.setHostName(cf.getValue("host"));
    email.setSmtpPort(Integer.parseInt(cf.getValue("port")));
    email.setAuthenticator( new DefaultAuthenticator(cf.getValue("user"),cf.getValue("password")) );
    try {
        email.setFrom(cf.getValue("user"),cf.getValue("from"));
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
