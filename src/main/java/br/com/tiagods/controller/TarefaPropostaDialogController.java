package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.repository.helpers.filters.NegocioPropostaFilter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.negocio.NegocioCategoria;
import br.com.tiagods.model.negocio.NegocioNivel;
import br.com.tiagods.model.negocio.NegocioOrigem;
import br.com.tiagods.model.negocio.NegocioServico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.model.negocio.NegocioProposta;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.NegocioPropostaImpl;
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
import javafx.util.Pair;

public class TarefaPropostaDialogController extends UtilsController implements Initializable{
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
    private TableView<NegocioProposta> tbPrincipal;
    
    private Stage stage;
	private NegocioProposta proposta;
	private NegocioPropostaImpl propostas;
	private NegociosNiveisImpl niveis;
	private NegocioCategoriasImpl categorias;
	private NegociosOrigensImpl origens;
	private NegocioServicosImpl servicos;
	private UsuariosImpl usuarios;
	
	public TarefaPropostaDialogController(Stage stage) {
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
		niveis = new NegociosNiveisImpl(getManager());
		origens = new NegociosOrigensImpl(getManager());
		servicos = new NegocioServicosImpl(getManager());
		propostas = new NegocioPropostaImpl(getManager());
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
		
		ChangeListener<Object> change = new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
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
		propostas = new NegocioPropostaImpl(getManager());

		NegocioPropostaFilter filter = new NegocioPropostaFilter();
		filter.setCategoria(cbCategoria.getValue());
		filter.setNivel(cbNivel.getValue());
		filter.setOrigem(cbOrigem.getValue());
		filter.setServico(cbServico.getValue());
		filter.setAtendente(cbAtendente.getValue());
		filter.setPesquisa(txPesquisa.getText());

		Pair<List<NegocioProposta>,Paginacao> lista = propostas.filtrar(null,filter);

		tbPrincipal.getItems().clear();
		tbPrincipal.getItems().addAll(lista.getKey());
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		try {
			loadFactory();
			combos();
			filtrar();
		}catch (Exception e) {
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
		}catch(Exception e) {
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
		NegocioProposta c = tbPrincipal.getSelectionModel().getSelectedItem();
		if(c!=null) {
			setProposta(c);
			stage.close();
		}
		else
			alert(AlertType.ERROR, "Erro", "Nenhum contato", "Nenhum contato foi selecionado", null, false);
	}	
	@SuppressWarnings("unchecked")
	private void tabela() {
		TableColumn<NegocioProposta, String> colunaCodigo = new  TableColumn<>("Nº");
		colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		TableColumn<NegocioProposta, String> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaNome.setCellFactory(param -> new TableCell<NegocioProposta,String>(){
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
		
		TableColumn<NegocioProposta, NegocioProposta.TipoStatus> colunaTipo = new  TableColumn<>("Status");
		colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoStatus"));
		colunaTipo.setCellFactory(param -> new TableCell<NegocioProposta,NegocioProposta.TipoStatus>(){
			@Override
			protected void updateItem(NegocioProposta.TipoStatus item, boolean empty) {
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
		TableColumn<NegocioProposta, Calendar> colunaCriadoEm = new  TableColumn<>("Criado Em");
		colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("criadoEm"));
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
		
		TableColumn<NegocioProposta, NegocioCategoria> colunaCategoria = new  TableColumn<>("Categoria");
		colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		colunaCategoria.setCellFactory(param -> new TableCell<NegocioProposta,NegocioCategoria>(){
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
		TableColumn<NegocioProposta, NegocioOrigem> colunaOrigem = new  TableColumn<>("Origem");
		colunaOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
		colunaOrigem.setCellFactory(param -> new TableCell<NegocioProposta,NegocioOrigem>(){
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
		TableColumn<NegocioProposta, NegocioNivel> colunaNivel = new  TableColumn<>("Nivel");
		colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		colunaNivel.setCellFactory(param -> new TableCell<NegocioProposta,NegocioNivel>(){
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
		TableColumn<NegocioProposta, NegocioServico> colunaServico = new  TableColumn<>("Servico");
		colunaServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
		colunaServico.setCellFactory(param -> new TableCell<NegocioProposta,NegocioServico>(){
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
		TableColumn<NegocioProposta, Usuario> colunaAtendente = new  TableColumn<>("Atendente");
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		colunaAtendente.setCellFactory(param -> new TableCell<NegocioProposta,Usuario>(){
			@Override
			protected void updateItem(Usuario item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.toString());
				}
			}
		});
		
		TableColumn<NegocioProposta, Number> colunaSelecionar = new  TableColumn<>("");
		colunaSelecionar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaSelecionar.setCellFactory(param -> new TableCell<NegocioProposta,Number>(){
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
						setProposta(tbPrincipal.getItems().get(getIndex()));
						stage.close();
					});
					setGraphic(button);
				}
			}
		});
		tbPrincipal.getColumns().addAll(colunaCodigo,colunaNome,colunaCriadoEm,colunaTipo,colunaCategoria,colunaNivel,colunaOrigem,colunaServico,
				colunaAtendente,colunaSelecionar);
		tbPrincipal.setTableMenuButtonVisible(true);
	}
	private void setProposta(NegocioProposta proposta) {
		this.proposta=proposta;
	}
	public NegocioProposta getProposta() {
		return this.proposta;
	}
}
