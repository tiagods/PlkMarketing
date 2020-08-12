package br.com.tiagods.controller.acesso;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.Usuarios;
import br.com.tiagods.util.SendEmail;
import br.com.tiagods.util.JavaFxUtil;
import br.com.tiagods.util.CriptografiaUtil;
import com.jfoenix.controls.JFXPasswordField;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

@Controller
public class TrocaSenhaController implements Initializable {

    Logger logger =LoggerFactory.getLogger(getClass());

    @FXML
    private JFXPasswordField txNovaSenha;

    @FXML
    private JFXPasswordField txRepetir;

    @FXML
    private Label lbAviso;

    private Stage stage;
    private Usuario usuario;

    @Autowired
    Usuarios usuarios;

    private String GREEN="-fx-border-color:green;";
    private String RED="-fx-border-color:green;";

    @Lazy
    @Autowired
    private StageManager stageManager;

    public void setPropriedades(Stage stage, Usuario usuario){
        this.stage=stage;
        this.usuario=usuario;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            txRepetir.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!txNovaSenha.getText().trim().equals("")){
                if(txRepetir.getText().trim().equals(txNovaSenha.getText())){
                    txRepetir.setStyle(GREEN);
                    lbAviso.setText("");
                }
                else{
                    txRepetir.setStyle(RED);
                    lbAviso.setText("As senhas não conferem!");
                }
            }
        });
    }
    @FXML
    private void salvar(ActionEvent event){
        CriptografiaUtil cripto = new CriptografiaUtil();
        if (!txRepetir.getText().equals(txNovaSenha.getText())) {
            JavaFxUtil.alert(Alert.AlertType.ERROR,"Erro","Senhas não conferem","As senhas digitadas não conferem, tente novamente",null,false);
        }
        else if(usuario.getSenha().equals(cripto.criptografar(txNovaSenha.getText()))){
            logger.debug("Senha é a mesma da anterior "+txRepetir.getText()+":"+txNovaSenha.getText());
            JavaFxUtil.alert(Alert.AlertType.ERROR,"Erro","Senhas não permitida",
                    "A senha informada é igual a senha anterior, tente novamente",null,false);

        }
        else if(txRepetir.getText().equals(txNovaSenha.getText())){
            logger.debug("Senha ok "+txRepetir.getText()+":"+txNovaSenha.getText());
            cripto = new CriptografiaUtil();
            String senhaCriptografada = cripto.criptografar(txNovaSenha.getText());
            try{
                usuarios.getOne(usuario.getId());
                usuario.setSenha(senhaCriptografada);
                usuario.setSenhaResetada(false);
                usuario = usuarios.save(usuario);

                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        SendEmail email = new SendEmail();
                        StringBuilder builder = new StringBuilder();
                        builder.append("Prezado ").append(usuario.getNome()).append(";").append("\n\n")
                                .append("Uma alteração de senha foi realizada no sistema Controle de Processos,").append("\n\n")
                                .append("Para logar no sistema informe a seguinte senha:\n\n").append(txNovaSenha.getText()).append("\n\n")
                                .append("A senha é de sua segurança pessoal e instransferível!");
                        email.enviaAlerta("webmaster@prolinkcontabil.com.br","Suporte \\ Prolink Contabil", Arrays.asList(new String[]{usuario.getEmail()}),"Controle de Processos - Redefinição de Senha",builder.toString(),false);
                        return null;
                    }
                };

                new Thread(task).start();
                UsuarioLogado.getInstance().setUsuario(usuario);
                stageManager.switchScene(FxmlView.MENU, false);
                stage.close();
            }catch (Exception e){
                JavaFxUtil.alert(Alert.AlertType.ERROR,"Erro","Tente novamente","Falha ao solicitar recuperacao de senha",e,true);
            }
        }
    }
}
