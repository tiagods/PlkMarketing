package br.com.tiagods.controller;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.*;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.*;
import br.com.tiagods.repository.helpers.filters.ProtocoloEntradaFilter;
import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;

import javax.persistence.PersistenceException;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class ProtocoloEntradaPesquisaController extends UtilsController implements Initializable {
	@FXML
	private HBox pnCheckBox;

	@FXML
	private JFXComboBox<ProtocoloEntrada.StatusRecebimento> cbRecebimento;

	@FXML
	private JFXComboBox<ProtocoloEntrada.StatusDevolucao> cbDevolucao;

	@FXML
	private HBox pnCheckBox1;

	@FXML
	private JFXDatePicker dataInicial;

	@FXML
	private JFXDatePicker dataFinal;

	@FXML
	private JFXTextField txPesquisa;

	@FXML
	private JFXComboBox<Usuario> cbAtendente;

	@FXML
	private JFXComboBox<ProtocoloEntrada.Classificacao> cbClassificar;

	@FXML
	private TableView<ProtocoloEntrada> tbPrincipal;

	@FXML
	private JFXComboBox<Integer> cbLimite;

	@FXML
	private Pagination pagination;

	private Stage stage;
	private ProtocoloEntradaFilter filter;
	private UsuariosImpl usuarios;
	private Paginacao paginacao;
	private ProtocolosEntradasImpl protocolos;

	private List<Usuario> usuarioAtivos = new ArrayList<>();

	public ProtocoloEntradaPesquisaController(Stage stage, ProtocoloEntradaFilter filter) {
		this.stage = stage;
		this.filter = filter;
	}

	private void abrirCadastro(ProtocoloEntrada t) {
		try {
			loadFactory();
			protocolos = new ProtocolosEntradasImpl(getManager());
			if(t!=null){
				t = protocolos.findById(t.getId());
			}
			Stage stage = new Stage();
			FXMLLoader loader = loaderFxml(FXMLEnum.PROTOCOLO_ENTRADA_CADASTRO);
			loader.setController(new ProtocoloEntradaCadastroController(stage, t));
			initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
			stage.setOnHiding(event -> {
				try {
					loadFactory();
					filtrar(this.paginacao);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					close();
				}
			});
		} catch (IOException e) {
			alert(AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
					"Falha ao localizar o arquivo" + FXMLEnum.CONTATO_CADASTRO, e, true);
		} finally {
			close();
		}
	}

	void combos(){
		usuarios = new UsuariosImpl(getManager());
		usuarioAtivos = usuarios.listarAtivos();

		cbAtendente.getItems().add(new Usuario(-1L,"Todos"));
		cbAtendente.getItems().addAll(usuarioAtivos);
		cbLimite.getItems().addAll(limiteTabela);

		cbAtendente.getSelectionModel().selectFirst();
		cbRecebimento.getItems().addAll(ProtocoloEntrada.StatusRecebimento.values());
		cbDevolucao.getItems().addAll(ProtocoloEntrada.StatusDevolucao.values());
		cbClassificar.getItems().addAll(ProtocoloEntrada.Classificacao.values());

		cbLimite.getSelectionModel().selectFirst();
		cbRecebimento.getSelectionModel().selectFirst();
		cbDevolucao.getSelectionModel().selectFirst();
		cbClassificar.getSelectionModel().selectFirst();

		txPesquisa.setVisible(false);

		ChangeListener change = (ChangeListener<Object>) (observable, oldValue, newValue) -> {
            try {
            	if(cbClassificar.getValue().equals(ProtocoloEntrada.Classificacao.USUARIO)){
            		cbAtendente.setVisible(true);
            		txPesquisa.setVisible(false);
				}
				else{
					cbAtendente.setVisible(false);
					txPesquisa.setVisible(true);
				}
                loadFactory();
                paginacao = new Paginacao(cbLimite.getValue());
                filtrar(paginacao);
            } catch (Exception e) {
                alert(AlertType.ERROR, "Erro", "", "Erro ao realizar filtro", e, true);
            } finally {
                close();
            }
        };
		if(filter!=null){
			cbRecebimento.setValue(filter.getRecebimento());
			cbDevolucao.setValue(filter.getDevolucao());
			cbClassificar.setValue(filter.getClassificacao());
			cbAtendente.setValue(filter.getUsuario());
			dataInicial.setValue(filter.getDataInicial());
			dataFinal.setValue(filter.getDataFinal());
		}

		cbRecebimento.valueProperty().addListener(change);
		cbDevolucao.valueProperty().addListener(change);
		cbClassificar.valueProperty().addListener(change);
		cbAtendente.valueProperty().addListener(change);
		dataFinal.valueProperty().addListener(change);
		dataInicial.valueProperty().addListener(change);
		cbLimite.valueProperty().addListener(change);

		pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
			paginacao.setPaginaAtual(newValue.intValue());
			try {
				loadFactory();
				filtrar(paginacao);
			} catch (PersistenceException e) {
				alert(AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao realizar consulta", e, true);
			} finally {
				close();
			}
		});
		paginacao = new Paginacao(cbLimite.getValue());
	}
	@FXML
	void exportar(ActionEvent event) {
		alert(AlertType.INFORMATION,"Ajuda","Recurso em desenvolvimento","Este recurso ainda não foi liberado...aguarde...");
	}
	List<ProtocoloEntrada> filtrar(Paginacao paginacao){
		protocolos = new ProtocolosEntradasImpl(getManager());
		Pair<List<ProtocoloEntrada>, Paginacao> list = protocolos.filtrar(paginacao, cbRecebimento.getValue(),
				cbDevolucao.getValue(), cbClassificar.getValue(), cbAtendente.getValue(), dataInicial.getValue(),
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
		try {
			loadFactory();
			combos();
			filtrar(this.paginacao);
		} catch (PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao realizar consulta", e, true);
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
			filtrar(this.paginacao);
		} catch (Exception e) {
			alert(AlertType.ERROR, "Erro", "", "Erro ao realizar filtro", e, true);
		} finally {
			close();
		}
	}
	@FXML
	void sair(ActionEvent event) {
		this.stage.close();
	}
	void tabela(){
		TableColumn<ProtocoloEntrada, Number> colunaId = new TableColumn<>("*");
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbPrincipal.getColumns().add(colunaId);

		TableColumn<ProtocoloEntrada, Calendar> colunaData = new TableColumn<>("Data");
		colunaData.setCellValueFactory(new PropertyValueFactory<>("dataEntrada"));
		colunaData.setCellFactory((TableColumn<ProtocoloEntrada, Calendar> param) -> new TableCell<ProtocoloEntrada, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(sdfH.format(item.getTime()));
				}
			}
		});
		tbPrincipal.getColumns().add(colunaData);

		TableColumn<ProtocoloEntrada, Boolean> colunaStatus = new TableColumn<>("Status");
		colunaStatus.setCellValueFactory(new PropertyValueFactory<>("recebido"));
		colunaStatus.setCellFactory((TableColumn<ProtocoloEntrada, Boolean> param) -> new TableCell<ProtocoloEntrada, Boolean>() {
			final Label label = new Label();
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
				if (item == null) {
					setText(null);
					setStyle("");
					setGraphic(null);
				} else {
					ProtocoloEntrada p = tbPrincipal.getItems().get(getIndex());
					if (!p.isRecebido()) {
						label.setText("Não recebido");
						label.setStyle("-fx-background-color:red;-fx-text-fill: white;");
					} else if (p.isDevolver() && p.isDevolvido()) {
						if (p.getPrazo().before(Calendar.getInstance())) {
							label.setText("Devolução Atrasada");
							label.setStyle("-fx-background-color:red;-fx-text-fill: white;");
						} else {
							label.setText("No Prazo");
							label.setStyle("-fx-background-color:green;-fx-text-fill: white;");
						}
					} else {
						label.setText("Concluido");
						label.setStyle("-fx-background-color:green;-fx-text-fill: white;");
					}
					setGraphic(label);
				}
			}

		});

		tbPrincipal.getColumns().add(colunaStatus);
		TableColumn<ProtocoloEntrada, Calendar> colunaDataDevolucao = new TableColumn<>("Devolver em");
		colunaDataDevolucao.setCellValueFactory(new PropertyValueFactory<>("prazo"));
		colunaDataDevolucao.setCellFactory((TableColumn<ProtocoloEntrada, Calendar> param) -> new TableCell<ProtocoloEntrada, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(sdf.format(item.getTime()));
				}
			}
		});
		tbPrincipal.getColumns().add(colunaDataDevolucao);
		TableColumn<ProtocoloEntrada, Cliente> colunaApelido = new TableColumn<>("Cliente");
		colunaApelido.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaApelido.setCellFactory((TableColumn<ProtocoloEntrada, Cliente> param) -> new TableCell<ProtocoloEntrada, Cliente>() {
			@Override
			protected void updateItem(Cliente item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(String.valueOf(item.getId()));
					String[] em = item.getNome().split(" ");
					if (em.length >= 1) {
						setText(item.getId()+"-"+em[0]);
					} else {
						setText(item.getId()+"-"+item);
					}
				}
			}
		});
		tbPrincipal.getColumns().add(colunaApelido);

		TableColumn<ProtocoloEntrada, String> colunaQuemRecebeu = new TableColumn<>("De");
		colunaQuemRecebeu.setCellValueFactory(new PropertyValueFactory<>("quemEntregou"));
		tbPrincipal.getColumns().add(colunaQuemRecebeu);

		TableColumn<ProtocoloEntrada, Usuario> colunaParaQuem = new TableColumn<>("Destino");
		colunaParaQuem.setCellValueFactory(new PropertyValueFactory<>("paraQuem"));
		tbPrincipal.getColumns().add(colunaParaQuem);

		TableColumn<ProtocoloEntrada, Usuario> colunaRecebedor = new TableColumn<>("Recebido por");
		colunaRecebedor.setCellValueFactory(new PropertyValueFactory<>("quemRecebeu"));
		tbPrincipal.getColumns().add(colunaRecebedor);

		TableColumn<ProtocoloEntrada, Calendar> colunaDataRecebimento = new TableColumn<>("Recebido em");
		colunaDataRecebimento.setCellValueFactory(new PropertyValueFactory<>("dataRecebimento"));
		colunaDataRecebimento.setCellFactory((TableColumn<ProtocoloEntrada, Calendar> param) -> new TableCell<ProtocoloEntrada, Calendar>() {
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setText(null);
					setStyle("");
				} else {
					setText(sdf.format(item.getTime()));
				}
			}
		});
		tbPrincipal.getColumns().add(colunaDataRecebimento);

		TableColumn<ProtocoloEntrada, Number> colunaEditar = new TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<ProtocoloEntrada, Number>() {
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
		tbPrincipal.getColumns().add(colunaEditar);

		TableColumn actionEdit = new TableColumn("Alterações");
		actionEdit.setCellValueFactory(new PropertyValueFactory<>("id"));
		Callback<TableColumn<ProtocoloEntrada, Long>, TableCell<ProtocoloEntrada, Long>> editFactory
				= (final TableColumn<ProtocoloEntrada, Long> param) -> {
			final TableCell<ProtocoloEntrada, Long> cell = new TableCell<ProtocoloEntrada, Long>() {
				private JFXButton button = new JFXButton("Encaminhar");
				@Override
				public void updateItem(Long item, boolean empty) {
					super.updateItem(item, empty);
					setAlignment(Pos.CENTER);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						button.getStyleClass().add("btDefaultText");
						try {
							buttonTable(button, IconsEnum.BUTTON_RETUITAR);
							setGraphic(button);
						} catch (IOException e) {}
						ProtocoloEntrada prot = tbPrincipal.getItems().get(getIndex());
						if(prot.isRecebido()
								&& prot.isDevolver()
								&& prot.isDevolvido()
								|| prot.isRecebido()
								&& !prot.isDevolver()) {
							button.setDisable(true);
							button.setText("Baixado");
						}
						else if (!prot.isRecebido()) {
							button.setDisable(false);
							button.setText("Encaminhar");
						} else if (prot.isRecebido()
								&& prot.isDevolver()
								&& !prot.isDevolvido()) {
							button.setDisable(false);
							button.setText("Novo Prazo");
						}
						button.setOnAction((ActionEvent event) -> {
							if (button.getText().equals("Encaminhar")) {
								ProtocoloEntrada p = tbPrincipal.getItems().get(getIndex());
								ChoiceDialog<Usuario> dialog = new ChoiceDialog<>();
								dialog.getItems().addAll(usuarioAtivos);
								dialog.getItems().remove(UsuarioLogado.getInstance().getUsuario());//sumir meu nome da lista
								dialog.setSelectedItem(usuarioAtivos.get(0));//inserir condição
								dialog.setTitle("Alterar responsável");
								dialog.setHeaderText("Por favor escolha um novo destino para esse protocolo");
								dialog.setContentText("Escolha pelo nome:");
								Optional<Usuario> result = dialog.showAndWait();
								if (result.isPresent()) {
									Usuario u = result.get();
									p.setParaQuem(u);
									try {
										loadFactory();
										protocolos = new ProtocolosEntradasImpl(getManager());
										protocolos.save(p);
										alert(AlertType.INFORMATION,"Sucesso","","Atualizado com sucesso");
										filtrar(paginacao);
									}catch (Exception e) {
										alert(AlertType.ERROR,"Erro","Erro ao Salvar","Não foi possivel salvar",e,true);
									}finally {
										close();
									}
								}
							} else if (button.getText().equals("Novo Prazo")) {
								ProtocoloEntrada p = prot;
								Optional<Pair<LocalDate, String>> result2 = AlterarPrazo(p.getPrazo());
								if (result2.isPresent()) {
									if (result2.get().getKey() != null) {
										LocalDate data2 = result2.get().getKey();
										p.setAdiado(true);
										p.setDevolvido(true);
										Calendar calendar = Calendar.getInstance();
										calendar.set(data2.getYear(), data2.getMonthValue() - 1, data2.getDayOfMonth());
										p.setPrazo(calendar);
									} else {
										p.setDevolver(false);
										p.setPrazo(null);
									}
									p.setMotivo(result2.get().getValue());
									try {
										loadFactory();
										protocolos = new ProtocolosEntradasImpl(getManager());
										protocolos.save(p);
										alert(AlertType.INFORMATION,"Sucesso","","Atualizado com sucesso");
										filtrar(paginacao);
									}catch (Exception e) {
										alert(AlertType.ERROR,"Erro","Erro ao Salvar","Não foi possivel salvar",e,true);
									}finally {
										close();
									}

								}
							}
						});
						setGraphic(button);
						setText(null);
					}
				}
			};
			return cell;
		};

		actionEdit.setCellFactory(editFactory);
		actionEdit.setPrefWidth(100);
		tbPrincipal.getColumns().add(actionEdit);

		TableColumn actionCol = new TableColumn("Conclusão");
		actionCol.setCellValueFactory(new PropertyValueFactory<>(""));
		Callback<TableColumn<ProtocoloEntrada, String>, TableCell<ProtocoloEntrada, String>> cellFactory
				= (final TableColumn<ProtocoloEntrada, String> param) -> {
			final TableCell<ProtocoloEntrada, String> cell = new TableCell<ProtocoloEntrada, String>() {
				final JFXButton btn = new JFXButton("Validar");

				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					btn.getStyleClass().add("btJFXDefault");
					setAlignment(Pos.CENTER);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						ProtocoloEntrada prot = tbPrincipal.getItems().get(getIndex());
						if (!prot.isRecebido()) {
							btn.setDisable(false);
							btn.setText("Baixar");
						} else if (prot.isRecebido()
								&& prot.isDevolver()
								&& !prot.isDevolvido()) {
							btn.setText("Devolver");
							btn.setDisable(false);
						} else {
							btn.setText("Concluido");
							btn.setDisable(true);
						}
						btn.setOnAction((ActionEvent event) -> {
							if (btn.getText().equals("Baixar")) {
								ProtocoloEntrada p = tbPrincipal.getItems().get(getIndex());
								TextInputDialog dialog = new TextInputDialog("");
								dialog.setTitle("Confirmar recebimento...");
								dialog.setHeaderText(null);
								dialog.setContentText("Informe uma observação (opcional):");
								Optional<String> result = dialog.showAndWait();
								if (result.isPresent()) {
									//baixar o documento
									Optional<Pair<LocalDate, Boolean>> result2 = Baixar();
									if (result2.isPresent()) {
										p.setObservacao(result.get());
										p.setQuemRecebeu(UsuarioLogado.getInstance().getUsuario());//receber o usuario logado
										if (result2.get().getValue() == true) {
											LocalDate data2 = result2.get().getKey();
											p.setDevolver(true);
//                                            p.setDevolverAte(new Date(data2.getYear(), data2.getMonthValue() - 1, data2.getDayOfMonth()));
											p.setPrazo(GregorianCalendar.from(data2.atStartOfDay(ZoneId.systemDefault())));
										}
										p.setRecebido(true);
										p.setDataRecebimento(Calendar.getInstance());
										try {
											loadFactory();
											protocolos = new ProtocolosEntradasImpl(getManager());
											protocolos.save(p);
											alert(AlertType.INFORMATION,"Sucesso","","Atualizado com sucesso");
											filtrar(paginacao);
										}catch (Exception e) {
											alert(AlertType.ERROR,"Erro","Erro ao Salvar","Não foi possivel salvar",e,true);
										}finally {
											close();
										}
									}
								}
							} else if (btn.getText().equals("Devolver")) {
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Confirme o dialogo para finalizar a sua ação...");
								alert.setHeaderText("Você deseja finalizar ou concluir gerando um protocolo de saida");
								alert.setContentText("Por favor escolha uma opção!");
								ButtonType btnConcluir = new ButtonType("Concluir");
								//ButtonType btnGerar = new ButtonType("Protocolo de Saída");
								alert.getButtonTypes().setAll(btnConcluir, ButtonType.CANCEL);
								ProtocoloEntrada p = tbPrincipal.getItems().get(getIndex());
								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() != ButtonType.CANCEL) {
									p.setDevolvido(true);
									try {
										loadFactory();
										protocolos = new ProtocolosEntradasImpl(getManager());
										protocolos.save(p);
										alert(AlertType.INFORMATION,"Sucesso","","Atualizado com sucesso");
										filtrar(paginacao);
									}catch (Exception e) {
										alert(AlertType.ERROR,"Erro","Erro ao Salvar","Não foi possivel salvar",e,true);
									}finally {
										close();
									}
								}
							}
						});
						setGraphic(btn);
						setText(null);
					}
				}
			};
			return cell;
		};
		actionCol.setCellFactory(cellFactory);
		tbPrincipal.getColumns().add(actionCol);
	}
	public Optional<Pair<LocalDate, Boolean>> Baixar() {
		Dialog<Pair<LocalDate, Boolean>> dialog = new Dialog<>();
		dialog.setTitle("Formulário de Validação");
		dialog.setHeaderText("Os documentos desse protocolo serão devolvidos para o cliente?");
		ButtonType botaoAplicar = new ButtonType("Aplicar", ButtonBar.ButtonData.OK_DONE);
		ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(botaoAplicar, botaoCancelar);
		AnchorPane pane = new AnchorPane();
		pane.setPrefSize(600, 400);

		JFXRadioButton radioYes = new JFXRadioButton("Sim");
		double distanciaEsquerda = 20;
		Label lb1 = new Label("Será devolvido?");
		lb1.setLayoutX(distanciaEsquerda);
		lb1.setLayoutY(50);
		radioYes.setLayoutX(distanciaEsquerda);
		radioYes.setLayoutY(70);
		JFXRadioButton radioNo = new JFXRadioButton("Não");
		radioNo.setLayoutX(100);
		radioNo.setLayoutY(70);
		ToggleGroup toggle = new ToggleGroup();
		toggle.getToggles().addAll(radioYes, radioNo);

		Label label = new Label("Será devolvido em: ");
		label.setLayoutX(distanciaEsquerda);
		label.setLayoutY(130);
		JFXDatePicker data = new JFXDatePicker();
		data.setLayoutX(distanciaEsquerda);
		data.setLayoutY(150);
		data.setEditable(false);
		LocalDate ld = LocalDate.now();
		LocalDate ld2 = ld.plusMonths(1);
		data.setValue(ld2);
		data.setEditable(false);
		data.setDayCellFactory((DatePicker param) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
				LocalDate date = LocalDate.now();
				LocalDate dataLimite = date.plusDays(365);
				if (item.isAfter(dataLimite)) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
					setTooltip(new Tooltip("Intervalo muito longo, diminua o prazo"));
				} else if (item.isBefore(date)) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
					setTooltip(new Tooltip("Data minima deve ser hoje! " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
				}
			}
		});
		radioNo.setSelected(true);

		JFXTextArea areaText = new JFXTextArea();
		areaText.setEditable(false);
		areaText.setText("*Uma vez informado a data de devolução, você deverá registrar um Protocolo de Saída pelo botão PROTOCOLAR DEVOLUÇÃO até a data de vencimento informada,\n"
				+ "*2 Alertas serão enviados antes do vencimento, se o prazo for excedido, informe um novo prazo atraves do botão ADIAR DEVOLUÇÃO.\n"
				+ "*Os protocolos de saída são opcionais, para visualizá-los clique no botão Protocolo de Saída no menu principal.");
		areaText.setWrapText(true);
		areaText.setLayoutX(distanciaEsquerda);
		areaText.setLayoutY(200);
		Node botaoAplicarAction = dialog.getDialogPane().lookupButton(botaoAplicar);
		pane.getChildren().addAll(lb1, radioYes, radioNo, label, data, areaText);
		dialog.getDialogPane().setContent(pane);
		radioYes.addEventHandler(ActionEvent.ACTION, (ActionEvent event1) -> {
			if (radioYes.isSelected()) {
				data.setDisable(false);
				botaoAplicarAction.setDisable(data.getValue() == null);
			} else {
				data.setDisable(true);
				botaoAplicarAction.setDisable(false);
			}
		});
		radioNo.addEventHandler(ActionEvent.ACTION, (ActionEvent event1) -> {
			if (radioYes.isSelected()) {
				data.setDisable(false);
				botaoAplicarAction.setDisable(data.getValue() == null);
			} else {
				data.setDisable(true);
				botaoAplicarAction.setDisable(false);
			}
		});

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == botaoAplicar && radioYes.isSelected()) {
				return new Pair(data.getValue(), true);
			} else if (dialogButton == botaoAplicar && radioNo.isSelected()) {
				return new Pair(null, false);
			}
			return null;
		});
		Optional<Pair<LocalDate, Boolean>> result = dialog.showAndWait();
		return result;
	}

	public Optional<Pair<LocalDate, String>> AlterarPrazo(Calendar newDate) {
		Dialog<Pair<LocalDate, String>> dialog = new Dialog<>();
		dialog.setTitle("Formulário de Validação");
		dialog.setHeaderText("Altere o prazo para devolução dos documentos do protocolo ou cancele.");
		ButtonType botaoAplicar = new ButtonType("Aplicar", ButtonBar.ButtonData.OK_DONE);
		ButtonType botaoCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(botaoAplicar, botaoCancelar);
		AnchorPane pane = new AnchorPane();
		pane.setPrefSize(600, 400);

		double distanciaEsquerda = 20;
		Label lb1 = new Label("Será devolvido?");
		lb1.setLayoutX(distanciaEsquerda);
		lb1.setLayoutY(50);
		JFXRadioButton radioYes = new JFXRadioButton("Sim");
		radioYes.setSelected(true);
		radioYes.setLayoutX(distanciaEsquerda);
		radioYes.setLayoutY(70);
		JFXRadioButton radioNo = new JFXRadioButton("Não");
		radioNo.setLayoutX(100);
		radioNo.setLayoutY(70);
		ToggleGroup toggle = new ToggleGroup();
		toggle.getToggles().addAll(radioYes, radioNo);

		Label label = new Label("Será devolvido em: ");
		label.setLayoutX(distanciaEsquerda);
		label.setLayoutY(130);
		JFXDatePicker dataPicker = new JFXDatePicker();
		dataPicker.setLayoutX(distanciaEsquerda);
		dataPicker.setLayoutY(150);
		dataPicker.setEditable(false);
		GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
		calendar.setTime(newDate.getTime());
		LocalDate ld2 = calendar.toZonedDateTime().toLocalDate();
		dataPicker.setValue(ld2);
		dataPicker.setDayCellFactory((DatePicker param) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
				LocalDate date = LocalDate.now();
				LocalDate dataLimite = date.plusDays(365);
				if (item.isAfter(dataLimite)) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
					setTooltip(new Tooltip("Intervalo muito longo, diminua o prazo"));
				} else if (item.isBefore(date)) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
					setTooltip(new Tooltip("Data minima deve ser hoje! " + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
				}
			}
		});
		JFXTextArea areaText = new JFXTextArea();
		areaText.setPromptText("Descreva um resumo relatando o motivo desse novo prazo!");
		areaText.setWrapText(true);
		areaText.setLayoutX(distanciaEsquerda);
		areaText.setLayoutY(200);
		pane.getChildren().addAll(lb1, radioYes, radioNo, label, dataPicker, areaText);
		Node botaoAplicarAction = dialog.getDialogPane().lookupButton(botaoAplicar);
		botaoAplicarAction.setDisable(true);
		areaText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			botaoAplicarAction.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(pane);
		radioYes.addEventHandler(ActionEvent.ACTION, (ActionEvent event1) -> {
			if (radioYes.isSelected()) {
				dataPicker.setDisable(false);
				areaText.setPromptText("Descreva um resumo relatando o motivo desse novo prazo!");
			} else {
				dataPicker.setDisable(true);
			}
		});
		radioNo.addEventHandler(ActionEvent.ACTION, (ActionEvent event1) -> {
			if (radioYes.isSelected()) {
				areaText.setPromptText("Descreva um resumo relatando o motivo desse novo prazo!");
				dataPicker.setDisable(false);
			} else {
				dataPicker.setDisable(true);
			}
		});
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == botaoAplicar && radioYes.isSelected()) {
				return new Pair<>(dataPicker.getValue(), areaText.getText());
			} else if (dialogButton == botaoAplicar && radioNo.isSelected()) {
				return new Pair<>(null, areaText.getText());
			}
			return null;
		});
		Optional<Pair<LocalDate, String>> result = dialog.showAndWait();
//            if(result.isPresent()){
//                result.ifPresent(u -> {
//                    System.out.println("is pressed ok "+u.getKey()+" "+u.getValue());});
//            }
		return result;
	}
}

