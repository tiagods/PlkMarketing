package br.com.tiagods.util.alerta;

import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.util.SendEmail;

public class AlertaImplantacao  {
    public void primeiraEtapa(ImplantacaoProcessoEtapa etapa){

    }
    public void proximaChamada(){

    }
    public void enviarAviso(){
        SendEmail email = new SendEmail();
        email.enviaAlerta("","","","","");
    }
}
