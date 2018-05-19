package br.com.tiagods.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.exception.FXMLNaoEncontradoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuController extends UtilsController implements Initializable{
	@FXML
    private JFXButton btnCaixa;

	//private Stage stage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        
	}
	@FXML
    void caixa(ActionEvent event){

    }
	@FXML
    void cliente(ActionEvent event) {
    
	}
    @FXML
    void empresa(ActionEvent event) {

    }
    @FXML
    void produto(ActionEvent event) {
        
    }
    @FXML
    void pedidoCaixa(ActionEvent event){
    	
    }
    @FXML
    void pedidoDelivery(ActionEvent event){
    	
    }
    @FXML
    void sobre(ActionEvent event) {

    }

    @FXML
    void sair(ActionEvent event) {
	    System.exit(0);
    }
    @FXML
    void usuario(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.USUARIOPESQUISA);
            loader.setController(new UsuarioPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
        }catch(FXMLNaoEncontradoException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo Empresa.fxml",e,true);
        }
    }   
    
}