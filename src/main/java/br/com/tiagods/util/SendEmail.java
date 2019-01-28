package br.com.tiagods.util;
import br.com.tiagods.config.MailConfig;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

public class SendEmail {
	
	private String errorMessage="";
	
    public boolean enviaAlerta(String from, String fromResume, String conta, String titulo, String mensagem){
        MailConfig cf = MailConfig.getInstance();

        HtmlEmail email = new HtmlEmail();
        email.setHostName(cf.getValue("host"));
        email.setSmtpPort(587);
        email.setAuthenticator( new DefaultAuthenticator(cf.getValue("user"),cf.getValue("password")) );
        try {
            email.setFrom(from,fromResume);
            email.setSubject(titulo);
            email.setHtmlMsg(mensagem);
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
