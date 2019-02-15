package br.com.tiagods.controller;

import br.com.tiagods.model.Cliente;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.repository.helpers.ClientesImpl;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.fxutils.maskedtextfield.MaskTextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ImplantacaoProcessoCadastroController extends UtilsController implements Initializable {
    @FXML
    private MaskTextField txCliente;

    @FXML
    private Label txPacoteNome;

    @FXML
    private JFXButton btnCopiarAlterarPacote;

    @FXML
    private JFXButton btnEtapa;

    @FXML
    private Label txNomeCliente;

    @FXML
    private TableView<ImplantacaoProcessoEtapa> tbEtapa;

    private Stage stage;

    private ClientesImpl clientes;
    private Cliente clienteSelecionado;
    private ImplantacaoProcesso processo;

    public ImplantacaoProcessoCadastroController(Stage stage, ImplantacaoProcesso processo){
        this.stage=stage;
        this.processo=processo;
    }

    private void combos(){
        txCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.trim().equals("")){
                clienteSelecionado = null;
                txNomeCliente.setText("");
            }
            else{
                try {
                    loadFactory();
                    clientes = new ClientesImpl(getManager());
                    clienteSelecionado = clientes.findById(Long.parseLong(txCliente.getText()));
                    if (clienteSelecionado!=null) txNomeCliente.setText(clienteSelecionado.getNome());
                    else txNomeCliente.setText("");
                }catch (Exception e){
                    clienteSelecionado = null;
                }finally {
                    close();
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {

    }
}
