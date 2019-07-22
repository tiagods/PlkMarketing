package br.com.tiagods.util.alerta;

import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.util.SendEmail;

import java.util.Arrays;

public class AlertaImplantacao  {
    public void primeiraEtapa(ImplantacaoProcessoEtapa etapa){

    }
    public void proximaChamada(ImplantacaoProcessoEtapa etapa){

    }
    public void enviarAviso(){
        SendEmail email = new SendEmail();
        email.enviaAlerta("","", Arrays.asList(""),"","",false);
    }
}
