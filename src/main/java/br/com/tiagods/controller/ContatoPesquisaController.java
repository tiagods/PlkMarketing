package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Contato;
import br.com.tiagods.model.Contato.ContatoTipo;
import br.com.tiagods.model.Contato.PessoaTipo;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioLista;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.ContatosImpl;
import br.com.tiagods.repository.helpers.NegocioCategoriasImpl;
import br.com.tiagods.repository.helpers.NegocioNiveisImpl;
import br.com.tiagods.repository.helpers.NegocioOrigensImpl;
import br.com.tiagods.repository.helpers.NegocioServicosImpl;
import br.com.tiagods.repository.helpers.NegociosListasImpl;
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
import javafx.util.Pair;

public class ContatoPesquisaController extends UtilsController implements Initializable{
	@FXML
    private HBox pnCheckBox;

    @FXML
    private JFXComboBox<Contato.PessoaTipo> cbTipo;

    @FXML
    private JFXComboBox<Contato.ContatoTipo> cbContatoTipo;

    @FXML
    private JFXComboBox<NegocioLista> cbLista;

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
    private JFXComboBox<String> cbMalaDireta;
    
    @FXML
    private HBox pnCheckBox1;

    @FXML
    private JFXDatePicker dataInicial;

    @FXML
    private JFXDatePicker dataFinal;

    @FXML
    private JFXTextField txPesquisa;

    @FXML
    private JFXComboBox<String> cbClassificar;

    @FXML
    private TableView<Contato> tbPrincipal;

    @FXML
    private TableView<Contato> tbListas;

    @FXML
    private TableView<Contato> tbTarefas;
    
    @FXML
    private Pagination pagination;
    
    @FXML
    private JFXComboBox<Integer> cbLimite;

	private Stage stage;
	private ContatosImpl contatos;
	private NegocioNiveisImpl niveis;
	private NegocioCategoriasImpl categorias;
	private NegocioOrigensImpl origens;
	private NegocioServicosImpl servicos;
	private NegociosListasImpl listas;
	private UsuariosImpl usuarios;
	
	private Paginacao paginacao;
	
	
	public ContatoPesquisaController(Stage stage) {
		this.stage = stage;
	}
	
