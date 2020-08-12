package br.com.tiagods.controller;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.repository.*;
import br.com.tiagods.util.CepUtil;
import br.com.tiagods.util.JavaFxUtil;
import org.controlsfx.control.Rating;
import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.negocio.Contato;
import br.com.tiagods.model.negocio.Contato.ContatoTipo;
import br.com.tiagods.model.negocio.Contato.PessoaTipo;
import br.com.tiagods.model.negocio.NegocioCategoria;
import br.com.tiagods.model.negocio.NegocioLista;
import br.com.tiagods.model.negocio.NegocioMalaDireta;
import br.com.tiagods.model.negocio.NegocioNivel;
import br.com.tiagods.model.negocio.NegocioOrigem;
import br.com.tiagods.model.negocio.NegocioServico;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.model.negocio.NegocioTarefaContato;
import br.com.tiagods.model.PessoaFisica;
import br.com.tiagods.model.PessoaJuridica;
import br.com.tiagods.model.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import static br.com.tiagods.util.JavaFxUtil.alert;
import static br.com.tiagods.util.JavaFxUtil.buttonTable;

@Controller
public class ContatoCadastroController implements Initializable {

	@FXML
	private Label lbrating;
	
	@FXML
	private Rating rating;
	
	@FXML
    private JFXRadioButton rbProspeccao;

    @FXML
    private JFXRadioButton rbSondagem;

    @FXML
    private JFXTextField txNome;

    @FXML
    private JFXTextField txEmail;

    @FXML
    private JFXTextField txTelefone1;

    @FXML
    private JFXTextField txTelefone2;

    @FXML
    private JFXTextField txSite;

    @FXML
    private JFXCheckBox ckConvite;

    @FXML
    private JFXCheckBox ckMaterial;

    @FXML
    private JFXCheckBox ckNewsletter;

    @FXML
    private JFXRadioButton rbEmpresa;

    @FXML
    private JFXRadioButton rbPessoa;

    @FXML
    private Pane pnPessoaFisica;

    @FXML
    private JFXTextField txRG;

    @FXML
    private MaskedTextField txCPF;

    @FXML
    private MaskedTextField txDataNascimento;

    @FXML
    private Pane pnPessoaJuridica;

    @FXML
    private JFXTextField txRazao;

    @FXML
    private MaskedTextField txCNPJ;

    @FXML
    private JFXTextField txIM;

    @FXML
    private JFXTextField txIE;

    @FXML
    private JFXTextField txApelido;

    @FXML
    private JFXTextField txResponsavel;

    @FXML
    private MaskedTextField txCEP;

    @FXML
    private JFXTextField txLogradouro;

    @FXML
    private JFXTextField txNumero;

    @FXML
    private JFXTextField txBairro;

    @FXML
    private JFXTextField txComplemento;

    @FXML
    private JFXComboBox<Cidade.Estado> cbEstado;

    @FXML
    private JFXComboBox<Cidade> cbCidade;

    @FXML
    private JFXTextArea txResumo;

    @FXML
    private JFXTextArea txApresentacao;

    @FXML
    private JFXTextArea txDetalhesOrigem;

    @FXML
    private JFXComboBox<Usuario> cbAtendente;

    @FXML
    private JFXComboBox<NegocioLista> cbLista;

    @FXML
    private JFXComboBox<NegocioOrigem> cbOrigem;

    @FXML
    private JFXComboBox<NegocioCategoria> cbCategoria;

    @FXML
    private JFXComboBox<NegocioNivel> cbNivel;

    @FXML
    private JFXComboBox<NegocioServico> cbServico;

    @FXML
    private JFXComboBox<NegocioMalaDireta> cbMalaDireta;
    
    @FXML
    private TableView<NegocioTarefaContato> tbTarefas;
    
    @FXML
    private TableView<NegocioLista> tbListas;

