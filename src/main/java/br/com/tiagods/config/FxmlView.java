package br.com.tiagods.config;

import br.com.tiagods.config.enums.FXMLEnum;
import javafx.stage.Modality;

import java.util.ResourceBundle;

public enum FxmlView {

    USER("user", FXMLEnum.USUARIO_PESQUISA.getFile(),  Modality.WINDOW_MODAL),
    LOGIN("login", FXMLEnum.LOGIN.getFile(), Modality.WINDOW_MODAL),
    MENU("login", FXMLEnum.MAIN.getFile(), Modality.WINDOW_MODAL);
    String value;
    String file;
    Modality modality;
    FxmlView(String value, String file, Modality modality){
        this.value=value;
        this.file=file;
        this.modality=modality;
    }

    public String getTitle(){ return ResourceBundle.getBundle("Bundle").getString(value+".title"); };
    public String getFxmlFile(){ return file;};
    public Modality getModality() { return modality;}

}
