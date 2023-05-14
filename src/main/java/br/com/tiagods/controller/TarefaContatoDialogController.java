package br.com.tiagods.controller;

import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.PessoaJuridica;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.negocio.*;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.repository.*;
import br.com.tiagods.util.JavaFxUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.persistence.PersistenceException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static br.com.tiagods.util.JavaFxUtil.alert;

@Controller
public class TarefaContatoDialogController implements Initializable {
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

	@Autowired
	private Contatos contatos;
	@Autowired
	private NegociosNiveis niveis;
	@Autowired
	private NegociosCategorias categorias;
	@Autowired
	private NegociosOrigens origens;
	@Autowired
	private NegociosServicos servicos;
	@Autowired
	private Usuarios usuarios;
	
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

		cbCategoria.getItems().addAll(categorias.findAll());
		cbNivel.getItems().addAll(niveis.findAll());
		cbOrigem.getItems().addAll(origens.findAll());
		cbServico.getItems().addAll(servicos.findAll());
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
					filtrar();
				}catch(Exception e) {
					alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
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
		List<Contato> lista = contatos.filtrar(txPesquisa.getText().trim(),cbCategoria.getValue(),
				cbNivel.getValue(),cbOrigem.getValue(),cbServico.getValue());
		tbPrincipal.getItems().clear();
		tbPrincipal.getItems().addAll(lista);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		try {
			combos();
			filtrar();
		}catch (PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao executar a consulta", e, true);
		}
	}
	@FXML
	void pesquisar(KeyEvent event) {
		try {
			filtrar();
		}catch(PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
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
					JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_OK);
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

	public void setPropriedades(Stage stage) {
		this.stage = stage;
	}

}
