package br.com.tiagods.controller;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Cliente;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.repository.ImplantacaoProcessos;
import br.com.tiagods.repository.ImplantacaoProcessosEtapas;
import br.com.tiagods.repository.interfaces.Paginacao;
import br.com.tiagods.services.AlertaImplantacao;
import br.com.tiagods.util.*;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;

import static br.com.tiagods.util.JavaFxUtil.alert;

@Controller
public class ImplantacaoProcessoPesquisaController implements Initializable, StageController {
    @FXML
    private JFXToggleButton tggFinalizado;

    @FXML
    private TableView<ImplantacaoProcesso> tbPrincipal;

    @FXML
    private JFXComboBox<Integer> cbLimite;

    @FXML
    private Pagination pagination;

    private Paginacao paginacao;

    @Autowired
    private ImplantacaoProcessos processos;

    @Autowired
    private ImplantacaoProcessosEtapas etapas;

    private Stage stage;

    @Autowired
    AlertaImplantacao alertaImplantacao;
    @Autowired
    ImplantacaoProcessoCadastroController implantacaoProcessoCadastroController;

    @Lazy
    @Autowired
    StageManager stageManager;

    @Override
    public void setPropriedades(Stage stage) {
        this.stage = stage;
    }

    void abrirCadastro(ImplantacaoProcesso t){
        if(t!=null) {
            t = processos.getOne(t.getId());
        }
        Stage stage = stageManager.switchScene(FxmlView.IMPLANTACAO_PROCESSO_CADASTRO, true);
        implantacaoProcessoCadastroController.setPropriedades(stage, t);
        stage.setOnHiding(event -> {
            invocarFiltro();
        });

    }
    void combos(){
        tggFinalizado.selectedProperty().addListener(event->invocarFiltro());
    }
    boolean excluir(ImplantacaoProcesso n){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exclusão...");
        alert.setHeaderText(null);
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Tem certeza disso?");
        Optional<ButtonType> optional = alert.showAndWait();
        if (optional.get() == ButtonType.OK) {
            try{
                ImplantacaoProcesso t = processos.getOne(n.getId());
                processos.delete(t);
                alert(Alert.AlertType.INFORMATION, "Sucesso", null, "Removido com sucesso!",null, false);
                return true;
            }catch(Exception e){
                alert(Alert.AlertType.ERROR, "Erro", null,"Falha ao excluir o registro", e,true);
                return false;
            }
        }
        else return false;
    }
    @FXML
    void exportar(ActionEvent event){

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
                    Integer[] colunasLenght = new Integer[] { 10, 20,20,10,20,20,10,20,20,20};
                    String[] cabecalho = new String[] {"Processo","Data","Cliente_Apelido","Cliente_Nome","Cliente_Status",
                            "Finalizado","Data_Finalizacao","Progresso_Atual","Etapas_Concluidas","Total_Etapas"
                    };
                    listaImpressao.add(new ArrayList<>());
                    for (String c : cabecalho) {
                        listaImpressao.get(0).add(c);
                    }
                    try {
                        List<ImplantacaoProcesso> list = filtrar(null);
                        for (int i = 1; i <= list.size(); i++) {
                            listaImpressao.add(new ArrayList<String>());
                            ImplantacaoProcesso c = list.get(i-1);

                        }
                        ExcelGenericoUtil planilha = new ExcelGenericoUtil(export.getAbsolutePath(), listaImpressao, colunasLenght);
                        planilha.gerarExcel();
                        Platform.runLater(() ->alert(Alert.AlertType.INFORMATION,"Sucesso", "Relatorio gerado com sucesso","",null,false));
                        Desktop.getDesktop().open(export);
                    } catch (Exception e1) {
                        Platform.runLater(() ->alert(Alert.AlertType.ERROR,"Erro","","Erro ao criar a planilha",e1,true));
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
                alert(Alert.AlertType.ERROR,"Erro","Parâmetros vazios","Nenhum registro foi encontrato",null,false);
            }
        }catch (IOException e){
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o progresso", "O arquivo nao foi localizado",null,false);
        }
    }
    private List<ImplantacaoProcesso> filtrar(Paginacao paginacao){
        tbPrincipal.getItems().clear();
        List<ImplantacaoProcesso> list = processos.listarAtivos(tggFinalizado.isSelected());
        list.forEach(c-> {
            Optional<ImplantacaoProcesso> result = processos.findById(c.getId());
            if(result.isPresent()) {
                c = result.get();
            }
        });
        tbPrincipal.getItems().addAll(list);
        return list;
    }
    private void gerarRelatorio(ImplantacaoProcesso implantacaoProcesso) {
        try {
            List<TipoArquivo> arquivos = Arrays.asList(new TipoArquivo[]{TipoArquivo.XLS,TipoArquivo.HTML});
            ChoiceDialog<TipoArquivo> dialog = new ChoiceDialog<>(arquivos.get(0), arquivos);
            dialog.setTitle("Relatorio");
            dialog.setHeaderText("Relatorio para formato de arquivo");
            dialog.setContentText("Escolha uma opção:");

            final Optional<TipoArquivo> result = dialog.showAndWait();
            if (result.isPresent()) {
                FXMLLoader loader = new FXMLLoader(FXMLEnum.PROGRESS_SAMPLE.getLocalizacao());
                Alert progress = new Alert(Alert.AlertType.INFORMATION);
                progress.setHeaderText("");
                DialogPane dialogPane = new DialogPane();
                dialogPane.setContent(loader.load());
                progress.setDialogPane(dialogPane);
                Stage sta = (Stage) dialogPane.getScene().getWindow();
                Task<Void> run = new Task<Void>() {
                    {
                        setOnFailed(a -> sta.close());
                        setOnSucceeded(a -> sta.close());
                        setOnCancelled(a -> sta.close());
                    }

                    @Override
                    protected Void call() {
                        Platform.runLater(() -> sta.show());
                        try {
                            File arquivo = null;
                            if(result.get().equals(TipoArquivo.XLS))
                                arquivo = alertaImplantacao.gerarExcel(implantacaoProcesso,null,null,null,false);
                            else if(result.get().equals(TipoArquivo.HTML))
                                arquivo = alertaImplantacao.gerarHtml(implantacaoProcesso,null,null,null,false);
                            Desktop.getDesktop().open(arquivo);
                        } catch (Exception e1) {
                            Platform.runLater(() -> alert(Alert.AlertType.ERROR, "Erro", "", "Erro ao criar a planilha ", e1, true));
                        }
                        return null;
                    }
                };
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
        }catch (IOException e){
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o progresso", "O arquivo nao foi localizado",null,false);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabela();
        combos();
        invocarFiltro();
    }

    void invocarFiltro(){
        filtrar(this.paginacao);
    }

    @FXML
    void novo(ActionEvent event) {
        abrirCadastro(null);
    }

    private boolean salvarStatus(ImplantacaoProcesso n,boolean status){
        try {
            Optional<ImplantacaoProcesso> result = processos.findById(n.getId());
            if(result.isPresent()) {
                ImplantacaoProcesso t = result.get();
                t.setFinalizado(status);
                t.setDataFinalizacao(status?Calendar.getInstance():null);
                processos.save(t);
            }
            return true;
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro",null,"Erro ao salvar",e,true);
            return false;
        }
    }

    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }

    void tabela() {
        TableColumn<ImplantacaoProcesso, Number> columnId = new  TableColumn<>("*");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnId.setPrefWidth(40);

        TableColumn<ImplantacaoProcesso, Calendar> colunaData = new  TableColumn<>("Data");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("criadoEm"));
        colunaData.setCellFactory(param -> new TableCell<ImplantacaoProcesso,Calendar>(){
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else{
                    setText(DateUtil.format(item.getTime(), DateUtil.SDF));
                }
            }
        });
        colunaData.setPrefWidth(130);

        TableColumn<ImplantacaoProcesso, Cliente> colunaCliente = new  TableColumn<>("Cliente");
        colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colunaCliente.setCellFactory(param -> new TableCell<ImplantacaoProcesso,Cliente>(){
            @Override
            protected void updateItem(Cliente item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null || empty){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else setText(item.toString());
            }
        });
        colunaData.setPrefWidth(130);

        TableColumn<ImplantacaoProcesso, Boolean> colunaStatus = new  TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("finalizado"));
        colunaStatus.setCellFactory(param -> new TableCell<ImplantacaoProcesso,Boolean>(){
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else{
                    JFXButton rb = new JFXButton();
                    rb.setText(item?"Concluido":"Pendente");
                    rb.getStyleClass().add(item?"btGreen":"btRed");
                    rb.setTooltip(new Tooltip("Possibilita alterar o status da tarefa \nde concluido para pendente e vice-versa"));
                    rb.onActionProperty().set(event -> {

                        Dialog dialog = new Dialog();
                        dialog.setTitle("Alteração de Status");
                        dialog.setHeaderText("Selecione um status");

                        VBox stackPane = new VBox();
                        stackPane.setSpacing(15);

                        Map<JFXRadioButton,Boolean> map = new HashMap<>();
                        ToggleGroup group = new ToggleGroup();

                        int value = item?1:0;

                        for(int i=0;i<2;i++) {
                            JFXRadioButton jfxRadioButton = new JFXRadioButton(i==1?"Concluido":"Pendente");
                            jfxRadioButton.setSelectedColor(Color.GREEN);
                            jfxRadioButton.setUnSelectedColor(Color.RED);
                            if(value==i) jfxRadioButton.setSelected(true);
                            group.getToggles().add(jfxRadioButton);
                            stackPane.getChildren().add(jfxRadioButton);
                            map.put(jfxRadioButton,i==1);
                        }
                        ButtonType ok = new ButtonType("Salvar");
                        ButtonType cancelar = new ButtonType("Cancelar");
                        dialog.getDialogPane().getButtonTypes().addAll(ok,cancelar);
                        dialog.getDialogPane().setContent(stackPane);
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initStyle(StageStyle.UNDECORATED);
                        Optional<ButtonType> result = dialog.showAndWait();
                        if(result.get() == ok){
                            ImplantacaoProcesso processo = tbPrincipal.getItems().get(getIndex());
                            for(Node f : stackPane.getChildren()){
                                if(f instanceof JFXRadioButton && ((JFXRadioButton) f).isSelected()) {
                                    Boolean p = map.get(f);
                                    if (p!=item && salvarStatus(processo, p)) {
                                        tbPrincipal.getItems().get(getIndex()).setFinalizado(p);
                                        tbPrincipal.refresh();
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

        TableColumn<ImplantacaoProcesso, Number> colunaPendentes = new  TableColumn<>("");
        colunaPendentes.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaPendentes.setCellFactory(param -> new TableCell<ImplantacaoProcesso,Number>(){
            JFXSpinner spinner = new JFXSpinner();
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else{
                    Set<ImplantacaoProcessoEtapa> etapaSet = tbPrincipal.getItems().get(getIndex()).getEtapas();
                    double valor = 0;
                    double size = etapaSet.size();
                    for(ImplantacaoProcessoEtapa e : etapaSet){
                        if(e.getStatus().equals(ImplantacaoProcessoEtapa.Status.CONCLUIDO)) valor++;
                    }
                    if(size==0) setText("Nenhuma Etapa foi Encontrada");
                    else {
                        double percent = valor/size;
                        spinner.setProgress(percent);
                        setGraphic(spinner);
                        setText((int)valor+" de "+(int)size+" Etapas Concluidas");
                    }
                }
            }
        });

        TableColumn<ImplantacaoProcesso, Number> colunaExportar = new  TableColumn<>("");
        colunaExportar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaExportar.setCellFactory(param -> new TableCell<ImplantacaoProcesso,Number>(){
            JFXButton button = new JFXButton();
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
                    JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_EXCEL);
                    button.setOnAction(event -> {
                        gerarRelatorio(tbPrincipal.getItems().get(getIndex()));
                    });
                    setGraphic(button);
                }
            }
        });
        colunaExportar.setMaxWidth(50);

        TableColumn<ImplantacaoProcesso, Number> colunaEditar = new  TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<ImplantacaoProcesso,Number>(){
            JFXButton button = new JFXButton();
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
                    JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_EDIT);
                    button.setOnAction(event -> {
                        abrirCadastro(tbPrincipal.getItems().get(getIndex()));
                    });
                    setGraphic(button);
                }
            }
        });
        colunaEditar.setMaxWidth(50);

        TableColumn<ImplantacaoProcesso, Number> colunaExcluir = new  TableColumn<>("");
        colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaExcluir.setCellFactory(param -> new TableCell<ImplantacaoProcesso,Number>(){
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
                    final Tooltip tooltip = new Tooltip("Clique para remover o registro!");
                    button.setTooltip(tooltip);
                    button.setOnAction(event -> {
                        boolean removed = excluir(tbPrincipal.getItems().get(getIndex()));
                        if(removed) tbPrincipal.getItems().remove(getIndex());
                    });
                    setGraphic(button);
                }
            }
        });
        colunaExcluir.setMaxWidth(50);
        tbPrincipal.getColumns().addAll(colunaData,colunaCliente,colunaStatus,colunaPendentes,colunaExportar, colunaEditar,colunaExcluir);
        tbPrincipal.setTableMenuButtonVisible(true);
        tbPrincipal.setFixedCellSize(70);
    }
}
