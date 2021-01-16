package br.com.tiagods.controller;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.controller.ContatoCadastroController;
import br.com.tiagods.controller.NegocioCadastroController;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.negocio.*;
import br.com.tiagods.model.negocio.NegocioProposta.TipoEtapa;
import br.com.tiagods.model.negocio.NegocioProposta.TipoStatus;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.repository.*;
import br.com.tiagods.repository.helpers.filters.NegocioPropostaFilter;
import br.com.tiagods.repository.interfaces.Paginacao;
import br.com.tiagods.services.UsuarioLogService;
import br.com.tiagods.util.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.*;

@Controller
public class NegocioPesquisaController implements Initializable {

	@FXML
	private HBox pnCheckBox;

	@FXML
	private JFXComboBox<TipoStatus> cbStatus;

	@FXML
	private JFXComboBox<TipoEtapa> cbEtapa;

	@FXML
	private JFXComboBox<NegocioCategoria> cbCategoria;

	@FXML
	private JFXComboBox<NegocioNivel> cbNivel;

	@FXML
	private JFXComboBox<NegocioOrigem> cbOrigem;

	@FXML
	private JFXComboBox<NegocioServico> cbServico;

	@FXML
	private JFXComboBox<Usuario> cbAtendente;

	@FXML
	private JFXComboBox<String> cbPesquisaData;

	@FXML
	private JFXDatePicker dataInicial;

	@FXML
	private JFXDatePicker dataFinal;

	@FXML
	private JFXTextField txPesquisa;

	@FXML
	private JFXComboBox<String> cbClassificar;

	@FXML
	private TableView<NegocioProposta> tbPrincipal;

	@FXML
	private Pagination pagination;

	@FXML
	private JFXComboBox<Integer> cbLimite;

	private Paginacao paginacao;
	private Stage stage;
	@Autowired
	private NegociosNiveis niveis;
	@Autowired
	private NegociosCategorias categorias;
	@Autowired
	private NegociosOrigens origens;
	@Autowired
	private NegociosServicos servicos;
	@Autowired
	private NegociosPropostas propostas;
	@Autowired
	private Usuarios usuarios;
	@Autowired
	private UsuarioLogService usuarioLogService;

	@Lazy
	@Autowired
	private StageManager stageManager;
	@Autowired
	private NegocioCadastroController negocioCadastroController;
	@Autowired
	ContatoCadastroController contatoCadastroController;

	private NegocioPropostaFilter filter;

	public void setPropriedades(NegocioPropostaFilter filter, Stage stage) {
		this.filter = filter;
		this.stage = stage;
	}

	void abrirCadastro(NegocioProposta proposta) {
		if (proposta != null) {
			Optional<NegocioProposta> result = propostas.findById(proposta.getId());
			if(result.isPresent()) {
				proposta = result.get();
			}
		}
		Stage stage = stageManager.switchScene(FxmlView.NEGOCIO_CADASTRO, true);
		negocioCadastroController.setPropriedades(stage, proposta, null);
		stage.setOnHiding(event -> filtrar(this.paginacao));
	}
	private void abrirContato(Contato t) {
			Stage stage = stageManager.switchScene(FxmlView.CONTATO_CADASTRO, true);
			contatoCadastroController.setPropriedades(stage, t);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void combos() {
		NegocioCategoria categoria = new NegocioCategoria(-1L, "Qualquer");
		NegocioNivel nivel = new NegocioNivel(-1L, "Qualquer");
		NegocioOrigem origem = new NegocioOrigem(-1L, "Qualquer");
		NegocioServico servico = new NegocioServico(-1L, "Qualquer");
		Usuario usuario = new Usuario(-1L, "Qualquer");

		cbCategoria.getItems().add(categoria);
		cbNivel.getItems().add(nivel);
		cbOrigem.getItems().add(origem);
		cbServico.getItems().add(servico);
		cbAtendente.getItems().add(usuario);
		cbEtapa.getItems().addAll(TipoEtapa.values());
		cbStatus.getItems().addAll(TipoStatus.values());
		cbLimite.getItems().addAll(PaginacaoUtils.limiteTabela);

		cbPesquisaData.getItems().add("Criado em");
		cbPesquisaData.getSelectionModel().selectFirst();

		cbCategoria.getItems().addAll(categorias.findAll());
		cbNivel.getItems().addAll(niveis.findAll());
		cbOrigem.getItems().addAll(origens.findAll());
		cbServico.getItems().addAll(servicos.findAll());
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));

		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst();
		cbAtendente.getSelectionModel().selectFirst();

