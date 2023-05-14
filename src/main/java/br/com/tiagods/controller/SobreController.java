package br.com.tiagods.controller;

import br.com.tiagods.config.init.VersaoSistema;
import br.com.tiagods.model.VersaoApp;
import br.com.tiagods.repository.VersaoAppRepository;
import br.com.tiagods.util.DateUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class SobreController implements Initializable, StageController {

    @FXML
    private Label lbBanco;

    @FXML
    private Label lbDetalhes;

    @FXML
    private JFXTextArea txDescricao;

    private Stage stage;

    @FXML
    private JFXComboBox<VersaoApp> cbVersao;

    VersaoSistema versaoSistema = VersaoSistema.getInstance();

    @Autowired
    VersaoAppRepository repository;

    private void atualizarDetalhes(VersaoApp versao){
        StringBuilder builder = new StringBuilder();
        builder.append("Versao: ")
                .append(versao.getVersao())
                .append("\n")
                .append("Data: ")
                .append(DateUtil.format(versao.getHistorico().getTime(), DateUtil.SDF))
                .append("\n\n").append(versao.getDetalhes());
        txDescricao.setText(builder.toString().toUpperCase());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<VersaoApp> versaoList = repository.findAllByOrderByIdDesc();
        cbVersao.getItems().clear();
        cbVersao.getItems().addAll(versaoList);

        cbVersao.getSelectionModel().selectFirst();

        atualizarDetalhes(cbVersao.getValue());

        cbVersao.valueProperty().addListener((observable, oldValue, newValue) -> { if(newValue!=null) atualizarDetalhes(newValue);});

        String detalhes = "Versão do Sistema: "+versaoSistema.getVersao()+" de "+versaoSistema.getDate();
        lbDetalhes.setText(detalhes);
        lbBanco.setText("Versao do Banco:" +versaoSistema.getVersaoBanco());
    }
    @FXML
    void sair(ActionEvent event) {
        this.stage.close();
    }

    @Override
    public void setPropriedades(Stage stage) {
        this.stage = stage;
    }
}
