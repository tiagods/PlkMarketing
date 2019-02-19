package br.com.tiagods.controller;

import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Departamento;
import br.com.tiagods.repository.helpers.DepartamentosImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DepartamentoController extends UtilsController implements Initializable {

    @FXML
    private TableView<Departamento> tbPrincipal;

    private DepartamentosImpl departamentos;

    private Stage stage;

    public DepartamentoController(Stage stage) {
        this.stage = stage;
    }

    private void cadastrarDepartamento(int tableLocation, Departamento departamento) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Cadastro de Departamento");
        boolean novoRegistro = departamento.getId() == null;
        dialog.setHeaderText(novoRegistro ? "Informe os campos abaixo para cadastrar um novo registro" : "Informe os campos abaixo para atualizar o registro");
        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(getClass().getResource("/fxml/imagens/theme.png").toString()));
        // Set the button types.
        ButtonType salvarButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(salvarButtonType, ButtonType.CANCEL);
        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));
        JFXTextField departamentoNome = new JFXTextField();
        departamentoNome.setPromptText("Nome");
        departamentoNome.setText(departamento.getNome());
        departamentoNome.setMinWidth(200);

        JFXTextField departamentoEmail = new JFXTextField();
        departamentoEmail.setPromptText("E-Mail");
        departamentoEmail.setText(departamento.getEmail());
        departamentoEmail.setMinWidth(200);
        grid.add(new Label("Nome:"), 0, 0);
        grid.add(departamentoNome, 1, 0);
        Label label = new Label("");
        label.setPrefWidth(100);
        grid.add(label, 2, 0);
        grid.add(new Label("E-Mail:"), 0, 1);
        grid.add(departamentoEmail, 1, 1);
        // Enable/Disable login button depending on whether a username was entered.
        Node salvarButton = dialog.getDialogPane().lookupButton(salvarButtonType);
        salvarButton.setDisable(false);
        // Do some validation (using the Java 8 lambda syntax).
        departamentoNome.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) salvarButton.setDisable(true);
            else {
                Optional<Departamento> result = tbPrincipal.getItems().stream().filter(f -> f.getNome().equalsIgnoreCase(newValue.trim())).findFirst();
                if (result.isPresent() && result.get().getId()==departamento.getId()) {
                    label.setText("Registro já existe");
                    salvarButton.setDisable(true);
                }
                else{
                    salvarButton.setDisable(true);
                    label.setText("Válido");
                }
            }
        });
        dialog.getDialogPane().setContent(grid);
        // Request focus on the username field by default.
        Platform.runLater(() -> departamentoNome.requestFocus());
        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == salvarButtonType) {
                return new Pair<>(departamentoNome.getText(), departamentoEmail.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            try {
                loadFactory();
                departamentos = new DepartamentosImpl(getManager());
                departamento.setNome(pair.getKey());
                departamento.setEmail(pair.getValue());
                Departamento novoDepartamento = departamentos.save(departamento);
                if(tableLocation==-1) tbPrincipal.getItems().addAll(novoDepartamento);
                else
                    tbPrincipal.getItems().set(tableLocation, novoDepartamento);
            }catch (Exception e){
                alert(Alert.AlertType.ERROR,"Erro","Falha ao salvar","Ocorreu um erro ao tentar salvar o registro",e,true);
            }finally {
                close();
            }
        });
    }
    @FXML
    void exportar(ActionEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            tabela();
            loadFactory();
            departamentos = new DepartamentosImpl(getManager());
            tbPrincipal.getItems().addAll(departamentos.getAllByName());
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","Erro ao listar registros","",e,true);
        }finally {
            close();
        }
    }

    @FXML
    void novo(ActionEvent event){
        cadastrarDepartamento(-1,new Departamento());
    }

    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }

    private void tabela(){
        TableColumn<Departamento, Long> colunaId = new TableColumn<>("Nome");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Departamento, String> colunaEmail = new TableColumn<>("E-mail");
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaEmail.setCellFactory(param -> new TableCell<Departamento,String>(){
            final JFXTextArea textArea = new JFXTextArea();
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                textArea.setEditable(false);
                textArea.setWrapText(true);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    textArea.setText(item);
                    setGraphic(textArea);
                    setText(null);
                }
            }
        });

        TableColumn<Departamento, Number> colunaEditar = new  TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<Departamento,Number>(){
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
                    button.setOnAction(event -> cadastrarDepartamento(getIndex(),tbPrincipal.getItems().get(getIndex())));
                    setGraphic(button);
                }
            }
        });

        tbPrincipal.getColumns().addAll(colunaId,colunaEmail,colunaEditar);
        tbPrincipal.setFixedCellSize(50);
    }
}
