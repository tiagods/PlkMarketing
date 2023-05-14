package br.com.tiagods.controller;

import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.negocio.Franquia;
import br.com.tiagods.model.negocio.Franquia.Tipo;
import br.com.tiagods.model.negocio.FranquiaPacote;
import br.com.tiagods.repository.Franquias;
import br.com.tiagods.util.JavaFxUtil;
import br.com.tiagods.util.MoedaUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.fxutils.maskedtextfield.MaskTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

@Controller
public class FranquiaCadastroController implements Initializable {
	@FXML
    private JFXRadioButton rbComercio;

    @FXML
    private JFXRadioButton rbServico;

    @FXML
    private JFXTextField txNome;

    @FXML
    private JFXTextField txNomePacote;
    
    @FXML
    private JFXRadioButton rbAtivo;

    @FXML
    private JFXRadioButton rbInativo;

    @FXML
    private TableView<FranquiaPacote> tbPacote;

    @FXML
    private MaskTextField txInvestimento;

    @FXML
    private MaskTextField txFaturamento;

    @FXML
    private JFXTextField txRetorno;
    
	@FXML
	private Label franquiaCodigo;
	@FXML
	private Label franquiaTb;

	@Autowired
	private Franquias franquias;
    private Franquia franquia;
	private Stage stage;

	public void setPropriedades(Stage stage, Franquia franquia) {
		this.franquia=franquia;
		this.stage=stage;
		if(franquia!=null) {
			preencherFormulario(franquia);
		}
	}

	void combos() {
		ToggleGroup group1 = new ToggleGroup();
		group1.getToggles().addAll(rbAtivo,rbInativo);
		rbAtivo.setSelected(true);
		
		ToggleGroup group2 = new ToggleGroup();
		group2.getToggles().addAll(rbComercio,rbServico);
		rbServico.setSelected(true);
	}
	@FXML
    void incluirPacote(ActionEvent event) {
		if(franquia==null) {
			JavaFxUtil.alert(AlertType.ERROR,"Erro","","Salve antes de continuar",null,false);
    		return;
		}
		if(txNomePacote.getText().trim().length()==0) {
			JavaFxUtil.alert(AlertType.ERROR,"Erro","","Nome obrigatório",null,false);
    		return;
    	}
		if(txInvestimento.getText().trim().length()==0) {
			JavaFxUtil.alert(AlertType.ERROR,"Erro","","Investimento obrigatório",null,false);
    		return;
    	}
		if(txRetorno.getText().trim().length()==0) {
			JavaFxUtil.alert(AlertType.ERROR,"Erro","","Retorno obrigatório",null,false);
    		return;
    	}
		if(txFaturamento.getText().trim().length()==0) {
			JavaFxUtil.alert(AlertType.ERROR,"Erro","","Fatumento obrigatório",null,false);
    		return;
    	}
		else {
			try {
				FranquiaPacote pacote = new FranquiaPacote();
				if(franquiaCodigo.getText().trim().length()>0) {
					pacote.setId(Long.parseLong(franquiaCodigo.getText()));
					pacote.setCriadoEm(Calendar.getInstance());
				}
				int location = -1;
				if(franquiaTb.getText().trim().length()>0) {
					location = Integer.parseInt(franquiaTb.getText());
				}
				pacote.setNome(txNomePacote.getText());
				BigDecimal inv = new BigDecimal(Double.parseDouble(txInvestimento.getText().replace(",", ".")));
				pacote.setInvestimento(inv);
				BigDecimal fat = new BigDecimal(Double.parseDouble(txFaturamento.getText().replace(",", ".")));
				pacote.setFaturamento(fat);
				pacote.setPrevisao(txRetorno.getText());
				pacote.setLastUpdate(Calendar.getInstance());
				pacote.setFranquia(franquia);
				if(location!=-1) {
					tbPacote.getItems().set(location, pacote);
				}
				else
					tbPacote.getItems().add(pacote);
				tbPacote.refresh();
				
				txInvestimento.setText("");
				txFaturamento.setText("");
				txRetorno.setText("");
				txNomePacote.setText("");
				franquiaCodigo.setText("");
				franquiaTb.setText("");				
			}catch(Exception e) {
				JavaFxUtil.alert(AlertType.ERROR,"Erro","","Falha ao incluir registro",e,true);
			}
		}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	combos();
    	tabela();
    }
    private void preencherFormulario(Franquia franquia) {
    	txNome.setText(franquia.getNome());
    	if(franquia.getTipo().equals(Tipo.COMERCIO)) {
    		rbComercio.setSelected(true);
    	}
    	else {
    		rbServico.setSelected(true);
    	}
    	if(franquia.isAtivo()) {
    		rbAtivo.setSelected(true);
    	}
    	else
    		rbInativo.setSelected(true);
    	Set<FranquiaPacote> pacotes = franquia.getPacotes();
    	tbPacote.getItems().clear();
    	tbPacote.getItems().addAll(pacotes);
    	this.franquia=franquia;
    }
    
