package br.com.tiagods.controller;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.controller.ProtocoloEntradaPesquisaController;
import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.Cliente;
import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.controller.ProtocoloEntradaCadastroController;
import br.com.tiagods.repository.ProtocolosEntradas;
import br.com.tiagods.repository.interfaces.Paginacao;
import br.com.tiagods.repository.helpers.ProtocolosEntradasImpl;
import br.com.tiagods.repository.helpers.filters.ProtocoloEntradaFilter;
import br.com.tiagods.util.DateUtil;
import br.com.tiagods.util.JavaFxUtil;
import br.com.tiagods.util.alerta.AlertaProtocolo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static br.com.tiagods.util.JavaFxUtil.alert;
import static br.com.tiagods.util.JavaFxUtil.buttonTable;

@Component
public class TabelaProtocoloEntrada {

    private TableView<ProtocoloEntrada> tbPrincipal;
    private JFXRadioButton rbAdministrativo, rbComum;
    private List<Usuario> usuarioAtivos;

    @Autowired
    private ProtocolosEntradas protocolos;
    @Autowired
    private ProtocoloEntradaPesquisaController controller;
    @Autowired
    ProtocoloEntradaCadastroController protocoloEntradaCadastroController;
    @Autowired
    StageManager stageManager;
    @Autowired
    AlertaProtocolo alerta;

    private Paginacao paginacao;

    public void setPaginacao(Paginacao paginacao) {
        this.paginacao = paginacao;
    }
    public Paginacao getPaginacao() {
        return paginacao;
    }
    public List<Usuario> getUsuarioAtivos() {
        return usuarioAtivos;
    }
    public void setUsuarioAtivos(List<Usuario> usuarioAtivos) {
        this.usuarioAtivos = usuarioAtivos;
    }

    public void setPropriedades(TableView tbPrincipal, JFXRadioButton rbAdministrativo, JFXRadioButton rbComum){
        this.tbPrincipal = tbPrincipal;
        this.rbAdministrativo= rbAdministrativo;
        this.rbComum = rbComum;
    }

    public void abrirCadastro(ProtocoloEntrada t) {
        if(t!=null){
            Optional<ProtocoloEntrada> result = protocolos.findById(t.getId());
            if(result.isPresent()) {
                t = result.get();
            }
        }
        Stage stage = stageManager.switchScene(FxmlView.PROTOCOLO_ENTRADA_CADASTRO, true);
        protocoloEntradaCadastroController.setPropriedades(stage, t);
        stage.setOnHiding(event -> {
            try {
                filtrar(this.paginacao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    boolean excluir(ProtocoloEntrada n) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exclusão...");
        alert.setHeaderText(null);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem certeza disso?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            try {
                ProtocoloEntrada t = protocolos.getOne(n.getId());
                protocolos.delete(t);
                alert(Alert.AlertType.INFORMATION, "Sucesso", null, "Removido com sucesso!", null, false);
                return true;
            } catch (Exception e) {
                alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao excluir o registro", e, true);
                return false;
            }
        } else
            return false;
    }
    public List<ProtocoloEntrada> filtrar(Paginacao paginacao){
        List<ProtocoloEntrada> list;
        if(controller!=null) {
            list = controller.filtrar(paginacao);
        }
        else{
            ProtocoloEntradaFilter filter = new ProtocoloEntradaFilter();
            Pair<List<ProtocoloEntrada>, Paginacao> listPair = protocolos.filtrar(paginacao, filter);
            list = listPair.getKey();
            tbPrincipal.getItems().clear();
            tbPrincipal.getItems().addAll(list);
        }
        return list;
    }
    public void tabela(){
        tbPrincipal.getColumns().clear();

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
                    setText(DateUtil.format(item.getTime()));
                }
            }
        });
        tbPrincipal.getColumns().add(colunaData);

