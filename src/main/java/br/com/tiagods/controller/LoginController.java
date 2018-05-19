package br.com.tiagods.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;

import br.com.tiagods.config.UsuarioLogado;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.exception.FXMLNaoEncontradoException;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.CriptografiaUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController extends UtilsController implements Initializable{
    @FXML
    private JFXPasswordField txSenha;
    @FXML
    private JFXComboBox<Usuario> cbNome;
    @FXML
    private JFXButton btnEntrar;
    @FXML
    private JFXButton btnCancelar;
    private UsuariosImpl usuarios;
    private Stage stage;

    public LoginController(Stage stage){
        this.stage=stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadFactory();
            usuarios = new UsuariosImpl(super.getManager());
            List<Usuario> contas = usuarios.filtrar("", 1, "pessoa.nome");
            cbNome.getItems().addAll(contas);
            cbNome.getSelectionModel().selectFirst();
            txSenha.setFocusTraversable(true);
            txSenha.requestFocus();
        }catch(Exception e) {
            super.alert(Alert.AlertType.ERROR,"Login",null,"Erro ao listar Logins",e,true);
        }finally {
            super.close();
        }
        txSenha.requestFocus();
    }

    @FXML
    public void enterAcionado(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            logon();
        }
    }
    @FXML
    public void entrar(ActionEvent event) {
        logon();
    }
    private void logon() {
        String mensagem;
        if (cbNome.getValue() == null || txSenha.getText().equals("")) {
            super.alert(Alert.AlertType.ERROR,"Erro",null,"Usuario ou senha em branco!",null,false);
            return;
        } else {
            try {
                super.loadFactory();
                usuarios = new UsuariosImpl(super.getManager());
                Usuario usuario = usuarios.findByLoginAndSenha(
                        cbNome.getValue().getLogin(),
                        new CriptografiaUtil().criptografar(txSenha.getText().trim())
                );
                if (usuario != null) {
                	try {
	                    UsuarioLogado.getInstance().setUsuario(usuario);
	                    Stage stage1 = new Stage();
	                    FXMLLoader loader = loaderFxml(FXMLEnum.MAIN);
	                    loader.setController(new MenuController());
	                    initPanel(loader, stage1, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
	                    stage.close();
                	}catch (FXMLNaoEncontradoException ex) {
                		super.alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao abrir fxml", ex, false);
					}
                }
                else {
                    super.alert(Alert.AlertType.ERROR, "Erro", null, "usuario ou senha inválidos", null, false);
                    txSenha.setText("");
                }
            }catch(Exception e) {
                super.alert(Alert.AlertType.ERROR,"Erro",null,"Falha ao buscar usuario",e,true);
                e.printStackTrace();
            }finally {
                super.close();
            }
        }
    }

    @FXML
    void sair(ActionEvent event) {
        System.exit(0);
    }
}
