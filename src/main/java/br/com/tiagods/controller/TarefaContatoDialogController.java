package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import br.com.tiagods.controller.utils.UtilsController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.negocio.Contato;
import br.com.tiagods.model.negocio.NegocioCategoria;
import br.com.tiagods.model.negocio.NegocioNivel;
import br.com.tiagods.model.negocio.NegocioOrigem;
import br.com.tiagods.model.negocio.NegocioServico;
import br.com.tiagods.model.PessoaJuridica;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.repository.helpers.ContatosImpl;
import br.com.tiagods.repository.helpers.NegocioCategoriasImpl;
import br.com.tiagods.repository.helpers.NegocioNiveisImpl;
import br.com.tiagods.repository.helpers.NegocioOrigensImpl;
import br.com.tiagods.repository.helpers.NegocioServicosImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TarefaContatoDialogController extends UtilsController implements Initializable{
	@FXML
    private JFXComboBox<NegocioCategoria> cbCategoria;

    @FXML
    private JFXComboBox<NegocioOrigem> cbOrigem;

    @FXML
    private JFXComboBox<NegocioNivel> cbNivel;

    @FXML
    private JFXComboBox<NegocioServico> cbServico;

    @FXML
    private JFXComboBox<Usuario> cbAtendente;
    
    @FXML
    private HBox pnRadio;

    @FXML
    private JFXTextField txPesquisa;

    @FXML
    private TableView<Contato> tbPrincipal;
    
	private Stage stage;
	private Contato contato;
	
	private ContatosImpl contatos;
	private NegocioNiveisImpl niveis;
	private NegocioCategoriasImpl categorias;
	private NegocioOrigensImpl origens;
	private NegocioServicosImpl servicos;
	private UsuariosImpl usuarios;
	
	public TarefaContatoDialogController(Stage stage) {
		this.stage = stage;
	}
	
	private void combos() {
		NegocioCategoria categoria = new NegocioCategoria(-1L,"Catetoria");
		NegocioNivel nivel = new NegocioNivel(-1L,"Nivel");
		NegocioOrigem origem = new NegocioOrigem(-1L,"Origem");
		NegocioServico servico = new NegocioServico(-1L, "Servico");
		Usuario atendente = new Usuario(-1L,"Atendente");
		cbCategoria.getItems().add(categoria);
		cbNivel.getItems().add(nivel);
		cbOrigem.getItems().add(origem);
		cbServico.getItems().add(servico);
		cbAtendente.getItems().add(atendente);
		
		
		categorias = new NegocioCategoriasImpl(getManager());
		niveis = new NegocioNiveisImpl(getManager());
		origens = new NegocioOrigensImpl(getManager());
		servicos = new NegocioServicosImpl(getManager());
		contatos = new ContatosImpl(getManager());
		usuarios = new UsuariosImpl(getManager());
				
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
		
		ChangeListener<Object> change = new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				try {
					loadFactory();
					filtrar();
				}catch(Exception e) {
					alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
				}finally {
					close();
				}
			}
			
		};
		cbCategoria.valueProperty().addListener(change);
		cbNivel.valueProperty().addListener(change);
		cbOrigem.valueProperty().addListener(change);
		cbServico.valueProperty().addListener(change);
		cbAtendente.valueProperty().addListener(change);
	}
	void filtrar() {
		contatos = new ContatosImpl(getManager());
		List<Contato> lista = contatos.filtrar(txPesquisa.getText().trim(),cbCategoria.getValue(),
				cbNivel.getValue(),cbOrigem.getValue(),cbServico.getValue());
		tbPrincipal.getItems().clear();
		tbPrincipal.getItems().addAll(lista);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		try {
			loadFactory();
			combos();
			filtrar();
		}catch (PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao executar a consulta", e, true);
		}finally {
			close();
		}
	}
	@FXML
	void pesquisar(KeyEvent event) {
		try {
			loadFactory();
			filtrar();
		}catch(PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
		}finally {
			close();
		}
	}
	@FXML
	void sair(ActionEvent event) {
		stage.close();
	}
	@FXML
	void selecionar(ActionEvent event) {
		Contato c = tbPrincipal.getSelectionModel().getSelectedItem();
		if(c!=null) {
			setContato(c);
			stage.close();
		}
		else
			alert(AlertType.ERROR, "Erro", "Nenhum contato", "Nenhum contato foi selecionado", null, false);
	}	
	@SuppressWarnings("unchecked")
	private void tabela() {
		TableColumn<Contato, String> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaNome.setCellFactory(param -> new TableCell<Contato,String>(){
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item);
				}
			}
		});
		
		TableColumn<Contato, Contato.PessoaTipo> colunaTipo = new  TableColumn<>("Tipo");
		colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		colunaTipo.setCellFactory(param -> new TableCell<Contato,Contato.PessoaTipo>(){
			@Override
			protected void updateItem(Contato.PessoaTipo item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getDescricao());
				}
			}
		});
		TableColumn<Contato, PessoaJuridica> colunaResponsavel = new  TableColumn<>("Responsavel");
		colunaResponsavel.setCellValueFactory(new PropertyValueFactory<>("juridico"));
		colunaResponsavel.setCellFactory(param -> new TableCell<Contato,PessoaJuridica>(){
			@Override
			protected void updateItem(PessoaJuridica item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getResponsavel());
				}
			}
		});
		TableColumn<Contato, PessoaJuridica> colunaRazao = new  TableColumn<>("Razao Social");
		colunaRazao.setCellValueFactory(new PropertyValueFactory<>("juridico"));
		colunaRazao.setCellFactory(param -> new TableCell<Contato,PessoaJuridica>(){
			@Override
			protected void updateItem(PessoaJuridica item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getRazao());
				}
			}
		});
		
		TableColumn<Contato, NegocioCategoria> colunaCategoria = new  TableColumn<>("Categoria");
		colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		colunaCategoria.setCellFactory(param -> new TableCell<Contato,NegocioCategoria>(){
			@Override
			protected void updateItem(NegocioCategoria item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getNome());
				}
			}
		});
		TableColumn<Contato, NegocioOrigem> colunaOrigem = new  TableColumn<>("Origem");
		colunaOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
		colunaOrigem.setCellFactory(param -> new TableCell<Contato,NegocioOrigem>(){
			@Override
			protected void updateItem(NegocioOrigem item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getNome());
				}
			}
		});
		TableColumn<Contato, NegocioNivel> colunaNivel = new  TableColumn<>("Nivel");
		colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		colunaNivel.setCellFactory(param -> new TableCell<Contato,NegocioNivel>(){
			@Override
			protected void updateItem(NegocioNivel item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getNome());
				}
			}
		});
		TableColumn<Contato, NegocioServico> colunaServico = new  TableColumn<>("Servico");
		colunaServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
		colunaServico.setCellFactory(param -> new TableCell<Contato,NegocioServico>(){
			@Override
			protected void updateItem(NegocioServico item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getNome());
				}
			}
		});
		TableColumn<Contato, Usuario> colunaAtendente = new  TableColumn<>("Atendente");
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		colunaAtendente.setCellFactory(param -> new TableCell<Contato,Usuario>(){
			@Override
			protected void updateItem(Usuario item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getNomeResumido());
				}
			}
		});
		
		TableColumn<Contato, Number> colunaSelecionar = new  TableColumn<>("");
		colunaSelecionar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaSelecionar.setCellFactory(param -> new TableCell<Contato,Number>(){
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
						buttonTable(button, IconsEnum.BUTTON_OK);
					}catch (IOException e) {
					}
					button.setOnAction(event -> {
						setContato(tbPrincipal.getItems().get(getIndex()));
						stage.close();
					});
					setGraphic(button);
				}
			}
		});
		tbPrincipal.getColumns().addAll(colunaNome,colunaTipo, colunaRazao, colunaResponsavel,colunaCategoria,colunaNivel,colunaOrigem,colunaServico,
				colunaAtendente,colunaSelecionar);
		tbPrincipal.setTableMenuButtonVisible(true);
	}
	private void setContato(Contato contato) {
		this.contato=contato;
	}
	public Contato getContato() {
		return this.contato;
	}
}
