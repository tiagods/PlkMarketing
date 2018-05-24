package br.com.tiagods.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;

import br.com.tiagods.model.ConstantesTemporarias;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.TipoTarefa;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.NegociosTarefasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.repository.interfaces.NegocioTarefaPropostaDAO;
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
    private TableView<NegocioTarefa> tbPrincipal;
	private Stage stage;
	private UsuariosImpl usuarios;
	private NegociosTarefasImpl tarefas;
	
	
	public TarefaPesquisaController (Stage stage) {
		this.stage=stage;
	}
	void abrirCadastro(NegocioTarefa t) {
		
	}
	void abrirCadastroContatoOrProposta(NegocioTarefa t) {
		
	}
	
	void combos() {
		pnCheckBox.getChildren().forEach(c->{if(c instanceof JFXCheckBox) ((JFXCheckBox)c).setSelected(true);});
		rbHoje.setSelected(true);
		pnDatas.setVisible(false);
		usuarios = new UsuariosImpl(getManager());
		cbAtenente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
		
		tarefas = new NegociosTarefasImpl(getManager());
		tbPrincipal.getItems().addAll(tarefas.getAll());
		tbPrincipal.getItems().forEach(c->System.out.println(c.getAtendente().getNome()));
	}
	boolean excluir(NegocioTarefa n) {
		return false;
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
    	TableColumn<NegocioTarefa, Number> columnId = new  TableColumn<>("*");
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnId.setPrefWidth(40);
		
		TableColumn<NegocioTarefa, Calendar> colunaValidade = new  TableColumn<>("Prazo");
		colunaValidade.setCellValueFactory(new PropertyValueFactory<>("dataEvento"));
		colunaValidade.setCellFactory(param -> new TableCell<NegocioTarefa,Calendar>(){
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(item.getTime()));
				}
			}
		});
		
		TableColumn<NegocioTarefa, TipoTarefa> colunaAndamento = new  TableColumn<>("Andamento");
		colunaAndamento.setCellValueFactory(new PropertyValueFactory<>("tipoTarefa"));
		colunaAndamento.setCellFactory(param -> new TableCell<NegocioTarefa,TipoTarefa>(){
			@Override
			protected void updateItem(TipoTarefa item, boolean empty) {
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
		
		TableColumn<NegocioTarefa, NegocioTarefa> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellFactory(param -> new TableCell<NegocioTarefa,NegocioTarefa>(){
			@Override
			protected void updateItem(NegocioTarefa item, boolean empty) {
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
		TableColumn<NegocioTarefa, String> colunaTipo = new  TableColumn<>("Tipo");
		colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoTarefa"));
		colunaTipo.setCellFactory(param -> new TableCell<NegocioTarefa,String>(){
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
		
		TableColumn<NegocioTarefa, String> colunaDescricao = new  TableColumn<>("Descricao");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		colunaDescricao.setCellFactory(param -> new TableCell<NegocioTarefa,String>(){
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
		
		TableColumn<NegocioTarefa, Number> columnStatus = new  TableColumn<>("Status");
		columnStatus.setCellValueFactory(new PropertyValueFactory<>("finalizado"));
		columnStatus.setCellFactory(param -> new TableCell<NegocioTarefa,Number>(){
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.intValue()==1?"Finalizado":"Aberto");
				}
			}
		});
		
		TableColumn<NegocioTarefa, Usuario> columnAtendente = new  TableColumn<>("Atendente");
		columnAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		columnAtendente.setCellFactory(param -> new TableCell<NegocioTarefa,Usuario>(){
			@Override
			protected void updateItem(Usuario item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getLogin());
				}
			}
		});
		
		TableColumn<NegocioTarefa, NegocioTarefa> colunaAbrir = new  TableColumn<>("");
		colunaAbrir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaAbrir.setCellFactory(param -> new TableCell<NegocioTarefa,NegocioTarefa>(){
			JFXButton button = new JFXButton("");
			@Override
			protected void updateItem(NegocioTarefa item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btGreen");
					button.setOnAction(event -> {
						abrirCadastroContatoOrProposta(tbPrincipal.getItems().get(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		TableColumn<NegocioTarefa, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<NegocioTarefa,Number>(){
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
		TableColumn<NegocioTarefa, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<NegocioTarefa,Number>(){
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
		tbPrincipal.getColumns().addAll(colunaValidade,colunaAndamento,colunaTipo,colunaNome,colunaDescricao,
				columnAtendente,columnStatus,colunaAbrir,colunaEditar,colunaExcluir);
		tbPrincipal.setTableMenuButtonVisible(true);
    }
}
