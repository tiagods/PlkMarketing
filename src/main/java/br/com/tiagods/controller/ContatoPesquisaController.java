package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.exception.FXMLNaoEncontradoException;
import br.com.tiagods.model.Contato;
import br.com.tiagods.model.Contato.ContatoTipo;
import br.com.tiagods.model.Contato.PessoaTipo;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioLista;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.repository.helpers.ContatosImpl;
import br.com.tiagods.repository.helpers.NegocioCategoriasImpl;
import br.com.tiagods.repository.helpers.NegocioNiveisImpl;
import br.com.tiagods.repository.helpers.NegocioOrigensImpl;
import br.com.tiagods.repository.helpers.NegocioServicosImpl;
import br.com.tiagods.repository.helpers.NegociosListasImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private HBox pnCheckBox1;

    @FXML
    private JFXCheckBox ckConvite;

    @FXML
    private JFXCheckBox ckMaterial;

    @FXML
    private JFXCheckBox ckNewsletter;

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

	private Stage stage;
	private ContatosImpl contatos;
	private NegocioNiveisImpl niveis;
	private NegocioCategoriasImpl categorias;
	private NegocioOrigensImpl origens;
	private NegocioServicosImpl servicos;
	private NegociosListasImpl listas;
	private UsuariosImpl usuarios;
	
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
        }catch(FXMLNaoEncontradoException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo"+FXMLEnum.TAREFA_CADASTRO,e,true);
        }finally {
        	close();
        }
	}
	
	void combos() {
		NegocioCategoria categoria = new NegocioCategoria(-1L,"Categoria");
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
		
		cbCategoria.getItems().addAll(categorias.getAll());
		cbNivel.getItems().addAll(niveis.getAll());
		cbOrigem.getItems().addAll(origens.getAll());
		cbServico.getItems().addAll(servicos.getAll());
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
		//cbLista.getItems().addAll(listas.getAll());
		
		cbTipo.getItems().addAll(PessoaTipo.values());
		cbContatoTipo.getItems().addAll(ContatoTipo.values());
		
		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst(); 
		cbAtendente.getSelectionModel().selectFirst();
		//cbLista.getSelectionModel().selectFirst();
		
		cbTipo.getSelectionModel().select(PessoaTipo.CONTATO);
		cbContatoTipo.getSelectionModel().select(ContatoTipo.CONTATO);
		
		tbPrincipal.getItems().addAll(contatos.getAll());
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

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		try {
			loadFactory();
			combos();
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
	void sair(ActionEvent event) {
		stage.close();
	}
	void tabela() {
		TableColumn<Contato, String> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn<Contato, String> colunaTelefone = new  TableColumn<>("Telefone");
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

		TableColumn<Contato, String> colunaEmail= new  TableColumn<>("E-Mail");
		colunaEmail.setCellValueFactory(new PropertyValueFactory<>("telefone"));

		TableColumn<Contato, NegocioOrigem> colunaOrigem= new  TableColumn<>("Origem");
		colunaOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));

		TableColumn<Contato, NegocioServico> colunaServico= new  TableColumn<>("Servico");
		colunaServico.setCellValueFactory(new PropertyValueFactory<>("servico"));

		TableColumn<Contato, NegocioCategoria> colunaCategoria= new  TableColumn<>("Categoria");
		colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

		TableColumn<Contato, NegocioNivel> colunaNivel= new  TableColumn<>("Nivel");
		colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		
		TableColumn<Contato, Usuario> colunaAtendente= new  TableColumn<>("Atendente");
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		
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
		tbPrincipal.getColumns().addAll(colunaNome,colunaTelefone,colunaEmail,colunaOrigem,colunaServico,colunaCategoria,
				colunaNivel,colunaAtendente,colunaUtimoNegocio,colunaEditar,colunaExcluir);
	}
}
