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

public class ImplantacaoPacoteController extends UtilsController implements Initializable{

    @FXML
    private TableView<ImplantacaoPacote> tbPacote;

    @FXML
    private TableView<ImplantacaoPacoteEtapa> tbEtapa;

    @FXML
    private JFXButton btnCadastrarPacote;

    @FXML
    private JFXButton btnNovaEtapa;

    private Stage stage;

    private ImplantacaoPacotesImpl pacotes;

    private ImplantacaoPacote pacote;

    public ImplantacaoPacoteController(Stage stage) {
        this.stage=stage;
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
        btnCadastrarPacote.setOnAction(event -> cadastrarPacote(-1,new ImplantacaoPacote()));

        btnNovaEtapa.setOnAction(event -> {
            ImplantacaoPacote pacote = tbPacote.getSelectionModel().getSelectedItem();
            if(pacote!=null) {
                ImplantacaoPacoteEtapa etapa = new ImplantacaoPacoteEtapa();
                etapa.setPacote(pacote);
                cadastrarEtapa(false,-1,etapa);
            }
            else
                alert(Alert.AlertType.ERROR,"Erro","Pacote não selecionado","Por favor, selecione um pacote e tente novamente",null, false);
        });
        tbPacote.getItems().addAll(pacotes.getAll());
        tbPacote.setOnMouseClicked(event -> {
            salvar();

            ImplantacaoPacote pck = tbPacote.getSelectionModel().getSelectedItem();
            if (pck != null) {
                try {
                    loadFactory();
                    pacotes = new ImplantacaoPacotesImpl(getManager());
                    pck = pacotes.findById(pck.getId());
                    tbEtapa.getItems().addAll(pck.getEtapas());
                    this.pacote = pck;
                }catch (Exception e){
                    alert(Alert.AlertType.ERROR,"Erro","Erro ao carregar os registros","Ocorreu um erro ao carregar o registro",e,true);
                }finally {
                    close();
                }

            }
            else this.pacote = null;
        });

    }

    private void cadastrarPacote(int tableLocation,ImplantacaoPacote pacote){
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Cadastro de Pacotes");
        boolean novoRegistro = pacote.getId()==null;
        dialog.setHeaderText(novoRegistro?"Informe os campos abaixo para cadastrar um novo registro":"Informe os campos abaixo para atualizar o registro");

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
        Label label = new Label("");
        label.setPrefWidth(100);
        grid.add(label, 2, 0);

        grid.add(new Label("Descrição:"), 0, 1);
        grid.add(pacoteDescricao, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node salvarButton = dialog.getDialogPane().lookupButton(loginButtonType);
        salvarButton.setDisable(true);
        // Do some validation (using the Java 8 lambda syntax).
        pacoteNome.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.trim().isEmpty()) salvarButton.setDisable(true);
            else{
                Optional<ImplantacaoPacote> result = tbPacote.getItems().stream().filter(f-> f.getNome().equalsIgnoreCase(newValue.trim())).findFirst();
                salvarButton.setDisable(result.isPresent());
                if(result.isPresent())
                    label.setText("Registro já existe");
                else label.setText("Válido");
            }
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

            if(tableLocation==-1) tbPacote.getItems().addAll(pacote);
            else
                tbPacote.getItems().set(tableLocation, pacote);

        });
        tbEtapa.setSortPolicy(param -> {
            Comparator<ImplantacaoPacoteEtapa> comparator = Comparator.comparing(c->c.getAtividade().getNome());
            tbEtapa.getItems().sort(comparator.thenComparingInt(c->c.getEtapa().getValor()));
            return true;
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabelaPacote();
        tabelaEtapa();
        try {
            loadFactory();
            pacotes = new ImplantacaoPacotesImpl(getManager());
            combos();
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","Erro ao listar registros","Ocorreu um erro ao listar os registros",e,true);
        }finally {
            close();
        }
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

        tbEtapa.getColumns().addAll(colunaNome,colunaAtividade,colunaDescricao,colunaDepartamento,colunaTempo,colunaEditar);

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
        TableColumn<ImplantacaoPacote, Number> colunaEditar = new  TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<ImplantacaoPacote,Number>(){
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
                    }catch (IOException e) {
                    }
                    button.setOnAction(event -> cadastrarPacote(getIndex(),tbPacote.getItems().get(getIndex())));
                    setGraphic(button);
                }
            }
        });

        tbPacote.getColumns().addAll(colunaNome,colunaDescricao,colunaCriadoEm,colunaEditar);
        tbPacote.setFixedCellSize(50);
    }

    @FXML
    void salvar(ActionEvent event){
        salvar();
    }
    void salvar(){
        try{
            loadFactory();
            pacotes = new ImplantacaoPacotesImpl(getManager());
            pacotes.saveAll(tbPacote.getItems());
            tbPacote.getItems().clear();
            tbPacote.getItems().addAll(pacotes.getAll());
            tbEtapa.getItems().clear();
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
