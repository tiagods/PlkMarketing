package br.com.tiagods.controller;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.negocio.Franquia;
import br.com.tiagods.repository.Franquias;
import br.com.tiagods.repository.Usuarios;
import br.com.tiagods.util.DateUtil;
import br.com.tiagods.util.JavaFxUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.*;

@Controller
public class FranquiaPesquisaController implements Initializable, StageController {
    @FXML
    private HBox pnCheckBox;

    @FXML
    private JFXComboBox<Franquia.Tipo> cbTipo;

    @FXML
    private JFXComboBox<Usuario> cbAtendente;

    @FXML
    private HBox pnCheckBox1;

    @FXML
    private JFXTextField txPesquisa;

    @FXML
    private TableView<Franquia> tbPrincipal;
    @Autowired
    private Franquias franquias;
    @Autowired
	private Usuarios usuarios;
    private Stage stage;
	@Lazy
	@Autowired
	StageManager stageManager;

	@Autowired
	FranquiaCadastroController franquiaCadastroController;

	@Override
	public void setPropriedades(Stage stage) {
		this.stage = stage;
	}

	private void abrirCadastro(Franquia t) {
        Stage stage1 = stageManager.switchScene(FxmlView.FRANQUIA_CADASTRO, true);
        franquiaCadastroController.setPropriedades(stage1, t);
        stage1.setOnHiding(event -> filtrar());
    }

    void combos() {
        Usuario usuario = new Usuario(-1L, "Atendente");
        cbAtendente.getItems().add(usuario);

        cbTipo.getItems().addAll(Franquia.Tipo.values());
        cbAtendente.getItems().addAll(usuarios.findAll());

        cbTipo.getSelectionModel().select(Franquia.Tipo.TODOS);
        cbAtendente.getSelectionModel().selectFirst();

        ChangeListener<Object> change = (observable, oldValue, newValue) -> filtrar();
        cbTipo.valueProperty().addListener(change);
        cbAtendente.valueProperty().addListener(change);
        cbAtendente.setVisible(false);
    }

    @FXML
    void exportar(ActionEvent event) {

    }

    void filtrar() {
        tbPrincipal.getItems().clear();
        tbPrincipal.getItems().addAll(franquias.filtrar(txPesquisa.getText(), cbTipo.getValue(), cbAtendente.getValue()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabela();
        combos();
        tbPrincipal.getItems().addAll(franquias.findAll());
    }

    @FXML
    void novo(ActionEvent event) {
        abrirCadastro(null);
    }

    @FXML
    void pesquisar(KeyEvent event) {
	    filtrar();
    }

    @FXML
    void sair(ActionEvent event) {
        this.stage.close();
    }

    private boolean salvarStatus(Franquia franquia, boolean status) {
        Optional<Franquia> optional = franquias.findById(franquia.getId());
        if(optional.isPresent()){
            Franquia t = optional.get();
            t.setAtivo(status);
            t.setLastUpdate(Calendar.getInstance());
            franquias.save(t);
            return true;
        }
        return false;
    }

    void tabela() {
        TableColumn<Franquia, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Franquia, Boolean> colunaAtivo = new TableColumn<>("Ativo");
        colunaAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));
        colunaAtivo.setCellFactory(param -> new TableCell<Franquia, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    setText(item ? "Sim" : "Não");
                }
            }
        });
        TableColumn<Franquia, Franquia.Tipo> colunaTipo = new TableColumn<>("Tipo");
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Franquia, Calendar> colunaUpdate = new TableColumn<>("Atualização");
        colunaUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        colunaUpdate.setCellFactory(param -> new TableCell<Franquia, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    setText(DateUtil.format(item.getTime(), DateUtil.SDFH));
                }
            }
        });

        TableColumn<Franquia, Boolean> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("ativo"));
        colunaStatus.setCellFactory(param -> new TableCell<Franquia, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    JFXButton rb = new JFXButton();
                    rb.setText(item ? "Ativo" : "Inativo");
                    rb.getStyleClass().add(item ? "btGreen" : "btRed");

                    rb.onActionProperty().set(event -> {

                        Dialog dialog = new Dialog();
                        dialog.setTitle("Alteração de Status");
                        dialog.setHeaderText("Selecione um status");

                        VBox stackPane = new VBox();
                        stackPane.setSpacing(15);

                        Map<JFXRadioButton, Integer> map = new HashMap<>();
                        ToggleGroup group = new ToggleGroup();

                        int value = item ? 1 : 0;
                        for (int i = 0; i < 2; i++) {
                            JFXRadioButton jfxRadioButton = new JFXRadioButton(i == 0 ? "Inativo" : "Ativo");
                            jfxRadioButton.setSelectedColor(Color.GREEN);
                            jfxRadioButton.setUnSelectedColor(Color.RED);
                            if (value == i) jfxRadioButton.setSelected(true);
                            group.getToggles().add(jfxRadioButton);
                            stackPane.getChildren().add(jfxRadioButton);
                            map.put(jfxRadioButton, i);
                        }
                        ButtonType ok = new ButtonType("Alterar");
                        ButtonType cancelar = new ButtonType("Cancelar");
                        dialog.getDialogPane().getButtonTypes().addAll(ok, cancelar);
                        dialog.getDialogPane().setContent(stackPane);
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initStyle(StageStyle.UNDECORATED);
                        Optional<ButtonType> result = dialog.showAndWait();
                        if (result.get() == ok) {
                            Franquia franquia = tbPrincipal.getItems().get(getIndex());
                            for (Node f : stackPane.getChildren()) {
                                if (f instanceof JFXRadioButton && ((JFXRadioButton) f).isSelected()) {
                                    Integer p = map.get(f);
                                    if (p != value && salvarStatus(franquia, p == 1)) {
                                        tbPrincipal.getItems().get(getIndex()).setAtivo(p == 1);
                                        tbPrincipal.getItems().get(getIndex()).setLastUpdate(Calendar.getInstance());
                                        ;
                                        tbPrincipal.refresh();
                                    }
                                    break;
                                }
                            }
                            ;
                        }
                    });
                    setGraphic(rb);

                }
            }
        });

        TableColumn<Franquia, Number> colunaEditar = new TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<Franquia, Number>() {
            JFXButton button = new JFXButton();//Editar

            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                    setText("");
                    setGraphic(null);
                } else {
                    button.getStyleClass().add("btDefault");
                    JavaFxUtil.buttonTable(button, IconsEnum.BUTTON_EDIT);
                    button.setOnAction(event -> {
                        abrirCadastro(tbPrincipal.getItems().get(getIndex()));
                    });
                    setGraphic(button);
                }
            }
        });
        tbPrincipal.getColumns().addAll(colunaNome, colunaAtivo, colunaTipo, colunaUpdate, colunaStatus, colunaEditar);
    }

}