        TableColumn<ProtocoloEntrada, Boolean> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("recebido"));
        colunaStatus.setCellFactory((TableColumn<ProtocoloEntrada, Boolean> param) -> new TableCell<ProtocoloEntrada, Boolean>() {
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
                        setText("Não recebido");
                        setStyle("-fx-background-color:red;-fx-text-fill: white;");
                    } else if (p.isDevolver() && p.isDevolvido()) {
                        if (p.getPrazo().before(Calendar.getInstance())) {
                            setText("Devolução Atrasada");
                            setStyle("-fx-background-color:red;-fx-text-fill: white;");
                        } else {
                            setText("No Prazo");
                            setStyle("-fx-background-color:green;-fx-text-fill: white;");
                        }
                    } else {
                        setText("Concluido");
                        setStyle("-fx-background-color:green;-fx-text-fill: white;");
                    }
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
                    setText(DateUtil.format(item.getTime()));
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
                    setText(DateUtil.format(item.getTime()));
                }
            }
        });
        tbPrincipal.getColumns().add(colunaDataRecebimento);

        TableColumn actionEdit = new TableColumn("Alterações");
        actionEdit.setCellValueFactory(new PropertyValueFactory<>("id"));
        Callback<TableColumn<ProtocoloEntrada, Long>, TableCell<ProtocoloEntrada, Long>> editFactory
                = (final TableColumn<ProtocoloEntrada, Long> param) -> {
            final TableCell<ProtocoloEntrada, Long> cell = new TableCell<ProtocoloEntrada, Long>() {
                private JFXButton button = new JFXButton("");
                @Override
                public void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    setAlignment(Pos.CENTER);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btDefaultText");
                        if(rbAdministrativo.isSelected()){
                            button.setText("Editar");
                            buttonTable(button, IconsEnum.BUTTON_EDIT);
                            button.setOnAction(event ->abrirCadastro(tbPrincipal.getItems().get(getIndex())));
                        }
                        else {
                            buttonTable(button, IconsEnum.BUTTON_RETUITAR);
                            setGraphic(button);
                            ProtocoloEntrada prot = tbPrincipal.getItems().get(getIndex());
                            if (prot.isRecebido()
                                    && prot.isDevolver()
                                    && prot.isDevolvido()
                                    || prot.isRecebido()
                                    && !prot.isDevolver()) {
                                button.setDisable(true);
                                buttonTable(button, IconsEnum.BUTTON_OK);
                                setGraphic(button);
                                button.setText("Baixado");
                            } else if (!prot.isRecebido()) {
                                button.setDisable(false);
                                button.setText("Encaminhar");
                            } else if (prot.isRecebido()
                                    && prot.isDevolver()
                                    && !prot.isDevolvido()) {
                                button.setDisable(false);
                                buttonTable(button, IconsEnum.BUTTON_ADD);
                                setGraphic(button);
                                button.setText("Novo Prazo");
                            }
                            button.setOnAction((ActionEvent event) -> {
                                if (button.getText().equals("Encaminhar")) {
                                    ProtocoloEntrada p = tbPrincipal.getItems().get(getIndex());
                                    ChoiceDialog<Usuario> dialog = new ChoiceDialog<>();
                                    dialog.getItems().addAll(usuarioAtivos);
                                    if(usuarioAtivos.contains(UsuarioLogado.getInstance().getUsuario()))
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
                                            p = protocolos.save(p);
                                            alert(Alert.AlertType.INFORMATION, "Sucesso", "", "Atualizado com sucesso",null,false);
                                            filtrar(paginacao);
                                            alerta.programarEnvioDocumentoRecebido(p,true);
                                        } catch (Exception e) {
                                            alert(Alert.AlertType.ERROR, "Erro", "Erro ao Salvar", "Não foi possivel salvar", e, true);
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
                                            protocolos.save(p);
                                            alert(Alert.AlertType.INFORMATION, "Sucesso", "", "Atualizado com sucesso",null,false);
                                            filtrar(paginacao);
                                        } catch (Exception e) {
                                            alert(Alert.AlertType.ERROR, "Erro", "Erro ao Salvar", "Não foi possivel salvar", e, true);
                                        }

                                    }
                                }
                            });
                        }
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        };

        actionEdit.setCellFactory(editFactory);
        actionEdit.setPrefWidth(125);
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
                        if(rbAdministrativo.isSelected()){
                            btn.setText("Excluir");
                            buttonTable(btn, IconsEnum.BUTTON_REMOVE);
                            btn.setOnAction(event -> {
                                boolean removed = excluir(tbPrincipal.getItems().get(getIndex()));
                                if (removed) tbPrincipal.getItems().remove(getIndex());
                            });
                        }
                        else {
                            ProtocoloEntrada prot = tbPrincipal.getItems().get(getIndex());
                            if (!prot.isRecebido()) {
                                btn.setDisable(false);
                                btn.setText("Baixar");
                                buttonTable(btn, IconsEnum.BUTTON_DOWNLOAD);
                                setGraphic(btn);
                            } else if (prot.isRecebido()
                                    && prot.isDevolver()
                                    && !prot.isDevolvido()) {
                                btn.setText("Devolver");
                                btn.setDisable(false);
                                buttonTable(btn, IconsEnum.BUTTON_DEVOLVER);
                                setGraphic(btn);
                            } else {
                                btn.setText("Concluido");
                                btn.setDisable(true);
                                buttonTable(btn, IconsEnum.BUTTON_OK);
                                setGraphic(btn);
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
                                                protocolos.save(p);
                                                alert(Alert.AlertType.INFORMATION, "Sucesso", "", "Atualizado com sucesso",null,true);
                                                filtrar(paginacao);
                                            } catch (Exception e) {
                                                alert(Alert.AlertType.ERROR, "Erro", "Erro ao Salvar", "Não foi possivel salvar", e, true);
                                            }
                                        }
                                    }
                                } else if (btn.getText().equals("Devolver")) {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
                                            protocolos.save(p);
                                            alert(Alert.AlertType.INFORMATION, "Sucesso", "", "Atualizado com sucesso",null,false);
                                            filtrar(paginacao);
                                        } catch (Exception e) {
                                            alert(Alert.AlertType.ERROR, "Erro", "Erro ao Salvar", "Não foi possivel salvar", e, true);
                                        }
                                    }
                                }
                            });
                        }
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionCol.setCellFactory(cellFactory);
        actionCol.setPrefWidth(125);
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

        radioNo.setSelected(true);

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
