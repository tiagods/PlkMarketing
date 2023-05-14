package br.com.tiagods.config;

import br.com.tiagods.config.enums.FXMLEnum;
import javafx.stage.Modality;

import java.util.ResourceBundle;

public enum FxmlView {

    USER("user", FXMLEnum.USUARIO_PESQUISA.getFile(),  Modality.APPLICATION_MODAL),
    LOGIN("login", FXMLEnum.LOGIN.getFile(), Modality.WINDOW_MODAL),
    MENU("login", FXMLEnum.MAIN.getFile(), Modality.WINDOW_MODAL),
    TROCASENHA("login", FXMLEnum.TROCA_SENHA.getFile(), Modality.APPLICATION_MODAL),
    USUARIO_CADASTRO("login", FXMLEnum.USUARIO_CADASTRO.getFile(), Modality.APPLICATION_MODAL),
    RECUPERACAO_SENHA("login", FXMLEnum.RECUPERACAO_SENHA.getFile(), Modality.APPLICATION_MODAL),
    SOBRE("login", FXMLEnum.SOBRE.getFile(), Modality.APPLICATION_MODAL),
    USUARIO_PESQUISA("login", FXMLEnum.USUARIO_PESQUISA.getFile(), Modality.APPLICATION_MODAL),
    DEPARTAMENTO("login", FXMLEnum.DEPARTAMENTO.getFile(), Modality.APPLICATION_MODAL),
    CONTATO_PESQUISA("login", FXMLEnum.CONTATO_PESQUISA.getFile(), Modality.APPLICATION_MODAL),
    FRANQUIA_CADASTRO("login", FXMLEnum.FRANQUIA_CADASTRO.getFile(), Modality.APPLICATION_MODAL),
    TAREFA_CADASTRO("login", FXMLEnum.TAREFA_CADASTRO.getFile(), Modality.APPLICATION_MODAL),
    CONTATO_CADASTRO("login", FXMLEnum.CONTATO_CADASTRO.getFile(), Modality.APPLICATION_MODAL),
    NEGOCIO_CADASTRO("login", FXMLEnum.NEGOCIO_CADASTRO.getFile(), Modality.APPLICATION_MODAL),
    NEGOCIO_PESQUISA("login", FXMLEnum.NEGOCIO_PESQUISA.getFile(), Modality.APPLICATION_MODAL),
    PROTOCOLO_ENTRADA_PESQUISA("login", FXMLEnum.PROTOCOLO_ENTRADA_PESQUISA.getFile(), Modality.APPLICATION_MODAL),
    FRANQUIA_PESQUISA("login", FXMLEnum.FRANQUIA_PESQUISA.getFile(), Modality.APPLICATION_MODAL),
    IMPLATACAO_PACOTE_PESQUISA("login", FXMLEnum.IMPLATACAO_PACOTE_PESQUISA.getFile(), Modality.APPLICATION_MODAL),
    TAREFA_DIALOG_CONTATO("login", FXMLEnum.TAREFA_DIALOG_CONTATO.getFile(), Modality.APPLICATION_MODAL),
    TAREFA_DIALOG_PROPOSTA("login", FXMLEnum.TAREFA_DIALOG_PROPOSTA.getFile(), Modality.APPLICATION_MODAL),
    TAREFA_PESQUISA("login", FXMLEnum.TAREFA_PESQUISA.getFile(), Modality.APPLICATION_MODAL),
    IMPLANTACAO_PROCESSO_PESQUISA("login", FXMLEnum.IMPLANTACAO_PROCESSO_PESQUISA.getFile(), Modality.APPLICATION_MODAL),
    IMPLANTACAO_PROCESSO_CADASTRO("login", FXMLEnum.IMPLANTACAO_PROCESSO_CADASTRO.getFile(), Modality.APPLICATION_MODAL),
    IMPLANTACAO_ETAPA("login", FXMLEnum.IMPLANTACAO_ETAPA.getFile(), Modality.APPLICATION_MODAL),
    IMPLANTACAO_ETAPA_STATUS("login", FXMLEnum.IMPLANTACAO_ETAPA_STATUS.getFile(), Modality.APPLICATION_MODAL),
    IMPLATACAO_PACOTE_CADASTRO("login", FXMLEnum.IMPLATACAO_PACOTE_CADASTRO.getFile(), Modality.APPLICATION_MODAL),
    PROTOCOLO_ENTRADA_CADASTRO("login", FXMLEnum.PROTOCOLO_ENTRADA_CADASTRO.getFile(), Modality.APPLICATION_MODAL),
    ;

    String value;
    String file;
    Modality modality;
    FxmlView(String value, String file, Modality modality){
        this.value=value;
        this.file=file;
        this.modality=modality;
    }

    public String getTitle(){ return ResourceBundle.getBundle("Bundle").getString(value+".title"); }
    public String getFxmlFile(){ return file;}
    public Modality getModality() { return modality;}
}
