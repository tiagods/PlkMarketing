package br.com.tiagods.controller.acesso;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.controller.MenuController;
import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.repository.interfaces.StageController;
import br.com.tiagods.services.SendEmail;
import br.com.tiagods.util.CriptografiaUtil;
import com.jfoenix.controls.JFXPasswordField;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

@Controller
public class TrocaSenhaController extends UtilsController implements Initializable {

    Logger logger =LoggerFactory.getLogger(getClass());

    @FXML
    private JFXPasswordField txNovaSenha;

    @FXML
    private JFXPasswordField txRepetir;

    @FXML
    private Label lbAviso;

    private Stage stage;
    private Usuario usuario;

    private String GREEN="-fx-border-color:green;";
    private String RED="-fx-border-color:green;";

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
            alert(Alert.AlertType.ERROR,"Erro","Senhas não conferem","As senhas digitadas não conferem, tente novamente",null,false);
        }
        else if(usuario.getSenha().equals(cripto.criptografar(txNovaSenha.getText()))){
            logger.debug("Senha é a mesma da anterior "+txRepetir.getText()+":"+txNovaSenha.getText());
            alert(Alert.AlertType.ERROR,"Erro","Senhas não permitida",
                    "A senha informada é igual a senha anterior, tente novamente",null,false);

        }
        else if(txRepetir.getText().equals(txNovaSenha.getText())){
            logger.debug("Senha ok "+txRepetir.getText()+":"+txNovaSenha.getText());
            cripto = new CriptografiaUtil();
            String senhaCriptografada = cripto.criptografar(txNovaSenha.getText());
            try{
                loadFactory();
                UsuariosImpl usuarios = new UsuariosImpl(getManager());
                usuario = usuarios.findById(usuario.getId());
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
                Stage stage1 = new Stage();
                FXMLLoader loader = loaderFxml(FXMLEnum.MAIN);
                loader.setController(new MenuController());
                initPanel(loader, stage1, Modality.WINDOW_MODAL, StageStyle.DECORATED);
                stage.close();
            }catch (Exception e){
                alert(Alert.AlertType.ERROR,"Erro","Tente novamente","Falha ao solicitar recuperacao de senha",e,true);
            }finally {
                close();
            }
        }
    }
}
