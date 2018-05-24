package br.com.tiagods.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.exception.FXMLNaoEncontradoException;
import br.com.tiagods.repository.helpers.NegociosTarefasContatosImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    void empresa(ActionEvent event) {

    }

    @FXML
    void pessoa(ActionEvent event) {

    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadFactory();
			//NegociosTarefasImpl tarefas = new NegociosTarefasImpl(getManager());
			//tarefas.getAll().forEach(c->{System.out.println(c.getClasse());});
			NegociosTarefasContatosImpl tarefas = new NegociosTarefasContatosImpl(getManager());
			System.out.println(tarefas.getAll().size());
			
		}catch (Exception e) {
			
		}finally {
			close();
		}
	}   
    
    @FXML
    void prospeccao(ActionEvent event) {

    }

    @FXML
    void sair(ActionEvent event) {
	    System.exit(0);
    }

    @FXML
    void sobre(ActionEvent event) {

    }

    @FXML
    void tarefa(ActionEvent event) {
    	try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.TAREFAPESQUISA);
            loader.setController(new TarefaPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.TRANSPARENT);
        }catch(FXMLNaoEncontradoException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo"+FXMLEnum.TAREFAPESQUISA,e,true);
        }
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
                     "Falha ao localizar o arquivo"+FXMLEnum.USUARIOPESQUISA,e,true);
        }
    }

    
	
    
}