	@Autowired Contatos contatos;
	@Autowired NegociosNiveis niveis;
	@Autowired NegociosCategorias categorias;
	@Autowired NegociosOrigens origens;
	@Autowired NegociosServicos servicos;
	@Autowired NegociosListas listas;
	@Autowired Usuarios usuarios;
	@Autowired NegociosMalaDiretas malasDiretas;
	@Autowired NegociosTarefasContatos tarefas;
	@Autowired CepUtil cepUtil;
	@Lazy @Autowired StageManager stageManager;
	@Autowired TarefaCadastroController tarefaCadastroController;

	private Stage stage;
	private Contato contato;

	private void abrirTarefa(NegocioTarefaContato t) {
		Stage stage1 = stageManager.switchScene(FxmlView.TAREFA_CADASTRO, true);
		tarefaCadastroController.setPropriedades(stage1, t, contato);
		stage1.setOnHiding(event -> {
			contatos.findById(contato.getId()).ifPresent(contato1 -> {
				tbTarefas.getItems().clear();
				tbTarefas.getItems().addAll(contato1.getTarefas());
				tbTarefas.refresh();
			});
		});
	}
    @FXML
    void buscarCep(ActionEvent event) {
    	cepUtil.bucarCep(txCEP, txLogradouro, txNumero, txComplemento, txBairro, cbCidade, cbEstado);
    }
    
