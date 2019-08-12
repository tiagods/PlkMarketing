package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.com.tiagods.controller.acesso.RecuperacaoController;
import br.com.tiagods.controller.acesso.TrocaSenhaController;
import br.com.tiagods.controller.utils.UtilsController;
import com.jfoenix.controls.IFXTextInputControl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.CriptografiaUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class LoginController extends UtilsController implements Initializable{

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

    private UsuariosImpl usuarios;
    private Stage stage;
    @FXML
    private MediaView mediaView;

    private List<Usuario> contas;

    private boolean ultimoLogadoEncontrado = false;

    public LoginController(Stage stage){
        this.stage=stage;
    }

    private void carregarUsuarios(Usuario usuario){
        try {
            loadFactory();
            usuarios = new UsuariosImpl(getManager());
            contas = usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome);
            cbNome.getItems().clear();
            cbNome.getItems().addAll(contas);
            if(usuario!=null)
                cbNome.setValue(usuario);
            else
                cbNome.getSelectionModel().selectFirst();
        }catch(Exception e) {
            alert(Alert.AlertType.ERROR,"Login",null,"Erro ao listar Usuarios",e,true);
        }finally {
            close();
        }
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
        if(!UsuarioLogado.getInstance().lastLogin().equals("")){
            Optional<Usuario> result = contas.stream().filter(c->c.getLogin().equals(UsuarioLogado.getInstance().lastLogin())).findFirst();
            if(result.isPresent()) {
                this.ultimoLogadoEncontrado = true;
                cbNome.setValue(result.get());
            }
        }
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
        if (cbNome.getValue() == null || txSenha.getText().equals("")) {
            alert(Alert.AlertType.ERROR,"Erro",null,"Usuario ou senha em branco!",null,false);
            return;
        } else {
            try {
                loadFactory();
                usuarios = new UsuariosImpl(getManager());
                Usuario usuario = usuarios.findByLoginAndSenha(
                        cbNome.getValue().getLogin(),
                        new CriptografiaUtil().criptografar(txSenha.getText().trim())
                );
                if (usuario == null){
                    alert(Alert.AlertType.ERROR, "Erro", null, "usuario ou senha inválidos", null, false);
                    txSenha.setText("");
                }
                else if(usuario.isSenhaResetada()){
                    try {
                        Stage stage1 = new Stage();
                        FXMLLoader loader = loaderFxml(FXMLEnum.TROCA_SENHA);
                        loader.setController(new TrocaSenhaController(stage1,usuario));
                        initPanel(loader, stage1, Modality.WINDOW_MODAL, StageStyle.DECORATED);
                    }catch (IOException ex) {
                        alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao abrir fxml", ex, false);
                    }
                }
                else if (!usuario.isSenhaResetada()) {
                	try {
	                    UsuarioLogado.getInstance().setUsuario(usuario);
	                    Stage stage1 = new Stage();
	                    FXMLLoader loader = loaderFxml(FXMLEnum.MAIN);
	                    loader.setController(new MenuController());
	                    initPanel(loader, stage1, Modality.WINDOW_MODAL, StageStyle.DECORATED);
	                    stage.close();
                	}catch (IOException ex) {
                		alert(Alert.AlertType.ERROR, "Erro", null, "Falha ao abrir fxml", ex, false);
					}
                }

            }catch(Exception e) {
                super.alert(Alert.AlertType.ERROR,"Erro",null,"Falha ao buscar usuario",e,true);
                e.printStackTrace();
            }finally {
                close();
            }
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
