package br.com.tiagods.controller;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import br.com.tiagods.model.implantacao.ImplantacaoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoPacote;
import br.com.tiagods.model.implantacao.ImplantacaoPacoteEtapa;
import br.com.tiagods.repository.helpers.ImplantacaoPacotesImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ImplantacaoPacoteEtapaController extends UtilsController implements Initializable{
    @FXML
    private JFXButton btnNovaEtapa;

    @FXML
    private TableView<ImplantacaoPacoteEtapa> tbEtapa;

    private Stage stage;

    private ImplantacaoPacotesImpl pacotes;

    private ImplantacaoPacote pacote;

    public ImplantacaoPacoteEtapaController(ImplantacaoPacote pck, Stage stage) {
        this.pacote = pck;
        this.stage = stage;
    }
    void cadastrarEtapa(boolean editar, int tableLocation, ImplantacaoPacoteEtapa etapa){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.IMPLATACAO_ETAPA);
            ImplantacaoEtapaController controller = new ImplantacaoEtapaController(editar,etapa,pacote,stage);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                ImplantacaoPacoteEtapa et = (ImplantacaoPacoteEtapa)controller.getEtapa();
                if(et!=null && controller.isEtapaValida()) {
                    if (tableLocation == -1) tbEtapa.getItems().add(et);
                    else tbEtapa.getItems().set(tableLocation, et);
                    pacote.setEtapas(tbEtapa.getItems().stream().collect(Collectors.toSet()));
                    tbEtapa.refresh();
                }
            });
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro","Falha ao localizar o arquivo "+FXMLEnum.NEGOCIO_PESQUISA,e,true);
        }
    }
    private void combos(){
        btnNovaEtapa.setOnAction(event -> {
            ImplantacaoPacoteEtapa etapa = new ImplantacaoPacoteEtapa();
            etapa.setPacote(pacote);
            cadastrarEtapa(false,-1,etapa);
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabelaEtapa();
        combos();
        tbEtapa.getItems().addAll(ordenar(pacote));
    }

    private List<ImplantacaoPacoteEtapa> ordenar(ImplantacaoPacote pacote){
        Comparator<ImplantacaoPacoteEtapa> comparator = Comparator.comparing(c->c.getAtividade().getNome());
        List<ImplantacaoPacoteEtapa> etapas = new ArrayList<>();
        etapas.addAll(pacote.getEtapas());
        Collections.sort(etapas,comparator.thenComparingInt(c->c.getEtapa().getValor()));
        return etapas;
    }
    void tabelaEtapa(){
        TableColumn<ImplantacaoPacoteEtapa, ImplantacaoPacoteEtapa.Etapa> colunaNome = new TableColumn<>("Etapa");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("etapa"));

        TableColumn<ImplantacaoPacoteEtapa, ImplantacaoAtividade> colunaAtividade = new TableColumn<>("Atividade");
        colunaAtividade.setCellValueFactory(new PropertyValueFactory<>("atividade"));

        TableColumn<ImplantacaoPacoteEtapa, ImplantacaoAtividade> colunaDescricao = new TableColumn<>("Descricao");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<ImplantacaoPacoteEtapa, Departamento> colunaDepartamento = new TableColumn<>("Departamento");
        colunaDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));

        TableColumn<ImplantacaoPacoteEtapa, Integer> colunaTempo = new TableColumn<>("Tempo(dias)");
        colunaTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));

        TableColumn<ImplantacaoPacoteEtapa, ImplantacaoEtapa.Etapa> colunaEditar = new  TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaEditar.setCellFactory(param -> new TableCell<ImplantacaoPacoteEtapa,ImplantacaoEtapa.Etapa>(){
            JFXButton button = new JFXButton();//Editar
            @Override
            protected void updateItem(ImplantacaoEtapa.Etapa item, boolean empty) {
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
                    button.setOnAction(event -> cadastrarEtapa(true,getIndex(),tbEtapa.getItems().get(getIndex())));
                    setGraphic(button);
                }
            }
        });
        TableColumn<ImplantacaoPacoteEtapa, ImplantacaoEtapa.Etapa> colunaExcluir = new  TableColumn<>("");
        colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaExcluir.setCellFactory(param -> new TableCell<ImplantacaoPacoteEtapa,ImplantacaoEtapa.Etapa>(){
            JFXButton button = new JFXButton();//
            @Override
            protected void updateItem(ImplantacaoEtapa.Etapa item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else{
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_REMOVE);
                    }catch (IOException e) {
                    }
                    button.setOnAction(event -> tbEtapa.getItems().remove(getIndex()));
                    setGraphic(button);
                }
            }
        });
        tbEtapa.getColumns().addAll(colunaNome,colunaAtividade,colunaDescricao,colunaDepartamento,colunaTempo,colunaEditar,colunaExcluir);
    }

    @FXML
    void salvar(ActionEvent event){
        salvar();
    }

    void salvar(){
        try{
            loadFactory();
            pacotes = new ImplantacaoPacotesImpl(getManager());
            pacote = pacotes.save(pacote);
            tbEtapa.getItems().clear();
            tbEtapa.getItems().addAll(ordenar(pacote));
            alert(Alert.AlertType.INFORMATION,"Sucesso","","Salvo com sucesso!",null,false);
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","","Falha ao tentar salvar os registros!",e,true);
        }finally {
            close();
        }
    }
    @FXML
    void sair(ActionEvent event){
        stage.close();
    }
}
