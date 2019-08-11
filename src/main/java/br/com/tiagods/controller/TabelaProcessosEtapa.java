package br.com.tiagods.controller;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.implantacao.*;
import br.com.tiagods.repository.helpers.ImplantacaoProcessoEtapasImpl;
import br.com.tiagods.services.SendEmail;
import br.com.tiagods.util.alerta.AlertaImplantacao;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class TabelaProcessosEtapa extends UtilsController {

    private Logger logger = LoggerFactory.getLogger(TabelaProcessosEtapa.class);

    private TableView<ImplantacaoProcessoEtapa> tbPrincipal;

    private ImplantacaoProcessoEtapasImpl etapas;

    public TabelaProcessosEtapa(TableView<ImplantacaoProcessoEtapa> tbPrincipal) {
        this.tbPrincipal = tbPrincipal;
    }

    private void cadastrarEtapas(ImplantacaoProcessoEtapa etapa, int index, boolean editar) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.IMPLANTACAO_ETAPA_STATUS);
            ImplantacaoProcessoEtapa etapa2 = recarregar(etapa);
            ImplantacaoEtapaStatusController controller = new ImplantacaoEtapaStatusController(etapa2, stage, editar);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                ImplantacaoProcessoEtapa newEtapa = recarregar(etapa2);
                if (newEtapa != null) tbPrincipal.getItems().set(index, newEtapa);
            });
        } catch (IOException ex) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro", "Falha ao localizar o arquivo " + FXMLEnum.IMPLANTACAO_ETAPA_STATUS, ex, true);
        }
    }

    private ImplantacaoProcessoEtapa recarregar(ImplantacaoProcessoEtapa etapa) {
        try {
            loadFactory();
            etapas = new ImplantacaoProcessoEtapasImpl(getManager());
            etapa = etapas.findById(etapa.getId());
            return etapa;
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao carregar os registros", "Ocorreu um erro ao carregar o registro", e, true);
            return null;
        } finally {
            close();
        }
    }

    private void notificarProximaEtapa(ImplantacaoProcessoEtapa pe) {
        Usuario usuario = UsuarioLogado.getInstance().getUsuario();
        String email = pe.getEtapa().getDepartamento().getEmail().trim();

        Calendar prazo = pe.getDataAtualizacao();
        if(prazo== null) prazo = Calendar.getInstance();
        prazo.add(Calendar.DAY_OF_MONTH, pe.getEtapa().getTempo());

        if (!email.equals("")) {
            SendEmail mail = new SendEmail();
            AlertaImplantacao alertaImplantacao = new AlertaImplantacao();
            List<ImplantacaoProcessoEtapa> list = etapas.filtrar(null, pe.getProcesso(), pe.getEtapa().getAtividade(), null, null);
            List<ImplantacaoProcessoEtapaStatus> status = alertaImplantacao.organizarLista(list);

            List<String> cabecalho = Arrays.asList("Sistema Controle de Processos/Implanta&ccedil;&atilde;o",
                    "Ol&aacute;;",
                    usuario!=null?usuario.getNome():""+" acabou de concluir uma Etapa de Implanta&ccedil;&atilde;o",
                    "Voc&ecirc; foi designado para concluir a proxima Etapa, abaixo uma descri&ccedil;&atilde;o:");


            List<String> rodape = Arrays.asList("N&atilde;o se esque&ccedil;a de concluir a tarefa at&eacute; "+sdf.format(prazo.getTime()));

            Map<ImplantacaoProcessoEtapa, List<ImplantacaoProcessoEtapaStatus>> map = new HashMap<>();
            map.put(pe, status);
            String value = alertaImplantacao.montarMensagem(map,cabecalho,rodape);
            mail.enviaAlerta("documentos@prolinkcontabil.com.br", "Implantacao \\ Prolink Contabil",
                    new ArrayList<>(Arrays.asList(email.split(";"))), pe.getProcesso().getCliente().getIdFormatado() + " - Processo de Implantação, Nova Etapa em Aberto",
                    value, true);
        }

    }

    private boolean salvarEConcluir(String descricao, ImplantacaoProcessoEtapa ip, int index) {
        try {
            loadFactory();
            etapas = new ImplantacaoProcessoEtapasImpl(getManager());

            ip = etapas.findById(ip.getId());

            ImplantacaoProcessoEtapaStatus status = new ImplantacaoProcessoEtapaStatus();
            status.setDescricao(descricao);
            status.setFinalizado(true);
            status.setProcessoEtapa(ip);

            Set<ImplantacaoProcessoEtapaStatus> statusSet = ip.getHistorico();
            statusSet.add(status);

            ip.setDataAtualizacao(Calendar.getInstance());
            ip.setStatus(ImplantacaoProcessoEtapa.Status.CONCLUIDO);
            ip = etapas.save(ip);

            tbPrincipal.getItems().set(index, ip);

            // metodo de atualização da proxima etapa, remocao por ser menos funcional

            ImplantacaoEtapa.Etapa etapa = ip.getEtapa().getEtapa();
            Optional<ImplantacaoEtapa.Etapa> re = Arrays.asList(ImplantacaoEtapa.Etapa.values()).stream().filter(c -> c.getValor() == etapa.getValor() + 1).findAny();
            if (re.isPresent()) {
                List<ImplantacaoProcessoEtapa> list = etapas.filtrar(null, ip.getProcesso(), ip.getEtapa().getAtividade(), re.get(), ImplantacaoProcessoEtapa.Status.AGUARDANDO_ANTERIOR);
                if (list.size() == 1) {
                    logger.debug("Preparanto notificacao de etapa");
                    ImplantacaoProcessoEtapa processoEtapa = list.get(0);
                    processoEtapa.setStatus(ImplantacaoProcessoEtapa.Status.ABERTO);
                    processoEtapa.setDataLiberacao(Calendar.getInstance());
                    processoEtapa.setDataAtualizacao(Calendar.getInstance());
                    final ImplantacaoProcessoEtapa pe = etapas.save(processoEtapa);

                    notificarProximaEtapa(pe);

                    Optional<ImplantacaoProcessoEtapa> result = tbPrincipal.getItems().stream().filter(c -> c.getId() == pe.getId()).findAny();
                    if (result.isPresent()) {
                        int in = tbPrincipal.getItems().indexOf(result.get());
                        tbPrincipal.getItems().set(in, pe);
                    }
                } else if (list.size() > 1) {
                    alert(Alert.AlertType.ERROR,
                            "Erro",
                            "",
                            "Foram encontradas mais etapas para o mesmo processo e atividade! Informe o erro para o administrador do sistema",
                            new Exception("Foram encontradas mais etapas para o mesmo processo e atividade! Etapa=" + re.get() + ";processo=" + ip.getProcesso().getId() + ";atividade=" + ip.getEtapa().getAtividade()), true);
                }
            }
            else logger.debug("Is empty");
            tbPrincipal.refresh();
            alert(Alert.AlertType.INFORMATION, "Sucesso", "", "Salvo com sucesso!", null, false);
            return true;
        } catch (Exception e) {
            alert(Alert.AlertType.ERROR, "Erro", "", "Falha ao tentar salvar o registro!", e, true);
            return false;
        } finally {
            close();
        }
    }

    void tabela() {
        final String etapa = "etapa";

        tbPrincipal.getColumns().clear();

        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoProcesso> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("processo"));

        TableColumn<ImplantacaoProcessoEtapa, Calendar> colunaData = new TableColumn<>("Data Liberaçao");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataLiberacao"));
        colunaData.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, Calendar> param) -> new TableCell<ImplantacaoProcessoEtapa, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                } else {
                    setText(sdf.format(item.getTime()));
                }
            }
        });

        TableColumn<ImplantacaoProcessoEtapa, Number> colunaPrazo = new TableColumn<>("Prazo");
        colunaPrazo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaPrazo.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, Number> param) -> new TableCell<ImplantacaoProcessoEtapa, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                final JFXTextArea area = new JFXTextArea();
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    ImplantacaoProcessoEtapa processoEtapa = tbPrincipal.getItems().get(getIndex());
                    area.setText(processoEtapa.getStatusVencimento());
                    setGraphic(area);

                    if (processoEtapa.getVencido().equals(ImplantacaoProcessoEtapa.Vencido.VENCIDO))
                        setStyle("-fx-background-color: red; -fx-text-fill: white;");
                    else if (processoEtapa.getVencido().equals(ImplantacaoProcessoEtapa.Vencido.NO_PRAZO))
                        setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    else setStyle("");
                }
            }
        });
        colunaPrazo.setPrefWidth(100);

        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaDepartamento = new TableColumn<>("Responsavel");
        colunaDepartamento.setCellValueFactory(new PropertyValueFactory<>(etapa));
        colunaDepartamento.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> param) -> new TableCell<ImplantacaoProcessoEtapa, ImplantacaoEtapa>() {
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                } else {
                    setText(item.getDepartamento().toString());
                }
            }
        });

        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaEtapa = new TableColumn<>("Etapa");
        colunaEtapa.setCellValueFactory(new PropertyValueFactory<>(etapa));
        colunaEtapa.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> param) -> new TableCell<ImplantacaoProcessoEtapa, ImplantacaoEtapa>() {
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                } else {
                    setText(item.getEtapa().toString());
                }
            }
        });
        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaAtividade = new TableColumn<>("Atividade");
        colunaAtividade.setCellValueFactory(new PropertyValueFactory<>(etapa));
        colunaAtividade.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> param) -> new TableCell<ImplantacaoProcessoEtapa, ImplantacaoEtapa>() {
            final JFXTextArea area = new JFXTextArea();

            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    area.setText(item.getAtividade().toString());
                    setGraphic(area);
                }
            }
        });
        colunaAtividade.setPrefWidth(150);
        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaDescricao = new TableColumn<>("O que devo fazer?");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>(etapa));
        colunaDescricao.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> param) -> new TableCell<ImplantacaoProcessoEtapa, ImplantacaoEtapa>() {
            final JFXTextArea area = new JFXTextArea();

            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    area.setText(item.getDescricao());
                    setGraphic(area);
                }
            }
        });
        colunaDescricao.setPrefWidth(150);

        TableColumn<ImplantacaoProcessoEtapa, Number> colunaEditar = new TableColumn<>("Historico");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa, Number>() {
            final JFXButton button = new JFXButton();

            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    ImplantacaoProcessoEtapa ip = tbPrincipal.getItems().get(getIndex());
                    button.getStyleClass().add("btDefault");
                    setGraphic(button);
                    try {
                        if (ip.getStatus().equals(ImplantacaoProcessoEtapa.Status.CONCLUIDO))
                            buttonTable(button, IconsEnum.BUTTON_VIEW);
                        else if (ip.getStatus().equals(ImplantacaoProcessoEtapa.Status.ABERTO))
                            buttonTable(button, IconsEnum.BUTTON_EDIT);
                        else setGraphic(null);
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                    final Tooltip tooltip = new Tooltip("Clique para criar uma nota!");
                    button.setTooltip(tooltip);
                    button.setOnAction(event -> cadastrarEtapas(ip, getIndex(), ip.getStatus().equals(ImplantacaoProcessoEtapa.Status.ABERTO)));
                }
            }
        });
        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoProcessoEtapa.Status> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colunaStatus.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa, ImplantacaoProcessoEtapa.Status>() {
            JFXButton button = new JFXButton();

            @Override
            protected void updateItem(ImplantacaoProcessoEtapa.Status item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    ImplantacaoProcessoEtapa ip = tbPrincipal.getItems().get(getIndex());
                    button.setDisable(item.equals(ImplantacaoProcessoEtapa.Status.CONCLUIDO) ||
                            item.equals(ImplantacaoProcessoEtapa.Status.AGUARDANDO_ANTERIOR));
                    button.getStyleClass().add("");
                    try {
                        buttonTable(button, item.getIcon());
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                    final Tooltip tooltip = new Tooltip("Clique para encerrar essa etapa");
                    button.setText(item.equals(ImplantacaoProcessoEtapa.Status.ABERTO) ? "Baixar" : item.toString());
                    button.setTooltip(tooltip);
                    button.setOnAction(event -> {
                        TextInputDialog dialog = new TextInputDialog("");
                        dialog.setTitle("Entrada de texto");
                        dialog.setHeaderText("Entre com uma observacao para finalizar essa etapa");
                        dialog.setContentText("Informe uma observacao:");
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            salvarEConcluir(result.get(), ip, getIndex());
                        }
                    });
                    setGraphic(button);
                }
            }
        });
        colunaStatus.setPrefWidth(200);
        tbPrincipal.setFixedCellSize(70);
        tbPrincipal.getColumns().addAll(colunaId, colunaData, colunaPrazo, colunaDepartamento, colunaEtapa, colunaAtividade, colunaDescricao, colunaEditar, colunaStatus);
    }
}
