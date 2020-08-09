package br.com.tiagods.controller;

import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class ImplantacaoEtapaController extends UtilsController implements Initializable {
    @FXML
    private JFXComboBox<ImplantacaoEtapa.Etapa> cbEtapa;

    @FXML
    private JFXComboBox<Departamento> cbDepartamento;

    @FXML
    private JFXComboBox<ImplantacaoAtividade> cbAtividade;

    @FXML
    private JFXComboBox<Integer> cbTempo;

    @FXML
    private JFXTextField txDescricao;

    @FXML
    private JFXButton btnHelp;

    private ImplantacaoEtapa etapa;
    private Stage stage;
    private ImplantacaoAtividadesImpl atividades;
    private DepartamentosImpl departamentos;
    private Object object;
    private boolean valida = false;
    private boolean edicao = false;

    public ImplantacaoEtapaController(boolean edicao, ImplantacaoEtapa etapa, Object object, Stage stage) {
        this.edicao=edicao;
        this.etapa=etapa;
        this.stage=stage;
        this.object=object;
    }

    @FXML
    private void aplicar(ActionEvent event){
        if(cbAtividade.getValue()==null){
            alert(Alert.AlertType.ERROR,"Erro","Campo Vázio é Obrigatório","Campo Atividades/Documentos é obrigatório",null,false);
            return;
        }
        if(cbEtapa.getValue()==null){
            alert(Alert.AlertType.ERROR,"Erro","Campo Vázio é Obrigatório","Campo Etapa é obrigatório",null,false);
            return;
        }
        if(cbDepartamento.getValue()==null){
            alert(Alert.AlertType.ERROR,"Erro","Campo Vázio é Obrigatório","Campo Departamento é obrigatório",null,false);
            return;
        }
        if(txDescricao.getText().trim().equals("")) {
            alert(Alert.AlertType.ERROR,"Erro","Campo Vázio é Obrigatório","Campo Descrição",null,false);
            return;
        }
        else {
            if(object instanceof ImplantacaoPacote){
                Set<ImplantacaoPacoteEtapa> items = ((ImplantacaoPacote) object).getEtapas();
                Optional<ImplantacaoPacoteEtapa> result = items.stream()
                        .filter(c->c.getAtividade().getId()==cbAtividade.getValue().getId() && c.getEtapa().equals(cbEtapa.getValue()))
                        .findFirst();
                if(result.isPresent()){
                    if(result.get().getId() == ((ImplantacaoPacoteEtapa) etapa).getId() && edicao) aplicarESair();
                    else alert(Alert.AlertType.ERROR,"Erro","Valor duplicado",
                            "Já foi informado um processo para Atividade:"+result.get().getAtividade()+"\n e Etapa:"+result.get().getEtapa()+"\nMude alguns dos parametros e tente novamente",null,false);
                }
                else aplicarESair();
            }
            else if(object instanceof ImplantacaoProcesso){
                Set<ImplantacaoProcessoEtapa> items = ((ImplantacaoProcesso) object).getEtapas();
                Optional<ImplantacaoProcessoEtapa> result = items.stream()
                        .filter(c->c.getEtapa().getAtividade().getId()==cbAtividade.getValue().getId() && c.getEtapa().getEtapa().equals(cbEtapa.getValue()))
                        .findFirst();
                if(result.isPresent()){
                    if(edicao) aplicarESair();
                    else alert(Alert.AlertType.ERROR,"Erro","Valor duplicado",
                            "Já foi informado um processo para Atividade:"+result.get().getEtapa().getAtividade()+"\n e Etapa:"+result.get().getEtapa()+"\nMude alguns dos parametros e tente novamente",null,false);
                }
                else aplicarESair();
            }
        }
    }
    private void aplicarESair(){
        etapa.setAtividade(cbAtividade.getValue());
        etapa.setEtapa(cbEtapa.getValue());
        etapa.setDepartamento(cbDepartamento.getValue());
        etapa.setTempo(cbTempo.getValue());
        etapa.setDescricao(txDescricao.getText());
        valida = true;
        stage.close();
    }
    @FXML
    private void cadastrarDocumento(ActionEvent event){
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Cadastro de Pacotes");
        dialog.setHeaderText("Informe os campos abaixo para cadastrar um novo registro");

// Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(getClass().getResource("/fxml/imagens/theme.png").toString()));

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        JFXTextField documentoNome = new JFXTextField();
        documentoNome.setPromptText("Nome");
        JFXTextField documentoDescricao = new JFXTextField();
        documentoDescricao.setPromptText("Descricao");

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(documentoNome, 1, 0);
        Label label = new Label("");
        label.setPrefWidth(100);
        grid.add(label, 2, 0);

        grid.add(new Label("Descrição:"), 0, 1);
        grid.add(documentoDescricao, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node salvarButton = dialog.getDialogPane().lookupButton(loginButtonType);
        salvarButton.setDisable(true);
        // Do some validation (using the Java 8 lambda syntax).
        documentoNome.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()) salvarButton.setDisable(true);
            else{
                Optional<ImplantacaoAtividade> result = cbAtividade.getItems().stream().filter(f-> f.getNome().equalsIgnoreCase(newValue.trim())).findFirst();
                salvarButton.setDisable(result.isPresent());
                if(result.isPresent())
                    label.setText("Registro já existe");
                else label.setText("Válido");
            }
        });
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> documentoNome.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(documentoNome.getText(), documentoDescricao.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {

            try{
                loadFactory();
                atividades = new ImplantacaoAtividadesImpl(getManager());
                ImplantacaoAtividade atividade = new ImplantacaoAtividade();
                atividade.setNome(pair.getKey());
                atividade.setDescricao(pair.getValue());
                atividades.save(atividade);
                cbAtividade.getItems().clear();
                cbAtividade.getItems().addAll(atividades.getAll());
            }catch (Exception e){
                alert(Alert.AlertType.ERROR,"Erro","Não foi possivel salvar","Falha ao tentar salvar o registro",e,true);
            }finally {
                close();
            }

        });
    }

    private void combos(){
        atividades = new ImplantacaoAtividadesImpl(getManager());
        cbAtividade.getItems().addAll(atividades.getAll());

        cbAtividade.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue==null) return;
            if(object instanceof ImplantacaoPacote){
                Set<ImplantacaoPacoteEtapa> etapaSet = ((ImplantacaoPacote) object).getEtapas();
                List<ImplantacaoPacoteEtapa> res = etapaSet.stream()
                        .filter(c->c.getAtividade().getId()==newValue.getId()).collect(Collectors.toList());
                if(edicao && etapa.getAtividade().getId()==newValue.getId()){
                    cbEtapa.getItems().clear();
                    cbEtapa.getItems().add(etapa.getEtapa());
                    cbEtapa.setValue(etapa.getEtapa());
                }
                else if(res.isEmpty()) {
                    cbEtapa.getItems().clear();
                    cbEtapa.getItems().add(ImplantacaoEtapa.Etapa.PRIMEIRA);
                    cbEtapa.getSelectionModel().select(ImplantacaoEtapa.Etapa.PRIMEIRA);
                }
                else{
                    for(ImplantacaoEtapa.Etapa e : ImplantacaoEtapa.Etapa.values()){
                        Optional<ImplantacaoPacoteEtapa> value = res.stream().filter(c-> c.getEtapa().equals(e)).findAny();
                        if(!value.isPresent()) {
                            cbEtapa.getItems().clear();
                            cbEtapa.getItems().add(e);
                            cbEtapa.setValue(e);
                            break;
                        }
                    }
                }
            }
            else if(object instanceof ImplantacaoProcesso){
                Set<ImplantacaoProcessoEtapa> etapaSet = ((ImplantacaoProcesso) object).getEtapas();
                List<ImplantacaoProcessoEtapa> res = etapaSet.stream()
                        .filter(c->c.getEtapa().getAtividade().getId()==newValue.getId()).collect(Collectors.toList());
                if(edicao && etapa.getAtividade().getId()==newValue.getId()){
                    cbEtapa.getItems().clear();
                    cbEtapa.getItems().add(etapa.getEtapa());
                    cbEtapa.setValue(etapa.getEtapa());
                }
                else if(res.isEmpty()) {
                    cbEtapa.getItems().clear();
                    cbEtapa.getItems().add(ImplantacaoEtapa.Etapa.PRIMEIRA);
                    cbEtapa.setValue(ImplantacaoEtapa.Etapa.PRIMEIRA);
                }
                else{
                    for(ImplantacaoEtapa.Etapa e : ImplantacaoEtapa.Etapa.values()){
                        Optional<ImplantacaoProcessoEtapa> value = res.stream().filter(c-> c.getEtapa().getEtapa().equals(e)).findAny();
                        if(!value.isPresent()) {
                            cbEtapa.getItems().clear();
                            cbEtapa.getItems().add(e);
                            cbEtapa.setValue(e);
                            break;
                        }
                    }
                }
            }
        });

        departamentos = new DepartamentosImpl(getManager());
        cbDepartamento.getItems().addAll(departamentos.getAll());

        for(int i = 1; i<=365; i++) cbTempo.getItems().add(i);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            loadFactory();
            combos();
            if(etapa!=null) preencherFormulario(etapa);
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","Erro ao getAllFetchJoin registros","",e,true);
        }finally {
            close();
        }
    }

    private void preencherFormulario(ImplantacaoEtapa etapa){
        cbAtividade.setValue(etapa.getAtividade());
        cbEtapa.setValue(etapa.getEtapa());
        cbDepartamento.setValue(etapa.getDepartamento());
        cbTempo.setValue(etapa.getTempo());
        txDescricao.setText(etapa.getDescricao()!=null?etapa.getDescricao():"");
        this.etapa=etapa;
    }

    @FXML
    private void cancelar(ActionEvent event){
        this.etapa = null;
        stage.close();
    }
    public ImplantacaoEtapa getEtapa(){return etapa;}

    public boolean isEtapaValida(){
        return valida;
    }
}
