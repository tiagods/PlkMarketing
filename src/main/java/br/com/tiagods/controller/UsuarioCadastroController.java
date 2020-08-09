package br.com.tiagods.controller;

import br.com.tiagods.model.Cidade;
import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.PessoaFisica;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.UsuariosDepartamentos;
import br.com.tiagods.repository.Usuarios;
import br.com.tiagods.util.JavaFxUtil;
import br.com.tiagods.util.CepUtil;
import br.com.tiagods.util.CriptografiaUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.fxutils.maskedtextfield.MaskedTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class UsuarioCadastroController  implements Initializable {
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
    private JFXPasswordField txSenha;

    @FXML
    private JFXPasswordField txConfirmarSenha;

    @FXML
    private JFXComboBox<Departamento> cbDepartamento;

    @FXML
    private JFXComboBox<String> cbStatus;

    @FXML
    private JFXButton btnSalvar;
    @FXML
    private JFXButton btnSair;

	private Usuario usuario;

	private Stage stage;

	@Autowired
	private Usuarios usuarios;

	@Autowired
    private UsuariosDepartamentos usuariosDepartamentos;

    @Autowired
    CepUtil cep;

    @FXML
    void bucarCep(ActionEvent event){
        cep.bucarCep(txCEP,txLogradouro,txNumero,txComplemento,txBairro,cbCidade,cbEstado);
    }

	private void combos(){
		cbStatus.getItems().add("Ativo");
        cbStatus.getItems().add("Inativo");
        cbStatus.getSelectionModel().selectFirst();

        List<Departamento> departamentoList = usuariosDepartamentos.findAllByOrderByNome();
        cbDepartamento.getItems().clear();
        cbDepartamento.getItems().addAll(departamentoList);
        //cbDepartamento.getSelectionModel().selectFirst();

        cep.comboRegiao(cbCidade,cbEstado);
    }

    public void setPropriedades(Stage stage, Usuario usuario) {
        this.stage = stage;
        this.usuario = usuario;
        if(usuario!=null) {
            preencherFormulario(usuario);
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        combos();
    }

    void preencherFormulario(Usuario usuario) {
        txCodigo.setText(String.valueOf(usuario.getId()));
        txEmail.setEditable(false);
        PessoaFisica fisica = usuario.getFisica();
        if(fisica!=null) {
	        txRG.setText(fisica.getRg()==null?"":fisica.getRg());
	        txCPF.setPlainText(fisica.getCpf()==null?"":fisica.getCpf());
	        txDataNascimento.setPlainText(fisica.getAniversario()==null?"":fisica.getAniversario());
        }
        txNome.setText(usuario.getNome());
        cbStatus.setValue(usuario.getAtivo()==1?"Ativo":"Inativo");
        cbDepartamento.setValue(usuario.getDepartamento());
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

        if(txEmail.getText().trim().equals("")){
            JavaFxUtil.alert(Alert.AlertType.ERROR, "E-mail Invalido", null, "E-mail não informado",null, false);
            return;
        }
        if(cbDepartamento.getValue()==null){
            JavaFxUtil.alert(Alert.AlertType.ERROR, "Departamento Invalido", null, "Departamento não informado",null, false);
            return;
        }
        try {
            boolean validarLogin = false;
            if(txCodigo.getText().equals("")) {
                usuario = new Usuario();
                validarLogin = validarLogin(txEmail.getText());
                if (!validarLogin)
                    return;
                else
                    validarLogin = validarSenha();
            } else {
                usuario.setId(Long.parseLong(txCodigo.getText()));
                if (txSenha.getText().trim().equals(""))
                    validarLogin = true;
                else
                    validarLogin = validarSenha();
            }
            System.out.println(validarLogin);
            if (validarLogin) {
                if (!txSenha.getText().trim().equals("")) {
                    CriptografiaUtil cripto = new CriptografiaUtil();
                    usuario.setSenha(cripto.criptografar(txSenha.getText()));
                }
                PessoaFisica pessoaFisica = new PessoaFisica();
                pessoaFisica.setRg(txRG.getText().trim());
                pessoaFisica.setCpf(txCPF.getPlainText());
                pessoaFisica.setAniversario(txDataNascimento.getPlainText());
                usuario.setFisica(pessoaFisica);
                usuario.setNome(txNome.getText().trim());
                usuario.setEmail(txEmail.getText().trim());
                usuario.setDepartamento(cbDepartamento.getValue());
                usuario.setTelefone(txTelefone.getPlainText());
                usuario.setCelular(txCelular.getPlainText());
                usuario.setCep(txCEP.getPlainText());
                usuario.setEndereco(txLogradouro.getText().trim());
                usuario.setNumero(txNumero.getText().trim());
                usuario.setBairro(txBairro.getText().trim());
                usuario.setComplemento(txComplemento.getText());
                usuario.setEstado(cbEstado.getValue());
                usuario.setCidade(cbCidade.getValue());
                if(cbStatus.getValue().equalsIgnoreCase("Ativo"))
                    usuario.setAtivo(1);
                else
                    usuario.setAtivo(0);
                usuario = usuarios.save(usuario);
                preencherFormulario(usuario);
                txSenha.setText("");
                txConfirmarSenha.setText("");
                JavaFxUtil.alert(Alert.AlertType.INFORMATION,"Sucesso",null,"Salvo com sucesso",null,false);
                stage.close();
            }
        } catch (Exception e) {
            JavaFxUtil.alert(Alert.AlertType.ERROR,"Erro",null,"Erro ao salvar o registro do Usuario",e,true);
        }
	}
	@FXML
    void sair(ActionEvent event){
	    stage.close();
    }
    private boolean validarLogin(String email){
        Optional<Usuario> optional = usuarios.findByEmail(email);
        if(optional.isPresent()) {
            JavaFxUtil.alert(Alert.AlertType.ERROR,"Informação incompleta!","Login inválido!",
                    optional.get().getNome()+" já esta usando esse login",null,false);
            return false;
        }
        else
            return true;
    }
    private boolean validarSenha() {
        if (txSenha.getText().trim().equals("")) {
            JavaFxUtil.alert(Alert.AlertType.ERROR,"Informação incompleta!","Senhas em branco ou não conferem",
                    "Por favor verifique se a senha esta correta",null,false);
            return false;
        } else {
            if (txSenha.getText().trim().equals(txConfirmarSenha.getText().trim()))
                return true;
            else {
                JavaFxUtil.alert(Alert.AlertType.ERROR,"Informação incompleta!","Senhas em branco ou não conferem",
                        "Por favor verifique se a senha esta correta",null,false);
                return false;
            }
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
