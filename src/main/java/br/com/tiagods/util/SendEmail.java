package br.com.tiagods.util;
import br.com.tiagods.config.MailConfig;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SendEmail {

    Logger logger = LoggerFactory.getLogger(getClass());

	private String errorMessage="";
	
    public boolean enviaAlerta(String fromMail, String fromResume,
                               List<String> conta, String titulo, String mensagem,
                               boolean htmlMsg){
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
            logger.debug("Email enviado com sucesso");
            return true;
        } catch (EmailException e) {
            logger.debug("Falha ao enviar e-mail");
            errorMessage =e.getMessage();
            e.printStackTrace();
            return false;
        }
    }
    public String getErroMessage(){
    	return this.errorMessage;
    }
}
