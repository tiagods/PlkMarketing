package br.com.tiagods.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import br.com.tiagods.model.Contato;
import br.com.tiagods.repository.helpers.ContatosImpl;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ContatoCadastroController extends UtilsController implements Initializable{
	private Stage stage;
	private Contato contato;
	private ContatosImpl contatos;
	
	public ContatoCadastroController(Stage stage, Contato contato) {
		this.stage=stage;;
		this.contato=contato;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadFactory();
			if(contato!=null) {
				contatos = new ContatosImpl(getManager());
				this.contato = contatos.findById(this.contato.getId());
				preencherFormulario(this.contato);
			}
		}catch(PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro ao carregar formulario","Erro ao realizar consulta", e, true);
		}finally {
			close();
		}	
	}
	
	private void preencherFormulario(Contato contato) {
		
	}
}
