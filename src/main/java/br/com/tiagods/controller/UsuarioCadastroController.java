package br.com.tiagods.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fxutils.maskedtextfield.MaskedTextField;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.CriptografiaUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UsuarioCadastroController extends UtilsController implements Initializable {
    @FXML
    private AnchorPane pnCadastro;

    @FXML
    private JFXTextField txCodigo;

    @FXML
    private JFXTextField txNome;

    @FXML
    private Pane pnPessoaFisica;

    @FXML
    private JFXTextField txRG;

    @FXML
    private MaskedTextField txCPF;

    @FXML
    private MaskedTextField txDataNascimento;

    @FXML
    private JFXTextField txEmail;

    @FXML
    private MaskedTextField txCEP;

    @FXML
    private JFXTextField txLogradouro;

    @FXML
    private JFXTextField txBairro;

    @FXML
    private JFXTextField txComplemento;

    @FXML
    private JFXComboBox<Cidade.Estado> cbEstado;

    @FXML
    private JFXComboBox<Cidade> cbCidade;

    @FXML
    private MaskedTextField txTelefone;

    @FXML
    private MaskedTextField txCelular;

    @FXML
    private JFXTextField txNumero;

    @FXML
    private JFXTextField txLogin;

    @FXML
    private JFXPasswordField txSenha;

    @FXML
    private JFXPasswordField txConfirmarSenha;

    @FXML
    private JFXComboBox<?> cbNivel;
    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnSair;

	private Usuario usuario = null;
	private Stage stage;
	private UsuariosImpl usuarios;

	public UsuarioCadastroController(Usuario usuario, Stage stage) {
		this.stage = stage;
		this.usuario=usuario;
	}
    @FXML
    void bucarCep(ActionEvent event){
        bucarCep(txCEP,txLogradouro,txNumero,txComplemento,txBairro,cbCidade,cbEstado);
    }
	private void combos(){
		comboRegiao(cbCidade,cbEstado,getManager());
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
		    super.loadFactory();
            combos();
            if(usuario!=null) {
                preencherFormulario(usuario);
            }
        }catch (Exception e){
            super.alert(Alert.AlertType.ERROR, "Erro", null,
                    "Falha ao listar os registros", e,true);
            e.printStackTrace();
        }finally {
		    super.close();
        }
    }
	void preencherFormulario(Usuario usuario) {
        txCodigo.setText(String.valueOf(usuario.getId()));
        txLogin.setText(usuario.getLogin());
        txLogin.setEditable(false);
        //PessoaFisica fisica = usuario.getPessoaFisica();
        //txRG.setText(fisica.getRg()==null?"":fisica.getRg());
        //txCPF.setPlainText(fisica.getCpf()==null?"":fisica.getCpf());
        //txDataNascimento.setPlainText(fisica.getAniversario()==null?"":fisica.getAniversario());
        //Pessoa pessoa = usuario.getPessoa();
        txNome.setText(usuario.getNome());
        txEmail.setText(usuario.getEmail());
        txTelefone.setPlainText(usuario.getTelefone());
        txCelular.setPlainText(usuario.getCelular());
        txCEP.setPlainText(usuario.getCep());
        txLogradouro.setText(usuario.getEndereco());
        txNumero.setText(usuario.getNumero());
	    txBairro.setText(usuario.getBairro());
	    txComplemento.setText(usuario.getComplemento());
	    cbEstado.setValue(usuario.getEstado());
	    cbCidade.setValue(usuario.getCidade());
	    this.usuario= usuario;
	}
	@FXML
	void salvar(ActionEvent event) {
        boolean validarLogin = false;
        if(txLogin.getText().trim().equals("")){
            super.alert(Alert.AlertType.ERROR, "Login Invalido", null, "Login não informado",null, false);
            return;
        }

        try {
            super.loadFactory();
            usuarios = new UsuariosImpl(getManager());
            //Pessoa pessoa = new Pessoa();
            if(txCodigo.getText().equals("")) {
                usuario = new Usuario();
                //pessoa.setCriadoEm(Calendar.getInstance());
                //pessoa.setCriadoPor(UsuarioLogado.getInstance().getUsuario());
                validarLogin = validarLogin(txLogin.getText());
                if (!validarLogin)
                    return;
                else
                    validarLogin = validarSenha();
            } else {
                usuario.setId(Long.parseLong(txCodigo.getText()));
                //pessoa = usuario.getPessoa();
                if (txSenha.getText().trim().equals(""))
                    validarLogin = true;
                else
                    validarLogin = validarSenha();
            }

//            PessoaFisica pessoaFisica = new PessoaFisica();
//            pessoaFisica.setRg(txRG.getText().trim());
//            pessoaFisica.setCpf(txCPF.getPlainText());
//            pessoaFisica.setAniversario(txDataNascimento.getPlainText());
//            usuario.setPessoaFisica(pessoaFisica);

            usuario.setNome(txNome.getText().trim());
            usuario.setEmail(txEmail.getText().trim());
            usuario.setTelefone(txTelefone.getPlainText());
            usuario.setCelular(txCelular.getPlainText());
            usuario.setCep(txCEP.getPlainText());
            usuario.setEndereco(txLogradouro.getText().trim());
            usuario.setNumero(txNumero.getText().trim());
            usuario.setBairro(txBairro.getText().trim());
            usuario.setComplemento(txComplemento.getText());
            usuario.setEstado(cbEstado.getValue());
            usuario.setCidade(cbCidade.getValue());
            usuario.setLogin(txLogin.getText());
            if (validarLogin) {
                if (!txSenha.getText().trim().equals("")) {
                    CriptografiaUtil cripto = new CriptografiaUtil();
                    usuario.setSenha(cripto.criptografar(txSenha.getText()));
                }
                try {
                    super.loadFactory();
                    usuarios = new UsuariosImpl(getManager());
                    usuario = usuarios.save(usuario);
                    preencherFormulario(usuario);
                    txSenha.setText("");
                    txConfirmarSenha.setText("");
                    alert(Alert.AlertType.INFORMATION,"Sucesso",null,
                            "Salvo com sucesso",null,false);
                    stage.close();
                } catch (Exception e) {
                    super.alert(Alert.AlertType.ERROR,"Erro",null,"Erro ao salvar o registro do Usuario",e,true);
                }
            }
        } catch (Exception e) {
        } finally {
            super.close();
        }
	}
	@FXML
    void sair(ActionEvent event){
	    stage.close();
    }
    private boolean validarLogin(String login){
        Usuario u = usuarios.findByLogin(login);
        if(u!=null) {
            super.alert(Alert.AlertType.ERROR,"Informação incompleta!","Login inválido!",
                    u.getNome()+" já esta usando esse login",null,false);
            return false;
        }
        else
            return true;
    }
    private boolean validarSenha() {
        if (txSenha.getText().trim().equals("")) {
            super.alert(Alert.AlertType.ERROR,"Informação incompleta!","Senhas em branco ou não conferem",
                    "Por favor verifique se a senha esta correta",null,false);
            return false;
        } else {
            if (txSenha.getText().trim().equals(txConfirmarSenha.getText().trim()))
                return true;
            else {
                super.alert(Alert.AlertType.ERROR,"Informação incompleta!","Senhas em branco ou não conferem",
                        "Por favor verifique se a senha esta correta",null,false);
                return false;
            }
        }
    }
}
