package br.com.tiagods.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.repository.Contatos;
import br.com.tiagods.repository.Usuarios;
import br.com.tiagods.repository.helpers.*;
import br.com.tiagods.services.UsuarioLogService;
import br.com.tiagods.util.DateUtil;
import br.com.tiagods.util.JavaFxUtil;
import br.com.tiagods.util.MyFileUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.negocio.Contato;
import br.com.tiagods.model.negocio.Contato.ContatoTipo;
import br.com.tiagods.model.negocio.Contato.PessoaTipo;
import br.com.tiagods.model.negocio.NegocioCategoria;
import br.com.tiagods.model.negocio.NegocioLista;
import br.com.tiagods.model.negocio.NegocioNivel;
import br.com.tiagods.model.negocio.NegocioOrigem;
import br.com.tiagods.model.negocio.NegocioServico;
import br.com.tiagods.model.negocio.ServicoContratado;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.model.negocio.NegocioProposta;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.util.ExcelGenericoUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.apache.tools.ant.taskdefs.Java;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ContatoPesquisaController implements StageController,Initializable {
	@FXML
	private HBox pnCheckBox;

	@FXML
	private JFXComboBox<Contato.PessoaTipo> cbTipo;

	@FXML
	private JFXComboBox<Contato.ContatoTipo> cbContatoTipo;

	@FXML
	private JFXComboBox<NegocioLista> cbLista;

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
	private JFXComboBox<String> cbMalaDireta;

	@FXML
	private HBox pnCheckBox1;

	@FXML
	private JFXDatePicker dataInicial;

	@FXML
	private JFXDatePicker dataFinal;

	@FXML
	private JFXTextField txPesquisa;

	@FXML
	private JFXComboBox<String> cbClassificar;

	@FXML
	private TableView<Contato> tbPrincipal;

	@FXML
	private TableView<Contato> tbListas;

	@FXML
	private TableView<Contato> tbTarefas;

	@FXML
	private Pagination pagination;

	@FXML
	private JFXComboBox<Integer> cbLimite;

	private Stage stage;
	@Autowired
	private Contatos contatos;
	@Autowired
	private NegociosNiveis niveis;
	@Autowired
	private NegocioCategorias categorias;
	@Autowired
	private NegocioOrigens origens;
	@Autowired
	private NegocioServicos servicos;
	@Autowired
	private NegociosListas listas;
	@Autowired
	private NegociosPropostas propostas;
	@Autowired
	private Usuarios usuarios;

	@Autowired
	UsuarioLogService usuarioLogService;

	private Paginacao paginacao;

	private void abrirCadastro(Contato t) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.CONTATO_CADASTRO);
			loader.setController(new ContatoCadastroController(stage, t));
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
			stage.setOnHiding(event -> filtrar(this.paginacao));
		} catch (IOException e) {
			JavaFxUtil.alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo" + FXMLEnum.CONTATO_CADASTRO, e, true);
		}
	}

	void abrirCadastroNegocio(NegocioProposta proposta,Contato contato) {
		try {
			loadFactory();
			if (proposta != null) {
				propostas = new NegocioPropostaImpl(getManager());
				proposta = propostas.findById(proposta.getId());
			}
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.NEGOCIO_CADASTRO);
			loader.setController(new NegocioCadastroController(stage, proposta,contato));
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
			stage.setOnHiding(event -> filtrar(this.paginacao));
		} catch (IOException e) {
			JavaFxUtil.alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo" + FXMLEnum.NEGOCIO_CADASTRO, e, true);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void combos() {
		NegocioCategoria categoria = new NegocioCategoria(-1L, "Categoria");
		String[] malaDireta = new String[] { "Mala Direta", "Convite para Eventos", "Material", "Newsletter" };
		NegocioNivel nivel = new NegocioNivel(-1L, "Nivel");
		NegocioOrigem origem = new NegocioOrigem(-1L, "Origem");
		NegocioServico servico = new NegocioServico(-1L, "Servico");
		Usuario usuario = new Usuario(-1L, "Atendente");
		NegocioLista lista = new NegocioLista(-1L, "Lista");

		cbCategoria.getItems().add(categoria);
		cbNivel.getItems().add(nivel);
		cbOrigem.getItems().add(origem);
		cbServico.getItems().add(servico);
		cbAtendente.getItems().add(usuario);
		cbLista.getItems().add(lista);

		cbLimite.getItems().addAll(limiteTabela);
		cbCategoria.getItems().addAll(categorias.getAll());
		cbNivel.getItems().addAll(niveis.getAll());
		cbOrigem.getItems().addAll(origens.getAll());
		cbServico.getItems().addAll(servicos.getAll());
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
		cbLista.getItems().addAll(listas.getAll());
		cbMalaDireta.getItems().addAll(malaDireta);

		cbTipo.getItems().addAll(PessoaTipo.values());
		cbContatoTipo.getItems().addAll(ContatoTipo.values());

		cbLimite.getSelectionModel().selectFirst();
		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst();
		cbAtendente.getSelectionModel().selectFirst();
		cbLista.getSelectionModel().selectFirst();
		cbMalaDireta.getSelectionModel().selectFirst();

		cbTipo.getSelectionModel().select(PessoaTipo.CONTATO);
		cbContatoTipo.getSelectionModel().select(ContatoTipo.CONTATO);

		ChangeListener change = (ChangeListener<Object>) (observable, oldValue, newValue) -> {
			paginacao = new Paginacao(cbLimite.getValue());
			filtrar(paginacao);
        };
		cbTipo.valueProperty().addListener(change);
		cbContatoTipo.valueProperty().addListener(change);
		cbLista.valueProperty().addListener(change);
		cbCategoria.valueProperty().addListener(change);
		cbMalaDireta.valueProperty().addListener(change);
		cbNivel.valueProperty().addListener(change);
		cbOrigem.valueProperty().addListener(change);
		cbServico.valueProperty().addListener(change);
		cbAtendente.valueProperty().addListener(change);
		dataFinal.valueProperty().addListener(change);
		dataInicial.valueProperty().addListener(change);
		cbLimite.valueProperty().addListener(change);
		
		pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
			paginacao.setPaginaAtual(newValue.intValue());
			filtrar(paginacao);
		});
		paginacao = new Paginacao(cbLimite.getValue());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
						Integer[] colunasLenght = new Integer[]
								{10, 20, 20, 20, 30, 20, 20, 10, 10, 10, 10, 10, 10, 10, 10, 10, 15, 15, 10, 15, 10, 10, 10, 15, 10, 15, 15, 15, 20, 10, 10, 10, 15, 15, 15, 15, 20, 20, 20, 10, 10, 20, 20, 20};
						String[] cabecalho = new String[]{"Cod", "Periodo", "Empresa/Pessoa", "Tipo", "Nome", "Razao",
								"Apelido", "Responsavel", "Cnpj", "IM", "IE", "RG", "CPF", "ANIVERSARIO", "Telefone", "Celular", "E-Mail", "Site",
								"CEP", "Logradouro", "Nº", "Bairro", "Compl", "Cidade", "UF", "Data Criacao", "Atendente",
								"Criador", "Mala Direta", "Convite", "Material", "Newsletter", "Categoria", "Nivel",
								"Servico", "Origem", "Detalhes Origem", "Resumo", "Apresentacao", "Listas", "Qtd Negocios", "Status Negocio", "Honorário", "Servicos Contratados"};

						listaImpressao.add(new ArrayList<>());
						for (String c : cabecalho) {
							listaImpressao.get(0).add(c);
						}
						try {
							loadFactory();
							propostas = new NegocioPropostaImpl(getManager());
							List<Contato> finalList = filtrar(null);
							for (int i = 1; i <= finalList.size(); i++) {
								listaImpressao.add(new ArrayList<String>());
								Long newId = finalList.get(i - 1).getId();
								Contato c = contatos.findById(newId);
								listaImpressao.get(i).add(c.getId());
								listaImpressao.get(i).add(new SimpleDateFormat("MM/yyyy").format(c.getCriadoEm().getTime()));
								listaImpressao.get(i).add(c.getPessoaTipo().getDescricao());
								listaImpressao.get(i).add(c.getContatoTipo().getDescricao());
								listaImpressao.get(i).add(c.getNome());

								String razao = "", apelido = "", responsavel = "", cnpj = "", im = "", ie = "";
								String rg = "", cpf = "", aniversario = "";

								if (c.getPessoaTipo().equals(PessoaTipo.EMPRESA) && c.getJuridico() != null) {
									razao = c.getJuridico().getRazao();
									apelido = c.getJuridico().getApelido();
									responsavel = c.getJuridico().getResponsavel();
									cnpj = c.getJuridico().getCnpj();
									im = c.getJuridico().getIm();
									ie = c.getJuridico().getIe();

								} else if (c.getPessoaTipo().equals(PessoaTipo.PESSOA) && c.getFisico() != null) {
									rg = c.getFisico().getRg();
									cpf = c.getFisico().getCpf();
									Calendar calendar = c.getFisico().getNiver();
									if (calendar != null) rg = new SimpleDateFormat("dd/MM").format(calendar.getTime());
								}
								listaImpressao.get(i).add(razao);
								listaImpressao.get(i).add(apelido);
								listaImpressao.get(i).add(responsavel);
								listaImpressao.get(i).add(cnpj);
								listaImpressao.get(i).add(im);
								listaImpressao.get(i).add(ie);

								listaImpressao.get(i).add(rg);
								listaImpressao.get(i).add(cpf);
								listaImpressao.get(i).add(aniversario);

								listaImpressao.get(i).add(c.getTelefone());
								listaImpressao.get(i).add(c.getCelular());
								listaImpressao.get(i).add(c.getEmail());
								listaImpressao.get(i).add(c.getSite());
								listaImpressao.get(i).add(c.getCep());
								listaImpressao.get(i).add(c.getEndereco());
								listaImpressao.get(i).add(c.getNumero());
								listaImpressao.get(i).add(c.getBairro());
								listaImpressao.get(i).add(c.getComplemento());
								listaImpressao.get(i).add(c.getCidade());
								listaImpressao.get(i).add(c.getEstado());
								listaImpressao.get(i).add(c.getCriadoEm()==null?"" : sdfH.format(c.getCriadoEm().getTime()));
								listaImpressao.get(i).add(c.getAtendente() == null ? "" : c.getAtendente().getNome());
								listaImpressao.get(i).add(c.getCriadoPor() == null ? "" : c.getCriadoPor().getNome());

								listaImpressao.get(i).add(c.getMalaDireta());
								listaImpressao.get(i).add(c.isConvite() ? "Sim" : "Não");
								listaImpressao.get(i).add(c.isMaterial() ? "Sim" : "Não");
								listaImpressao.get(i).add(c.isNewsletter() ? "Sim" : "Não");

								listaImpressao.get(i).add(c.getCategoria());
								listaImpressao.get(i).add(c.getNivel());
								listaImpressao.get(i).add(c.getServico());
								listaImpressao.get(i).add(c.getOrigem());
								listaImpressao.get(i).add(c.getDetalhesOrigem());
								listaImpressao.get(i).add(c.getResumo());
								listaImpressao.get(i).add(c.getApresentacao());

								String lista = c.getListas().stream().map(m -> m.toString()).collect(Collectors.joining(","));
								listaImpressao.get(i).add(lista);

								listaImpressao.get(i).add(c.getNegocios().size());

								StringBuilder valorHonorario = new StringBuilder();
								StringBuilder statusNegocio = new StringBuilder();
								StringBuilder statusServicosNegocios = new StringBuilder();

								for (NegocioProposta negocio : c.getNegocios()) {
									valorHonorario.append(negocio.getId());
									valorHonorario.append("-");
									valorHonorario
											.append(nf.format(negocio.getHonorario()));
									valorHonorario.append("\n ");
									statusNegocio.append(negocio.getId());
									statusNegocio.append("-");
									statusNegocio.append(negocio.getTipoStatus().getDescricao());
									statusNegocio.append("\n ");

									Set<ServicoContratado> servicos = negocio.getServicosContratados();
									for (ServicoContratado sc : servicos) {
										statusServicosNegocios.append(negocio.getId());
										statusServicosNegocios.append(" - ");
										statusServicosNegocios.append(sc.getServicosAgregados().getNome());
										statusServicosNegocios.append(" - ");
										statusServicosNegocios
												.append(nf.format(sc.getValor()));
										statusServicosNegocios.append("\n ");
									}
								}
								listaImpressao.get(i).add(statusNegocio.toString());//status do negocio
								listaImpressao.get(i).add(valorHonorario.toString());//valor do negocio
								listaImpressao.get(i).add(statusServicosNegocios.toString());//valor do negocio
							}
							ExcelGenericoUtil planilha = new ExcelGenericoUtil(export.getAbsolutePath(), listaImpressao, colunasLenght);
							planilha.gerarExcel();
							usuarioLogService.salvarLog("Contato", "Exportar", "Exportou relatorio xls");
							Platform.runLater(() -> JavaFxUtil.alert(AlertType.INFORMATION, "Sucesso", "Relatorio gerado com sucesso", "", null, false));
							Desktop.getDesktop().open(export);
						} catch (Exception e1) {
							Platform.runLater(() -> JavaFxUtil.alert(AlertType.ERROR, "Erro", "", "Erro ao criar a planilha ", e1, true));
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
				JavaFxUtil.alert(AlertType.ERROR, "Erro", "Parâmetros vazios", "Nenhum registro foi encontrato",null,false);
			}
		}catch (IOException e){
			JavaFxUtil.alert(AlertType.ERROR, "Erro", "Erro ao abrir o progresso", "O arquivo nao foi localizado",null,false);
		}
	}

	boolean excluir(Contato n) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK && n.getId()!=null) {
			contatos.delete(n);
			return true;
		} else
			return false;
	}

	private List<Contato> filtrar(Paginacao paginacao) {
		contatos = new ContatosImpl(getManager());
		Pair<List<Contato>, Paginacao> list = contatos.filtrar(paginacao, cbTipo.getValue(), cbContatoTipo.getValue(),
				cbLista.getValue(), cbCategoria.getValue(), cbNivel.getValue(), cbOrigem.getValue(),
				cbServico.getValue(), cbAtendente.getValue(), cbMalaDireta.getValue(), dataInicial.getValue(),
				dataFinal.getValue(), txPesquisa.getText());
		if (paginacao != null) {
			this.paginacao = list.getValue();
			tbPrincipal.getItems().clear();
			tbPrincipal.getItems().addAll(list.getKey());
			pagination.setPageCount(paginacao.getTotalPaginas());
			pagination.setCurrentPageIndex(paginacao.getPaginaAtual());
		}
		return list.getKey();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		combos();
		filtrar(this.paginacao);
	}

	@FXML
	void novaTarefa(ActionEvent event) {

	}

	@FXML
	void novo(ActionEvent event) {
		abrirCadastro(null);
	}

	@FXML
	void pesquisar(KeyEvent event) {
		filtrar(this.paginacao);
	}

	@FXML
	void sair(ActionEvent event) {
		stage.close();
	}

	void tabela() {
		TableColumn<Contato, Calendar> colunaCriadoEm = new TableColumn<>("Data");
		colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("criadoEm"));
		colunaCriadoEm.setCellFactory(param -> new TableCell<Contato, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					setText(DateUtil.parse(item.getTime(), DateUtil.SDFH));
				}
			}
		});
		colunaCriadoEm.setPrefWidth(120);
		TableColumn<Contato, String> colunaNome = new TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaNome.setPrefWidth(120);

		TableColumn<Contato, Number> colunaTelefone = new TableColumn<>("Telefone");
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaTelefone.setCellFactory(param -> new TableCell<Contato, Number>() {
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					Contato c = tbPrincipal.getItems().get(getIndex());
					String telefone1 = c.getTelefone().length() > 0
							? c.getTelefone() + (c.getCelular().trim().length() > 0 ? "\\" + c.getCelular() : "")
							: c.getCelular();
					setText(telefone1);
				}
			}
		});
		colunaTelefone.setPrefWidth(60);

		TableColumn<Contato, String> colunaEmail = new TableColumn<>("E-Mail");
		colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colunaEmail.setCellFactory(param -> new TableCell<Contato, String>() {
			JFXButton button = new JFXButton();//
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (item==null || item.equals("") || empty) {
					setStyle("");
					setText("");
					setGraphic(null);
				} else {
					button.getStyleClass().add("btDefaultText");
					try {
						button.setText(item);
						JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_MAIL);
						final URI url = new URI("mailto", item, null);
						button.setOnAction(event -> {
							try {
								Desktop.getDesktop().mail(url);
							} catch (IOException e) {}
						});
						setGraphic(button);
					} catch (URISyntaxException | IOException e) {}
				}
			}
		});
		colunaEmail.setPrefWidth(70);

		TableColumn<Contato, NegocioOrigem> colunaOrigem = new TableColumn<>("Origem");
		colunaOrigem.setCellValueFactory(new PropertyValueFactory<>("origem"));
		colunaOrigem.setPrefWidth(70);

		TableColumn<Contato, NegocioServico> colunaServico = new TableColumn<>("Servico");
		colunaServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
		colunaServico.setPrefWidth(70);

		TableColumn<Contato, NegocioCategoria> colunaCategoria = new TableColumn<>("Categoria");
		colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		colunaCategoria.setPrefWidth(70);

		TableColumn<Contato, NegocioNivel> colunaNivel = new TableColumn<>("Nivel");
		colunaNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		colunaNivel.setPrefWidth(60);

		TableColumn<Contato, Usuario> colunaAtendente = new TableColumn<>("Atendente");
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		colunaAtendente.setPrefWidth(60);

		TableColumn<Contato, NegocioProposta> colunaUtimoNegocio = new TableColumn<>("Negocio");
		colunaUtimoNegocio.setCellValueFactory(new PropertyValueFactory<>("ultimoNegocio"));
		colunaUtimoNegocio.setCellFactory(param -> new TableCell<Contato, NegocioProposta>() {
			JFXButton button = new JFXButton();//
			@Override
			protected void updateItem(NegocioProposta item, boolean empty) {
				super.updateItem(item, empty);
				button.getStyleClass().add("btDefault");
				try {
					if (empty) {
						setStyle("");
						setText("");
						setGraphic(null);
					}
					else {
						IconsEnum en = item==null?IconsEnum.BUTTON_ADD:IconsEnum.BUTTON_PROPOSTA;
						JavaFxUtil.buttonTable(button, en);
						setGraphic(button);
					}
					button.setOnAction(event -> abrirCadastroNegocio(item, tbPrincipal.getItems().get(getIndex())));
				}catch (IOException e){}
			}
		});
		TableColumn<Contato, Number> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<Contato, Number>() {
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
					try {
						JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_EDIT);
					} catch (IOException e) {
					}
					button.setOnAction(event -> {
						abrirCadastro(tbPrincipal.getItems().get(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		TableColumn<Contato, Number> colunaExcluir = new TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<Contato, Number>() {
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
					try {
						JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_REMOVE);
					} catch (IOException e) {
					}
					button.setOnAction(event -> {
						boolean removed = excluir(tbPrincipal.getItems().get(getIndex()));
						if (removed)
							tbPrincipal.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		tbPrincipal.getColumns().addAll(colunaCriadoEm, colunaNome, colunaTelefone, colunaEmail, colunaOrigem,
				colunaServico, colunaCategoria, colunaNivel, colunaAtendente, colunaUtimoNegocio, colunaEditar,
				colunaExcluir);
	}

	@Override
	public void setPropriedades(Stage stage) {
		this.stage=stage;
	}
}
