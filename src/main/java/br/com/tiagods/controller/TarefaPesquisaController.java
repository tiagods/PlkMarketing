package br.com.tiagods.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;

import br.com.tiagods.model.ConstantesTemporarias;
import br.com.tiagods.model.Tarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.TarefasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TarefaPesquisaController extends UtilsController implements Initializable{

    @FXML
    private ImageView chEmail;

    @FXML
    private JFXCheckBox ckProposta;

    @FXML
    private JFXCheckBox ckReuniao;

    @FXML
    private JFXCheckBox ckTelefone;

    @FXML
    private JFXCheckBox ckWhatsApp;

    @FXML
    private JFXRadioButton rbTudo;

    @FXML
    private JFXRadioButton rbHoje;

    @FXML
    private JFXRadioButton rbSemana;

    @FXML
    private JFXRadioButton rbDefinir;

    @FXML
    private HBox pnDatas;
    
    @FXML
    private HBox pnCheckBox;

    @FXML
    private JFXDatePicker dataInicial;

    @FXML
    private JFXDatePicker dataFinal;

    @FXML
    private JFXToggleButton tggFinalizado;

    @FXML
    private JFXComboBox<Usuario> cbAtenente;

    @FXML
    private TableView<Tarefa> tbPrincipal;
	private Stage stage;
	private UsuariosImpl usuarios;
	private TarefasImpl tarefas;
	
	
	public TarefaPesquisaController (Stage stage) {
		this.stage=stage;
	}
	void abrirCadastro(Tarefa t) {
		
	}
	
	void combos() {
		pnCheckBox.getChildren().forEach(c->{if(c instanceof JFXCheckBox) ((JFXCheckBox)c).setSelected(true);});
		rbHoje.setSelected(true);
		pnDatas.setVisible(false);
		usuarios = new UsuariosImpl(getManager());
		cbAtenente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
	}
	void excluir() {
		
	}
    @FXML
    void exportar(ActionEvent event) {

    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		try {
			loadFactory();
			combos();
		}catch(Exception e) {
			
		}finally {
			close();
		}
	}
	@FXML
    void novo(ActionEvent event) {
		
    }

    @FXML
    void sair(ActionEvent event) {
    	stage.close();
    }	
    
    void tabela() {
    	TableColumn<Tarefa, Number> columnId = new  TableColumn<>("*");
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnId.setPrefWidth(40);

		TableColumn<Tarefa, String> columnNome = new  TableColumn<>("Nome");
		columnNome.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		columnNome.setCellFactory(param -> new TableCell<Tarefa,String>(){
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

		TableColumn<Tarefa, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<Tarefa,Number>(){
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
		TableColumn<Tarefa, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<Tarefa,Number>(){
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
					button.setOnAction(event -> {
						boolean removed = excluir(tbPrincipal.getItems().get(getIndex()));
						if(removed) tbPrincipal.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		tbPrincipal.getColumns().addAll(columnNome,colunaEditar,colunaExcluir);
		tbPrincipal.setTableMenuButtonVisible(true);
    }
}
