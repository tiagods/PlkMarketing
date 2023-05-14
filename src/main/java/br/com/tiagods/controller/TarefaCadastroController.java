package br.com.tiagods.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import javax.persistence.PersistenceException;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.repository.NegociosTarefasContatos;
import br.com.tiagods.repository.NegociosTarefasPropostas;
import br.com.tiagods.repository.Usuarios;
import br.com.tiagods.util.MyFileUtil;
import br.com.tiagods.util.storage.PathStorageEnum;
import br.com.tiagods.util.storage.Storage;
import br.com.tiagods.util.storage.StorageProducer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;

import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.negocio.Contato;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.negocio.NegocioTarefaContato;
import br.com.tiagods.model.negocio.NegocioTarefaProposta;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.negocio.NegocioProposta;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import static br.com.tiagods.util.JavaFxUtil.alert;

@Controller
public class TarefaCadastroController extends UtilsController implements Initializable{

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
    private JFXTextArea txDescricao;

    @FXML
    private JFXRadioButton rbNegocioContato;

    @FXML
    private JFXRadioButton rbNegocioProposta;

    @FXML
    private JFXTextField txIdPesquisa;

    @FXML
    private JFXTextField txNomePesquisa;

    @FXML
    private JFXComboBox<Usuario> cbResponsavel;

    @FXML
    private JFXDatePicker dpData;

    @FXML
    private JFXTimePicker tpTime;

    @FXML
    private JFXToggleButton tggFinalizado;

    @FXML
    private JFXButton btBuscar;
    @FXML
	private JFXTextField txFormulario;
	
    private Stage stage;

    @Autowired
    private Usuarios usuarios;
    
    private NegocioTarefa tarefa;
    
    private Object entidade;

    @Autowired
	private NegociosTarefasContatos contatos;
	@Autowired
    private NegociosTarefasPropostas propostas;
	@Autowired
	private TarefaContatoDialogController tarefaContatoDialogController;
	@Autowired
	private TarefaPropostaDialogController tarefaPropostaDialogController;
	@Lazy
	@Autowired
	private StageManager stageManager;


	private Storage storage = StorageProducer.newConfig();

	public void setPropriedades(Stage stage, NegocioTarefa tarefa, Object entidade) {
		this.stage = stage;
		this.entidade = entidade;
		this.tarefa = tarefa;
	}

