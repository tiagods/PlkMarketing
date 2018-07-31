package br.com.tiagods.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.ServicoContratado;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.modelcollections.NegocioProposta.TipoEtapa;
import br.com.tiagods.modelcollections.NegocioProposta.TipoStatus;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.NegocioCategoriasImpl;
import br.com.tiagods.repository.helpers.NegocioNiveisImpl;
import br.com.tiagods.repository.helpers.NegocioOrigensImpl;
import br.com.tiagods.repository.helpers.NegocioPropostaImpl;
import br.com.tiagods.repository.helpers.NegocioServicosImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.ExcelGenerico;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

public class NegocioPesquisaController extends UtilsController implements Initializable {
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
	private NegocioNiveisImpl niveis;
	private NegocioCategoriasImpl categorias;
	private NegocioOrigensImpl origens;
	private NegocioServicosImpl servicos;
	private NegocioPropostaImpl propostas;

	private UsuariosImpl usuarios;

	public NegocioPesquisaController(Stage stage) {
		this.stage = stage;
	}

	void abrirCadastro(NegocioProposta proposta) {
		try {
			loadFactory();
			if (proposta != null) {
				propostas = new NegocioPropostaImpl(getManager());
				proposta = propostas.findById(proposta.getId());
			}
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.NEGOCIO_CADASTRO);
			loader.setController(new NegocioCadastroController(stage, proposta));
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
			stage.setOnHiding(event -> {
				try {
					loadFactory();
					filtrar(this.paginacao);
				} catch (Exception e) {
					alert(AlertType.ERROR, "Error", "", "Erro ao abrir cadastro", e, true);
				} finally {
					close();
				}
			});
		} catch (IOException e) {
			alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo" + FXMLEnum.NEGOCIO_CADASTRO, e, true);
		} finally {
			close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void combos() {

		NegocioCategoria categoria = new NegocioCategoria(-1L, "Categoria");
		NegocioNivel nivel = new NegocioNivel(-1L, "Nivel");
		NegocioOrigem origem = new NegocioOrigem(-1L, "Origem");
		NegocioServico servico = new NegocioServico(-1L, "Servico");
		Usuario usuario = new Usuario(-1L, "Atendente");

		cbCategoria.getItems().add(categoria);
		cbNivel.getItems().add(nivel);
		cbOrigem.getItems().add(origem);
		cbServico.getItems().add(servico);
		cbAtendente.getItems().add(usuario);
		cbEtapa.getItems().addAll(TipoEtapa.values());
		cbStatus.getItems().addAll(TipoStatus.values());
		cbLimite.getItems().addAll(limiteTabela);

		categorias = new NegocioCategoriasImpl(getManager());
		niveis = new NegocioNiveisImpl(getManager());
		origens = new NegocioOrigensImpl(getManager());
		servicos = new NegocioServicosImpl(getManager());
		usuarios = new UsuariosImpl(getManager());
		propostas = new NegocioPropostaImpl(getManager());

		cbCategoria.getItems().addAll(categorias.getAll());
		cbNivel.getItems().addAll(niveis.getAll());
		cbOrigem.getItems().addAll(origens.getAll());
		cbServico.getItems().addAll(servicos.getAll());
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));

		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst();
		cbAtendente.getSelectionModel().selectFirst();
		cbStatus.getSelectionModel().selectFirst();
		cbEtapa.getSelectionModel().selectFirst();
		cbLimite.getSelectionModel().selectFirst();

		pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				paginacao.setPaginaAtual(newValue.intValue());
				try {
					loadFactory();
					filtrar(paginacao);
				} catch (PersistenceException e) {
					alert(AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao realizar consulta", e, true);
				} finally {
					close();
				}
			}
		});
		ChangeListener change = new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				try {
					loadFactory();
					paginacao = new Paginacao(cbLimite.getValue());
					filtrar(paginacao);
				} catch (Exception e) {
					alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao filtrar os registros", e, true);
				} finally {
					close();
				}
			}
		
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
			try {
				loadFactory();
				propostas = new NegocioPropostaImpl(getManager());
				NegocioProposta t = propostas.findById(proposta.getId());
				propostas.remove(t);
				alert(AlertType.INFORMATION, "Sucesso", null, "Removido com sucesso!", null, false);
				return true;
			} catch (Exception e) {
				alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao excluir o registro", e, true);
				return false;
			} finally {
				super.close();
			}
		} else
			return false;
	}
	@SuppressWarnings({"rawtypes","unchecked"})
	@FXML
	void exportar(ActionEvent event) {
		Runnable run = new Runnable() {
			@Override
			public void run() {
				String export = carregarArquivo("Salvar arquivo");
				if (!"".equals(export)) {
					ArrayList<ArrayList> listaImpressao = new ArrayList<>();
					Integer[] colunasLenght = new Integer[] { 8, 26, 11, 11, 8, 14, 16, 14, 17, 9, 12, 19,
							30, 10, 10, 10, 30, 11, 11 };
					String[] cabecalho = new String[] { "Negocio", "Nome", "E-mail", "Fone", "Celular",
							"Data Inicio", "Data Limite", "Criador", "Etapa", "Status", "Atendente",
							"Origem", "Categoria", "Nivel", "Servicos/Produtos", "Descricao", "Honorario",
							"Servicos Contratados", "Motivo da Perda", "Detalhe da Perda", "Data da Perda",
							"Data Finalização" };
					listaImpressao.add(new ArrayList<>());

					for (String c : cabecalho) {
						listaImpressao.get(0).add(c);
					}
					try {
						loadFactory();
						List<NegocioProposta> lista = filtrar(null);
						for (int i = 1; i <= lista.size(); i++) {
							listaImpressao.add(new ArrayList());
							NegocioProposta n = lista.get(i-1);
							
							listaImpressao.get(i).add(n.getId());
							listaImpressao.get(i).add(n.getNome());
							
							String [] param = new String[] {"","",""};
							if(n.getPessoa()!=null) {
								param[0]=(n.getPessoa().getEmail());
								param[1]=(n.getPessoa().getTelefone());
								param[2]=(n.getPessoa().getCelular());
							}
							listaImpressao.get(i).add(param[0]);
							listaImpressao.get(i).add(param[1]);
							listaImpressao.get(i).add(param[2]);
							
							listaImpressao.get(i).add(sdf.format(n.getDataInicio().getTime()));
							listaImpressao.get(i).add(n.getDataFim() == null ? "" : sdf.format(n.getDataFim().getTime()));
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
									.add(nf.format(n.getHonorario()));
							double vlrServicos = 0.00;
							
							Iterator<ServicoContratado> iterator = n.getServicosContratados().iterator();
							while (iterator.hasNext()) {
								ServicoContratado sc = iterator.next();
								vlrServicos += sc.getValor().doubleValue();
							}
							listaImpressao.get(i).add(nf.format(vlrServicos));
							listaImpressao.get(i).add(n.getMotivoPerda());
							listaImpressao.get(i).add(n.getDetalhesPerda());
							listaImpressao.get(i).add(n.getDataPerda() == null ? "" : sdf.format(n.getDataPerda().getTime()));
							listaImpressao.get(i).add(n.getDataFinalizacao() == null ? "" : sdf.format(n.getDataFinalizacao().getTime()));
						}
						ExcelGenerico planilha = new ExcelGenerico(export + ".xls", listaImpressao,
								colunasLenght);
						planilha.gerarExcel();
						salvarLog(getManager(), "Negocio","Exportar","Exportou relatorio xls");
						Platform.runLater(() -> alert(AlertType.INFORMATION, "Sucesso", "Relatorio gerado com sucesso", "", null,false));
						Desktop.getDesktop().open(new File(export + ".xls"));
					} catch (Exception e1) {
						alert(AlertType.ERROR, "Erro", "", "Erro ao criar a planilha", e1, true);
					} finally {
						close();
					}
				}		
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
				new Thread(run).start();
			}
		} else {
			alert(AlertType.ERROR,"Erro","Parâmetros vazios","Nenhum registro foi encontrato").showAndWait();
		}
	}

	private List<NegocioProposta> filtrar(Paginacao paginacao) {
		propostas = new NegocioPropostaImpl(getManager());
		String dataFiltro = "criadoEm";
		String ordenacao = "id";
		Pair<List<NegocioProposta>, Paginacao> list = propostas.filtrar(paginacao, cbStatus.getValue(),
				cbEtapa.getValue(), cbCategoria.getValue(), cbNivel.getValue(), cbOrigem.getValue(),
				cbServico.getValue(), cbAtendente.getValue(), dataInicial.getValue(), dataFinal.getValue(), dataFiltro,
				ordenacao, txPesquisa.getText());
		if (paginacao != null) {
			tbPrincipal.getItems().clear();
			tbPrincipal.getItems().addAll(list.getKey());
		}
		return list.getKey();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			tabela();
			loadFactory();
			combos();
			filtrar(paginacao);
		} catch (Exception e) {
			alert(AlertType.ERROR, "Erro", "Erro ao carregar formulario", "Erro ao realizar consulta", e, true);
		} finally {
			close();
		}
	}

	@FXML
	void novo(ActionEvent event) {
		abrirCadastro(null);
	}

	@FXML
	void pesquisar(KeyEvent event) {
		try {
			loadFactory();
			filtrar(paginacao);
		} catch (Exception e) {
			alert(AlertType.ERROR, "Error", "", "Erro ao listar registros", e, true);
		} finally {
			close();
		}
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
		colunaEtapa.setPrefWidth(40);

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
					setText(sdf.format(item.getTime()));
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
					setText(sdf.format(item.getTime()));
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
					setText(sdf.format(item.getTime()));
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
					try {
						buttonTable(button, IconsEnum.BUTTON_EDIT);
					} catch (IOException e) {
					}
					button.setOnAction(event -> {
						abrirCadastro(tbPrincipal.getItems().get(getIndex()));
					});
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
					try {
						buttonTable(button, IconsEnum.BUTTON_REMOVE);
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
		tbPrincipal.getColumns().addAll(colunaId, colunaNome, colunaStatus, colunaEtapa, colunaOrigem, colunaServico,
				colunaCategoria, colunaNivel, colunaCriadoEm, colunaVencimento, colunaAtendente, colunaEditar);
	}
}
