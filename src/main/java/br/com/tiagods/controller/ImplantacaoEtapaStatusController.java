package br.com.tiagods.controller;

import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapaStatus;
import br.com.tiagods.repository.ImplantacaoProcessosEtapas;
import br.com.tiagods.util.DateUtil;
import br.com.tiagods.util.JavaFxUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ImplantacaoEtapaStatusController implements Initializable {

    @FXML
    private JFXButton btnCadastrar;

    @FXML
    private TableView<ImplantacaoProcessoEtapaStatus> tbPrincipal;

    @Autowired
    private ImplantacaoProcessosEtapas etapas;

    private ImplantacaoProcessoEtapa etapa;
    private Stage stage;
    private boolean editar;

    public void setPropriedades(ImplantacaoProcessoEtapa etapa, Stage stage, boolean editar){
        this.etapa=etapa;
        this.stage=stage;
        this.editar=editar;
    }

    private void cadastrar(int localizacao, ImplantacaoProcessoEtapaStatus status){
        TextInputDialog dialog = new TextInputDialog(status.getDescricao());
        dialog.setTitle("Cadastro de Observações");
        boolean novoRegistro = status.getId()==null;
        dialog.setHeaderText(novoRegistro?"Informe os campos abaixo para cadastrar um novo registro":"Informe os campos abaixo para atualizar o registro");
        dialog.setContentText("");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            try {
                status.setDescricao(result.get());
                if (localizacao==-1) {
                    etapa.setDataAtualizacao(Calendar.getInstance());
                    status.setProcessoEtapa(etapa);
                    tbPrincipal.getItems().add(status);
                }
                else{
                    tbPrincipal.getItems().set(localizacao,status);
                }
                Set<ImplantacaoProcessoEtapaStatus> set = tbPrincipal.getItems().stream().collect(Collectors.toSet());
                etapa.setHistorico(set);
                etapa = etapas.save(etapa);
                preencherFormulario(etapa);
                JavaFxUtil.alert(Alert.AlertType.INFORMATION,"Sucesso","","Salvo com Sucesso!",null,false);
            }catch (Exception e){
                JavaFxUtil.alert(Alert.AlertType.ERROR,"Erro","","Falha ao salvar os registros!",e,true);
            }
        }
    }
    private void combos(){
        if(!editar)
            btnCadastrar.setDisable(true);
        else{
            btnCadastrar.setOnAction(event -> {
                cadastrar(-1,new ImplantacaoProcessoEtapaStatus());
            });
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabela();
        combos();
        preencherFormulario(etapa);
    }

    private void preencherFormulario(ImplantacaoProcessoEtapa etapa){
        tbPrincipal.getItems().clear();
        Comparator<ImplantacaoProcessoEtapaStatus> comparator = Comparator.comparing(c->c.getCriadoEm());
        List<ImplantacaoProcessoEtapaStatus> list = new ArrayList<>();
        list.addAll(etapa.getHistorico());
        Collections.sort(list, comparator);
        tbPrincipal.getItems().addAll(list);
    }
    @FXML
    private void sair(ActionEvent event){
        stage.close();
    }

    private void tabela(){
        TableColumn<ImplantacaoProcessoEtapaStatus, Calendar> colunaData = new TableColumn<>("Data");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("criadoEm"));
        colunaData.setCellFactory((TableColumn<ImplantacaoProcessoEtapaStatus, Calendar> param) ->
                new TableCell<ImplantacaoProcessoEtapaStatus, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                } else {
                    setText(DateUtil.format(item.getTime()));
                }
            }
        });
        TableColumn<ImplantacaoProcessoEtapaStatus, String> colunaDescricao = new TableColumn<>("Descricao");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaDescricao.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapaStatus,String>(){
            JFXTextArea txArea = new JFXTextArea();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else{
                    txArea.setText(item);
                    txArea.setEditable(false);
                    setGraphic(txArea);
                }
            }
        });

        TableColumn<ImplantacaoProcessoEtapaStatus, Usuario> colunaResponsavel = new TableColumn<>("Responsavel");
        colunaResponsavel.setCellValueFactory(new PropertyValueFactory<>("criadoPor"));

        TableColumn<ImplantacaoProcessoEtapaStatus, Number> colunaEditar = new TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapaStatus,Number>(){
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
                    ImplantacaoProcessoEtapaStatus ip = tbPrincipal.getItems().get(getIndex());
                    button.getStyleClass().add("");
                    JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_EDIT);
                    final Tooltip tooltip = new Tooltip("Clique para editar o registro");
                    button.setTooltip(tooltip);
                    button.setOnAction(event -> {
                        cadastrar(getIndex(),ip);
                    });
                    setGraphic(button);
                    button.setDisable(!editar);
                }
            }
        });
        colunaDescricao.setPrefWidth(200);
        tbPrincipal.setFixedCellSize(50);
        tbPrincipal.getColumns().addAll(colunaData,colunaDescricao,colunaResponsavel,colunaEditar);
    }
}