	@FXML
    void buscar(ActionEvent event) {
		Object controller = null;
		Stage stage = new Stage();
		FxmlView fxmlView = null;

		if(rbNegocioContato.isSelected()) {
			controller = tarefaContatoDialogController;
			tarefaContatoDialogController.setPropriedades(stage);
			fxmlView = FxmlView.TAREFA_DIALOG_CONTATO;
			stage = stageManager.switchScene(FxmlView.TAREFA_DIALOG_CONTATO, true);
		}
		else if(rbNegocioProposta.isSelected()) {
			controller = tarefaPropostaDialogController;
			tarefaPropostaDialogController.setPropriedades(stage);
			stage = stageManager.switchScene(FxmlView.TAREFA_DIALOG_PROPOSTA, true);
		}

		final Object controlador = controller;
		stage.setOnHiding(e -> {
				if(controlador instanceof TarefaContatoDialogController) {
					Contato contato = ((TarefaContatoDialogController)controlador).getContato();
					if(contato!=null) {
						txIdPesquisa.setText(String.valueOf(contato.getId()));
						txNomePesquisa.setText(contato.getNome());
					}
				}
				else if(controlador instanceof TarefaPropostaDialogController) {
					NegocioProposta proposta = ((TarefaPropostaDialogController)controlador).getProposta();
					if(proposta!=null) {
						txIdPesquisa.setText(String.valueOf(proposta.getId()));
						txNomePesquisa.setText(proposta.getNome());
					}
				}
		});
	}
	void combos() {
		ToggleGroup group1 = new ToggleGroup();
		group1.getToggles().addAll(rbEmail,rbProposta,rbReuniao,rbTelefone,rbWhatsApp);
		rbEmail.setSelected(true);
		
		ToggleGroup group2 = new ToggleGroup();
		group2.getToggles().addAll(rbNegocioContato,rbNegocioProposta);
		rbNegocioContato.setSelected(true);
		
		cbResponsavel.getItems().addAll(usuarios.filtrar("", 1, "login"));
		cbResponsavel.getSelectionModel().select(UsuarioLogado.getInstance().getUsuario());
		
		txIdPesquisa.setEditable(false);
		txNomePesquisa.setEditable(false);
		
		dpData.setValue(LocalDate.now());
		tpTime.setIs24HourView(true);
		tpTime.setValue(LocalTime.now());
		
		tggFinalizado.setSelected(false);
		
		ChangeListener<Boolean> listener = (observable, oldValue, newValue) -> {
            txIdPesquisa.setText("");
            txNomePesquisa.setText("");
        };
		rbNegocioContato.selectedProperty().addListener(listener);
		rbNegocioProposta.selectedProperty().addListener(listener);
		
		if(entidade!=null) {
			String id = "";
			String nome = "";
			if(entidade instanceof Contato) {
				rbNegocioContato.setSelected(true);
				id = ""+((Contato)entidade).getId();
				nome = ""+((Contato)entidade).getNome();
			}
			else if(entidade instanceof NegocioProposta) {
				rbNegocioProposta.setSelected(true);
				id = ""+((NegocioProposta)entidade).getId();
				nome = ""+((NegocioProposta)entidade).getNome();
			}			
			txIdPesquisa.setText(id);
			txNomePesquisa.setText(nome);
			rbNegocioContato.setDisable(true);
			rbNegocioProposta.setDisable(true);
			btBuscar.setDisable(true);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			combos();
			if(tarefa!=null) preencherFormulario(tarefa);
		}catch (PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao executar a consulta", e, true);
		}
	}
	@FXML
	private void operacaoArquivo(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("");
		ButtonType incluir = new ButtonType("Anexar Arquivo");
		ButtonType remover = new ButtonType("Remover");
		ButtonType visualizar = new ButtonType("Visualizar");
		ButtonType cancelar = ButtonType.CANCEL;
		alert.getButtonTypes().clear();
		alert.getButtonTypes().add(incluir);
		if(!txFormulario.getText().trim().equals(""))
			alert.getButtonTypes().addAll(remover,visualizar);
		alert.getButtonTypes().add(cancelar);
		Optional<ButtonType> result = alert.showAndWait();

		if(result.get().equals(incluir)){
			Set<FileChooser.ExtensionFilter> filters = new HashSet<>();
			filters.add(new FileChooser.ExtensionFilter("pdf, doc, docx", "*.doc","*.pdf","*.docx"));
			File file = storage.carregarArquivo(new Stage(), filters);
			if (file != null) {
				String novoNome = storage.gerarNome(file, PathStorageEnum.TAREFA_DOCUMENTO.getDescricao());
				try{
					storage.uploadFile(file, novoNome);
					txFormulario.setText(novoNome);
				} catch(Exception e) {
					alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Armazenamento de arquivo");
					alert.setHeaderText("Não foi possivel enviar o arquivo para o servidor");
					alert.setContentText("Um erro desconhecido não permitiu o envio do arquivo para uma pasta do servidor\n"+e);
					alert.showAndWait();
					e.printStackTrace();
				}
			}
		}
		else if(!txFormulario.getText().equals("")) {
			if (result.get().equals(remover)) {
				try {
					if(tarefa!=null){
						try {
							storage.delete(txFormulario.getText());
							txFormulario.setText("");
							tarefa.setFormulario("");
							if(tarefa instanceof NegocioTarefaContato) {
								this.tarefa = contatos.save((NegocioTarefaContato)tarefa);
							}
							else if(tarefa instanceof NegocioTarefaProposta) {
								this.tarefa = propostas.save((NegocioTarefaProposta)tarefa);
							}
						}catch (Exception e) {
							alert(Alert.AlertType.ERROR,"Erro",null,"Erro ao salvar o registro",e,true);
						}
					}

				} catch (Exception e) {
				}
			} else if (result.get().equals(visualizar)) {
				MyFileUtil.visualizarDocumento(txFormulario.getText(), storage);
			}
		}
	}
	void preencherFormulario(NegocioTarefa tarefa) {
		NegocioTarefa.TipoTarefa tipo =  tarefa.getTipoTarefa();
		if(tipo.equals(NegocioTarefa.TipoTarefa.EMAIL)) rbEmail.setSelected(true);
		else if(tipo.equals(NegocioTarefa.TipoTarefa.PROPOSTA)) rbProposta.setSelected(true);
		else if(tipo.equals(NegocioTarefa.TipoTarefa.REUNIAO)) rbReuniao.setSelected(true);
		else if(tipo.equals(NegocioTarefa.TipoTarefa.TELEFONE)) rbTelefone.setSelected(true);
		else if(tipo.equals(NegocioTarefa.TipoTarefa.WHATSAPP)) rbWhatsApp.setSelected(true);
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
			NegocioProposta proposta = ((NegocioTarefaProposta) tarefa).getProposta();
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

		//txFormulario.setText(tarefa.getFormulario());
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
    	NegocioTarefa.TipoTarefa tipo = NegocioTarefa.TipoTarefa.EMAIL;
    	if(rbProposta.isSelected()) tipo = NegocioTarefa.TipoTarefa.PROPOSTA;
    	else if(rbReuniao.isSelected()) tipo = NegocioTarefa.TipoTarefa.REUNIAO;
    	else if(rbTelefone.isSelected()) tipo = NegocioTarefa.TipoTarefa.TELEFONE;
    	else if(rbWhatsApp.isSelected()) tipo = NegocioTarefa.TipoTarefa.WHATSAPP;
    	tarefa.setTipoTarefa(tipo);
    	
    	tarefa.setDescricao(txDescricao.getText());
    	
    	tarefa.setAtendente(cbResponsavel.getValue());
    	tarefa.setDataEvento(GregorianCalendar.from
    			(dpData.getValue().atTime(tpTime.getValue()).atZone(ZoneId.systemDefault())));
    	
    	tarefa.setFinalizado(tggFinalizado.isSelected()?1:0);
		/*
		if (!txFormulario.getText().equals("") && !txFormulario.getText().startsWith(PathStorageEnum.TAREFA_DOCUMENTO.getDescricao()+"/")) {
			try {
				storage.transferTo(txFormulario.getText(), PathStorageEnum.TAREFA_DOCUMENTO.getDescricao()+"/"+txFormulario.getText());
				txFormulario.setText(PathStorageEnum.TAREFA_DOCUMENTO.getDescricao()+"/" + txFormulario.getText());
				tarefa.setFormulario(txFormulario.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			tarefa.setFormulario(txFormulario.getText());
		*/
    	try {
    		if(tarefa instanceof NegocioTarefaContato) {
    			this.tarefa = contatos.save((NegocioTarefaContato)tarefa);
    		}
    		else if(tarefa instanceof NegocioTarefaProposta) {
    			this.tarefa = propostas.save((NegocioTarefaProposta)tarefa);
    		}
    		alert(Alert.AlertType.INFORMATION,"Sucesso",null,
	                "Salvo com sucesso",null,false);
    	}catch (PersistenceException e) {
    		alert(Alert.AlertType.ERROR,"Erro",null,"Erro ao salvar o registro",e,true);
		}
    }
	
	
}