	private void abrirCadastro(Contato t) {
		try {
			loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CONTATO_CADASTRO);
            loader.setController(new ContatoCadastroController(stage,t));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
            	try {
        			loadFactory();
        			filtrar();
        		}catch(Exception e) {
        			e.printStackTrace();
        		}finally {
        			close();
        		}
            });
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo"+FXMLEnum.CONTATO_CADASTRO,e,true);
        }finally {
        	close();
        }
	}
	
	void combos() {
		NegocioCategoria categoria = new NegocioCategoria(-1L,"Categoria");
		String[] malaDireta = new String[] {"Mala Direta","Convite para Eventos","Material","Newsletter"};
		NegocioNivel nivel = new NegocioNivel(-1L,"Nivel");
		NegocioOrigem origem = new NegocioOrigem(-1L,"Origem");
		NegocioServico servico = new NegocioServico(-1L, "Servico");
		Usuario usuario = new Usuario(-1L,"Atendente");
		NegocioLista lista = new NegocioLista(-1L, "Lista");
		
		cbCategoria.getItems().add(categoria);
		cbNivel.getItems().add(nivel);
		cbOrigem.getItems().add(origem);
		cbServico.getItems().add(servico);
		cbAtendente.getItems().add(usuario);
		cbLista.getItems().add(lista);
		
		contatos = new ContatosImpl(getManager());
		categorias = new NegocioCategoriasImpl(getManager());
		niveis = new NegocioNiveisImpl(getManager());
		origens = new NegocioOrigensImpl(getManager());
		servicos = new NegocioServicosImpl(getManager());
		listas = new NegociosListasImpl(getManager());
		usuarios = new UsuariosImpl(getManager());
		
		cbLimite.getItems().addAll(limiteTabela);
		cbCategoria.getItems().addAll(categorias.getAll());
		cbNivel.getItems().addAll(niveis.getAll());
		cbOrigem.getItems().addAll(origens.getAll());
		cbServico.getItems().addAll(servicos.getAll());
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
		cbLista.getItems().addAll(listas.getAll());
		cbMalaDireta.getItems().addAll(malaDireta);
		
		cbTipo.getItems().addAll(PessoaTipo.values());
		cbContatoTipo.getItems().addAll(ContatoTipo.values());
		
		cbLimite.getSelectionModel().selectFirst();
		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst(); 
		cbAtendente.getSelectionModel().selectFirst();
		cbLista.getSelectionModel().selectFirst();
		cbMalaDireta.getSelectionModel().selectFirst();
		
		cbTipo.getSelectionModel().select(PessoaTipo.CONTATO);
		cbContatoTipo.getSelectionModel().select(ContatoTipo.CONTATO);
			
		ChangeListener change = new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				try {
					loadFactory();
					paginacao = new Paginacao(cbLimite.getValue());
					filtrar();
				}catch (Exception e) {
					alert(AlertType.ERROR, "Erro", "", "Erro ao realizar filtro", e, true);
				}finally {
					close();
				}
			}
		};
		cbTipo.valueProperty().addListener(change);
		cbContatoTipo.valueProperty().addListener(change);
		cbLista.valueProperty().addListener(change);
		cbCategoria.valueProperty().addListener(change);
		cbMalaDireta.valueProperty().addListener(change);
		cbNivel.valueProperty().addListener(change);
		cbOrigem.valueProperty().addListener(change);
		cbServico.valueProperty().addListener(change);
		cbAtendente.valueProperty().addListener(change);
		dataFinal.valueProperty().addListener(change);
		dataInicial.valueProperty().addListener(change);
		
		pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				paginacao.setPaginaAtual(newValue.intValue());
				try {
					loadFactory();
					filtrar();
				}catch(PersistenceException e) {
					alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
				}finally {
					close();
				}
			}
		});
		paginacao = new Paginacao(cbLimite.getValue());
	}
	@FXML
	void exportar(ActionEvent event) {

	}
	boolean excluir(Contato n) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			try{
				loadFactory();
				contatos = new ContatosImpl(getManager());
				Contato t = contatos.findById(n.getId());
				contatos.remove(t);
				alert(AlertType.INFORMATION, "Sucesso", null, "Removido com sucesso!",null, false);
				return true;
			}catch(Exception e){
				super.alert(Alert.AlertType.ERROR, "Erro", null,"Falha ao excluir o registro", e,true);
				return false;
			}finally{
				super.close();
			}
		}
		else return false;
	}
	
	void filtrar() {
		contatos = new ContatosImpl(getManager());
		Pair<List<Contato>,Paginacao> list = contatos.filtrar(paginacao,cbTipo.getValue(),cbContatoTipo.getValue(),cbLista.getValue(),cbCategoria.getValue(),cbNivel.getValue(),
				cbOrigem.getValue(),cbServico.getValue(),cbAtendente.getValue(),cbMalaDireta.getValue(),
				dataInicial.getValue(),dataFinal.getValue(),txPesquisa.getText());		
		paginacao = list.getValue();
		tbPrincipal.getItems().clear();
		tbPrincipal.getItems().addAll(list.getKey());
		
		pagination.setPageCount(paginacao.getTotalPaginas());
		pagination.setCurrentPageIndex(paginacao.getPaginaAtual());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		try {
			loadFactory();
			combos();
			filtrar();
		}catch(PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
		}finally {
			close();
		}	
	}
	@FXML
	void novaTarefa(ActionEvent event) {
		
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
		}catch (Exception e) {
			alert(AlertType.ERROR, "Erro", "", "Erro ao realizar filtro", e, true);
		}finally {
			close();
		}
	}
	@FXML
	void sair(ActionEvent event) {
		stage.close();
	}
	void tabela() {
		TableColumn<Contato, Calendar> colunaCriadoEm = new  TableColumn<>("Data");
		colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("criadoEm"));
		colunaCriadoEm.setCellFactory(param -> new TableCell<Contato,Calendar>(){
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(sdfH.format(item.getTime()));
				}
			}
		});
		colunaCriadoEm.setPrefWidth(120);
		TableColumn<Contato, String> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaNome.setPrefWidth(120);
		
		TableColumn<Contato, Number> colunaTelefone = new  TableColumn<>("Telefone");
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaTelefone.setCellFactory(param -> new TableCell<Contato,Number>(){
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					Contato c = tbPrincipal.getItems().get(getIndex());
					String telefone1 = c.getTelefone().length()>0 ? c.getTelefone()+"\\"+c.getCelular():""+c.getCelular();
					setText(telefone1);
				}
			}
		});
		colunaTelefone.setPrefWidth(60);
		
		TableColumn<Contato, String> colunaEmail= new  TableColumn<>("E-Mail");
		colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colunaEmail.setPrefWidth(70);
		
		TableColumn<Contato, NegocioOrigem> colunaOrigem= new  TableColumn<>("Origem");
		colunaOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
		colunaOrigem.setPrefWidth(70);
		
		TableColumn<Contato, NegocioServico> colunaServico= new  TableColumn<>("Servico");
		colunaServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
		colunaServico.setPrefWidth(70);
		
		TableColumn<Contato, NegocioCategoria> colunaCategoria= new  TableColumn<>("Categoria");
		colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		colunaCategoria.setPrefWidth(70);
		
		TableColumn<Contato, NegocioNivel> colunaNivel= new  TableColumn<>("Nivel");
		colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		colunaNivel.setPrefWidth(60);
		
		TableColumn<Contato, Usuario> colunaAtendente= new  TableColumn<>("Atendente");
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		colunaAtendente.setPrefWidth(60);
		
		TableColumn<Contato, NegocioProposta> colunaUtimoNegocio= new  TableColumn<>("Negocio");
		colunaUtimoNegocio.setCellValueFactory(new PropertyValueFactory<>("ultimoNegocio"));
		colunaUtimoNegocio.setCellFactory(param -> new TableCell<Contato,NegocioProposta>(){
			@Override
			protected void updateItem(NegocioProposta item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText("");
				}
			}
		});
		colunaUtimoNegocio.setPrefWidth(40);
		
		TableColumn<Contato, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<Contato,Number>(){
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
		TableColumn<Contato, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<Contato,Number>(){
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
		tbPrincipal.getColumns().addAll(colunaCriadoEm,colunaNome,colunaTelefone,colunaEmail,colunaOrigem,colunaServico,colunaCategoria,
				colunaNivel,colunaAtendente,colunaUtimoNegocio,colunaEditar,colunaExcluir);
	}
}
