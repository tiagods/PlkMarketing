package br.com.tiagods.controller;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.tiagods.model.negocio.NegocioProposta;
import br.com.tiagods.repository.helpers.NegocioPropostaImpl;
import br.com.tiagods.util.storage.Storage;
import javafx.scene.control.MenuItem;
import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.config.init.VersaoSistema;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Endereco;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.model.UsuarioLog;
import br.com.tiagods.repository.helpers.CidadesImpl;
import br.com.tiagods.repository.helpers.UsuarioLogImpl;
import br.com.tiagods.util.ComboBoxAutoCompleteUtil;
import br.com.tiagods.util.EnderecoUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class UtilsController extends PersistenciaController{
	private boolean habilidarFiltroCidade = true;
	protected final VersaoSistema sistemaVersao = VersaoSistema.getInstance();
	
	protected final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	//Locale locale = new Locale("pt", "BR");
	protected final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	protected final SimpleDateFormat sdfH = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	protected final Integer[] limiteTabela = new Integer[] {50,100,200};

	private NegocioPropostaImpl propostas;

	protected Stage abrirNegocioProposta(NegocioProposta proposta) {
		try {
			loadFactory();
			if (proposta != null) {
				propostas = new NegocioPropostaImpl(getManager());
				proposta = propostas.findById(proposta.getId());
			}
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.NEGOCIO_CADASTRO);
			loader.setController(new NegocioCadastroController(stage, proposta,null));
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
			return stage;
		} catch (IOException e) {
			alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo" + FXMLEnum.NEGOCIO_CADASTRO, e, true);
			return null;
		} finally {
			close();
		}
	}

	private Alert alert(AlertType alertType,String title, String header, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(contentText);
		return alert;
	}
	protected void alert(AlertType alertType, String title, String header, String contentText,Exception ex, boolean print) {
		Alert alert = alert(alertType,title,header,contentText);
		alert.getDialogPane().setExpanded(true);
		alert.getDialogPane().setMinSize(600,150);
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

	protected File salvarTemp(String extensao){
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		File file = new File(System.getProperty("java.io.tmpdir")+"/file-"+sdf.format(new Date())+"."+extensao);
		return file;
	}
	/*
	public String carregarArquivo(String title){
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setDialogTitle(title);
        String local = "";
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Planilha do Excel (*.xls)", ".xls"));
        int retorno = chooser.showSaveDialog(null);
        if(retorno==JFileChooser.APPROVE_OPTION){
        	local = chooser.getSelectedFile().getAbsolutePath(); //
        }
        return local;
    }
    */
	protected void comboRegiao(JFXComboBox<Cidade> cbCidade, JFXComboBox<Cidade.Estado> cbEstado, EntityManager manager){
		CidadesImpl cidades = new CidadesImpl(manager);
		Cidade cidade = cidades.findByNome("São Paulo");
		cbCidade.getItems().addAll(cidades.findByEstado(Cidade.Estado.SP));
		cbCidade.setValue(cidade);
		cbEstado.getItems().addAll(Cidade.Estado.values());
		cbEstado.setValue(Cidade.Estado.SP);
		cbEstado.valueProperty().addListener(new BuscaCep(cbCidade));
		new ComboBoxAutoCompleteUtil<>(cbCidade);
	}
	
	protected void bucarCep(MaskedTextField txCEP, JFXTextField txLogradouro, JFXTextField txNumero,JFXTextField txComplemento,
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
	protected void buttonTable(JFXButton btn,IconsEnum icon) throws IOException{
		buttonIcon(btn,icon,30);
	}
	protected void buttonMin(JFXButton btn,IconsEnum icon) throws IOException{
		buttonIcon(btn,icon,22);
	}
	private void buttonIcon(JFXButton btn,IconsEnum icon,int size){
		ImageView imageview = createImage(size,size,icon);
		btn.setGraphic(imageview);
		btn.setTooltip(icon.getTooltip());
	}
	protected Optional<String> cadastroRapido(){
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Cadastro rapido");
		dialog.setHeaderText("Crie um novo registro:");
		dialog.setContentText("Por favor entre com um novo nome");
		return dialog.showAndWait();
	}
	protected void iconMenuItem(MenuItem item, int x, int y, IconsEnum icon){
		item.setGraphic(createImage(x,y,icon));
	}
	protected ImageView createImage(int x, int y, IconsEnum icon) {
		Image image = new Image(icon.getLocalizacao().toString());
		ImageView imageview = new ImageView(image);
		imageview.setFitHeight(x);
		imageview.setFitWidth(y);
		imageview.setPreserveRatio(true);
		return imageview;
	}
	protected Stage initPanel(FXMLLoader loader,Stage stage, Modality modality, StageStyle ss) throws IOException{
    		final Parent root = loader.load();
	        final Scene scene = new Scene(root);
	        stage.initModality(modality);
	        stage.initStyle(ss);
	        stage.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
            stage.setScene(scene);
	        stage.show();
	        return stage;
    }
	protected FXMLLoader loaderFxml(FXMLEnum e) throws IOException{
    	final FXMLLoader loader = new FXMLLoader(e.getLocalizacao());
        return loader;
    }

	protected void salvarLog(EntityManager manager,String menu, String acao, String descricao) throws Exception{
		UsuarioLogImpl logImpl = new UsuarioLogImpl(manager);
		UsuarioLog log = new UsuarioLog();
		log.setData(Calendar.getInstance());
		log.setUsuario(UsuarioLogado.getInstance().getUsuario());
		log.setMenu(menu);
		log.setAcao(acao);
		log.setDescricao(descricao);
		logImpl.save(log);
	}
	protected Stage startProgress(){
		try {
			FXMLLoader loader = new FXMLLoader(FXMLEnum.PROGRESS_SAMPLE.getLocalizacao());
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("");
			DialogPane dialogPane = new DialogPane();
			dialogPane.setContent(loader.load());
			alert.setDialogPane(dialogPane);
			alert.show();
			return (Stage) dialogPane.getScene().getWindow();
		}catch(IOException e) {
			alert(AlertType.ERROR, "Erro", "Erro ao abrir Progresso", "");
			return null;
		}
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
	protected void visualizarDocumento(String text, Storage storage){
		try {
			File file = storage.downloadFile(text);
			if (file != null)
				Desktop.getDesktop().open(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
