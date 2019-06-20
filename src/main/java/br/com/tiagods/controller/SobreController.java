package br.com.tiagods.controller;

import br.com.tiagods.model.VersaoApp;
import br.com.tiagods.repository.helpers.VersaoAppImpl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SobreController extends UtilsController implements Initializable{

    @FXML
    private Label lbBanco;

    @FXML
    private Label lbDetalhes;

    @FXML
    private JFXTextArea txDescricao;

    private Stage stage;

    private VersaoAppImpl app;

    @FXML
    private JFXComboBox<VersaoApp> cbVersao;

    public SobreController(Stage stage){
        this.stage=stage;
    }

    private void atualizarDetalhes(VersaoApp versao){
        StringBuilder builder = new StringBuilder();
        builder.append("Versao: ")
                .append(versao.getVersao())
                .append("\n")
                .append("Data: ")
                .append(sdfH.format(versao.getHistorico().getTime()))
                .append("\n\n").append(versao.getDetalhes());
        txDescricao.setText(builder.toString().toUpperCase());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadFactory();

            app = new VersaoAppImpl(getManager());
            List<VersaoApp> versaoList = app.getAllDesc();

            cbVersao.getItems().clear();
            cbVersao.getItems().addAll(versaoList);

            cbVersao.getSelectionModel().selectFirst();

            atualizarDetalhes(cbVersao.getValue());

            cbVersao.valueProperty().addListener((observable, oldValue, newValue) -> { if(newValue!=null) atualizarDetalhes(newValue);});

            String detalhes = "Versão do Sistema: "+sistemaVersao.getVersao()+" de "+sistemaVersao.getDate();
            lbDetalhes.setText(detalhes);
            lbBanco.setText("Versao do Banco:" +sistemaVersao.getVersaoBanco());

        }catch(Exception e) {
            alert(Alert.AlertType.ERROR,"Login",null,"Erro ao listar Versoes",e,true);
        }finally {
            close();
        }
    }
    @FXML
    void sair(ActionEvent event) {
        this.stage.close();
    }

}
