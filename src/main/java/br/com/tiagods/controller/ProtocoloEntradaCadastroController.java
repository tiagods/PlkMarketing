package br.com.tiagods.controller;

import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.*;
import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.model.protocolo.ProtocoloItem;
import br.com.tiagods.repository.helpers.ClientesImpl;
import br.com.tiagods.repository.helpers.ProtocolosEntradasImpl;
import br.com.tiagods.repository.helpers.DepartamentosImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.alerta.AlertaProtocolo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.fxutils.maskedtextfield.MaskTextField;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ProtocoloEntradaCadastroController extends UtilsController implements Initializable {
    @FXML
    private JFXTextField txCodigo;

    @FXML
    private JFXTextField txEntreguePor;

    @FXML
    private MaskTextField txCliente;

    @FXML
    private JFXTextArea txClienteNome;

    @FXML
    private JFXComboBox<Departamento> cbDepartamento;

    @FXML
    private JFXComboBox<Usuario> cbFuncionario;

    @FXML
    private JFXTextArea txObservacao;

    @FXML
    private JFXComboBox<String> cbItemNome;

    @FXML
    private JFXComboBox<Integer> cbItemQuantidade;

    @FXML
    private JFXTextArea txDetalhes;

    @FXML
    private JFXButton btItemIncluir;

    @FXML
    private TableView<ProtocoloItem> tbItem;

    private Stage stage;
    private ProtocoloEntrada protocolo;
    private ProtocolosEntradasImpl entradas;
    private DepartamentosImpl departamentos;
    private UsuariosImpl usuarios;
    private ClientesImpl clientes;
    private Cliente cliente;

    public ProtocoloEntradaCadastroController(Stage stage, ProtocoloEntrada protocolo) {
        this.stage = stage;
        this.protocolo = protocolo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tabela();
            loadFactory();
            combos();
            if(protocolo!=null){
                preencherFormulario(protocolo);
            }
        } catch (PersistenceException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao realizar consulta", e, true);
        } finally {
            close();
        }
    }

    void combos(){
        cbItemNome.getItems().addAll(nomeItems());

        for(int i = 1; i<=100;i++) cbItemQuantidade.getItems().add(i);

        departamentos = new DepartamentosImpl(getManager());
        cbDepartamento.getItems().addAll(departamentos.getAll());

        cbDepartamento.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    loadFactory();
                    usuarios = new UsuariosImpl(getManager());
                    cbFuncionario.getItems().clear();
                    cbFuncionario.getItems().addAll(usuarios.getUsuariosByDepartamento(newValue));
                    cbFuncionario.getSelectionModel().selectFirst();
                } catch (PersistenceException e) {
                    alert(Alert.AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao realizar consulta", e, true);
                } finally {
                    close();
                }
            }
        });
        cbDepartamento.getSelectionModel().selectFirst();

        cbItemQuantidade.setValue(1);
        cbItemNome.setValue("Outros");
    }
    private ObservableList<String> nomeItems() {
        ObservableList<String> itensNomes = FXCollections.observableArrayList();
        itensNomes.add("Aviso de Férias");
        itensNomes.add("Carimbo");
        itensNomes.add("Carta de Recomendação");
        itensNomes.add("Carteira de Trabalho");
        itensNomes.add("Certificado Digital A1");
        itensNomes.add("Certificado Digital A3");
        itensNomes.add("Comprovantes de Pagamentos Diversos");
        itensNomes.add("Diário Geral");
        itensNomes.add("Documentos Admissionais");
        itensNomes.add("Documentos de Abertura");
        itensNomes.add("Documentos de Alteração");
        itensNomes.add("Documentos de Encerramento");
        itensNomes.add("Documentos Demissionais");
        itensNomes.add("Documentos para Alteração Contratual");
        itensNomes.add("Documentos para Homologação");
        itensNomes.add("Documentos para Implantação");
        itensNomes.add("Documentos para Imposto de Renda");
        itensNomes.add("Documentos para Processos - Prefeitura");
        itensNomes.add("Documentos para Processos - Receita");
        itensNomes.add("Extrato");
        itensNomes.add("FGTS");
        itensNomes.add("DAS");
        itensNomes.add("DARF");
        itensNomes.add("GPS");
        itensNomes.add("Ficha de Plano de Saude");
        itensNomes.add("Guia Previdência Social");
        itensNomes.add("Livro de Registro");
        itensNomes.add("Midia (CD,Pen Drive)");
        itensNomes.add("Movimentação Financeira");
        itensNomes.add("Movimento Contabil");
        itensNomes.add("Movimento Fiscal (Notas Fiscais de Entrada,Saida)");
        itensNomes.add("Notas Fiscais(Entrada,Saida)");
        itensNomes.add("Outros");
        itensNomes.add("Procuração");
        return itensNomes;
    }
    @FXML
    void novoItem(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Falta de informação!");
        alert.setHeaderText(null);

        if(txDetalhes.getText().trim().equals("")) {
            alert.setContentText("Campo detalhes é obrigatorio");
            alert.showAndWait();
            return;
        }
        try {
            ProtocoloItem i = new ProtocoloItem();
            i.setNome(cbItemNome.getValue());
            i.setQuantidade(cbItemQuantidade.getValue().intValue());
            i.setDetalhes(txDetalhes.getText());
            tbItem.getItems().add(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void preencherFormulario(ProtocoloEntrada protocolo){
        txCodigo.setText(""+protocolo.getId());
        txEntreguePor.setText(protocolo.getQuemEntregou());
        txCliente.setText(protocolo.getCliente()==null?"":""+protocolo.getCliente().getId());
        cliente = protocolo.getCliente();
        cbDepartamento.setValue(protocolo.getParaQuem()!=null?protocolo.getParaQuem().getDepartamento():null);
        cbFuncionario.setValue(protocolo.getParaQuem());
        txObservacao.setText(protocolo.getObservacao());

        Set<ProtocoloItem> itemSet = protocolo.getItems();
        tbItem.getItems().clear();
        tbItem.getItems().addAll(itemSet);
    }
    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }
    @FXML
    void salvar(ActionEvent event) {
        boolean novo = false;
        if(protocolo==null) {
            protocolo = new ProtocoloEntrada();
            novo = true;
        }
        protocolo.setQuemEntregou(txEntreguePor.getText());
        protocolo.setCliente(cliente);
        protocolo.setParaQuem(cbFuncionario.getValue());
        protocolo.setObservacao(txObservacao.getText());

        List<ProtocoloItem> items = tbItem.getItems();
        Set<ProtocoloItem> obs = new HashSet<>();
        for(ProtocoloItem i : items){
            i.setEntrada(protocolo);
            i.setCliente(cliente);
            obs.add(i);
        }
        protocolo.setItems(obs);
        try{
            loadFactory();
            entradas = new ProtocolosEntradasImpl(getManager());
            protocolo = entradas.save(protocolo);
            preencherFormulario(protocolo);
            AlertaProtocolo alerta = new AlertaProtocolo();
            if(novo){
                alerta.programarEnvioDocumentoRecebido(protocolo,true);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Enviar alerta");
                alert.setContentText("Deseja enviar um alerta para "+protocolo.getParaQuem().getNome()+"?");

                ButtonType buttonEnviar = new ButtonType("Salvar + Enviar Alerta");
                ButtonType buttonCancelar = new ButtonType("Cancelar");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(buttonEnviar,buttonCancelar);
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==buttonEnviar) alerta.programarEnvioDocumentoRecebido(protocolo,true);
            }
            stage.close();
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","Erro ao salvar","",e,true);
        }finally {
            close();
        }

    }
    public void tabela() {
        TableColumn<ProtocoloItem, Number> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(40);
        TableColumn<ProtocoloItem, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(120);
        TableColumn<ProtocoloItem, Number> colunaQuantidade = new TableColumn<>("Qtde");
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaQuantidade.setPrefWidth(50);
        TableColumn<ProtocoloItem, String> colunaDetalhes = new TableColumn<>("Detalhes");
        colunaDetalhes.setCellValueFactory(new PropertyValueFactory<>("detalhes"));
        colunaDetalhes.setPrefWidth(250);
        TableColumn actionCol = new TableColumn("");
        actionCol.setCellValueFactory(new PropertyValueFactory<>(""));
        Callback<TableColumn<ProtocoloItem, String>, TableCell<ProtocoloItem, String>> cellFactory
                = (final TableColumn<ProtocoloItem, String> param) -> {
            final TableCell<ProtocoloItem, String> cell = new TableCell<ProtocoloItem, String>() {
                final JFXButton button = new JFXButton();
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        button.getStyleClass().add("btDefault");
                        try {
                            buttonTable(button, IconsEnum.BUTTON_REMOVE);
                        } catch (IOException e) {}
                        button.setOnAction(event ->tbItem.getItems().remove(getIndex()));
                        setGraphic(button);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        actionCol.setCellFactory(cellFactory);
        tbItem.getColumns().addAll(colunaId, colunaNome, colunaQuantidade, colunaDetalhes, actionCol);
    }
    @FXML
    void textoAdicionado(KeyEvent event) {
        if (txCliente.getText().trim().equals("")){
            cliente = null;
            txClienteNome.setText("");
        }
        else{
            try {
                loadFactory();
                Long valor = Long.parseLong(txCliente.getText().trim());
                clientes = new ClientesImpl(getManager());
                cliente = clientes.findById(valor);
                if (cliente != null) {
                    txClienteNome.setText(cliente.getNome());
                } else {
                    txClienteNome.setText("");
                }
            } catch (PersistenceException e) {
                alert(Alert.AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao realizar consulta", e, true);
            } finally {
                close();
            }
        }
    }
}
