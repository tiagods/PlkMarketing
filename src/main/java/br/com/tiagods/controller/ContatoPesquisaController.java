package br.com.tiagods.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.model.ConstantesTemporarias;
import br.com.tiagods.model.Contato;
import br.com.tiagods.model.Contato.ContatoTipo;
import br.com.tiagods.model.Contato.PessoaTipo;
import br.com.tiagods.model.NegocioCategoria;
import br.com.tiagods.model.NegocioNivel;
import br.com.tiagods.model.NegocioOrigem;
import br.com.tiagods.model.NegocioServico;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.NegocioLista;
import br.com.tiagods.repository.helpers.ContatosImpl;
import br.com.tiagods.repository.helpers.NegocioCategoriasImpl;
import br.com.tiagods.repository.helpers.NegocioNiveisImpl;
import br.com.tiagods.repository.helpers.NegocioOrigensImpl;
import br.com.tiagods.repository.helpers.NegocioServicosImpl;
import br.com.tiagods.repository.helpers.NegociosListasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ContatoPesquisaController extends UtilsController implements Initializable{
	@FXML
    private HBox pnCheckBox;

    @FXML
    private JFXComboBox<Contato.PessoaTipo> cbTipo;

    @FXML
    private JFXComboBox<Contato.ContatoTipo> cbContatoTipo;

    @FXML
    private JFXComboBox<NegocioLista> cbLista;

    @FXML
    private JFXComboBox<NegocioCategoria> cbCategoria;

    @FXML
    private JFXComboBox<NegocioNivel> cbNivel;

    @FXML
    private JFXComboBox<NegocioOrigem> cbOrigem;

    @FXML
    private JFXComboBox<NegocioServico> cbServico;

    @FXML
    private JFXComboBox<Usuario> cbAtendente;

    @FXML
    private HBox pnCheckBox1;

    @FXML
    private JFXCheckBox ckConvite;

    @FXML
    private JFXCheckBox ckMaterial;

    @FXML
    private JFXCheckBox ckNewsletter;

    @FXML
    private JFXDatePicker dataInicial;

    @FXML
    private JFXDatePicker dataFinal;

    @FXML
    private JFXTextField txPesquisa;

    @FXML
    private JFXComboBox<String> cbClassificar;

    @FXML
    private TableView<Contato> tbPrincipal;

	private Stage stage;
	private ContatosImpl contatos;
	private NegocioNiveisImpl niveis;
	private NegocioCategoriasImpl categorias;
	private NegocioOrigensImpl origens;
	private NegocioServicosImpl servicos;
	private NegociosListasImpl listas;
	private UsuariosImpl usuarios;
	
	public ContatoPesquisaController(Stage stage) {
		this.stage = stage;
	}
	void combos() {
		String todas="Todas";
		NegocioCategoria categoria = new NegocioCategoria(-1L,todas);
		NegocioNivel nivel = new NegocioNivel(-1L,todas);
		NegocioOrigem origem = new NegocioOrigem(-1L,todas);
		NegocioServico servico = new NegocioServico(-1L, todas);
		Usuario usuario = new Usuario(-1L,todas);
		NegocioLista lista = new NegocioLista(-1L, todas);
		
		cbCategoria.getItems().add(categoria);
		cbNivel.getItems().add(nivel);
		cbOrigem.getItems().add(origem);
		cbServico.getItems().add(servico);
		cbAtendente.getItems().add(usuario);
		cbLista.getItems().add(lista);
		
		contatos = new ContatosImpl(getManager());
		categorias = new NegocioCategoriasImpl(getManager());
		niveis = new NegocioNiveisImpl(getManager());
		origens = new NegocioOrigensImpl(getManager());
		servicos = new NegocioServicosImpl(getManager());
		listas = new NegociosListasImpl(getManager());
		usuarios = new UsuariosImpl(getManager());
		
		cbCategoria.getItems().addAll(categorias.getAll());
		cbNivel.getItems().addAll(niveis.getAll());
		cbOrigem.getItems().addAll(origens.getAll());
		cbServico.getItems().addAll(servicos.getAll());
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
		//cbLista.getItems().addAll(listas.getAll());
		
		cbTipo.getItems().addAll(PessoaTipo.values());
		cbContatoTipo.getItems().addAll(ContatoTipo.values());
		
		cbCategoria.getSelectionModel().selectFirst();
		cbNivel.getSelectionModel().selectFirst();
		cbOrigem.getSelectionModel().selectFirst();
		cbServico.getSelectionModel().selectFirst(); 
		cbAtendente.getSelectionModel().selectFirst();
		//cbLista.getSelectionModel().selectFirst();
		
		cbTipo.getSelectionModel().select(PessoaTipo.TODAS);
		cbContatoTipo.getSelectionModel().select(ContatoTipo.TODAS);
		
		tbPrincipal.getItems().addAll(contatos.getAll());
	}
	@FXML
	void exportar(ActionEvent event) {

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		try {
			loadFactory();
			combos();
		}catch(PersistenceException e) {
			alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
		}finally {
			close();
		}	
	}
	
	@FXML
	void novo(ActionEvent event) {
		
	}

	@FXML
	void sair(ActionEvent event) {
		stage.close();
	}
	void tabela() {
		
	}
}
