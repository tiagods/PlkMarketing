package br.com.tiagods.util;
import br.com.tiagods.config.MailConfig;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

public class SendEmail {
	
	private String errorMessage="";
	
    public boolean enviaAlerta(String fromMail, String fromResume, List<String> conta, String titulo, String mensagem, boolean htmlMsg){
        MailConfig cf = MailConfig.getInstance();

        HtmlEmail email = new HtmlEmail();
        email.setHostName(cf.getValue("host"));
        email.setSmtpPort(587);
        email.setAuthenticator( new DefaultAuthenticator(cf.getValue("user"),cf.getValue("password")) );
        try {
            email.setFrom(fromMail,fromResume);
            email.setSubject(titulo);
            for(String mail : conta) email.addTo(mail.trim());
            if(htmlMsg)
                email.setHtmlMsg(mensagem);
            else
                email.setTextMsg(mensagem);
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
