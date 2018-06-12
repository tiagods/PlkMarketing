package br.com.tiagods.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;
import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.modelcollections.NegocioProposta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class NegocioCadastroController implements Initializable{
	@FXML
    private JFXRadioButton rbContato;

    @FXML
    private JFXRadioButton rbEnvioProposta;

    @FXML
    private JFXRadioButton rbFollowup;

    @FXML
    private JFXRadioButton rbFechamento;

    @FXML
    private JFXRadioButton rbIndefinida;

    @FXML
    private Rating rating;

    @FXML
    private Label lbrating;

    @FXML
    private JFXTextField txNome;

    @FXML
    private JFXRadioButton rbAndamento;

    @FXML
    private JFXRadioButton rbGanho;

    @FXML
    private JFXRadioButton rbPerdido;

    @FXML
    private JFXRadioButton rbIndefinido;

    @FXML
    private JFXDatePicker dtInicio;

    @FXML
    private JFXDatePicker dtFinal;

    @FXML
    private JFXTextField txHonorario;

    @FXML
    private JFXTextField txDescricao;

    @FXML
    private JFXComboBox<?> cbAtendente;

    @FXML
    private JFXComboBox<?> cbCategoria;

    @FXML
    private JFXComboBox<?> cbNivel;

    @FXML
    private JFXComboBox<?> cbOrigem;

    @FXML
    private JFXComboBox<?> cbServico;

    @FXML
    private JFXTextField txEmail;

    @FXML
    private MaskedTextField txTelefone;

    @FXML
    private MaskedTextField txCelular;

    @FXML
    private JFXTextField txSite;

    @FXML
    private JFXTextArea txResumo;

    @FXML
    private JFXTextArea txApresentacao;

    @FXML
    private JFXTextArea txDetalhesOrigem;

    @FXML
    private JFXTextField txValorServico;

    @FXML
    private TableView<?> tbServico;

    @FXML
    private TableView<?> tbListas1;

    @FXML
    private JFXTextField txNomeDocumento;

    @FXML
    private JFXTextField txLocalizacao;

    @FXML
    private TableView<?> tbTarefas;

	private NegocioProposta proposta;
	private Stage stage;
	
	public NegocioCadastroController(Stage stage, NegocioProposta proposta) {
		this.stage = stage;
		this.proposta = proposta;
		if(proposta!=null) preencherFormulario(proposta);
	}
	

    @FXML
    void anexarDocumento(ActionEvent event) {

    }
    @FXML
    void cadastrarServico(ActionEvent event) {

    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	private void preencherFormulario(NegocioProposta proposta) {
		
	}
    @FXML
    void mailSend(ActionEvent event) {

    }

    @FXML
    void novaTarefa(ActionEvent event) {

    }

    @FXML
    void openBrowser(ActionEvent event) {

    }
    @FXML
    void sair(ActionEvent event) {
    	stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {

    }

    @FXML
    void salvarDocumento(ActionEvent event) {

    }

    @FXML
    void salvarServico(ActionEvent event) {

    }

    @FXML
    void serviçosAdicionais(ActionEvent event) {

    }
}
