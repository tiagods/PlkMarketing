package br.com.tiagods.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.model.Contato.ContatoTipo;
import br.com.tiagods.model.Contato.PessoaTipo;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioLista;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.modelcollections.NegocioEtapa;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.modelcollections.NegocioStatus;
import br.com.tiagods.repository.helpers.NegocioCategoriasImpl;
import br.com.tiagods.repository.helpers.NegocioEtapasImpl;
import br.com.tiagods.repository.helpers.NegocioNiveisImpl;
import br.com.tiagods.repository.helpers.NegocioOrigensImpl;
import br.com.tiagods.repository.helpers.NegocioPropostaImpl;
import br.com.tiagods.repository.helpers.NegocioServicosImpl;
import br.com.tiagods.repository.helpers.NegocioStatusImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.LoadingView;
import br.com.tiagods.util.WorkIndicatorProgress;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NegocioPesquisaController extends UtilsController implements Initializable{
	@FXML
    private HBox pnCheckBox;

    @FXML
    private JFXComboBox<NegocioStatus> cbStatus;

    @FXML
    private JFXComboBox<NegocioEtapa> cbEtapa;

    @FXML
    private JFXComboBox<NegocioCategoria> cbCategoria;

    @FXML
    private JFXComboBox<NegocioNivel> cbNivel;

    @FXML
    private JFXComboBox<NegocioOrigem> cbOrigem;

    @FXML
    private JFXComboBox<NegocioServico> cbServico;

    @FXML
    private JFXComboBox<Usuario> cbAtendente;

    @FXML
    private JFXComboBox<String> cbPesquisaData;

    @FXML
    private JFXDatePicker dataInicial;

    @FXML
    private JFXDatePicker dataFinal;

    @FXML
    private JFXTextField txPesquisa;

    @FXML
    private JFXComboBox<String> cbClassificar;

    @FXML
    private TableView<NegocioProposta> tbPrincipal;
	
    @FXML
    private Pagination pagination;
    
	private Stage stage;
	private NegocioNiveisImpl niveis;
	private NegocioCategoriasImpl categorias;
	private NegocioOrigensImpl origens;
	private NegocioServicosImpl servicos;
	private NegocioEtapasImpl etapas;
	private NegocioStatusImpl status;
	private NegocioPropostaImpl propostas;

	private UsuariosImpl usuarios;
	
	public NegocioPesquisaController(Stage stage) {
		this.stage=stage;
	}
	
	private void combos() {
		String todas="Todas";
		NegocioCategoria categoria = new NegocioCategoria(-1L,todas);
		NegocioNivel nivel = new NegocioNivel(-1L,todas);
		NegocioOrigem origem = new NegocioOrigem(-1L,todas);
		NegocioServico servico = new NegocioServico(-1L, todas);
		Usuario usuario = new Usuario(-1L,todas);
		NegocioLista lista = new NegocioLista(-1L, todas);
		NegocioEtapa etapa = new NegocioEtapa(-1L,todas);
		NegocioStatus sta = new NegocioStatus(-1L,todas);
		
		cbCategoria.getItems().add(categoria);
		cbNivel.getItems().add(nivel);
		cbOrigem.getItems().add(origem);
		cbServico.getItems().add(servico);
		cbAtendente.getItems().add(usuario);
		cbStatus.getItems().add(sta);
		cbEtapa.getItems().add(etapa);
		
		categorias = new NegocioCategoriasImpl(getManager());
		niveis = new NegocioNiveisImpl(getManager());
		origens = new NegocioOrigensImpl(getManager());
		servicos = new NegocioServicosImpl(getManager());
		usuarios = new UsuariosImpl(getManager());
		status = new NegocioStatusImpl(getManager());
		etapas = new NegocioEtapasImpl(getManager());
		propostas = new NegocioPropostaImpl(getManager());
		
		cbCategoria.getItems().addAll(categorias.getAll());
		cbNivel.getItems().addAll(niveis.getAll());
		cbOrigem.getItems().addAll(origens.getAll());
		cbServico.getItems().addAll(servicos.getAll());
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
		cbStatus.getItems().addAll(status.getAll());
		cbEtapa.getItems().addAll(etapas.getAll());
		
		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst(); 
		cbAtendente.getSelectionModel().selectFirst();
		cbStatus.getSelectionModel().selectFirst();
		cbEtapa.getSelectionModel().selectFirst();
		
		 pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
		      @Override
		      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		        System.out.println("Pagination Changed from " + oldValue + " , to " + newValue);
		        //currentPageIndex = newValue.intValue();
		        //updatePersonView();
		      }
		  });
	}
	@FXML
	void exportar(ActionEvent event) {

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			tabela();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					final WorkIndicatorProgress wd = new WorkIndicatorProgress(stage, "Loading Project Files...");
					wd.addTaskEndNotification(result -> {
				       System.out.println("Exit");
				    });
				    wd.exec("123", inputParam -> {
				       
				    	try {
							loadFactory();
							//combos();
							for (int i = 0; i < 10; i++) {
					           System.out.println("Loading data... '123' =->"+inputParam);
					           try {
					              Thread.sleep(1000);
					              wd.updateMesage("Loading "+i);
					           }
					           catch (InterruptedException e) {
					              e.printStackTrace();
					           }
							}
							
							tbPrincipal.getItems().addAll(propostas.getAll());
				    	
				    } catch (Exception e) {
						alert(AlertType.ERROR, "Erro", "Erro ao carregar formulario", "Erro ao realizar consulta", e, true);
					} finally {
						close();
					}				
				       return new Integer(1);
				    });		
				}
			});
		
		
	}
	
	@FXML
    void novo(ActionEvent event) {

    }

    @FXML
    void sair(ActionEvent event) {
    	stage.close();
    }
    void tabela() {
    	
    }
}
