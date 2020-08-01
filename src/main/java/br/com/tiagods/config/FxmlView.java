package br.com.tiagods.config;

import br.com.tiagods.config.enums.FXMLEnum;
import javafx.stage.Modality;

import java.util.ResourceBundle;

public enum FxmlView {

    USER("user", FXMLEnum.USUARIO_PESQUISA.getFile(),  Modality.WINDOW_MODAL),
    LOGIN("login", FXMLEnum.LOGIN.getFile(), Modality.WINDOW_MODAL),
    MENU("login", FXMLEnum.MAIN.getFile(), Modality.WINDOW_MODAL),
    TROCASENHA("login", FXMLEnum.TROCA_SENHA.getFile(), Modality.WINDOW_MODAL),
    USUARIO_CADASTRO("login", FXMLEnum.USUARIO_CADASTRO.getFile(), Modality.APPLICATION_MODAL),
    RECUPERACAO_SENHA("login", FXMLEnum.RECUPERACAO_SENHA.getFile(), Modality.APPLICATION_MODAL),
    SOBRE("login", FXMLEnum.SOBRE.getFile(), Modality.APPLICATION_MODAL),
    USUARIO_PESQUISA("login", FXMLEnum.USUARIO_PESQUISA.getFile(), Modality.APPLICATION_MODAL),
    DEPARTAMENTO("login", FXMLEnum.DEPARTAMENTO.getFile(), Modality.APPLICATION_MODAL);

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
