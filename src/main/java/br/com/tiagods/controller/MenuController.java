package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.tiagods.config.enums.FXMLEnum;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuController extends UtilsController implements Initializable{
    @FXML
    private Pane pnCenter;
    @FXML
    private Label txEntrevistasHoje;
    @FXML
    private Label txTarefasHoje;
    @FXML
    private Label txAnunciosAbertos;

    @FXML
    void contato(ActionEvent event) {
    	try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CONTATO_PESQUISA);
            loader.setController(new ContatoPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.CONTATO_PESQUISA,e,true);
        }
	
    }
    @FXML
    void franquia(ActionEvent event) {
    	try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.FRANQUIA_PESQUISA);
            loader.setController(new FranquiaPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.FRANQUIA_PESQUISA,e,true);
        }
    }
    @FXML
    void negocio(ActionEvent event) {
    	try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.NEGOCIO_PESQUISA);
            loader.setController(new NegocioPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.NEGOCIO_PESQUISA,e,true);
        }
    }
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		txEntrevistasHoje.setText("Em desenvolvimento");
		txAnunciosAbertos.setText("Em desenvolvimento");
		txTarefasHoje.setText("Em desenvolvimento");
	}
    
    public void starter() {
    	try {
            FXMLLoader loader = loaderFxml(FXMLEnum.PROGRESS_SAMPLE);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(loader.load());
            alert.setDialogPane(dialogPane);
            alert.show();
            
            Stage sta = (Stage)dialogPane.getScene().getWindow();
            Runnable run = new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						loadFactory();
						Thread.sleep(3000);
						Platform.runLater(()-> sta.close());
					}catch(Exception e) {
			            alert(Alert.AlertType.ERROR, "Erro", "Erro de comunicação",
			                    "Erro ao tentar comunicar com o serviçobanco de dados ",e,false);
					}finally {
						close();
					}		
				}
			};
            new Thread(run).start();
            
        }catch(Exception e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.CONTATO_PESQUISA,e,true);
        }	
    }
    @FXML
    void sair(ActionEvent event) {
	    System.exit(0);
    }
    @FXML
    void sobre(ActionEvent event) {
    	starter();
    }

    @FXML
    void tarefa(ActionEvent event) {
    	try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.TAREFA_PESQUISA);
            loader.setController(new TarefaPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo"+FXMLEnum.TAREFA_PESQUISA,e,true);
        }
    }

    @FXML
    void usuario(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.USUARIO_PESQUISA);
            loader.setController(new UsuarioPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
        }catch(IOException e) {
        	 alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                     "Falha ao localizar o arquivo"+FXMLEnum.USUARIO_PESQUISA,e,true);
        }
    }
}