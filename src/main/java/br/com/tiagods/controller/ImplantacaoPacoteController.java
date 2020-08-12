package br.com.tiagods.controller;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.implantacao.ImplantacaoPacote;
import br.com.tiagods.model.implantacao.ImplantacaoPacoteEtapa;
import br.com.tiagods.repository.ImplantacaoPacotes;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Controller
public class ImplantacaoPacoteController implements Initializable, StageController{
    @FXML
    private JFXButton btnCadastrarPacote;

    @FXML
    private JFXButton btnCopiaDePacote;

    @FXML
    private TableView<ImplantacaoPacote> tbPacote;

    private Stage stage;

    @Autowired
    private ImplantacaoPacotes pacotes;

    @Override
    public void setPropriedades(Stage stage) {
        this.stage=stage;
    }

    private void cadastrarEtapas(ImplantacaoPacote pck){
        try {
            pck = pacotes.findById(pck.getId());
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.IMPLATACAO_PACOTE_CADASTRO);
            ImplantacaoPacoteEtapaController controller = new ImplantacaoPacoteEtapaController(pck,stage);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
        } catch (IOException ex) {
        }
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
            ImplantacaoPacote newPacote = salvar(pacote);
            if(newPacote!=null) {
                if (tableLocation == -1) tbPacote.getItems().addAll(pacote);
                else
                    tbPacote.getItems().set(tableLocation, pacote);
            }
        });
    }

    private void combos(){
        btnCadastrarPacote.setOnAction(event -> cadastrarPacote(-1,new ImplantacaoPacote()));
        tbPacote.getItems().addAll(pacotes.getAll());

        btnCopiaDePacote.setVisible(false);
        btnCopiaDePacote.setOnAction(event->{

            if(tbPacote.getItems().size()<2) alert(Alert.AlertType.ERROR,"Erro","","Precisa ter no minimo 2 pacotes cadastrados no sistema",null, false);
            else{
                //ao realizar esse processo, todos os registros do pacote de destino serão removidos
                List<ImplantacaoPacote> items = new ArrayList<>(tbPacote.getItems());
                ImplantacaoPacote origem = Pacote1(items,"Primeiro, selecione a origem");
                if(origem!=null){
                    items.remove(origem);
                    ImplantacaoPacote destino = Pacote1(items,"Agora selecione o destino");
                    if(destino!=null){
                        try{
                            loadFactory();
                            pacotes = new ImplantacaoPacotesImpl(getManager());
                            origem = pacotes.findById(origem.getId());
                            destino = pacotes.findById(destino.getId());
                            Set<ImplantacaoPacoteEtapa> copiaOrigem = origem.getEtapas();
                            Set<ImplantacaoPacoteEtapa> copiaDestino = new HashSet<>();
                        }catch (Exception e){
                            alert(Alert.AlertType.ERROR,"Erro","Erro ao carregar os registros","Ocorreu um erro ao carregar o registro",e,true);
                        } finally {
                            close();
                        }
                    }
                }

            }
        });
    }

    private ImplantacaoPacote Pacote1(List<ImplantacaoPacote> list, String header){
        List<ImplantacaoPacote> choices = new ArrayList<>();
        choices.addAll(list);
        ChoiceDialog<ImplantacaoPacote> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle("Copia de Pacotes");
        dialog.setHeaderText(header);
        dialog.setContentText("Escolha um pacote:");
        Optional<ImplantacaoPacote> result = dialog.showAndWait();
        if (result.isPresent()) return result.get();
        else return null;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabelaPacote();
        try {
            loadFactory();
            pacotes = new ImplantacaoPacotesImpl(getManager());
            combos();
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","Erro ao getAllFetchJoin registros","Ocorreu um erro ao getAllFetchJoin os registros",e,true);
        }finally {
            close();
        }
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
                        buttonTable(button, IconsEnum.BUTTON_RENAME);
                    }catch (IOException e) {
                    }
                    button.setOnAction(event -> cadastrarPacote(getIndex(),tbPacote.getItems().get(getIndex())));
                    setGraphic(button);
                }
            }
        });
        TableColumn<ImplantacaoPacote, Number> colunaEtapa = new  TableColumn<>("");
        colunaEtapa.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEtapa.setCellFactory(param -> new TableCell<ImplantacaoPacote,Number>(){
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
                    button.setOnAction(event -> {
                        ImplantacaoPacote pck = tbPacote.getItems().get(getIndex());
                        cadastrarEtapas(pck);
                    });
                    setGraphic(button);
                }
            }
        });
        tbPacote.getColumns().addAll(colunaNome,colunaDescricao,colunaCriadoEm,colunaEditar,colunaEtapa);
        tbPacote.setFixedCellSize(50);
    }
    private ImplantacaoPacote salvar(ImplantacaoPacote pacote){
        try{
            loadFactory();
            pacotes = new ImplantacaoPacotesImpl(getManager());
            ImplantacaoPacote pck = pacotes.save(pacote);
            return pck;
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","","Falha ao tentar salvar os registros!",e,true);
            return null;
        }finally {
            close();
        }
    }
    @FXML
    void sair(ActionEvent event){
        stage.close();
    }
}
