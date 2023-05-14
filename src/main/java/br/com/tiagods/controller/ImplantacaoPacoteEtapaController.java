package br.com.tiagods.controller;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import br.com.tiagods.model.implantacao.ImplantacaoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoPacote;
import br.com.tiagods.model.implantacao.ImplantacaoPacoteEtapa;
import br.com.tiagods.controller.ImplantacaoEtapaController;
import br.com.tiagods.repository.ImplantacaoPacotes;
import br.com.tiagods.util.JavaFxUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ImplantacaoPacoteEtapaController implements Initializable {
    @FXML
    private JFXButton btnNovaEtapa;

    @FXML
    private TableView<ImplantacaoPacoteEtapa> tbEtapa;

    private Stage stage;

    @Autowired
    private ImplantacaoPacotes pacotes;
    @Lazy
    @Autowired
    private StageManager stageManager;
    @Autowired
    ImplantacaoEtapaController implantacaoEtapaController;

    private ImplantacaoPacote pacote;

    public void setPropriedades(ImplantacaoPacote pacote, Stage stage) {
        this.pacote = pacote;
        this.stage = stage;
    }

    void cadastrarEtapa(boolean editar, int tableLocation, ImplantacaoPacoteEtapa etapa){
        Stage stage = stageManager.switchScene(FxmlView.IMPLANTACAO_ETAPA, true);
        implantacaoEtapaController.setPropriedades(editar, etapa, pacote, stage);
        final ImplantacaoEtapaController controller = implantacaoEtapaController;
        stage.setOnHiding(event -> {
            ImplantacaoPacoteEtapa et = (ImplantacaoPacoteEtapa)controller.getEtapa();
            if(et!=null && controller.isEtapaValida()) {
                if (tableLocation == -1) tbEtapa.getItems().add(et);
                else tbEtapa.getItems().set(tableLocation, et);
                pacote.setEtapas(tbEtapa.getItems().stream().collect(Collectors.toSet()));
                tbEtapa.refresh();
            }
        });
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
                    JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_EDIT);
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
                    JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_REMOVE);
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
            Set<ImplantacaoPacoteEtapa> pacoteEtapas = tbEtapa.getItems().stream().collect(Collectors.toSet());
            pacote.setEtapas(pacoteEtapas);
            pacote = pacotes.save(pacote);
            tbEtapa.getItems().clear();
            tbEtapa.getItems().addAll(ordenar(pacote));
            JavaFxUtil.alert(Alert.AlertType.INFORMATION,"Sucesso","","Salvo com sucesso!",null,false);
        }catch (Exception e){
            JavaFxUtil.alert(Alert.AlertType.ERROR,"Erro","","Falha ao tentar salvar os registros!",e,true);
        }
    }
    @FXML
    void sair(ActionEvent event){
        stage.close();
    }
}