		if(filter!=null && filter.getStatus().equals(TipoStatus.STATUS))
			cbStatus.setValue(filter.getStatus());
		else
			cbStatus.getSelectionModel().selectFirst();
		cbEtapa.getSelectionModel().selectFirst();
		cbLimite.getSelectionModel().selectFirst();

		pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            paginacao.setPaginaAtual(newValue.intValue());
			filtrar(paginacao);
        });
		ChangeListener change = (ChangeListener<Object>) (observable, oldValue, newValue) -> {
			paginacao = new Paginacao(cbLimite.getValue());
			filtrar(paginacao);
        };
		cbStatus.valueProperty().addListener(change);
		cbEtapa.valueProperty().addListener(change);
		cbCategoria.valueProperty().addListener(change);
		cbNivel.valueProperty().addListener(change);
		cbOrigem.valueProperty().addListener(change);
		cbServico.valueProperty().addListener(change);
		cbAtendente.valueProperty().addListener(change);
		dataInicial.valueProperty().addListener(change);
		dataFinal.valueProperty().addListener(change);
		cbLimite.valueProperty().addListener(change);

		paginacao = new Paginacao(cbLimite.getValue());
	}

	private boolean excluir(NegocioProposta proposta) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			Optional<NegocioProposta> opt = propostas.findById(proposta.getId());
			if(opt.isPresent()){
				NegocioProposta t = opt.get();
				propostas.delete(t);
				return true;
			}
		}
		return false;
	}
	@FXML
	void exportar(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(FXMLEnum.PROGRESS_SAMPLE.getLocalizacao());
			Alert progress = new Alert(Alert.AlertType.INFORMATION);
			progress.setHeaderText("");
			DialogPane dialogPane = new DialogPane();
			dialogPane.setContent(loader.load());
			progress.setDialogPane(dialogPane);
			Stage sta = (Stage) dialogPane.getScene().getWindow();

			Task<Void> run = new Task<Void>() {
				{
					setOnFailed(a ->sta.close());
					setOnSucceeded(a ->sta.close());
					setOnCancelled(a ->sta.close());
				}
				@Override
				protected Void call() {
					File export = MyFileUtil.salvarTemp("xls");
					Platform.runLater(() -> sta.show());
					ArrayList<ArrayList> listaImpressao = new ArrayList<>();
					Integer[] colunasLenght = new Integer[] { 8, 26, 11, 11, 8, 14, 16, 14, 17, 9, 12, 19,
							30, 10, 10, 10, 30, 11, 11 };
					String[] cabecalho = new String[] { "Negocio", "Nome", "E-mail", "Fone", "Celular",
							"Data Inicio", "Data Limite", "Criador", "Etapa", "Status", "Atendente",
							"Origem", "Categoria", "Nivel", "Servicos/Produtos", "Descricao", "Honorario",
							"Valor Servicos Contratados", "Motivo da Perda", "Detalhe da Perda", "Data da Perda",
							"Data Finalização" };
					listaImpressao.add(new ArrayList<>());
					for (String c : cabecalho) {
						listaImpressao.get(0).add(c);
					}
					try {
						List<NegocioProposta> lista = filtrar(null);
						for (int i = 1; i <= lista.size(); i++) {
							listaImpressao.add(new ArrayList());
							Long newId = lista.get(i-1).getId();
							NegocioProposta n = propostas.getOne(newId);

							listaImpressao.get(i).add(n.getId());
							listaImpressao.get(i).add(n.getNome());
							
							String [] param = new String[] {"","",""};
							if(n.getNegocioContato()!=null) {
								param[0]=(n.getNegocioContato().getEmail());
								param[1]=(n.getNegocioContato().getTelefone());
								param[2]=(n.getNegocioContato().getCelular());
							}
							listaImpressao.get(i).add(param[0]);
							listaImpressao.get(i).add(param[1]);
							listaImpressao.get(i).add(param[2]);
							
							listaImpressao.get(i).add(n.getDataInicio() == null?"": DateUtil.format(n.getDataInicio().getTime(), DateUtil.SDF));
							listaImpressao.get(i).add(n.getDataFim() == null ? "" : DateUtil.format(n.getDataFim().getTime(), DateUtil.SDF));
							listaImpressao.get(i).add(n.getCriadoPor()!=null?n.getCriadoPor().getNome():"");
							listaImpressao.get(i).add(n.getTipoEtapa().getDescricao());
							listaImpressao.get(i).add(n.getTipoStatus().getDescricao());
							listaImpressao.get(i).add(n.getAtendente().getNome());
							listaImpressao.get(i).add(n.getOrigem() == null ? "": n.getOrigem());
							listaImpressao.get(i).add(n.getCategoria() == null ? "": n.getCategoria());
							listaImpressao.get(i).add(n.getNivel() == null ? "" : n.getNivel());
							listaImpressao.get(i).add(n.getServico() == null ? "": n.getServico());
							listaImpressao.get(i).add(n.getDescricao());
							listaImpressao.get(i)
									.add(MoedaUtil.format(n.getHonorario()));
							double vlrServicos = 0.00;
							Iterator<ServicoContratado> iterator = n.getServicosContratados().iterator();
							while (iterator.hasNext()) {
								ServicoContratado sc = iterator.next();
								vlrServicos += sc.getValor().doubleValue();
							}
							listaImpressao.get(i).add(MoedaUtil.format(new BigDecimal(vlrServicos)));
							listaImpressao.get(i).add(n.getMotivoPerda());
							listaImpressao.get(i).add(n.getDetalhesPerda());
							listaImpressao.get(i).add(n.getDataPerda() == null ? "" : DateUtil.format(n.getDataPerda().getTime()));
							listaImpressao.get(i).add(n.getDataFinalizacao() == null ? "" : DateUtil.format(n.getDataFinalizacao().getTime()));
						}
						ExcelGenericoUtil planilha = new ExcelGenericoUtil(export.getAbsolutePath(), listaImpressao,colunasLenght);
						planilha.gerarExcel();
						usuarioLogService.salvarLog("Negocio","Exportar","Exportou relatorio xls");
						Platform.runLater(() -> JavaFxUtil.alert(AlertType.INFORMATION, "Sucesso", "Relatorio gerado com sucesso", "", null,false));
						Desktop.getDesktop().open(export);
					} catch (Exception e1) {
						Platform.runLater(() -> JavaFxUtil.alert(AlertType.ERROR, "Erro", "", "Erro ao criar a planilha", e1, true));
					}
				return null;
			}
		};
		if (tbPrincipal.getItems().size() >= 1) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Exportação");
			alert.setContentText("Para exportar, realize um filtro antes!\nVocê ja filtrou os dados?");
			ButtonType ok = new ButtonType("Exportar e Abrir");
			ButtonType cancelar = new ButtonType("Cancelar");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().addAll(ok, cancelar);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ok) {
				Thread thread = new Thread(run);
				thread.start();
				sta.setOnCloseRequest(ae -> {
					try {
						thread.interrupt();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		} else {
			JavaFxUtil.alert(AlertType.ERROR,"Erro","Parâmetros vazios","Nenhum registro foi encontrato",null,false);
		}
		}catch (IOException e){
				JavaFxUtil.alert(AlertType.ERROR, "Erro", "Erro ao abrir o progresso", "O arquivo nao foi localizado",null,false);
		}
	}

	private List<NegocioProposta> filtrar(Paginacao paginacao) {
		if(this.filter==null) filter = new NegocioPropostaFilter();
		filter.setStatus(cbStatus.getValue());
		filter.setEtapa(cbEtapa.getValue());
		filter.setCategoria(cbCategoria.getValue());
		filter.setNivel(cbNivel.getValue());
		filter.setOrigem(cbOrigem.getValue());
		filter.setServico(cbServico.getValue());
		filter.setAtendente(cbAtendente.getValue());
		filter.setDataFiltro("criadoEm");
		filter.setDataInicial(dataInicial.getValue());
		filter.setDataFinal(dataFinal.getValue());
		filter.setPesquisa(txPesquisa.getText());
		filter.setOrdenacao("id");
		Pair<List<NegocioProposta>,Paginacao> lista = propostas.filtrar(paginacao,filter);
		if (paginacao != null) {
			tbPrincipal.getItems().clear();
			tbPrincipal.getItems().addAll(lista.getKey());
		}
		filter = null;
		return lista.getKey();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		combos();
		filtrar(paginacao);
	}

	@FXML
	void novo(ActionEvent event) {
		abrirCadastro(null);
	}

	@FXML
	void pesquisar(KeyEvent event) {
		filtrar(paginacao);
	}

	@FXML
	void sair(ActionEvent event) {
		stage.close();
	}

	@SuppressWarnings("unchecked")
	void tabela() {
		TableColumn<NegocioProposta, Number> colunaId = new TableColumn<>("*");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<NegocioProposta, String> colunaNome = new TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaNome.setPrefWidth(120);

		TableColumn<NegocioProposta, TipoStatus> colunaStatus = new TableColumn<>("Status");
		colunaStatus.setCellValueFactory(new PropertyValueFactory<>("tipoStatus"));
		colunaStatus.setPrefWidth(40);

		TableColumn<NegocioProposta, TipoEtapa> colunaEtapa = new TableColumn<>("Etapa");
		colunaEtapa.setCellValueFactory(new PropertyValueFactory<>("tipoEtapa"));
		colunaEtapa.setCellFactory(param -> new TableCell<NegocioProposta, TipoEtapa>() {
			JFXButton button = new JFXButton();// Editar
			@Override
			protected void updateItem(TipoEtapa item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					button.getStyleClass().add("btDefault");
					JavaFxUtil.buttonTable(button, item.getIco());
					button.setOnAction(event -> {
						abrirCadastro(tbPrincipal.getItems().get(getIndex()));
					});
					setGraphic(button);
				}
			}
		});

		TableColumn<NegocioProposta, NegocioOrigem> colunaOrigem = new TableColumn<>("Origem");
		colunaOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
		colunaOrigem.setPrefWidth(60);

		TableColumn<NegocioProposta, NegocioServico> colunaServico = new TableColumn<>("Servico");
		colunaServico.setCellValueFactory(new PropertyValueFactory<>("servico"));

		TableColumn<NegocioProposta, NegocioCategoria> colunaCategoria = new TableColumn<>("Categoria");
		colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

		TableColumn<NegocioProposta, NegocioNivel> colunaNivel = new TableColumn<>("Nivel");
		colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));

		TableColumn<NegocioProposta, Calendar> colunaCriadoEm = new TableColumn<>("Inicio");
		colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
		colunaCriadoEm.setCellFactory(param -> new TableCell<NegocioProposta, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					setText(DateUtil.format(item.getTime()));
				}
			}
		});

		TableColumn<NegocioProposta, Calendar> colunaVencimento = new TableColumn<>("Vencimento");
		colunaVencimento.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
		colunaVencimento.setCellFactory(param -> new TableCell<NegocioProposta, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					setText(DateUtil.format(item.getTime()));
				}
			}
		});
		TableColumn<NegocioProposta, Calendar> colunaConclusao = new TableColumn<>("Conclusao");
		colunaConclusao.setCellValueFactory(new PropertyValueFactory<>("dataFinalizacao"));
		colunaConclusao.setCellFactory(param -> new TableCell<NegocioProposta, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					setText(DateUtil.format(item.getTime()));
				}
			}
		});
		TableColumn<NegocioProposta, Usuario> colunaAtendente = new TableColumn<>("Atendente");
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));

		TableColumn<NegocioProposta, Number> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<NegocioProposta, Number>() {
			JFXButton button = new JFXButton();// Editar

			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					button.getStyleClass().add("btDefault");
					JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_EDIT);
					button.setOnAction(event -> {
						abrirCadastro(tbPrincipal.getItems().get(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		TableColumn<NegocioProposta, Number> colunaAbrirContato = new TableColumn<>("");
		colunaAbrirContato.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaAbrirContato.setCellFactory(param -> new TableCell<NegocioProposta, Number>() {
			JFXButton button = new JFXButton();//
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					button.getStyleClass().add("btDefault");
					JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_CONTATO);
					button.setOnAction(event -> abrirContato(tbPrincipal.getItems().get(getIndex()).getNegocioContato()));
					setGraphic(button);
				}
			}
		});
		TableColumn<NegocioProposta, Number> colunaExcluir = new TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<NegocioProposta, Number>() {
			JFXButton button = new JFXButton();// excluir
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					button.getStyleClass().add("btDefault");
					JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_REMOVE);
					button.setOnAction(event -> {
						boolean removed = excluir(tbPrincipal.getItems().get(getIndex()));
						if (removed)
							tbPrincipal.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		tbPrincipal.getColumns().addAll(colunaId, colunaNome, colunaStatus, colunaEtapa, colunaOrigem, colunaServico,
				colunaCategoria, colunaNivel, colunaCriadoEm, colunaVencimento, colunaAtendente, colunaAbrirContato,colunaEditar);
	}
}
