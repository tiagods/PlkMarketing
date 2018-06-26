package br.com.tiagods.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.repository.helpers.CidadesImpl;
import br.com.tiagods.util.ComboBoxAutoCompleteUtil;
import br.com.tiagods.util.EnderecoUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class UtilsController extends PersistenciaController{
	private JFXButton buttonNovo;
	private JFXButton buttonEditar;
	private JFXButton buttonSalvar;
	private JFXButton buttonExcluir;
	private JFXButton buttonCancelar;
	private JFXButton buttonSair;
	private boolean habilidarFiltroCidade = true;

	final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	//Locale locale = new Locale("pt", "BR");
	final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	final SimpleDateFormat sdfH = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public void alert(AlertType alertType, String title, String header, String contentText,Exception ex, boolean print) {
		Alert alert = new Alert(alertType);
		alert.getDialogPane().setExpanded(true);
		alert.getDialogPane().setMinSize(600,150);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(contentText);
		if(alert.getAlertType()==AlertType.ERROR && ex!=null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String exceptionText = sw.toString();
			Label label = new Label("Mais detalhes do erro:");
			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);
			// Set expandable Exception into the dialog pane.
			alert.getDialogPane().setExpandableContent(expContent);

			if(print) {
				try {
					LocalDateTime dateTime = LocalDateTime.now();
					File log = new File(System.getProperty("user.dir") + "/log/" +
							dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "-erro.txt");
					if (!log.getParentFile().exists())
						log.getParentFile().mkdir();
					FileWriter fw = new FileWriter(log, true);
					String line = System.getProperty("line.separator");
					fw.write(
							dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "=" +
									header + ":" +
									contentText +
									line +
									exceptionText
					);
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		alert.showAndWait();
	}

	public void comboRegiao(JFXComboBox<Cidade> cbCidade, JFXComboBox<Cidade.Estado> cbEstado, EntityManager manager){
		CidadesImpl cidades = new CidadesImpl(manager);
		Cidade cidade = cidades.findByNome("São Paulo");
		cbCidade.getItems().addAll(cidades.findByEstado(Cidade.Estado.SP));
		cbCidade.setValue(cidade);
		cbEstado.getItems().addAll(Cidade.Estado.values());
		cbEstado.setValue(Cidade.Estado.SP);
		cbEstado.valueProperty().addListener(new BuscaCep(cbCidade));
		new ComboBoxAutoCompleteUtil<>(cbCidade);
	}
	void bucarCep(MaskedTextField txCEP, JFXTextField txLogradouro, JFXTextField txNumero,JFXTextField txComplemento,
				  JFXTextField txBairro, JFXComboBox<Cidade> cbCidade, JFXComboBox<Cidade.Estado> cbEstado
				  ){
		try{
			loadFactory();
			EnderecoUtil util = EnderecoUtil.getInstance();
			if(txCEP.getPlainText().trim().length()==8) {
				Endereco endereco = util.pegarCEP(txCEP.getPlainText());
				if(endereco!=null){
					habilidarFiltroCidade = false;
					CidadesImpl cidades = new CidadesImpl(getManager());
					Cidade cidade = cidades.findByNome(endereco.getLocalidade());
					txLogradouro.setText(endereco.getLogradouro());
					txNumero.setText("");
					txComplemento.setText(endereco.getComplemento());
					txBairro.setText(endereco.getBairro());
					cbCidade.getItems().clear();

					List<Cidade> cidadeList = cidades.findByEstado(endereco.getUf());
					cbEstado.setValue(endereco.getUf());
					cbCidade.getItems().addAll(cidadeList);
					cbCidade.setValue(cidade);
					new ComboBoxAutoCompleteUtil<>(cbCidade);
					habilidarFiltroCidade = true;

				}
				else
					alert(Alert.AlertType.WARNING,"CEP Invalido",null,
							"Verifique se o cep informado é valido ou se existe uma conexão com a internet",null, false);
			}
			else{
				alert(Alert.AlertType.WARNING,"CEP Invalido",null,"Verifique o cep informado",null, false);
			}
		}catch(Exception e){
			alert(Alert.AlertType.ERROR,"Falha na conexão com o banco de dados",null,
					"Houve uma falha na conexão com o banco de dados",e,true);
		}finally {
			close();
		}
	}
	public Optional<String> cadastroRapido(){
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Cadastro rapido");
		dialog.setHeaderText("Crie um novo registro:");
		dialog.setContentText("Por favor entre com um novo nome");
		return dialog.showAndWait();
	}
	
	public FXMLLoader loaderFxml(FXMLEnum e) throws IOException{
    	final FXMLLoader loader = new FXMLLoader(e.getLocalizacao());
        return loader;
    }
    public Stage initPanel(FXMLLoader loader,Stage stage, Modality modality, StageStyle ss) throws IOException{
    		final Parent root = loader.load();
	        final Scene scene = new Scene(root);
	        stage.initModality(modality);
	        stage.initStyle(ss);
	        stage.setScene(scene);
	        stage.show();
	        return stage;
    }
	public void buttonTable(JFXButton btn,IconsEnum icon) throws IOException{
		ImageView imageview = createImage(30,30,icon);
		btn.setGraphic(imageview);
	}
	
	private ImageView createImage(int x, int y, IconsEnum icon) {
		Image image = new Image(icon.getLocalizacao().toString());
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(x);
		imageview.setFitWidth(y);
		imageview.setPreserveRatio(true);
		return imageview;
	}
    
	public class BuscaCep implements ChangeListener<Cidade.Estado>{
		private JFXComboBox<Cidade> cbCidade;
		public BuscaCep(JFXComboBox<Cidade> cbCidade){
			this.cbCidade=cbCidade;
		}
		@Override
		public void changed(ObservableValue<? extends Cidade.Estado> observable, Cidade.Estado oldValue, Cidade.Estado newValue) {
			if (newValue != null && habilidarFiltroCidade) {
				try {
					loadFactory(getManager());
					CidadesImpl cidades = new CidadesImpl(getManager());
					cbCidade.getItems().clear();
					List<Cidade> listCidades = cidades.findByEstado(newValue);
					cbCidade.getItems().addAll(listCidades);
					cbCidade.getSelectionModel().selectFirst();
				} catch (Exception e) {
				} finally {
					close();
				}
			}
		}
	}
}
