package br.com.tiagods.controller;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Cliente;
import br.com.tiagods.model.implantacao.ImplantacaoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.ClientesImpl;
import br.com.tiagods.repository.helpers.ImplantacaoProcessosImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.fxutils.maskedtextfield.MaskTextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ImplantacaoProcessoPesquisaController extends UtilsController implements Initializable {
    @FXML
    private JFXToggleButton tggFinalizado;

    @FXML
    private TableView<ImplantacaoProcesso> tbPrincipal;

    @FXML
    private JFXComboBox<Integer> cbLimite;

    @FXML
    private Pagination pagination;

    private Paginacao paginacao;

    private ImplantacaoProcessosImpl processos;

    private Stage stage;


    public ImplantacaoProcessoPesquisaController(Stage stage){
        this.stage = stage;
    }


    void abrirCadastro(ImplantacaoProcesso t){
        try {
            loadFactory();

            if(t!=null) {
                processos = new ImplantacaoProcessosImpl(getManager());
                t = processos.findById(t.getId());
            }
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.IMPLANTACAO_PROCESSO_CADASTRO);
            loader.setController(new ImplantacaoProcessoCadastroController(stage,t));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                try {
                    loadFactory();
                    filtrar(this.paginacao);
                }catch(Exception e) {
                    e.printStackTrace();
                }finally {
                    close();
                }
            });

        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo"+FXMLEnum.IMPLANTACAO_PROCESSO_CADASTRO,e,true);
        }finally {
            close();
        }

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
                loadFactory();
                processos = new ImplantacaoProcessosImpl(getManager());
                ImplantacaoProcesso t = processos.findById(n.getId());
                processos.remove(t);
                alert(Alert.AlertType.INFORMATION, "Sucesso", null, "Removido com sucesso!",null, false);
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

    void filtrar(Paginacao paginacao){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabela();
        try{
            loadFactory();
            processos = new ImplantacaoProcessosImpl(getManager());
            tbPrincipal.getItems().clear();
            tbPrincipal.getItems().addAll(processos.getAll());
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro",null,"Erro ao getAllFetchJoin registros",e,true);
        }finally {
            close();
        }
    }
    @FXML
    void novo(ActionEvent event) {
        abrirCadastro(null);
    }

    private boolean salvarStatus(ImplantacaoProcesso n,boolean status){
        try{
            loadFactory();
            processos = new ImplantacaoProcessosImpl(getManager());
            ImplantacaoProcesso t = processos.findById(n.getId());
            t.setFinalizado(status);
            t.setDataFinalizacao(status?Calendar.getInstance():null);
            processos.save(t);
            return true;
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro",null,"Erro ao salvar",e,true);
            return false;
        }finally {
            close();
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

        TableColumn<ImplantacaoProcesso, Calendar> colunaData = new  TableColumn<>("Prazo");
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
                    setText(sdf.format(item.getTime()));
                }
            }
        });
        colunaData.setPrefWidth(130);

        TableColumn<ImplantacaoProcesso, Cliente> colunaCliente = new  TableColumn<>("Prazo");
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
                        ButtonType ok = new ButtonType("Alterar");
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

        TableColumn<ImplantacaoProcesso, Number> colunaEditar = new  TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<ImplantacaoProcesso,Number>(){
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
                    }catch (IOException e) {}
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
                    try {
                        buttonTable(button,IconsEnum.BUTTON_REMOVE);
                    }catch (IOException e) {
                    }
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
        tbPrincipal.getColumns().addAll(colunaData,colunaCliente,colunaStatus,colunaEditar,colunaExcluir);
        tbPrincipal.setTableMenuButtonVisible(true);
        tbPrincipal.setFixedCellSize(70);
    }
}