    @FXML
    void sair(ActionEvent event) {
    	this.stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {
    	if(txNome.getText().trim().length()==0) {
			JavaFxUtil.alert(AlertType.ERROR,"Erro","","Nome obrigatório",null,false);
    		return;
    	}
    	else salvar();

    }
    private void salvar() {
		if(franquia==null) {
			franquia = new Franquia();
			franquia.setCriadoEm(Calendar.getInstance());
		}
		franquia.setLastUpdate(Calendar.getInstance());
		franquia.setAtivo(rbAtivo.isSelected());
		franquia.setTipo(rbComercio.isSelected()?Tipo.COMERCIO:Tipo.SERVICO);
		franquia.setNome(txNome.getText().trim());
		Set<FranquiaPacote> pacotes = new HashSet<>();
		pacotes.addAll(tbPacote.getItems());
		pacotes.forEach(c->c.setFranquia(franquia));
		franquia.setPacotes(pacotes);

		this.franquia = franquias.save(franquia);
		preencherFormulario(franquia);
		JavaFxUtil.alert(Alert.AlertType.INFORMATION,"Sucesso",null,"Salvo com sucesso",null,false);
    }
    private void tabela() {
    	TableColumn<FranquiaPacote, Number> colunaid = new  TableColumn<>("*");
		colunaid.setCellValueFactory(new PropertyValueFactory<>("id"));
		
    	TableColumn<FranquiaPacote, String> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		TableColumn<FranquiaPacote, BigDecimal> colunaInvestimento = new  TableColumn<>("Investimento");
		colunaInvestimento.setCellValueFactory(new PropertyValueFactory<>("investimento"));
		colunaInvestimento.setCellFactory(param -> new TableCell<FranquiaPacote,BigDecimal>(){
			@Override
			protected void updateItem(BigDecimal item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(MoedaUtil.format(item));
				}
			}
		});
		colunaInvestimento.setPrefWidth(120);
		
		TableColumn<FranquiaPacote, String> colunaRetorno = new  TableColumn<>("Retorno");
		colunaRetorno.setCellValueFactory(new PropertyValueFactory<>("previsao"));
		
		TableColumn<FranquiaPacote, BigDecimal> colunaFaturamento = new  TableColumn<>("Faturamento");
		colunaFaturamento.setCellValueFactory(new PropertyValueFactory<>("faturamento"));
		colunaFaturamento.setCellFactory(param -> new TableCell<FranquiaPacote,BigDecimal>(){
			@Override
			protected void updateItem(BigDecimal item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(MoedaUtil.format(item));
				}
			}
		});
		colunaFaturamento.setPrefWidth(120);
		TableColumn<FranquiaPacote, String> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaEditar.setCellFactory(param -> new TableCell<FranquiaPacote,String>(){
			JFXButton button = new JFXButton();//Editar
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btDefault");
					JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_EDIT);
					button.setOnAction(event -> {
						FranquiaPacote pacote = tbPacote.getItems().get(getIndex());
						txNomePacote.setText(pacote.getNome());
						txRetorno.setText(pacote.getPrevisao());
						txInvestimento.setText(pacote.getInvestimento().toString().replace(".", ","));
						txFaturamento.setText(pacote.getFaturamento().toString().replace(".", ","));
						if(pacote.getId()!=null) {
							franquiaCodigo.setText(pacote.getId()+"");
						}
						franquiaTb.setText(getIndex()+"");
					});
					setGraphic(button);
				}
			}
		});
		TableColumn<FranquiaPacote, String> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaExcluir.setCellFactory(param -> new TableCell<FranquiaPacote,String>(){
			JFXButton button = new JFXButton();
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btDefault");
					JavaFxUtil.buttonTable(button,IconsEnum.BUTTON_REMOVE);
					button.setOnAction(event -> {
						tbPacote.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		tbPacote.getColumns().addAll(colunaid,colunaNome,colunaInvestimento,colunaRetorno,colunaFaturamento,colunaEditar,colunaExcluir);
    }
}
