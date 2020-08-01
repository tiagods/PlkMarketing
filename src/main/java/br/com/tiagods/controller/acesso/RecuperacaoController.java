package br.com.tiagods.controller.acesso;

import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.Usuarios;
import br.com.tiagods.services.SendEmail;
import br.com.tiagods.util.CriptografiaUtil;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class RecuperacaoController extends UtilsController implements Initializable {

    private List<Usuario> contas;
    private Stage stage;
    @FXML
    private JFXComboBox<Usuario> cbLogin;

    private Usuario usuario;

    @Autowired Usuarios usuarios;

    public void setPropriedades(Stage stage, List<Usuario> contas, Usuario usuario){
        this.usuario = usuario;
        this.contas = contas;
        this.stage=stage;
        cbLogin.getItems().addAll(contas);
        if(usuario!=null) cbLogin.setValue(usuario);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void esqueciSenha(ActionEvent event){
        CriptografiaUtil criptografia = new CriptografiaUtil();
        String senhaNova = criptografia.gerarSenhaAleatoria();
        String senhaCriptografada = criptografia.criptografar(senhaNova);
        Optional<Usuario> result = usuarios.findById(cbLogin.getValue().getId());
        if(result.isPresent()) {
            usuario.setSenha(senhaCriptografada);
            usuario.setSenhaResetada(true);
            usuario = usuarios.save(usuario);
            SendEmail email = new SendEmail();
            StringBuilder builder = new StringBuilder();
            builder.append("Prezado ").append(usuario.getNome()).append(";").append("\n\n")
                    .append("Uma solicitação de nova senha foi gerada no sistema Controle de Processos,").append("\n\n")
                    .append("Para logar no sistema informe a seguinte senha:\n\n").append(senhaNova).append("\n\n")
                    .append("Entre no sistema e redefina uma nova senha");
            email.enviaAlerta("webmaster@prolinkcontabil.com.br", "Suporte \\ Prolink Contabil",
                    Arrays.asList(new String[]{usuario.getEmail()}), "Controle de Processos - Redefinição de Senha", builder.toString(), false);
            alert(Alert.AlertType.INFORMATION, "Aviso", "Verifique sua caixa postal",
                    "E-mail com senha foram enviados para a conta: " + usuario.getEmail(), null, false);
            stage.close();
        }
    }

}