    void combos() {
    	ToggleGroup group1 = new ToggleGroup();
    	group1.getToggles().addAll(rbEmpresa,rbPessoa);
    	
    	ChangeListener<Boolean> change = (observable, oldValue, newValue) -> {
			if(rbEmpresa.isSelected()) {
				pnPessoaJuridica.setVisible(true);
				pnPessoaFisica.setVisible(false);
			}
			else if(rbPessoa.isSelected()) {
				pnPessoaFisica.setVisible(true);
				pnPessoaJuridica.setVisible(false);
			}
		};
		rbEmpresa.selectedProperty().addListener(change);
		rbPessoa.selectedProperty().addListener(change);

		rbPessoa.setSelected(true);
		rbProspeccao.setSelected(true);
		
		ToggleGroup group2 = new ToggleGroup();
    	group2.getToggles().addAll(rbProspeccao,rbSondagem);
    			
		cbCategoria.getItems().add(null);
		cbNivel.getItems().add(null);
		cbOrigem.getItems().add(null);
		cbServico.getItems().add(null);
		
		cbCategoria.getItems().addAll(categorias.findAll());
		cbNivel.getItems().addAll(niveis.findAll());
		cbOrigem.getItems().addAll(origens.findAll());
		cbServico.getItems().addAll(servicos.findAll());
		cbAtendente.getItems().addAll(usuarios.findAllByAtivoOrderByNome(1));
		cbLista.getItems().addAll(listas.findAll());
		cbMalaDireta.getItems().addAll(malasDiretas.findAll());
		
		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst(); 
		cbAtendente.getSelectionModel().select(UsuarioLogado.getInstance().getUsuario());;
		cbLista.getSelectionModel().selectFirst();
		cbMalaDireta.getSelectionModel().selectFirst();
		
		rating.setPartialRating(true);
		rating.setUpdateOnHover(true);
		rating.setRating(0);
		rating.hoverProperty().addListener((observable, oldValue, newValue) -> lbrating.setText(String.format("%.2f",rating.getRating())));
		cepUtil.comboRegiao(cbCidade,cbEstado);
    }
    boolean excluirTarefa(NegocioTarefaContato n) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			Optional<NegocioTarefaContato> t = tarefas.findById(n.getId());
			if(t.isPresent()) {
				tarefas.delete(t.get());
				JavaFxUtil.alert(AlertType.INFORMATION, "Sucesso", null, "Removido com sucesso!", null, false);
				return true;
			}
			return false;
		}
		else return false;
	}
    boolean excluirLista(NegocioLista n) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			return true;
		}
		else return false;
	}
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		tabelaListas();
		tabelaTarefa();
		combos();
	}

    @FXML
    private void incluirLista(ActionEvent event) {
    	if(cbLista.getValue()!=null && !tbListas.getItems().contains(cbLista.getValue())) {
        	tbListas.getItems().add(cbLista.getValue());
        	tbListas.refresh();
    	}
    	else
    		alert(AlertType.ERROR, "Erro", "Impossivel incluir lista","O contato ja tem a lista informada", null, false);
    	
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
    	if(contato==null) {
    		this.contato = new Contato();
    		if(salvar()) abrirTarefa(null);
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
	private void preencherFormulario(Contato contato) {
		if(contato.getContatoTipo().equals(ContatoTipo.PROSPECCAO)) {
			rbProspeccao.setSelected(true);
		}
		else if(contato.getContatoTipo().equals(ContatoTipo.SONDAGEM)){
			rbSondagem.setSelected(true);
		}
		
		if(contato.getPessoaTipo().equals(PessoaTipo.PESSOA)) {
			rbPessoa.setSelected(true);	
			pnPessoaFisica.setVisible(true);
			pnPessoaJuridica.setVisible(false);
			
			if(contato.getFisico()!=null) {
				PessoaFisica f = contato.getFisico();
				txRG.setText(f.getRg());
				txCPF.setPlainText(f.getCpf());
				//txDataNascimento.setText(f.getAniversario());
				SimpleDateFormat sd = new SimpleDateFormat("ddMM");
				txDataNascimento.setPlainText(f.getNiver()==null?"":sd.format(f.getNiver().getTime()));
			}
		}
		if(contato.getPessoaTipo().equals(PessoaTipo.EMPRESA)) {
			rbEmpresa.setSelected(true);
			pnPessoaFisica.setVisible(false);
			pnPessoaJuridica.setVisible(true);
			
			txRazao.setText(contato.getJuridico().getRazao());
			txApelido.setText(contato.getJuridico().getApelido());
			txCNPJ.setPlainText(contato.getJuridico().getCnpj());
			txResponsavel.setText(contato.getJuridico().getResponsavel());
			txIM.setText(contato.getJuridico().getIm());
			txIE.setText(contato.getJuridico().getIe());
		}
		
		txNome.setText(contato.getNome());
		txEmail.setText(contato.getEmail());
		txSite.setText(contato.getSite());
		txTelefone1.setText(contato.getTelefone());
		txTelefone2.setText(contato.getCelular());
		
		cbAtendente.setValue(contato.getAtendente());
		cbCategoria.setValue(contato.getCategoria());
		cbNivel.setValue(contato.getNivel());
		cbOrigem.setValue(contato.getOrigem());
		cbServico.setValue(contato.getServico());
		
		ckConvite.setSelected(contato.isConvite());
		ckMaterial.setSelected(contato.isMaterial());
		ckNewsletter.setSelected(contato.isNewsletter());
		
		cbMalaDireta.setValue(contato.getMalaDireta());
		
		txResumo.setText(contato.getResumo());
		txApresentacao.setText(contato.getApresentacao());
		txDetalhesOrigem.setText(contato.getDetalhesOrigem());
		
		txTelefone1.setText(contato.getTelefone());
		txTelefone2.setText(contato.getCelular());
		txCEP.setPlainText(contato.getCep());
		txLogradouro.setText(contato.getEndereco());
		txNumero.setText(contato.getNumero());
		txBairro.setText(contato.getBairro());
		txComplemento.setText(contato.getComplemento());
		cbEstado.getSelectionModel().select(contato.getEstado());
		cbCidade.setValue(contato.getCidade());
		
		tbListas.getItems().clear();
		tbListas.getItems().addAll(contato.getListas());
		
		tbTarefas.getItems().clear();
		tbTarefas.getItems().addAll(contato.getTarefas());
		this.contato = contato;
	}
    @FXML
    void sair(ActionEvent event) {
    	stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {
    	salvar();
    }
    private boolean salvar() {
    	if(txNome.getText().trim().equals("")) {
    		alert(Alert.AlertType.ERROR,"Erro",null,"Nome do contato é obrigatório",null,false);
	        return false;
    	}
    	
    	if(contato==null) 
    		contato = new Contato();
    	if(rbEmpresa.isSelected()) {
    		contato.setFisico(null);
    		PessoaJuridica juridica = new PessoaJuridica();
    		juridica.setRazao(txRazao.getText());
    		juridica.setApelido(txApelido.getText());
    		juridica.setCnpj(txCNPJ.getPlainText());
    		juridica.setResponsavel(txResponsavel.getText());
    		juridica.setIm(txIM.getText());
    		juridica.setIe(txIE.getText());
    		contato.setJuridico(juridica);
    		contato.setPessoaTipo(PessoaTipo.EMPRESA);
    	}
    	else if(rbPessoa.isSelected()) {
    		contato.setJuridico(null);
    		PessoaFisica fisica = new PessoaFisica();
    		//fisica.setAniversario(txDataNascimento.getText());
    		try {
    			SimpleDateFormat sd = new SimpleDateFormat("ddMM");
    			if(txDataNascimento.getPlainText().trim().length()==0) {
    				fisica.setNiver(null);
    			}
    			else {
    				Date date = sd.parse(txDataNascimento.getPlainText());
    				Calendar calendar = Calendar.getInstance();
    				calendar.setTime(date);
    				fisica.setNiver(calendar);
    			}
    		}catch(ParseException e) {
	    		alert(Alert.AlertType.ERROR,"Erro",null,"Verifique se a data de aniversário esta correta!",null,false);
		        return false;
    		}
    		fisica.setCpf(txCPF.getPlainText());
    		fisica.setRg(txRG.getText());
    		contato.setFisico(fisica);
    		contato.setPessoaTipo(PessoaTipo.PESSOA);
    	}
    	if(rbProspeccao.isSelected()) {
    		contato.setContatoTipo(ContatoTipo.PROSPECCAO);
    	}
    	else if(rbSondagem.isSelected()) {
    		contato.setContatoTipo(ContatoTipo.SONDAGEM);
    	}
    	contato.setNome(txNome.getText());
    	contato.setEmail(txEmail.getText());
    	contato.setSite(txSite.getText());
    	
    	contato.setAtendente(cbAtendente.getValue());
    	contato.setCategoria(cbCategoria.getValue());
    	contato.setNivel(cbNivel.getValue());
    	contato.setOrigem(cbOrigem.getValue());
    	contato.setServico(cbServico.getValue());
    	
    	Set<NegocioLista> list = new HashSet<>();
    	list.addAll(tbListas.getItems());
    	contato.setListas(list);
    	
    	contato.setApresentacao(txApresentacao.getText());
    	contato.setDetalhesOrigem(txDetalhesOrigem.getText());
    	contato.setResumo(txResumo.getText());
    	
    	contato.setMaterial(ckMaterial.isSelected());
    	contato.setConvite(ckConvite.isSelected());
    	contato.setNewsletter(ckNewsletter.isSelected());
    	contato.setMalaDireta(cbMalaDireta.getValue());
    	
    	contato.setTelefone(txTelefone1.getText());
    	contato.setCelular(txTelefone2.getText());
    	contato.setCep(txCEP.getPlainText());
    	contato.setEndereco(txLogradouro.getText().trim());
    	contato.setNumero(txNumero.getText().trim());
    	contato.setBairro(txBairro.getText().trim());
    	contato.setComplemento(txComplemento.getText());
    	contato.setEstado(cbEstado.getValue());
    	contato.setCidade(cbCidade.getValue());

		this.contato = contatos.save(contato);
		preencherFormulario(contato);
		return contato != null;
    }
    
    private boolean salvarStatus(NegocioTarefaContato tarefa,int status){
		NegocioTarefaContato t = tarefas.getOne(tarefa.getId());
		t.setFinalizado(status);
		tarefas.save(t);
		return true;
	}
	@SuppressWarnings("unchecked")
	void tabelaListas() {
    	TableColumn<NegocioLista, Number> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		TableColumn<NegocioLista, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<NegocioLista,Number>(){
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
					JavaFxUtil.buttonTable(button,IconsEnum.BUTTON_REMOVE);
					button.setOnAction(event -> {
						boolean removed = excluirLista(tbListas.getItems().get(getIndex()));
						if(removed) tbListas.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		tbListas.getColumns().addAll(colunaNome,colunaExcluir);
    }

	@SuppressWarnings("rawtypes")
	void tabelaTarefa() {
		TableColumn<NegocioTarefaContato, Calendar> colunaValidade = new  TableColumn<>("Prazo");
		colunaValidade.setCellValueFactory(new PropertyValueFactory<>("dataEvento"));
		colunaValidade.setCellFactory(param -> new TableCell<NegocioTarefaContato,Calendar>(){
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
		
		TableColumn<NegocioTarefaContato, TipoTarefa> colunaTipo = new  TableColumn<>("Tipo");
		colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoTarefa"));
		colunaTipo.setCellFactory(param -> new TableCell<NegocioTarefaContato,TipoTarefa>(){
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
		
		TableColumn<NegocioTarefaContato, Number> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellFactory(param -> new TableCell<NegocioTarefaContato,Number>(){
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
		TableColumn<NegocioTarefaContato, String> colunaLocalizacao = new  TableColumn<>("Localizacao");
		colunaLocalizacao.setCellValueFactory(new PropertyValueFactory<>("classe"));
		colunaLocalizacao.setCellFactory(param -> new TableCell<NegocioTarefaContato,String>(){
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
		TableColumn<NegocioTarefaContato, String> colunaDescricao = new  TableColumn<>("Descricao");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		colunaDescricao.setCellFactory((TableColumn<NegocioTarefaContato, String> param) -> {
			final TableCell<NegocioTarefaContato, String> cell = new TableCell<NegocioTarefaContato, String>() {
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
		
		TableColumn<NegocioTarefaContato, Number> colunaStatus = new  TableColumn<>("Status");
		colunaStatus.setCellValueFactory(new PropertyValueFactory<>("finalizado"));
		colunaStatus.setCellFactory(param -> new TableCell<NegocioTarefaContato,Number>(){
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
							NegocioTarefaContato tarefa = tbTarefas.getItems().get(getIndex());
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
		TableColumn<NegocioTarefaContato, Usuario> columnAtendente = new  TableColumn<>("Atendente");
		columnAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		columnAtendente.setCellFactory(param -> new TableCell<NegocioTarefaContato,Usuario>(){
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
		
		TableColumn<NegocioTarefaContato, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<NegocioTarefaContato,Number>(){
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
					buttonTable(button, IconsEnum.BUTTON_EDIT);
					button.setOnAction(event -> {
						abrirTarefa(tbTarefas.getItems().get(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		TableColumn<NegocioTarefaContato, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<NegocioTarefaContato,Number>(){
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
					JavaFxUtil.buttonTable(button,IconsEnum.BUTTON_REMOVE);
					button.setOnAction(event -> {
						boolean removed = excluirTarefa(tbTarefas.getItems().get(getIndex()));
						if(removed) tbTarefas.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		tbTarefas.getColumns().addAll(colunaLocalizacao,colunaValidade,colunaTipo,colunaNome,colunaDescricao,
				columnAtendente,colunaStatus,colunaEditar,colunaExcluir);
		tbTarefas.setTableMenuButtonVisible(true);
		tbTarefas.setFixedCellSize(50);
	}

	public void setPropriedades(Stage stage, Contato contato) {
		this.stage = stage;
		this.contato = contato;
		if(this.contato!=null) {
			contatos.findById(this.contato.getId()).ifPresent(cons->preencherFormulario(this.contato));
		}
	}
}
