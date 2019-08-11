package br.com.tiagods.controller.acesso;

import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.CriptografiaUtil;
import br.com.tiagods.services.SendEmail;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class RecuperacaoController extends UtilsController implements Initializable {

    private List<Usuario> contas;
    private Stage stage;
    @FXML
    private JFXComboBox<Usuario> cbLogin;

    private Usuario usuario;

    public RecuperacaoController(Stage stage, List<Usuario> contas, Usuario usuario){
        this.usuario = usuario;
        this.contas = contas;
        this.stage=stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbLogin.getItems().addAll(contas);
        if(usuario!=null) cbLogin.setValue(usuario);
    }
    @FXML
    void esqueciSenha(ActionEvent event){
        CriptografiaUtil criptografia = new CriptografiaUtil();
        String senhaNova = criptografia.gerarSenhaAleatoria();
        String senhaCriptografada = criptografia.criptografar(senhaNova);

        try{
            loadFactory();
            UsuariosImpl usuarios = new UsuariosImpl(getManager());
            usuario = usuarios.findById(cbLogin.getValue().getId());
            usuario.setSenha(senhaCriptografada);
            usuario.setSenhaResetada(true);
            usuario = usuarios.save(usuario);
            SendEmail email = new SendEmail();
            StringBuilder builder = new StringBuilder();
            builder.append("Prezado ").append(usuario.getNome()).append(";").append("\n\n")
                    .append("Uma solicitação de nova senha foi gerada no sistema Controle de Processos,").append("\n\n")
                    .append("Para logar no sistema informe a seguinte senha:\n\n").append(senhaNova).append("\n\n")
                    .append("Entre no sistema e redefina uma nova senha");
            email.enviaAlerta("webmaster@prolinkcontabil.com.br","Suporte \\ Prolink Contabil",
                    Arrays.asList(new String[]{usuario.getEmail()}),"Controle de Processos - Redefinição de Senha",builder.toString(),false);
///
//            alert(Alert.AlertType.INFORMATION,"Aviso","Verifique sua caixa postal",
//                    "E-mail com senha foram enviados para a conta: "+usuario.getEmail(),null,false);

            stage.close();
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","Tente novamente","Falha ao solicitar recuperacao de senha",e,true);
        }finally {
            close();
        }

    }

}
