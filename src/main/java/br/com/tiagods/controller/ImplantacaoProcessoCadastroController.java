package br.com.tiagods.controller;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.Cliente;
import br.com.tiagods.model.implantacao.*;
import br.com.tiagods.repository.helpers.ClientesImpl;
import br.com.tiagods.repository.helpers.ImplantacaoPacotesImpl;
import br.com.tiagods.repository.helpers.ImplantacaoProcessosImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.fxutils.maskedtextfield.MaskTextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ImplantacaoProcessoCadastroController extends UtilsController implements Initializable {
    @FXML
    private MaskTextField txCliente;

    @FXML
    private Label txPacoteNome;

    @FXML
    private JFXButton btnCopiarAlterarPacote;

    @FXML
    private JFXButton btnEtapa;

    @FXML
    private Label txNomeCliente;

    @FXML
    private JFXDatePicker dtFinalizacao;

    @FXML
    private TableView<ImplantacaoProcessoEtapa> tbEtapa;

    private Stage stage;

    private ClientesImpl clientes;
    private Cliente clienteSelecionado;
    private ImplantacaoProcesso processo;
    private ImplantacaoProcessosImpl processos;
    private ImplantacaoPacotesImpl pacotes;

    public ImplantacaoProcessoCadastroController(Stage stage, ImplantacaoProcesso processo){
        this.stage=stage;
        this.processo=processo;
    }

    public enum BotaoPacote{
        COPIAR("Copiar Pacote"),ALTERAR("Alterar Pacote");
        private String descricao;
        BotaoPacote(String descricao){
            this.descricao=descricao;
        }
        @Override
        public String toString() {
            return this.descricao;
        }
    }
    void cadastrarEtapa(boolean editar, int tableLocation, ImplantacaoProcessoEtapa etapa){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.IMPLATACAO_ETAPA);
            ImplantacaoEtapaController controller = new ImplantacaoEtapaController(editar,etapa.getEtapa(),processo,stage);
            loader.setController(controller);
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
                ImplantacaoEtapa et = controller.getEtapa();
                if(et!=null && controller.isEtapaValida()) {
                    etapa.setEtapa(et);
                    if (tableLocation == -1) tbEtapa.getItems().add(etapa);
                    else tbEtapa.getItems().set(tableLocation, etapa);
                    tbEtapa.refresh();
                }
            });
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro","Falha ao localizar o arquivo "+FXMLEnum.NEGOCIO_PESQUISA,e,true);
        }

    }
    private void combos(){
        txPacoteNome.setText("");
        btnEtapa.setOnAction(event -> {
                ImplantacaoProcessoEtapa etapa = new ImplantacaoProcessoEtapa();
                etapa.setProcesso(processo);
                processo.setEtapas(tbEtapa.getItems().stream().collect(Collectors.toSet()));
                cadastrarEtapa(false,-1,etapa);
        });
        txCliente.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.trim().equals("")){
                clienteSelecionado = null;
                txNomeCliente.setText("");
            }
            else{
                try {
                    loadFactory();
                    clientes = new ClientesImpl(getManager());
                    clienteSelecionado = clientes.findById(Long.parseLong(txCliente.getText()));
                    if (clienteSelecionado!=null) txNomeCliente.setText(clienteSelecionado.getNome());
                    else txNomeCliente.setText("");
                }catch (Exception e){
                    clienteSelecionado = null;
                }finally {
                    close();
                }
            }
        });
        btnCopiarAlterarPacote.setText(BotaoPacote.COPIAR.toString());
        btnCopiarAlterarPacote.setOnAction(event -> {
            try {
                loadFactory();
                List<ImplantacaoPacote> choices = new ArrayList<>();
                pacotes = new ImplantacaoPacotesImpl(getManager());
                choices.addAll(pacotes.getAll());
                if(processo.getPacote()!=null){ choices.remove(processo.getPacote()); }

                ChoiceDialog<ImplantacaoPacote> dialog = new ChoiceDialog<>();
                dialog.getItems().addAll(choices);
                if(choices.size()>0) dialog.setSelectedItem(choices.get(0));
                dialog.setTitle("Seleção de Pacotes");
                dialog.setHeaderText("Copiar parâmetros do pacote");
                dialog.setContentText("Escolha um pacote:");
                Optional<ImplantacaoPacote> result = dialog.showAndWait();
                if (result.isPresent()){
                    if(processo.getPacote()!=null){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Alteração!");
                        alert.setHeaderText("Alterar Pacote");
                        alert.setContentText("Deseja mudar copiar os parâmetros?\nAs alterações serão perdidas!");
                        ButtonType ok = new ButtonType("Alterar Pacote");
                        ButtonType cancel = new ButtonType("Cancelar");
                        alert.getButtonTypes().clear();
                        alert.getButtonTypes().addAll(ok,cancel);
                        Optional<ButtonType> result2 = alert.showAndWait();
                        if(result2.get() == ok){
                            mudarPacote(result.get());
                        }
                    }
                    else {
                        mudarPacote(result.get());
                    }
                }
            }catch (Exception e){
                alert(Alert.AlertType.ERROR,"Erro","Erro ao listar registros","Ocorreu um erro ao listar os registros",e,true);
            }finally {
                close();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tabelaEtapa();
            loadFactory();
            processos = new ImplantacaoProcessosImpl(getManager());
            combos();
            if (processo != null)
                preencherFormulario(processo);
            else
                processo = new ImplantacaoProcesso();
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","Erro ao listar registros","Ocorreu um erro ao listar os registros",e,true);
        }finally {
            close();
        }
    }

    private void mudarPacote(ImplantacaoPacote pack){
        pack = pacotes.findById(pack.getId());
        processo.setPacote(pack);
        tbEtapa.getItems().clear();
        for (ImplantacaoPacoteEtapa et : pack.getEtapas()) {
            ImplantacaoProcessoEtapa ipe = new ImplantacaoProcessoEtapa(et, processo);
            tbEtapa.getItems().add(ipe);
        }
        btnCopiarAlterarPacote.setText(BotaoPacote.ALTERAR.toString());
        txPacoteNome.setText(pack.getNome());
    }

    private void preencherFormulario(ImplantacaoProcesso processo){
        txCliente.setText(String.valueOf(processo.getCliente().getId()));
        dtFinalizacao.setValue(
                processo.getDataFinalizacao()==null?
                null:
                processo.getDataFinalizacao().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        tbEtapa.getItems().clear();
        tbEtapa.getItems().addAll(processo.getEtapas());
        if(processo.getPacote()!=null){
            txPacoteNome.setText(processo.getPacote().getNome());
            btnCopiarAlterarPacote.setText(BotaoPacote.ALTERAR.toString());
        }
        else {
            txPacoteNome.setText("");
            btnCopiarAlterarPacote.setText(BotaoPacote.COPIAR.toString());
        }
        this.processo = processo;
    }

    @FXML
    void sair(ActionEvent event) {
        stage.close();
    }

    @FXML
    void salvar(ActionEvent event) {
        if(clienteSelecionado==null){
            alert(Alert.AlertType.ERROR,"Erro","","Por favor, informe um cliente",null,false);return;
        }
        processo.setFinalizado(dtFinalizacao.getValue()!=null);
        if(dtFinalizacao.getValue()==null) {
            processo.setDataFinalizacao(null);
        }
        else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.from(dtFinalizacao.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            processo.setDataFinalizacao(calendar);
        }
        Set<ImplantacaoProcessoEtapa> etapas = new HashSet<>();
        etapas.addAll(tbEtapa.getItems().stream().collect(Collectors.toSet()));
        processo.setEtapas(etapas);
        processo.setCliente(clienteSelecionado);
        try{
            loadFactory();
            processos = new ImplantacaoProcessosImpl(getManager());
            processo = processos.save(processo);
            preencherFormulario(processo);
            alert(Alert.AlertType.INFORMATION,"Salvo","","Salvo com sucesso!",null,false);
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","Falha ao salvar","Ocorreu um erro ao tentar salvar",e,true);
        }finally {
            close();
        }
    }
    void tabelaEtapa(){
        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaNome = new TableColumn<>("Etapa");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaNome.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa,ImplantacaoEtapa>(){
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null || empty){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else setText(""+item.getEtapa());
            }
        });
        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaAtividade = new TableColumn<>("Atividade");
        colunaAtividade.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaAtividade.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa,ImplantacaoEtapa>(){
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null || empty){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else setText(item.getAtividade().toString());
            }
        });

        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaDescricao = new TableColumn<>("Descricao");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaDescricao.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa,ImplantacaoEtapa>(){
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null || empty){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else setText(item.getDescricao().toString());
            }
        });
        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaDepartamento = new TableColumn<>("Departamento");
        colunaDepartamento.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaDepartamento.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa,ImplantacaoEtapa>(){
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null || empty){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else setText(item.getDepartamento().toString());
            }
        });

        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaTempo = new TableColumn<>("Tempo(dias)");
        colunaTempo.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaTempo.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa,ImplantacaoEtapa>(){
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null || empty){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else setText(""+item.getTempo());
            }
        });

        TableColumn<ImplantacaoProcessoEtapa, Long> colunaEditar = new  TableColumn<>("");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa,Long>(){
            JFXButton button = new JFXButton();//Editar
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if(empty){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else{
                    button.getStyleClass().add("btDefault");
                    try {
                        buttonTable(button, IconsEnum.BUTTON_EDIT);
                    }catch (IOException e) {
                    }
                    button.setOnAction(event -> cadastrarEtapa(true,getIndex(),tbEtapa.getItems().get(getIndex())));
                    setGraphic(button);
                }
            }
        });
        tbEtapa.getColumns().addAll(colunaNome,colunaAtividade,colunaDescricao,colunaDepartamento,colunaTempo,colunaEditar);
    }
}
