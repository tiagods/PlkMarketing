package br.com.tiagods.controller;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.repository.Usuarios;
import br.com.tiagods.repository.interfaces.StageController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class UsuarioPesquisaController implements Initializable, StageController {
	@FXML
	private JFXTextField txPesquisa;
	@FXML
	private TableView<Usuario> tbPrincipal;
	private Stage stage;

	@Autowired
	Usuarios usuarios;

	@Autowired
	UsuarioCadastroController usuarioCadastroController;

	@Lazy
	@Autowired
	StageManager stageManager;

	@Override
	public void setPropriedades(Stage stage)	{
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		filtrar();
	}
	
	private	void abrirCadastro(Usuario usuario){
		Stage stage = new Stage();
		stageManager.switchScene(FxmlView.USUARIO_CADASTRO, stage);
		usuarioCadastroController.setPropriedades(stage,usuario);
		stage.setOnHiding(event -> filtrar());
	}
	@FXML
	private void cadastrar(ActionEvent event) {
		abrirCadastro(null);
	}

	private void filtrar() {
		tbPrincipal.getItems().clear();
		List<Usuario> usuarioList = usuarios.findAllByNomeContainingIgnoreCaseOrderByNome(txPesquisa.getText().trim());
		tbPrincipal.getItems().addAll(usuarioList);
	}
	@FXML
	void pesquisar(KeyEvent event) {
		filtrar();
	}
	@SuppressWarnings("unchecked")
	private void tabela() {
		TableColumn<Usuario, Number> columnId = new  TableColumn<>("*");
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnId.setPrefWidth(40);

		TableColumn<Usuario, String> columnNome = new  TableColumn<>("Nome");
		columnNome.setCellValueFactory(new PropertyValueFactory<>(ConstantesTemporarias.pessoa_nome));
		columnNome.setCellFactory(param -> new TableCell<Usuario,String>(){
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
		columnNome.setPrefWidth(250);
		columnNome.setMaxWidth(320);

		TableColumn<Usuario, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<Usuario,Number>(){
			JFXButton button = new JFXButton("Editar");
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btGreen");
					button.setOnAction(event -> {
						abrirCadastro(tbPrincipal.getItems().get(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		TableColumn<Usuario, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<Usuario,Number>(){
			JFXButton button = new JFXButton("Excluir");
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btRed");
					button.setDisable(true);
					button.setTooltip(new Tooltip("Desabilitado"));
					setGraphic(button);
				}
			}
		});
		tbPrincipal.getColumns().addAll(columnId,columnNome,colunaEditar,colunaExcluir);
		tbPrincipal.setTableMenuButtonVisible(true);
	}
	@FXML
	void sair(ActionEvent event){
		stage.close();
	}
}
