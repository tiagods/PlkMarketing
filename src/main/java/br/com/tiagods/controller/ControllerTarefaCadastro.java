package br.com.tiagods.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;

import br.com.tiagods.config.UsuarioLogado;
import br.com.tiagods.model.Contato;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.model.NegocioTarefaContato;
import br.com.tiagods.model.NegocioTarefaProposta;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ControllerTarefaCadastro extends UtilsController implements Initializable{
	@FXML
    private HBox pnRadio;

    @FXML
    private JFXRadioButton rbEmail;

    @FXML
    private JFXRadioButton rbProposta;

    @FXML
    private JFXRadioButton rbReuniao;

    @FXML
    private JFXRadioButton rbTelefone;

    @FXML
    private JFXRadioButton rbWhatsApp;

    @FXML
    private JFXRadioButton rbContato;

    @FXML
    private JFXTextArea txDescricao;

    @FXML
    private JFXRadioButton rbNegocioContato;

    @FXML
    private JFXRadioButton rbNegocioProposta;

    @FXML
    private JFXComboBox<Usuario> cbResponsavel;

    @FXML
    private JFXDatePicker dpData;

    @FXML
    private JFXTimePicker tpTime;

    @FXML
    private JFXToggleButton tggFinalizado;
    
    @FXML
    private JFXTextField txIdPesquisa;

    @FXML
    private JFXTextField txNomePesquisa;
	
    private Stage stage;
    
    private UsuariosImpl usuarios;
    
    private NegocioTarefa tarefa;
    
	public ControllerTarefaCadastro(Stage stage, NegocioTarefa tarefa) {
		this.stage = stage;
		if(tarefa!=null) preencherFormulario(tarefa);
	}
	@FXML
    void buscar(ActionEvent event) {
		if(rbNegocioContato.isSelected()){
			
		}
		else if(rbNegocioProposta.isSelected()) {
			
		}
    }	
	void combos() {
		ToggleGroup group1 = new ToggleGroup();
		group1.getToggles().addAll(rbContato,rbEmail,rbProposta,rbReuniao,rbTelefone,rbWhatsApp);
		rbContato.setSelected(true);
		
		ToggleGroup group2 = new ToggleGroup();
		group2.getToggles().addAll(rbNegocioContato,rbNegocioProposta);
		rbNegocioContato.setSelected(true);
		
		usuarios = new UsuariosImpl(getManager());
		cbResponsavel.getItems().addAll(usuarios.filtrar("", 1, "login"));
		cbResponsavel.getSelectionModel().select(UsuarioLogado.getInstance().getUsuario());
		
		txIdPesquisa.setEditable(false);
		txNomePesquisa.setEditable(false);
		
		dpData.setValue(LocalDate.now());
		tpTime.setIs24HourView(true);
		tpTime.setValue(LocalTime.now());
		
		tggFinalizado.setSelected(false);
		
		ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				txIdPesquisa.setText("");
				txNomePesquisa.setText("");
			}
		};
		rbNegocioContato.selectedProperty().addListener(listener);
		rbNegocioProposta.selectedProperty().addListener(listener);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			loadFactory();
			combos();
		}catch (PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao executar a consulta", e, true);
		}finally {
			close();
		}
	}
	void preencherFormulario(NegocioTarefa tarefa) {
		TipoTarefa tipo =  tarefa.getTipoTarefa();
		if(tipo.equals(TipoTarefa.EMAIL)) rbEmail.setSelected(true);
		else if(tipo.equals(TipoTarefa.PROPOSTA)) rbProposta.setSelected(true);
		else if(tipo.equals(TipoTarefa.REUNIAO)) rbReuniao.setSelected(true);
		else if(tipo.equals(TipoTarefa.TELEFONE)) rbTelefone.setSelected(true);
		else if(tipo.equals(TipoTarefa.WHATSAPP)) rbWhatsApp.setSelected(true);
		if(tarefa instanceof NegocioTarefaContato){
			rbNegocioContato.setSelected(true);
			Contato contato = ((NegocioTarefaContato)tarefa).getContato();
			if(contato!=null) {
				txIdPesquisa.setText(""+contato.getId());
				txNomePesquisa.setText(contato.toString());
			}
		}
		else if(tarefa instanceof NegocioTarefaProposta) {
			rbNegocioProposta.setSelected(true);
			NegocioProposta proposta = ((NegocioTarefaProposta)tarefa).getNegocio();
			if(proposta!=null) {
				txIdPesquisa.setText(""+proposta.getId());
				txNomePesquisa.setText(proposta.toString());
			}
		}
		txDescricao.setText(tarefa.getDescricao());
		
		cbResponsavel.setValue(tarefa.getAtendente());
		
		dpData.setValue(tarefa.getDataEvento().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		tpTime.setValue(tarefa.getDataEvento().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
		
		tggFinalizado.setSelected(tarefa.getFinalizado()==1);
		this.tarefa=tarefa;
	}
    @FXML
    void sair(ActionEvent event) {
    	stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {
    	if(txIdPesquisa.getText().equals("")) {
    		alert(AlertType.ERROR, "Erro", "Erro ao salvar", "Nenhum contato/proposta foi informado", null, false);
    		return;
    	}
    	if(dpData.getValue()==null || tpTime.getValue()==null) {
    		alert(AlertType.ERROR, "Erro", "Erro ao salvar", "Verifique a data e horario informado", null, false);
    		return;
    	}
    	if(rbNegocioContato.isSelected()) {
    		if(tarefa == null) tarefa = new NegocioTarefaContato();
    		else tarefa = new NegocioTarefaContato(tarefa.getId());
    		((NegocioTarefaContato)tarefa).setContato(new Contato(Long.parseLong(txIdPesquisa.getText())));
    	}
    	else if(rbNegocioProposta.isSelected()) {
    		if(tarefa == null) tarefa = new NegocioTarefaProposta();
    		else tarefa = new NegocioTarefaProposta(tarefa.getId());
    		((NegocioTarefaProposta)tarefa).setProposta(new NegocioProposta(Long.parseLong(txIdPesquisa.getText())));
    		
    	}
    	TipoTarefa tipo = TipoTarefa.EMAIL;
    	if(rbProposta.isSelected()) tipo = TipoTarefa.PROPOSTA;
    	else if(rbReuniao.isSelected()) tipo = TipoTarefa.REUNIAO;
    	else if(rbTelefone.isSelected()) tipo = TipoTarefa.TELEFONE;
    	else if(rbWhatsApp.isSelected()) tipo = TipoTarefa.WHATSAPP;
    	tarefa.setTipoTarefa(tipo);
    	
    	tarefa.setDescricao(txDescricao.getText());
    	
    	tarefa.setAtendente(cbResponsavel.getValue());
    	tarefa.setDataEvento(GregorianCalendar.from
    			(dpData.getValue().atTime(tpTime.getValue()).atZone(ZoneId.systemDefault())));
    	
    	tarefa.setFinalizado(tggFinalizado.isSelected()?1:0);
    }
	
	
}
