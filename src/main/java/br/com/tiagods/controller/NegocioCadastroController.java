package br.com.tiagods.controller;

import java.net.URL;
import java.util.AbstractMap;
import java.util.ResourceBundle;

import javax.persistence.Tuple;

import com.sun.prism.shader.Texture_ImagePattern_AlphaTest_Loader;

import br.com.tiagods.modelcollections.NegocioProposta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class NegocioCadastroController implements Initializable{

	private NegocioProposta proposta;
	private Stage stage;
	
	public NegocioCadastroController(Stage stage, NegocioProposta proposta) {
		this.stage = stage;
		this.proposta = proposta;
		if(proposta!=null) preencherFormulario(proposta);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	private void preencherFormulario(NegocioProposta proposta) {
		
	}
	@FXML
	void sair(ActionEvent event) {
		stage.close();
	}

}
