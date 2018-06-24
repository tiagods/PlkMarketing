package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.sun.mail.imap.protocol.Item;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.modelcollections.NegocioProposta.TipoEtapa;
import br.com.tiagods.modelcollections.NegocioProposta.TipoStatus;
import br.com.tiagods.repository.helpers.NegocioCategoriasImpl;
import br.com.tiagods.repository.helpers.NegocioEtapasImpl;
import br.com.tiagods.repository.helpers.NegocioNiveisImpl;
import br.com.tiagods.repository.helpers.NegocioOrigensImpl;
import br.com.tiagods.repository.helpers.NegocioPropostaImpl;
import br.com.tiagods.repository.helpers.NegocioServicosImpl;
import br.com.tiagods.repository.helpers.NegocioStatusImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NegocioPesquisaController extends UtilsController implements Initializable{
	@FXML
    private HBox pnCheckBox;

    @FXML
    private JFXComboBox<TipoStatus> cbStatus;

    @FXML
    private JFXComboBox<TipoEtapa> cbEtapa;

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
	
	void abrirCadastro(NegocioProposta proposta) {
		try {
			loadFactory();
			if(proposta!=null) {
				propostas = new NegocioPropostaImpl(getManager());
				proposta = propostas.findById(proposta.getId());
			}
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.NEGOCIO_CADASTRO);
            loader.setController(new NegocioCadastroController(stage,proposta));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
            	try {
        			loadFactory();
        			filtrar();
        		}catch(Exception e) {
        			alert(AlertType.ERROR,"Error","","Erro ao abrir cadastro",e,true);
        		}finally {
        			close();
        		}
            });
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo"+FXMLEnum.NEGOCIO_CADASTRO,e,true);
        }finally {
        	close();
        }
	}
	
	private void combos() {
		NegocioCategoria categoria = new NegocioCategoria(-1L,"Categoria");
		NegocioNivel nivel = new NegocioNivel(-1L,"Nivel");
		NegocioOrigem origem = new NegocioOrigem(-1L,"Origem");
		NegocioServico servico = new NegocioServico(-1L, "Servico");
		Usuario usuario = new Usuario(-1L,"Atendente");
		
		cbCategoria.getItems().add(categoria);
		cbNivel.getItems().add(nivel);
		cbOrigem.getItems().add(origem);
		cbServico.getItems().add(servico);
		cbAtendente.getItems().add(usuario);
		cbEtapa.getItems().addAll(TipoEtapa.values());
		cbStatus.getItems().addAll(TipoStatus.values());
		
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
		
		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst(); 
		cbAtendente.getSelectionModel().selectFirst();
		cbStatus.getSelectionModel().selectFirst();
		cbEtapa.getSelectionModel().selectFirst();
		
		pagination.setVisible(false);
		pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Pagination Changed from " + oldValue + " , to " + newValue);
				// currentPageIndex = newValue.intValue();
				// updatePersonView();
			}
		});
		
		ChangeListener<Object> change = new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				try {
					loadFactory();
					filtrar();
				}catch (Exception e) {
					alert(Alert.AlertType.ERROR, "Erro", null,"Falha ao filtrar os registros", e,true);
				}finally {
					close();
				}
			}
		};
		cbStatus.valueProperty().addListener(change);
		cbEtapa.valueProperty().addListener(change);
		cbCategoria.valueProperty().addListener(change);
		cbNivel.valueProperty().addListener(change);
		cbOrigem.valueProperty().addListener(change);
		cbServico.valueProperty().addListener(change);
		cbAtendente.valueProperty().addListener(change);
		dataInicial.valueProperty().addListener(change);
		dataFinal.valueProperty().addListener(change);
	}
	private boolean excluir(NegocioProposta proposta) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			try{
				loadFactory();
				propostas = new NegocioPropostaImpl(getManager());
				NegocioProposta t = propostas.findById(proposta.getId());
				propostas.remove(t);
				alert(AlertType.INFORMATION, "Sucesso", null, "Removido com sucesso!",null, false);
				return true;
			}catch(Exception e){
				alert(Alert.AlertType.ERROR, "Erro", null,"Falha ao excluir o registro", e,true);
				return false;
			}finally{
				super.close();
			}
		}
		else return false;
	}
	
	@FXML
	void exportar(ActionEvent event) {

	}
	private void filtrar(){
		propostas = new NegocioPropostaImpl(getManager());		
		String dataFiltro = "criadoEm";
		String ordenacao = "id";
		List<NegocioProposta> list = propostas.filtrar(
				cbStatus.getValue(),cbEtapa.getValue(),cbCategoria.getValue(),cbNivel.getValue(),
				cbOrigem.getValue(),cbServico.getValue(),cbAtendente.getValue(),dataInicial.getValue(),
				dataFinal.getValue(),dataFiltro,ordenacao,txPesquisa.getText());
		tbPrincipal.getItems().clear();
		tbPrincipal.getItems().addAll(list);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			try{
				tabela();
				loadFactory();
				combos();
				filtrar();
		    } catch (Exception e) {
				alert(AlertType.ERROR, "Erro", "Erro ao carregar formulario", "Erro ao realizar consulta", e, true);
			} finally {
				close();
			}
	}	
	@FXML
    void novo(ActionEvent event) {
		abrirCadastro(null);
    }
	@FXML
	void pesquisar(KeyEvent event) {
		try {
			loadFactory();
			filtrar();
		}catch(Exception e) {
			alert(AlertType.ERROR,"Error","","Erro ao listar registros",e,true);
		}finally {
			close();
		}
	}

    @FXML
    void sair(ActionEvent event) {
    	stage.close();
    }
    @SuppressWarnings("unchecked")
	void tabela() {
    	TableColumn<NegocioProposta, Number> colunaId = new  TableColumn<>("*");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<NegocioProposta, String> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaNome.setPrefWidth(120);
		
		TableColumn<NegocioProposta, TipoStatus> colunaStatus = new  TableColumn<>("Status");
		colunaStatus.setCellValueFactory(new PropertyValueFactory<>("tipoStatus"));
		colunaStatus.setPrefWidth(40);
		
		TableColumn<NegocioProposta, TipoEtapa> colunaEtapa= new  TableColumn<>("Etapa");
		colunaEtapa.setCellValueFactory(new PropertyValueFactory<>("tipoEtapa"));
		colunaEtapa.setPrefWidth(40);
		
		TableColumn<NegocioProposta, NegocioOrigem> colunaOrigem= new  TableColumn<>("Origem");
		colunaOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
		colunaOrigem.setPrefWidth(60);
		
		TableColumn<NegocioProposta, NegocioServico> colunaServico= new  TableColumn<>("Servico");
		colunaServico.setCellValueFactory(new PropertyValueFactory<>("servico"));

		TableColumn<NegocioProposta, NegocioCategoria> colunaCategoria= new  TableColumn<>("Categoria");
		colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

		TableColumn<NegocioProposta, NegocioNivel> colunaNivel= new  TableColumn<>("Nivel");
		colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		
		TableColumn<NegocioProposta, Calendar> colunaCriadoEm= new  TableColumn<>("Inicio");
		colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
		colunaCriadoEm.setCellFactory(param -> new TableCell<NegocioProposta,Calendar>(){
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(sdf.format(item.getTime()));
				}
			}
		});
		
		TableColumn<NegocioProposta, Calendar> colunaVencimento= new  TableColumn<>("Vencimento");
		colunaVencimento.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
		colunaVencimento.setCellFactory(param -> new TableCell<NegocioProposta,Calendar>(){
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(sdf.format(item.getTime()));
				}
			}
		});
		TableColumn<NegocioProposta, Calendar> colunaConclusao= new  TableColumn<>("Conclusao");
		colunaConclusao.setCellValueFactory(new PropertyValueFactory<>("dataFinalizacao"));
		colunaConclusao.setCellFactory(param -> new TableCell<NegocioProposta,Calendar>(){
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(sdf.format(item.getTime()));
				}
			}
		});
		TableColumn<NegocioProposta, Usuario> colunaAtendente= new  TableColumn<>("Atendente");
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		
		TableColumn<NegocioProposta, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<NegocioProposta,Number>(){
			JFXButton button = new JFXButton();//Editar
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btDefault");
					try {
						buttonTable(button, IconsEnum.BUTTON_EDIT);
					}catch (IOException e) {
					}
					button.setOnAction(event -> {
						abrirCadastro(tbPrincipal.getItems().get(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		TableColumn<NegocioProposta, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<NegocioProposta,Number>(){
			JFXButton button = new JFXButton();//excluir
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btDefault");
					try {
						buttonTable(button,IconsEnum.BUTTON_REMOVE);
					}catch (IOException e) {
					}
					button.setOnAction(event -> {
						boolean removed = excluir(tbPrincipal.getItems().get(getIndex()));
						if(removed) tbPrincipal.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		tbPrincipal.getColumns().addAll(colunaId,colunaNome,colunaStatus,colunaEtapa,colunaOrigem,colunaServico,colunaCategoria,
				colunaNivel,colunaCriadoEm,colunaVencimento,colunaAtendente,colunaEditar);
    }
}
