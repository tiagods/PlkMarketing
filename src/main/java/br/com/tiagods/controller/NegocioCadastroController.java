package br.com.tiagods.controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javax.persistence.PersistenceException;

import br.com.tiagods.util.storage.PathStorageEnum;
import br.com.tiagods.util.storage.Storage;
import br.com.tiagods.util.storage.StorageProducer;
import javafx.scene.control.*;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.controlsfx.control.Rating;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.negocio.Contato;
import br.com.tiagods.model.negocio.NegocioCategoria;
import br.com.tiagods.model.negocio.NegocioDocumento;
import br.com.tiagods.model.negocio.NegocioNivel;
import br.com.tiagods.model.negocio.NegocioOrigem;
import br.com.tiagods.model.negocio.NegocioServico;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.model.negocio.NegocioTarefaProposta;
import br.com.tiagods.model.negocio.ServicoAgregado;
import br.com.tiagods.model.negocio.ServicoContratado;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.model.negocio.NegocioProposta;
import br.com.tiagods.model.negocio.NegocioProposta.TipoEtapa;
import br.com.tiagods.model.negocio.NegocioProposta.TipoStatus;
import br.com.tiagods.repository.helpers.NegocioCategoriasImpl;
import br.com.tiagods.repository.helpers.NegocioNiveisImpl;
import br.com.tiagods.repository.helpers.NegocioOrigensImpl;
import br.com.tiagods.repository.helpers.NegocioPropostaImpl;
import br.com.tiagods.repository.helpers.NegocioServicoAgregadoImpl;
import br.com.tiagods.repository.helpers.NegocioServicoContratadoImpl;
import br.com.tiagods.repository.helpers.NegocioServicosImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasPropostasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NegocioCadastroController extends UtilsController implements Initializable{
	@FXML
    private JFXRadioButton rbContato;

    @FXML
    private JFXRadioButton rbEnvioProposta;

    @FXML
    private JFXRadioButton rbFollowup;

    @FXML
    private JFXRadioButton rbFechamento;

    @FXML
    private JFXRadioButton rbSemMovimento;

    @FXML
    private Rating rating;

    @FXML
    private Label lbrating;

    @FXML
    private JFXTextField txNome;

    @FXML
    private JFXRadioButton rbAndamento;

    @FXML
    private JFXRadioButton rbGanho;

    @FXML
    private JFXRadioButton rbPerdido;

    @FXML
    private JFXRadioButton rbIndefinida;

    @FXML
    private JFXDatePicker dtInicio;

    @FXML
    private JFXDatePicker dtFinal;

    @FXML
    private JFXTextField txHonorario;

    @FXML
    private JFXTextArea txDescricao;

    @FXML
    private JFXComboBox<Usuario> cbAtendente;

    @FXML
    private JFXComboBox<NegocioCategoria> cbCategoria;

    @FXML
    private JFXComboBox<NegocioNivel> cbNivel;

    @FXML
    private JFXComboBox<NegocioOrigem> cbOrigem;

    @FXML
    private JFXComboBox<NegocioServico> cbServico;

    @FXML
    private JFXTextField txEmail;

    @FXML
    private JFXTextField txTelefone1;

    @FXML
    private JFXTextField txTelefone2;

    @FXML
    private JFXTextField txSite;

    @FXML
    private JFXTextArea txResumo;

    @FXML
    private JFXTextArea txApresentacao;

    @FXML
    private JFXTextArea txDetalhesOrigem;

    @FXML
    private JFXTextField txValorServico;

    @FXML
    private TableView<ServicoContratado> tbServico;
    
    @FXML
    private JFXComboBox<ServicoAgregado> cbServicoAgregado;

    @FXML
    private TableView<NegocioDocumento> tbDocumentos;

    @FXML
    private JFXTextArea txDocumentoDescricao;
    
    @FXML
    private JFXTextField txDocumentoNome;

    @FXML
    private JFXTextField txFormulario;
    
    @FXML
    private JFXTextField txIdPesquisa;

    @FXML
    private JFXTextField txNomePesquisa;
    
    @FXML
    private TableView<NegocioTarefaProposta> tbTarefas;
    
    @FXML
    private Label txServicoLocation;
    
    @FXML
    private Label txServicoId;

	@FXML
	private Label txDocumentoLocation;

	@FXML
	private Label txDocumentoId;

	@FXML
	private Accordion accordion;
	@FXML
	private TitledPane tpContato;

	private NegocioProposta proposta;
	private Stage stage;
	private Contato contato;

	private NegocioPropostaImpl propostas;
	private NegociosTarefasPropostasImpl tarefas;
	private NegocioNiveisImpl niveis;
	private NegocioCategoriasImpl categorias;
	private NegocioOrigensImpl origens;
	private NegocioServicosImpl servicos;
	private UsuariosImpl usuarios;
	private NegocioServicoContratadoImpl contratados;
	private NegocioServicoAgregadoImpl agregados;

	Storage storage = StorageProducer.newConfig();

	public NegocioCadastroController(Stage stage, NegocioProposta proposta, Contato contato) {
		this.stage = stage;
		this.proposta = proposta;
		this.contato = contato;
	}
	private void abrirTarefa(NegocioTarefaProposta t) {
		try {
			Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.TAREFA_CADASTRO);
            loader.setController(new TarefaCadastroController(stage,t,proposta));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
            	try {
        			loadFactory();
        			propostas = new NegocioPropostaImpl(getManager());
        			proposta = propostas.findById(proposta.getId());
        			tbTarefas.getItems().clear();
        			tbTarefas.getItems().addAll(proposta.getTarefas());
        			tbTarefas.refresh();
        		}catch(Exception e) {
        			e.printStackTrace();
        		}finally {
        			close();
        		}
            });
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.TAREFA_CADASTRO,e,true);
        }
	}
    @FXML
    void anexarDocumento(ActionEvent event) {
			Set<FileChooser.ExtensionFilter> filters = new HashSet<>();
			filters.add(new FileChooser.ExtensionFilter("pdf, doc, docx", "*.doc","*.pdf","*.docx"));
			File file = storage.carregarArquivo(new Stage(), filters);
			if (file != null) {
				String novoNome = storage.gerarNome(file, PathStorageEnum.NEGOCIO_DOCUMENTO.getDescricao());
				try{
					storage.uploadFile(file, novoNome);
					txFormulario.setText(novoNome);
				} catch(Exception e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Armazenamento de arquivo");
					alert.setHeaderText("Não foi possivel enviar o arquivo para o servidor");
					alert.setContentText("Um erro desconhecido não permitiu o envio do arquivo para uma pasta do servidor\n"+e);
					alert.showAndWait();
					e.printStackTrace();
				}
			}
    }
    
    @FXML
    void buscar(ActionEvent event) {
    	try {
    		Stage stage1 = new Stage();
    		TarefaContatoDialogController controller = new TarefaContatoDialogController(stage1);
    		FXMLLoader loader = loaderFxml(FXMLEnum.TAREFA_DIALOG_CONTATO);
            loader.setController(controller);
            initPanel(loader, stage1, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            final TarefaContatoDialogController controlador = controller;
            stage1.setOnHiding(e -> {
            		Contato contato = ((TarefaContatoDialogController)controlador).getContato();
            		if(contato!=null) {
            			preencherContato(contato,true);
            		}
            });
        }catch(IOException ex) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.TAREFA_DIALOG_CONTATO,ex,true);
        }
    }
    @FXML
    void cadastrarServico(ActionEvent event) {
    	
    }
    void combos() {
		rbContato.setSelected(true);
		rbAndamento.setSelected(true);
    	
		ToggleGroup group1 = new ToggleGroup();
    	group1.getToggles().addAll(rbContato,rbEnvioProposta,rbFollowup,rbFechamento,rbIndefinida);
    	ToggleGroup group2 = new ToggleGroup();
    	group2.getToggles().addAll(rbAndamento,rbGanho,rbPerdido,rbSemMovimento);
    	    	
    	categorias = new NegocioCategoriasImpl(getManager());
		niveis = new NegocioNiveisImpl(getManager());
		origens = new NegocioOrigensImpl(getManager());
		servicos = new NegocioServicosImpl(getManager());
		usuarios = new UsuariosImpl(getManager());
		agregados = new NegocioServicoAgregadoImpl(getManager());
		
		cbCategoria.getItems().add(null);
		cbNivel.getItems().add(null);
		cbOrigem.getItems().add(null);
		cbServico.getItems().add(null);
		
		cbCategoria.getItems().addAll(categorias.getAll());
		cbNivel.getItems().addAll(niveis.getAll());
		cbOrigem.getItems().addAll(origens.getAll());
		cbServico.getItems().addAll(servicos.getAll());
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
		cbServicoAgregado.getItems().addAll(agregados.getAll());
		
		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst(); 
		cbAtendente.getSelectionModel().select(UsuarioLogado.getInstance().getUsuario());
		
		txHonorario.setText("0,00");
    }
    boolean excluirTarefa(NegocioTarefaProposta n) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			try{
				loadFactory();
				tarefas = new NegociosTarefasPropostasImpl(getManager());
				NegocioTarefaProposta t = tarefas.findById(n.getId());
				tarefas.remove(t);
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
    boolean excluirServico(ServicoContratado n) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			try{
				loadFactory();
				contratados = new NegocioServicoContratadoImpl(getManager());
				ServicoContratado t = contratados.findById(n.getId());
				contratados.remove(t);
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			tabelaTarefa();
			tabelaServicos();
			tabelaDocumentos();
			loadFactory();
			combos();
			if(proposta!=null) preencherFormulario(this.proposta);
			else if(proposta==null && contato!=null){
				preencherContato(contato,true);
			}
		}catch(PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro ao carregar formulario","Erro ao realizar consulta", e, true);
		}finally {
			close();
		}	
	}
	void limparDocumentos(){
		txDocumentoId.setText("");
		txDocumentoLocation.setText("");
		txDocumentoNome.setText("");
		txDocumentoDescricao.setText("");
		txFormulario.setText("");
	}
    @FXML
    void mailSend(ActionEvent event) {
		if(!txEmail.getText().trim().equals("")){
			try {
				URI url = new URI("mailto", txEmail.getText(), null);
				Desktop.getDesktop().mail(url);
			} catch (IOException e) {} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
    }
    @FXML
    void novaTarefa(ActionEvent event) {
    	if(proposta==null) {
    		alert(AlertType.ERROR, "Erro","Salve um negocio para criar uma tarefa","",null,false);
    	}
    	else
    		abrirTarefa(null);
    }
    @FXML
    void openBrowser(ActionEvent event) {
		if(!txSite.getText().trim().equals("")){
			try {
				Desktop.getDesktop().browse(new URI(txSite.getText()));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
    }
    private void preencherContato(Contato c,boolean caixaSelecao) {
    	txIdPesquisa.setText(String.valueOf(c.getId()));
		txNomePesquisa.setText(c.getNome());
		txEmail.setText(c.getEmail());
		txSite.setText(c.getSite());
		txTelefone1.setText(c.getTelefone());
		txTelefone2.setText(c.getCelular());
		txResumo.setText(c.getResumo());
		txApresentacao.setText(c.getApresentacao());
		txDetalhesOrigem.setText(c.getDetalhesOrigem());
		if(caixaSelecao) {
			txNome.setText(c.getNome());
			cbAtendente.setValue(c.getAtendente());
			cbCategoria.setValue(c.getCategoria());
			cbNivel.setValue(c.getNivel());
			cbOrigem.setValue(c.getOrigem());
			cbServico.setValue(c.getServico());
		}
		accordion.setExpandedPane(tpContato);
	}
    private void preencherFormulario(NegocioProposta proposta) {
		txNome.setText(proposta.getNome());
		if(proposta.getTipoEtapa().equals(TipoEtapa.CONTATO)) rbContato.setSelected(true);
		else if(proposta.getTipoEtapa().equals(TipoEtapa.PROPOSTA)) rbEnvioProposta.setSelected(true);
		else if(proposta.getTipoEtapa().equals(TipoEtapa.FOLLOWUP)) rbFollowup.setSelected(true);
		else if(proposta.getTipoEtapa().equals(TipoEtapa.FECHAMENTO)) rbFechamento.setSelected(true);
		if(proposta.getTipoStatus().equals(TipoStatus.ANDAMENTO)) {
			rbAndamento.setSelected(true);
		}
		else if(proposta.getTipoStatus().equals(TipoStatus.GANHO)){
			rbGanho.setSelected(true);
		}
		else if(proposta.getTipoStatus().equals(TipoStatus.PERDIDO)){
			rbPerdido.setSelected(true);
		}
		else if(proposta.getTipoStatus().equals(TipoStatus.SEMMOVIMENTO)){
			rbSemMovimento.setSelected(true);
		}
		LocalDate data1 = proposta.getDataInicio()!=null ? 
				proposta.getDataInicio().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():null;		
		LocalDate data2 = proposta.getDataFim()!=null ? 
				proposta.getDataFim().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate():null;
		dtInicio.setValue(data1);
		dtFinal.setValue(data2);
		
		txHonorario.setText(proposta.getHonorario().toString().replace(".", ","));
		txDescricao.setText(proposta.getDescricao());
		
		cbAtendente.setValue(proposta.getAtendente());
		cbCategoria.setValue(proposta.getCategoria());
		cbNivel.setValue(proposta.getNivel());
		cbOrigem.setValue(proposta.getOrigem());
		cbServico.setValue(proposta.getServico());
		
		if(proposta.getNegocioContato()!=null) {
			Contato c = proposta.getNegocioContato();
			preencherContato(c,false);

		}		
		tbServico.getItems().clear();
		tbServico.getItems().addAll(proposta.getServicosContratados());
		tbDocumentos.getItems().clear();
		tbDocumentos.getItems().addAll(proposta.getDocumentos());
		tbTarefas.getItems().clear();
		tbTarefas.getItems().addAll(proposta.getTarefas());
	}
    @FXML
    void sair(ActionEvent event) {
    	stage.close();
    }
    
    @FXML
    void salvar(ActionEvent event) {
    	if(txIdPesquisa.getText().equals("")){
    		alert(AlertType.ERROR,"Erro","Selecione um contato","Escolha um contato e tenten novamente",null,false);
    		return;
    	}
    	if(proposta==null) {
    		proposta = new NegocioProposta();
    	}
    	proposta.setNome(txNome.getText());
    	TipoEtapa etapa = TipoEtapa.CONTATO;
    	if(rbContato.isSelected()) etapa = TipoEtapa.CONTATO;
    	else if(rbEnvioProposta.isSelected()) etapa = TipoEtapa.PROPOSTA;
    	else if(rbFollowup.isSelected()) etapa = TipoEtapa.FOLLOWUP;
    	else if(rbFechamento.isSelected()) etapa = TipoEtapa.FECHAMENTO;
    	else if(rbIndefinida.isSelected()) etapa = TipoEtapa.INDEFINIDA;
    	proposta.setTipoEtapa(etapa);
    	
    	TipoStatus status = TipoStatus.ANDAMENTO;
    	if(rbAndamento.isSelected()) status = TipoStatus.ANDAMENTO;
    	else if(rbGanho.isSelected()) status = TipoStatus.GANHO;
    	else if(rbPerdido.isSelected()) status = TipoStatus.PERDIDO;
    	else if(rbSemMovimento.isSelected()) status = TipoStatus.SEMMOVIMENTO;
    	
    	proposta.setTipoStatus(status);
    	
    	Calendar inicio = dtInicio.getValue()==null?null:GregorianCalendar.from(dtInicio.getValue().atStartOfDay(ZoneId.systemDefault()));
    	Calendar fim = dtFinal.getValue()==null?null:GregorianCalendar.from(dtFinal.getValue().atStartOfDay(ZoneId.systemDefault()));
    	
    	proposta.setDataInicio(inicio);
    	proposta.setDataFim(fim);
    	try {
    		Double valor = Double.parseDouble(txHonorario.getText().replace(",", "."));
    		proposta.setHonorario(new BigDecimal(valor));
    	}catch (Exception e) {
			alert(AlertType.ERROR,"Erro","Honorario informado esta incorreto","",null,false);
    		return;
		}
    	proposta.setDescricao(txDescricao.getText());
    	proposta.setAtendente(cbAtendente.getValue());
    	proposta.setCategoria(cbCategoria.getValue());
    	proposta.setNivel(cbNivel.getValue());
    	proposta.setOrigem(cbOrigem.getValue());
    	proposta.setServico(cbServico.getValue());
    	
    	Set<NegocioDocumento> documentos = new HashSet<>();
    	documentos.addAll(tbDocumentos.getItems());
		documentos.stream().forEach(c->{
			if (!c.getUrl().equals("") && !c.getUrl().startsWith(PathStorageEnum.NEGOCIO_DOCUMENTO.getDescricao()+"/")) {
				try {
					String to = PathStorageEnum.NEGOCIO_DOCUMENTO.getDescricao()+"/"+c.getUrl();
					storage.transferTo(c.getUrl(), to);
					c.setUrl(to);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		proposta.setDocumentos(documentos);
    	
    	Set<ServicoContratado> servicos = new HashSet<>();
    	servicos.addAll(tbServico.getItems());
		proposta.setServicosContratados(servicos);
    	try {
    		Contato contato = new Contato(Long.parseLong(txIdPesquisa.getText()));
    		proposta.setNegocioContato(contato);
    		loadFactory();
    		propostas = new NegocioPropostaImpl(getManager());
    		this.proposta = propostas.save(proposta);
    		preencherFormulario(proposta);
    		alert(AlertType.INFORMATION,"Sucesso","", "Salvo com sucesso!",null,false);
    	}catch(Exception e) {
    		alert(AlertType.ERROR,"Erro","Falha ao tentar salvar o registro", "Não foi possivel salvar o registro",e,true);
    	}finally {
    		close();
		}
    }

    @FXML
    void salvarDocumento(ActionEvent event) {
		if(txFormulario.getText().equals("") || txDocumentoNome.getText().trim().equals("")) {
			alert(AlertType.ERROR,"Erro","","Nome e Documento são obrigatórios!",null,false);
			return;
		}
		else {
			try {
				NegocioDocumento documento = new NegocioDocumento();
				if(txDocumentoId.getText().trim().length()>0) {
					documento.setId(Long.parseLong(txDocumentoId.getText()));
				}
				int location = -1;
				if(txDocumentoLocation.getText().trim().length()>0) {
					location = Integer.parseInt(txDocumentoLocation.getText());
				}
				documento.setData(Calendar.getInstance());
				documento.setNegocio(this.proposta);
				documento.setNome(txDocumentoNome.getText());
				documento.setDescricao(txDocumentoDescricao.getText());
				documento.setUrl(txFormulario.getText());
				documento.setUsuario(UsuarioLogado.getInstance().getUsuario());
				if(location!=-1) {
					tbDocumentos.getItems().set(location, documento);
				}
				else
					tbDocumentos.getItems().add(documento);
				limparDocumentos();
				tbDocumentos.refresh();
			}catch(Exception e) {
				alert(AlertType.ERROR,"Erro","","Falha ao incluir registro",e,true);
			}
		}

    }

    @FXML
    void salvarServico(ActionEvent event) {
    	if(proposta==null) {
			alert(AlertType.ERROR,"Erro","","Salve antes de continuar",null,false);
    		return;
		}
    	else if(txValorServico.getText().trim().length()==0) {
    		alert(AlertType.ERROR,"Erro","","Valor obrigatório",null,false);
    		return;
    	}
    	else if(tbServico.getItems()
    			.stream()
    			.filter(c->c.getServicosAgregados().getId()==cbServicoAgregado.getValue().getId())
    			.findAny().isPresent()) {
    		alert(AlertType.ERROR, "Erro", "Impossivel incluir o servico","O servico informado ja foi incluido", null, false);
    	}
		else {
			try {
				ServicoContratado contratado= new ServicoContratado();
				if(txServicoId.getText().trim().length()>0) {
					contratado.setId(Long.parseLong(txServicoId.getText()));
				}
				int location = -1;
				if(txServicoLocation.getText().trim().length()>0) {
					location = Integer.parseInt(txServicoLocation.getText());
				}
				contratado.setNegocios(proposta);
				contratado.setServicosAgregados(cbServicoAgregado.getValue());
				
				BigDecimal inv = new BigDecimal(Double.parseDouble(txValorServico.getText().replace(",", ".")));
				contratado.setValor(inv);
				if(location!=-1) {
					tbServico.getItems().set(location, contratado);
				}
				else
					tbServico.getItems().add(contratado);
				tbServico.refresh();
			}catch(Exception e) {
				alert(AlertType.ERROR,"Erro","","Falha ao incluir registro",e,true);	
			}
		}

    }
    private boolean salvarStatus(NegocioTarefaProposta tarefa,int status){
		try{
			loadFactory();
			tarefas = new NegociosTarefasPropostasImpl(getManager());
			NegocioTarefaProposta t = tarefas.findById(tarefa.getId());
			t.setFinalizado(status);
			tarefas.save(t);
			return true;
		}catch (Exception e){
			alert(AlertType.ERROR,"Erro",null,"Erro ao salvar",e,true);
			return false;
		}finally {
			close();
		}
	}
    void tabelaDocumentos() {    	
    	TableColumn<NegocioDocumento, String> colunaNome = new  TableColumn<>("Nome");
    	colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	
    	TableColumn<NegocioDocumento, String> colunaDescricao = new  TableColumn<>("Descricao");
    	colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
    	
    	TableColumn<NegocioDocumento, Calendar> colunaData = new  TableColumn<>("Data");
    	colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
    	colunaData.setCellFactory(param -> new TableCell<NegocioDocumento,Calendar>(){
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

		TableColumn<NegocioDocumento, String> colunaAnexo = new  TableColumn<>("");
		colunaAnexo.setCellValueFactory(new PropertyValueFactory<>("url"));
		colunaAnexo.setCellFactory(param -> new TableCell<NegocioDocumento,String>(){
			JFXButton button = new JFXButton("");
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null || item.equals("")){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btDefault");
					try {
						IconsEnum ic = IconsEnum.BUTTON_CLIP;
						buttonTable(button, ic);
					}catch (IOException e) {
					}
					button.setOnAction(event -> {
						visualizarDocumento(item, storage);
					});
					setGraphic(button);
				}
			}
		});
		colunaAnexo.setMaxWidth(50);
    	TableColumn<NegocioDocumento, Usuario> colunaUsuario = new  TableColumn<>("Usuario");
		colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		TableColumn<NegocioDocumento, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<NegocioDocumento,Number>(){
			JFXButton button = new JFXButton();//Editar
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(empty){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btDefault");
					try {buttonTable(button, IconsEnum.BUTTON_EDIT);}catch (IOException e) {}
					button.setOnAction(event -> {
						NegocioDocumento doc = tbDocumentos.getItems().get(getIndex());
						txDocumentoDescricao.setText(doc.getDescricao());
						txDocumentoId.setText(doc.getId()==null?"":String.valueOf(doc.getId()));
						txDocumentoNome.setText(doc.getNome());
						txDocumentoLocation.setText(String.valueOf(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		TableColumn<NegocioDocumento, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<NegocioDocumento,Number>(){
			JFXButton button = new JFXButton();//excluir
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(empty){
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
						tbDocumentos.getItems().remove(getIndex());
						limparDocumentos();
					});
					setGraphic(button);
				}
			}
		});
    	tbDocumentos.getColumns().addAll(colunaNome,colunaDescricao,colunaData,colunaUsuario,colunaAnexo,colunaEditar,colunaExcluir);
    	
    }
    void tabelaServicos() {
    	TableColumn<ServicoContratado, ServicoAgregado> colunaNome = new  TableColumn<>("Nome");
    	colunaNome.setCellValueFactory(new PropertyValueFactory<>("servicosAgregados"));
    	colunaNome.setCellFactory(param -> new TableCell<ServicoContratado,ServicoAgregado>(){
			@Override
			protected void updateItem(ServicoAgregado item, boolean empty) {
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
		TableColumn<ServicoContratado, BigDecimal> colunaValor = new  TableColumn<>("Valor");
		colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		colunaValor.setCellFactory(param -> new TableCell<ServicoContratado,BigDecimal>(){
			@Override
			protected void updateItem(BigDecimal item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(nf.format(item.doubleValue()));
				}
			}
		});
		
		TableColumn<ServicoContratado, ServicoAgregado> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("servicosAgregados"));
		colunaEditar.setCellFactory(param -> new TableCell<ServicoContratado,ServicoAgregado>(){
			JFXButton button = new JFXButton();//Editar
			@Override
			protected void updateItem(ServicoAgregado item, boolean empty) {
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
						ServicoContratado sc = tbServico.getItems().get(getIndex());
						cbServicoAgregado.setValue(sc.getServicosAgregados());
						txValorServico.setText(sc.getValor().toString().replace(".", ","));
						txServicoId.setText(sc.getId()!=null?String.valueOf(sc.getId()):"");
						txServicoLocation.setText(String.valueOf(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		TableColumn<ServicoContratado, ServicoAgregado> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("servicosAgregados"));
		colunaExcluir.setCellFactory(param -> new TableCell<ServicoContratado,ServicoAgregado>(){
			JFXButton button = new JFXButton();//excluir
			@Override
			protected void updateItem(ServicoAgregado item, boolean empty) {
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
						ServicoContratado sc = tbServico.getItems().get(getIndex());
						boolean removed = sc.getId()==null?true:excluirServico(sc);
						if(removed) tbServico.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		tbServico.getColumns().addAll(colunaNome,colunaValor,colunaEditar,colunaExcluir);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	void tabelaTarefa() {
		TableColumn<NegocioTarefaProposta, Calendar> colunaValidade = new  TableColumn<>("Prazo");
		colunaValidade.setCellValueFactory(new PropertyValueFactory<>("dataEvento"));
		colunaValidade.setCellFactory(param -> new TableCell<NegocioTarefaProposta,Calendar>(){
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
		
		TableColumn<NegocioTarefaProposta, TipoTarefa> colunaTipo = new  TableColumn<>("Tipo");
		colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoTarefa"));
		colunaTipo.setCellFactory(param -> new TableCell<NegocioTarefaProposta,TipoTarefa>(){
			@Override
			protected void updateItem(TipoTarefa item, boolean empty) {
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
		
		TableColumn<NegocioTarefaProposta, Number> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellFactory(param -> new TableCell<NegocioTarefaProposta,Number>(){
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					NegocioTarefa t = tbTarefas.getItems().get(getIndex());
					setText(t.toString());
				}
			}
		});
		TableColumn<NegocioTarefaProposta, String> colunaLocalizacao = new  TableColumn<>("Localizacao");
		colunaLocalizacao.setCellValueFactory(new PropertyValueFactory<>("classe"));
		colunaLocalizacao.setCellFactory(param -> new TableCell<NegocioTarefaProposta,String>(){
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
		TableColumn<NegocioTarefaProposta, String> colunaDescricao = new  TableColumn<>("Descricao");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		colunaDescricao.setCellFactory((TableColumn<NegocioTarefaProposta, String> param) -> {
			final TableCell<NegocioTarefaProposta, String> cell = new TableCell<NegocioTarefaProposta, String>() {
				final JFXTextArea textArea = new JFXTextArea();
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					textArea.setEditable(false);
					textArea.setWrapText(true);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						textArea.setText(item);
						setGraphic(textArea);
						setText(null);
					}
				}
			};
			return cell;
		});
		colunaDescricao.setPrefWidth(200);
		
		TableColumn<NegocioTarefaProposta, Number> colunaStatus = new  TableColumn<>("Status");
		colunaStatus.setCellValueFactory(new PropertyValueFactory<>("finalizado"));
		colunaStatus.setCellFactory(param -> new TableCell<NegocioTarefaProposta,Number>(){
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					JFXButton rb = new JFXButton();
					rb.setText(item.intValue()==0?"Pendente":"Concluido");
					rb.getStyleClass().add(item.intValue()==0?"btRed":"btGreen");

					rb.onActionProperty().set(event -> {

						Dialog dialog = new Dialog();
						dialog.setTitle("Alteração de Status");
						dialog.setHeaderText("Selecione um status");

						VBox stackPane = new VBox();
						stackPane.setSpacing(15);

						Map<JFXRadioButton,Integer> map = new HashMap<>();
						ToggleGroup group = new ToggleGroup();
						
						for(int i=0;i<2;i++) {
							JFXRadioButton jfxRadioButton = new JFXRadioButton(i==0?"Pendente":"Concluido");
							jfxRadioButton.setSelectedColor(Color.GREEN);
							jfxRadioButton.setUnSelectedColor(Color.RED);
							if(item.intValue()==i) jfxRadioButton.setSelected(true);
							group.getToggles().add(jfxRadioButton);
							stackPane.getChildren().add(jfxRadioButton);
							map.put(jfxRadioButton,i);
						}
						ButtonType ok = new ButtonType("Alterar");
						ButtonType cancelar = new ButtonType("Cancelar");
						dialog.getDialogPane().getButtonTypes().addAll(ok,cancelar);
						dialog.getDialogPane().setContent(stackPane);
						dialog.initModality(Modality.APPLICATION_MODAL);
						dialog.initStyle(StageStyle.UNDECORATED);
						@SuppressWarnings("unchecked")
						Optional<ButtonType> result = dialog.showAndWait();
						if(result.get() == ok){
							NegocioTarefaProposta tarefa = tbTarefas.getItems().get(getIndex());
							for(Node f : stackPane.getChildren()){
								if(f instanceof JFXRadioButton && ((JFXRadioButton) f).isSelected()) {
									Integer p = map.get(f);
									if (p!=item.intValue() && salvarStatus(tarefa, p)) {
										tbTarefas.getItems().get(getIndex()).setFinalizado(p);
										tbTarefas.refresh();
									}
									break;
								}
							};
						}
                    });
					setGraphic(rb);

				}
			}
		});
		colunaStatus.setPrefWidth(100);
		TableColumn<NegocioTarefaProposta, Usuario> columnAtendente = new  TableColumn<>("Atendente");
		columnAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		columnAtendente.setCellFactory(param -> new TableCell<NegocioTarefaProposta,Usuario>(){
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

		TableColumn<NegocioTarefaProposta, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<NegocioTarefaProposta,Number>(){
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
					button.setOnAction(event ->
						abrirTarefa(tbTarefas.getItems().get(getIndex()))
					);
					setGraphic(button);
				}
			}
		});
		TableColumn<NegocioTarefaProposta, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<NegocioTarefaProposta,Number>(){
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
						boolean removed = excluirTarefa(tbTarefas.getItems().get(getIndex()));
						if(removed) tbTarefas.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		tbTarefas.getColumns().addAll(colunaValidade,colunaTipo,colunaNome,colunaDescricao,
				columnAtendente,colunaStatus,colunaEditar,colunaExcluir);
		tbTarefas.setTableMenuButtonVisible(true);
		tbTarefas.setFixedCellSize(50);
	}

}
