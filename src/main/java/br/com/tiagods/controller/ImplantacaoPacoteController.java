package br.com.tiagods.controller;

import br.com.tiagods.model.UsuarioDepartamento;
import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import br.com.tiagods.model.implantacao.ImplantacaoPacote;
import br.com.tiagods.model.implantacao.ImplantacaoPacoteEtapa;
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

import java.net.URL;
import java.util.*;

public class ImplantacaoPacoteController extends UtilsController implements Initializable{

    @FXML
    private TableView<ImplantacaoPacote> tbPacote;

    @FXML
    private TableView<ImplantacaoPacoteEtapa> tbEtapa;

    @FXML
    private JFXButton btnCadastrarPacote;

    private Stage stage;

    public ImplantacaoPacoteController(Stage stage) {
        this.stage=stage;
    }

    void cadastrarPacote(ImplantacaoPacote pacote){
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Cadastro de Pacotes");
        boolean novoRegistro = pacote.getId()==null;
        dialog.setHeaderText(novoRegistro?"Informe os campos abaixo para cadastrar o novo pacote":"Informe os campos abaixo para atualizar o pacote");

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

        JFXTextField pacoteNome = new JFXTextField();
        pacoteNome.setPromptText("Nome");
        pacoteNome.setText(pacote.getNome());
        JFXTextField pacoteDescricao = new JFXTextField();
        pacoteDescricao.setPromptText("Descricao");
        pacoteDescricao.setText(pacote.getDescricao());

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(pacoteNome, 1, 0);
        grid.add(new Label("Descrição:"), 0, 1);
        grid.add(pacoteDescricao, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node salvarButton = dialog.getDialogPane().lookupButton(loginButtonType);
        salvarButton.setDisable(true);
        // Do some validation (using the Java 8 lambda syntax).
        pacoteNome.textProperty().addListener((observable, oldValue, newValue) -> {
            salvarButton.setDisable(newValue.trim().isEmpty());
        });
        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> pacoteNome.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(pacoteNome.getText(), pacoteDescricao.getText());
            }
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            pacote.setNome(pair.getKey());
            pacote.setDescricao(pair.getValue());
            if(novoRegistro)
                tbPacote.getItems().addAll(pacote);
            else{
                Optional<ImplantacaoPacote> res = tbPacote.getItems().stream().filter(f->f.getId()==pacote.getId()).findFirst();
                if(res.isPresent()){
                    int i = tbPacote.getItems().indexOf(res.get());
                    tbPacote.getItems().set(i,pacote);
                }
            }
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabelaPacote();
        tabelaEtapa();

        btnCadastrarPacote.setOnAction(event -> cadastrarPacote(new ImplantacaoPacote()));

        ImplantacaoPacote teste = new ImplantacaoPacote();
        teste.setId(1L);
        teste.setNome("Padrao");
        teste.setDescricao("xxx");
        teste.setCriadoEm(Calendar.getInstance());

        UsuarioDepartamento departamento = new UsuarioDepartamento();
        departamento.setNome("Contabilidade");
        ImplantacaoAtividade atividade = new ImplantacaoAtividade();
        atividade.setNome("Abertura de Empresa");
        ImplantacaoPacoteEtapa etapa = new ImplantacaoPacoteEtapa();
        etapa.setAtividade(atividade);
        etapa.setDepartamento(departamento);
        teste.getEtapas().add(etapa);

        teste.getEtapas().add(etapa);

        tbPacote.getItems().addAll(teste);

        tbPacote.setOnMouseClicked(event -> {
            tbEtapa.getItems().clear();
            ImplantacaoPacote pck = tbPacote.getSelectionModel().getSelectedItem();
            if(pck!=null) tbEtapa.getItems().addAll(pck.getEtapas());
        });
    }

    void tabelaEtapa(){
        TableColumn<ImplantacaoPacoteEtapa, ImplantacaoPacoteEtapa.Etapa> colunaNome = new TableColumn<>("Etapa");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("etapa"));

        TableColumn<ImplantacaoPacoteEtapa, ImplantacaoAtividade> colunaAtividade = new TableColumn<>("Atividade");
        colunaAtividade.setCellValueFactory(new PropertyValueFactory<>("atividade"));

        TableColumn<ImplantacaoPacoteEtapa, UsuarioDepartamento> colunaDepartamento = new TableColumn<>("Departamento");
        colunaDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));

        TableColumn<ImplantacaoPacoteEtapa, Integer> colunaTempo = new TableColumn<>("Tempo(dias)");
        colunaTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));

        tbEtapa.getColumns().addAll(colunaNome,colunaAtividade,colunaDepartamento,colunaTempo);

    }

    void tabelaPacote(){
        TableColumn<ImplantacaoPacote, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<ImplantacaoPacote, String> colunaDescricao = new  TableColumn<>("Descricao");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaDescricao.setCellFactory((TableColumn<ImplantacaoPacote, String> param) -> {
            final TableCell<ImplantacaoPacote, String> cell = new TableCell<ImplantacaoPacote, String>() {
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
            };
            return cell;
        });
        colunaDescricao.setPrefWidth(100);
        TableColumn<ImplantacaoPacote, Calendar> colunaCriadoEm = new TableColumn<>("Data");
        colunaCriadoEm.setCellValueFactory(new PropertyValueFactory<>("criadoEm"));
        colunaCriadoEm.setCellFactory(param -> new TableCell<ImplantacaoPacote, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    setText(sdf.format(item.getTime()));
                }
            }
        });
        colunaCriadoEm.setPrefWidth(120);
        tbPacote.getColumns().addAll(colunaNome,colunaDescricao,colunaCriadoEm);
        tbPacote.setFixedCellSize(50);
    }

    @FXML
    void sair(ActionEvent event){
        stage.close();
    }
}
