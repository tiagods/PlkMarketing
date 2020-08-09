package br.com.tiagods.controller;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.config.init.VersaoSistema;
import br.com.tiagods.controller.acesso.RecuperacaoController;
import br.com.tiagods.controller.acesso.TrocaSenhaController;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.Usuarios;
import br.com.tiagods.util.JavaFxUtil;
import br.com.tiagods.util.CriptografiaUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import io.reactivex.rxjava3.core.Observable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class LoginController implements Initializable {

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

    @Autowired UsuarioCadastroController usuarioCadastroController;
    @Autowired RecuperacaoController recuperacaoController;
    @Autowired TrocaSenhaController trocaSenhaController;
    VersaoSistema versaoSistema = VersaoSistema.getInstance();

    private boolean ultimoLogadoEncontrado = false;

    private void carregarUsuarios(Usuario usuario){
        Observable.fromArray(contas)
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
        contas = usuarios.findAllByAtivoOrderByNome(1);
        carregarUsuarios(null);

        txSenha.setFocusTraversable(true);
        txSenha.requestFocus();

        String detalhes = "Versão do Sistema: "+versaoSistema.getVersao()+" de "+versaoSistema.getDate();
        lbDetalhes.setText(detalhes);
        lbBanco.setText("Versao do Banco:" +versaoSistema.getVersaoBanco());
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
        Observable.just(cbNome.getValue())
                .flatMap(u -> validar(u, txSenha.getText()))
                .flatMap(c -> c.isPresent() ? Observable.just(c.get()) : Observable.error(new Exception("Usuario e senha invalidos")))
                .subscribe(on ->{
                    if(on.isSenhaResetada()) {
                        Stage stage = new Stage();
                        stageManager.switchScene(FxmlView.TROCASENHA, stage);
                        trocaSenhaController.setPropriedades(stage, on);
                    } else
                        stageManager.switchScene(FxmlView.MENU, null);
                    }, tr ->
                        JavaFxUtil.alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro", tr.getMessage(), null, false)
                );
    }

    Observable<Optional<Usuario>> validar(Usuario usuario, String senha) {
        Usuario u = usuario;
        if(u==null || senha==null || senha.trim().equals("")) {
            return Observable.error(new Exception("Usuario ou senha não informada"));
        }
        else {
            String novaSenha = new CriptografiaUtil().criptografar(senha);
            Optional<Usuario> result = contas.stream()
                    .filter(f -> f.getEmail().equals(u.getEmail()) && f.getSenha().equals(novaSenha))
                    .findFirst();
            return Observable.just(result);
        }
    }

    @FXML
    void esqueciASenha(ActionEvent event){
        logger.debug("Esqueci a senha acionado");
        Usuario ultimoUsuario =ultimoLogadoEncontrado ? cbNome.getValue() : null;
        Stage stage = stageManager.switchScene(FxmlView.RECUPERACAO_SENHA, new Stage());
        recuperacaoController.setPropriedades(stage, contas, ultimoUsuario);
    }
    @FXML
    void novoAcesso(ActionEvent event){
        logger.debug("Novo acesso acionado");
        Stage stage1 = stageManager.switchScene(FxmlView.USUARIO_CADASTRO, new Stage());
        usuarioCadastroController.setPropriedades(stage1, null);
        stage1.setOnHiding(e -> carregarUsuarios(usuarioCadastroController.getUsuario()));
    }
    @FXML
    void sair(ActionEvent event) {
        Platform.exit();
    }
}
