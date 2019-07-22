package br.com.tiagods.controller;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.implantacao.*;
import br.com.tiagods.repository.helpers.ImplantacaoProcessoEtapasImpl;
import br.com.tiagods.util.SendEmail;
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
import java.util.stream.Collectors;

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
        String email = pe.getEtapa().getDepartamento().getEmail().trim();
        Usuario usuario = UsuarioLogado.getInstance().getUsuario();
        if (!email.equals("")) {
            StringBuilder builder = new StringBuilder();
            builder.append("Sistema Controle de Processos/Implantação \n\n")
                    .append("Olá,\n\n")
                    .append(usuario.getNome())
                    .append(" acabou de concluir uma Etapa de Implantação do cliente ")
                    .append(pe.getProcesso().getCliente().toString())
                    .append("\n\n")
                    .append("Você foi designado para concluir a proxima Etapa, abaixo uma descricao:\n\n")
                    .append("Data Liberação: ").append(pe.getDataLiberacao() != null ? sdf.format(pe.getDataLiberacao().getTime()) : "").append("\n")
                    .append("Cliente Apelido:").append(pe.getProcesso().getCliente().getIdFormatado()).append("\n")
                    .append("Cliente Nome:").append(pe.getProcesso().getCliente().getNome()).append("\n")
                    .append("Depto Responsavel:").append(pe.getEtapa().getDepartamento()).append("\n")
                    .append("Etapa:").append(pe.getEtapa().getEtapa()).append("\n")
                    .append("Atividade:").append(pe.getEtapa().getAtividade().getNome()).append("\n")
                    .append("O que fazer? (Sua tarefa) :").append(pe.getEtapa().getDescricao()).append("\n\n\n")
                    .append("Historico das atividades anteriores :\n");

            //pegando sets dos objetos e reunindo em um unico list
            List<ImplantacaoProcessoEtapa> list = etapas.filtrar(null, pe.getProcesso(), pe.getEtapa().getAtividade(), null, null);
            List<ImplantacaoProcessoEtapaStatus> result = list.stream()
                    .map(ImplantacaoProcessoEtapa::getHistorico)
                    .flatMap(c -> c.stream()).collect(Collectors.toList());


            Collections.sort(result, Comparator.comparing(ImplantacaoProcessoEtapaStatus::getCriadoEm));

            result.forEach(c -> {
                builder.append("Data: ").append(c.getCriadoEm() == null ? "" : sdf.format(c.getCriadoEm().getTime())).append("\t\t")
                        .append("Autor: ").append(c.getCriadoPor().getLogin()).append("\t\t")
                        .append("Descricao: ").append(c.getDescricao()).append("\n");

            });

            Calendar data = pe.getDataAtualizacao();
            if (data != null) {
                data.add(Calendar.DAY_OF_MONTH, pe.getEtapa().getTempo());
                builder.append("\n")
                        .append("Não se esqueça de concluir a tarefa até " + sdf.format(data.getTime()));
            }
            SendEmail mail = new SendEmail();
            mail.enviaAlerta("documentos@prolinkcontabil.com.br", "Implantacao \\ Prolink Contabil",
                    new ArrayList<>(Arrays.asList(email.split(";"))), pe.getProcesso().getCliente().getIdFormatado() + " - Processo de Implantação, Nova Etapa em Aberto",
                    builder.toString(), false);
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
