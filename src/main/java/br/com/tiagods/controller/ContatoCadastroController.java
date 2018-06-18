package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.UsuarioLogado;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.exception.FXMLNaoEncontradoException;
import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Contato;
import br.com.tiagods.model.Contato.ContatoTipo;
import br.com.tiagods.model.Contato.PessoaTipo;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.model.NegocioMalaDireta;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioLista;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.NegocioTarefaContato;
import br.com.tiagods.model.NegocioTarefaProposta;
import br.com.tiagods.model.PessoaFisica;
import br.com.tiagods.model.PessoaJuridica;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.ContatosImpl;
import br.com.tiagods.repository.helpers.NegocioCategoriasImpl;
import br.com.tiagods.repository.helpers.NegocioNiveisImpl;
import br.com.tiagods.repository.helpers.NegocioOrigensImpl;
import br.com.tiagods.repository.helpers.NegocioServicosImpl;
import br.com.tiagods.repository.helpers.NegociosListasImpl;
import br.com.tiagods.repository.helpers.NegociosMalaDiretaImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasContatosImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasPropostasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.controlsfx.control.Rating;

public class ContatoCadastroController extends UtilsController implements Initializable{
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
    private MaskedTextField txTelefone;

    @FXML
    private MaskedTextField txCelular;

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
    
    private Stage stage;
	private Contato contato;
	private ContatosImpl contatos;
	private NegocioNiveisImpl niveis;
	private NegocioCategoriasImpl categorias;
	private NegocioOrigensImpl origens;
	private NegocioServicosImpl servicos;
	private NegociosListasImpl listas;
	private UsuariosImpl usuarios;
	private NegociosMalaDiretaImpl malasDiretas;
	private NegociosTarefasContatosImpl tarefas;
	
    public ContatoCadastroController(Stage stage, Contato contato) {
		this.stage=stage;;
		this.contato=contato;
	}
    private void abrirTarefa(NegocioTarefaContato t) {
		try {
			Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.TAREFA_CADASTRO);
            loader.setController(new TarefaCadastroController(stage,t,contato));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
            	try {
        			loadFactory();
        			contatos = new ContatosImpl(getManager());
        			contato = contatos.findById(contato.getId());
        			tbTarefas.getItems().clear();
        			tbTarefas.getItems().addAll(contato.getTarefas());
        			tbTarefas.refresh();
        		}catch(Exception e) {
        			e.printStackTrace();
        		}finally {
        			close();
        		}
            });
        }catch(FXMLNaoEncontradoException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.TAREFA_CADASTRO,e,true);
        }
	}
    @FXML
    void buscarCep(ActionEvent event) {
    	bucarCep(txCEP, txLogradouro, txNumero, txComplemento, txBairro, cbCidade, cbEstado);
    }
    
    void combos() {
    	ToggleGroup group1 = new ToggleGroup();
    	group1.getToggles().addAll(rbEmpresa,rbPessoa);
    	
    	ChangeListener<Boolean> change = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(rbEmpresa.isSelected()) {
					pnPessoaJuridica.setVisible(true);
					pnPessoaFisica.setVisible(false);
				}
				else if(rbPessoa.isSelected()) {
					pnPessoaFisica.setVisible(true);
					pnPessoaJuridica.setVisible(false);
				}
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
		
		contatos = new ContatosImpl(getManager());
		categorias = new NegocioCategoriasImpl(getManager());
		niveis = new NegocioNiveisImpl(getManager());
		origens = new NegocioOrigensImpl(getManager());
		servicos = new NegocioServicosImpl(getManager());
		listas = new NegociosListasImpl(getManager());
		usuarios = new UsuariosImpl(getManager());
		malasDiretas = new NegociosMalaDiretaImpl(getManager());
		
		cbCategoria.getItems().addAll(categorias.getAll());
		cbNivel.getItems().addAll(niveis.getAll());
		cbOrigem.getItems().addAll(origens.getAll());
		cbServico.getItems().addAll(servicos.getAll());
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
		cbLista.getItems().addAll(listas.getAll());
		cbMalaDireta.getItems().addAll(malasDiretas.getAll());
		
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
		rating.hoverProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				lbrating.setText(String.format("%.2f",rating.getRating()));
				
			}
		});
		comboRegiao(cbCidade,cbEstado,getManager());
    }
    boolean excluirTarefa(NegocioTarefaContato n) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			try{
				loadFactory();
				tarefas = new NegociosTarefasContatosImpl(getManager());
				NegocioTarefaContato t = tarefas.findById(n.getId());
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
		try {
			tabelaListas();
			tabelaTarefa();
			loadFactory();
			combos();
			if(contato!=null) {
				contatos = new ContatosImpl(getManager());
				this.contato = contatos.findById(this.contato.getId());
				preencherFormulario(this.contato);
			}
		}catch(PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro ao carregar formulario","Erro ao realizar consulta", e, true);
		}finally {
			close();
		}	
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
    void novaTarefa(ActionEvent event) {
    	if(contato==null) {
    		this.contato = new Contato();
    		if(salvar()) 
    			abrirTarefa(null);
    		
    	}
    	else
    		abrirTarefa(null);
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
				txRG.setText(contato.getFisico().getRg());
				txCPF.setPlainText(contato.getFisico().getCpf());
				txDataNascimento.setPlainText(contato.getFisico().getAniversario());
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
		txTelefone.setPlainText(contato.getTelefone());
		txCelular.setPlainText(contato.getCelular());
		
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
		
		txTelefone.setPlainText(contato.getTelefone());
		txCelular.setPlainText(contato.getCelular());
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
    		fisica.setAniversario(txDataNascimento.getPlainText());
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
    	
    	contato.setTelefone(txTelefone.getPlainText());
    	contato.setCelular(txCelular.getPlainText());
    	contato.setCep(txCEP.getPlainText());
    	contato.setEndereco(txLogradouro.getText().trim());
    	contato.setNumero(txNumero.getText().trim());
    	contato.setBairro(txBairro.getText().trim());
    	contato.setComplemento(txComplemento.getText());
    	contato.setEstado(cbEstado.getValue());
    	contato.setCidade(cbCidade.getValue());
    	try {
	        loadFactory();
	        contatos = new ContatosImpl(getManager());
	        this.contato = contatos.save(contato);
	        preencherFormulario(contato);
	        alert(Alert.AlertType.INFORMATION,"Sucesso",null,
	                "Salvo com sucesso",null,false);
	        return true;
	    } catch (PersistenceException e) {
	        alert(Alert.AlertType.ERROR,"Erro",null,"Erro ao salvar o registro",e,true);
	        return false;
	    }finally {
			close();
		}
    }
    
    private boolean salvarStatus(NegocioTarefaContato tarefa,int status){
		try{
			loadFactory();
			tarefas = new NegociosTarefasContatosImpl(getManager());
			NegocioTarefaContato t = tarefas.findById(tarefa.getId());
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
					try {
						buttonTable(button,IconsEnum.BUTTON_REMOVE);
					}catch (IOException e) {
					}
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
					setText(item.getLogin());
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
					try {
						buttonTable(button, IconsEnum.BUTTON_EDIT);
					}catch (IOException e) {
					}
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
		tbTarefas.getColumns().addAll(colunaLocalizacao,colunaValidade,colunaTipo,colunaNome,colunaDescricao,
				columnAtendente,colunaStatus,colunaEditar,colunaExcluir);
		tbTarefas.setTableMenuButtonVisible(true);
		tbTarefas.setFixedCellSize(50);
	}
	
	
	
	
}
