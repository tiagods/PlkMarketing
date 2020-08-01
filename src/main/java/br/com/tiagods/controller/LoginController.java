package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.controller.acesso.RecuperacaoController;
import br.com.tiagods.controller.acesso.TrocaSenhaController;
import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.factory.ConnectionFactory;
import br.com.tiagods.repository.Registers;
import br.com.tiagods.repository.Usuarios;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.CriptografiaUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController extends UtilsController implements Initializable {

    Logger logger = LoggerFactory.getLogger(getClass());

    @FXML
    private JFXPasswordField txSenha;
    @FXML
    private JFXComboBox<Usuario> cbNome;
    @FXML
    private JFXButton btnEntrar;
    @FXML
    private Label lbDetalhes;
    @FXML
    private Label lbBanco;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnNovoAcesso;

    @Autowired
    private Usuarios usuarios;

    private Stage stage;
    @FXML
    private MediaView mediaView;

    @Lazy
    @Autowired
    private StageManager stageManager;

    private List<Usuario> contas;

    private boolean ultimoLogadoEncontrado = false;

    private void carregarUsuarios(Usuario usuario){
        Observable.fromArray(usuarios.findAllByAtivoOrderByNome(1))
                .flatMap(c1 -> {
                    cbNome.getItems().clear();
                    cbNome.getItems().addAll(c1);
                    cbNome.getSelectionModel().selectFirst();
                    return Observable.just(Optional.ofNullable(usuario));
                }).subscribe(on->{
                    if(on.isPresent()) cbNome.setValue(on.get());
                });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            /*
            Path path = Paths.get("C:\\Users\\Tiago\\Sunday Wordpress theme Video background.mp4");
            MediaPlayer player = new MediaPlayer(new Media(path.toUri().toURL().toExternalForm()));
            player.setOnEndOfMedia(() -> {
                player.seek(Duration.ZERO);
                player.play();
            });
            mediaView.setMediaPlayer(player);
            player.play();
            */
        carregarUsuarios(null);

        txSenha.setFocusTraversable(true);
        txSenha.requestFocus();

        String detalhes = "Versão do Sistema: "+sistemaVersao.getVersao()+" de "+sistemaVersao.getDate();
        lbDetalhes.setText(detalhes);
        lbBanco.setText("Versao do Banco:" +sistemaVersao.getVersaoBanco());
        cbNome.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) ultimoLogadoEncontrado = true;
        });
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
        Usuario usuario = cbNome.getValue();

        Observable.just(usuario)
                .flatMap(u -> validar(u, txSenha.getText()))
                .flatMap(o -> Observable.just(usuarios.findByEmailAndSenha(o.getEmail(), o.getSenha())))
                .flatMap(c -> c.isPresent() ? Observable.just(c.get()) : Observable.error(new Exception("Usuario e senha invalidos")))
                .subscribe(on->{
                    if(on.isSenhaResetada()) {
                        //stageManager.switchScene(FxmlView.TROCASENHA);
                        //trocaSenhaController.setConta(c1);
                    } else {
                        stageManager.switchScene(FxmlView.MENU, null);
                    }
                });

        if (cbNome.getValue() == null || txSenha.getText().equals("")) {
            alert(Alert.AlertType.ERROR,"Erro",null,"Usuario ou senha em branco!",null,false);
            return;
        } else {

            Optional<Usuario> result = usuarios.findByEmailAndSenha(
                    cbNome.getValue().getEmail(),
                    new CriptografiaUtil().criptografar(txSenha.getText().trim())
            );
            if(result.isPresent()){
                Usuario conta = result.get();

                if(conta.isSenhaResetada()){
                    try {
                        Stage stage1 = new Stage();
                        FXMLLoader loader = loaderFxml(FXMLEnum.TROCA_SENHA);
                        loader.setController(new TrocaSenhaController(stage1,conta));
                        initPanel(loader, stage1, Modality.WINDOW_MODAL, StageStyle.DECORATED);
                    }catch (IOException ex) {
                        alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao abrir fxml", ex, false);
                    }
                }
                else {
                    try {
                        UsuarioLogado.getInstance().setUsuario(conta);
                        Stage stage1 = new Stage();
                        FXMLLoader loader = loaderFxml(FXMLEnum.MAIN);
                        loader.setController(new MenuController());
                        initPanel(loader, stage1, Modality.WINDOW_MODAL, StageStyle.DECORATED);
                        stage.close();
                    }catch (IOException ex) {
                        alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao abrir fxml", ex, false);
                    }
                }
            } else {
                alert(Alert.AlertType.ERROR, "Erro", null, "usuario ou senha inválidos", null, false);
                txSenha.setText("");
            }
        }
    }

    Observable<Usuario> validar(Usuario u, String senha) {
        if(u==null || senha==null || senha.trim().equals("")) {
            return Observable.error(new Exception("Usuario ou senha não informada"));
        }
        else {
            u.setSenha(new CriptografiaUtil().criptografar(txSenha.getText().trim()));
            return Observable.just(u);
        }
    }

    @FXML
    void esqueciASenha(ActionEvent event){
        logger.debug("Esqueci a senha acionado");
        try {
            Stage stage1 = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.RECUPERACAO_SENHA);
            Usuario ultimoUsuario =ultimoLogadoEncontrado? cbNome.getValue():null;
            loader.setController(new RecuperacaoController(stage1,contas, ultimoUsuario));
            initPanel(loader, stage1, Modality.WINDOW_MODAL, StageStyle.DECORATED);
        }catch (IOException ex) {
            alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao abrir fxml", ex, false);
        }
    }
    @FXML
    void novoAcesso(ActionEvent event){
        logger.debug("Novo acesso acionado");
        try {
            Stage stage1 = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.USUARIO_CADASTRO);
            UsuarioCadastroController controller = new UsuarioCadastroController(null,stage1);
            loader.setController(controller);
            initPanel(loader, stage1, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage1.setOnHiding(e -> carregarUsuarios(controller.getUsuario()));
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.USUARIO_CADASTRO,e,true);
        }
    }
    @FXML
    void sair(ActionEvent event) {
        System.exit(0);
    }
}
