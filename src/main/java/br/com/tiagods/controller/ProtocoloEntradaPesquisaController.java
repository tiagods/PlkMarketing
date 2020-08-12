package br.com.tiagods.controller;

import br.com.tiagods.model.Usuario;
import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.repository.helpers.ProtocolosEntradasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.repository.helpers.filters.ProtocoloEntradaFilter;
import br.com.tiagods.repository.interfaces.Paginacao;
import br.com.tiagods.util.JavaFxUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ProtocoloEntradaPesquisaController implements Initializable {
	@FXML
	private HBox pnCheckBox;

	@FXML
	private JFXComboBox<ProtocoloEntrada.StatusRecebimento> cbRecebimento;

	@FXML
	private JFXComboBox<ProtocoloEntrada.StatusDevolucao> cbDevolucao;

	@FXML
	private HBox pnCheckBox1;

	@FXML
	private JFXDatePicker dataInicial;

	@FXML
	private JFXDatePicker dataFinal;

	@FXML
	private JFXTextField txPesquisa;

	@FXML
	private JFXComboBox<Usuario> cbAtendente;

	@FXML
	private JFXComboBox<ProtocoloEntrada.Classificacao> cbClassificar;

	@FXML
	private TableView<ProtocoloEntrada> tbPrincipal;

	@FXML
	private JFXComboBox<Integer> cbLimite;

	@FXML
	private JFXRadioButton rbAdministrativo;

	@FXML
	private JFXRadioButton rbComum;

	@FXML
	private Pagination pagination;

	private Stage stage;
	private ProtocoloEntradaFilter filter;
	private UsuariosImpl usuarios;
	private ProtocolosEntradasImpl protocolos;
	private TabelaProtocoloEntrada auxProtocolo;

	public void setPropriedades(Stage stage, ProtocoloEntradaFilter filter) {
		this.stage = stage;
		this.filter = filter;
	}

	void combos(){
		ToggleGroup group = new ToggleGroup();
		group.getToggles().addAll(rbAdministrativo,rbComum);
		rbComum.setSelected(true);

		ChangeListener<Boolean> radio = (observable, oldValue, newValue) -> {
			tbPrincipal.refresh();
		};

		auxProtocolo.setUsuarioAtivos(usuarios.listarAtivos());

		Usuario usuarioTEMP = new Usuario(-1L,"Todos");
		cbAtendente.getItems().add(usuarioTEMP);
		cbAtendente.getItems().addAll(auxProtocolo.getUsuarioAtivos());
		cbLimite.getItems().addAll(JavaFxUtil.getLimiteTabela());

		cbAtendente.getSelectionModel().selectFirst();
		cbRecebimento.getItems().addAll(ProtocoloEntrada.StatusRecebimento.values());
		cbDevolucao.getItems().addAll(ProtocoloEntrada.StatusDevolucao.values());
		cbClassificar.getItems().addAll(ProtocoloEntrada.Classificacao.values());

		cbLimite.getSelectionModel().selectFirst();
		cbRecebimento.getSelectionModel().selectFirst();
		cbDevolucao.getSelectionModel().selectFirst();
		cbClassificar.getSelectionModel().selectFirst();

		txPesquisa.setVisible(false);

		ChangeListener change = (ChangeListener<Object>) (observable, oldValue, newValue) -> {
			if(cbClassificar.getValue().equals(ProtocoloEntrada.Classificacao.USUARIO)){
				cbAtendente.setVisible(true);
				txPesquisa.setVisible(false);
			}
			else{
				cbAtendente.setVisible(false);
				txPesquisa.setVisible(true);
			}
			auxProtocolo.setPaginacao(new Paginacao(cbLimite.getValue()));
			filtrar(auxProtocolo.getPaginacao());
        };
		if(filter!=null){
			cbRecebimento.setValue(filter.getRecebimento());
			cbDevolucao.setValue(filter.getDevolucao());
			if(filter.getUsuario()!=null &&
					filter.getUsuario().getDepartamento()!=null &&
					filter.getUsuario().getDepartamento().getId()==11){
				cbAtendente.setValue(usuarioTEMP);
				rbAdministrativo.setSelected(true);
			}
			else
				cbAtendente.setValue(filter.getUsuario());
			cbClassificar.setValue(filter.getClassificacao());
			dataInicial.setValue(filter.getDataInicial());
			dataFinal.setValue(filter.getDataFinal());
		}

		cbRecebimento.valueProperty().addListener(change);
		cbDevolucao.valueProperty().addListener(change);
		cbClassificar.valueProperty().addListener(change);
		cbAtendente.valueProperty().addListener(change);
		dataFinal.valueProperty().addListener(change);
		dataInicial.valueProperty().addListener(change);
		cbLimite.valueProperty().addListener(change);

		rbAdministrativo.selectedProperty().addListener(radio);
		rbComum.selectedProperty().addListener(radio);

		pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
			auxProtocolo.getPaginacao().setPaginaAtual(newValue.intValue());
			filtrar(auxProtocolo.getPaginacao());
		});
		auxProtocolo.setPaginacao(new Paginacao(cbLimite.getValue()));
	}

	@FXML
	void exportar(ActionEvent event) {
		JavaFxUtil.alert(AlertType.INFORMATION,"Ajuda","Recurso em desenvolvimento","Este recurso ainda não foi liberado...aguarde...",null,false);
	}

	public List<ProtocoloEntrada> filtrar(Paginacao paginacao){
		filter = new ProtocoloEntradaFilter();
		filter.setRecebimento(cbRecebimento.getValue());
		filter.setDevolucao(cbDevolucao.getValue());
		filter.setClassificacao(cbClassificar.getValue());
		filter.setDataInicial(dataInicial.getValue());
		filter.setDataFinal(dataFinal.getValue());
		filter.setPesquisa(txPesquisa.getText());
		filter.setUsuario(cbAtendente.getValue());

		Pair<List<ProtocoloEntrada>, Paginacao> list = protocolos.filtrar(paginacao, filter);

		if (paginacao != null) {
			auxProtocolo.setPaginacao(list.getValue());
			tbPrincipal.getItems().clear();
			tbPrincipal.getItems().addAll(list.getKey());
			pagination.setPageCount(paginacao.getTotalPaginas());
			pagination.setCurrentPageIndex(paginacao.getPaginaAtual());
		}
		return list.getKey();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		transaformarEm servico
		auxProtocolo = new TabelaProtocoloEntrada(this,tbPrincipal,rbAdministrativo,rbComum);
		combos();
		auxProtocolo.tabela();
		filtrar(auxProtocolo.getPaginacao());
	}
	@FXML
	void novo(ActionEvent event) {
		auxProtocolo.abrirCadastro(null);
	}

	@FXML
	void pesquisar(KeyEvent event) {
		filtrar(auxProtocolo.getPaginacao());
	}
	@FXML
	void sair(ActionEvent event) {
		this.stage.close();
	}
}

