package br.com.tiagods.controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.controller.utils.SobreController;
import br.com.tiagods.controller.utils.UtilsController;
import br.com.tiagods.model.Departamento;
import br.com.tiagods.model.implantacao.ImplantacaoAtividade;
import br.com.tiagods.model.implantacao.ImplantacaoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.model.negocio.NegocioProposta;
import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.*;
import br.com.tiagods.repository.helpers.filters.NegocioPropostaFilter;
import br.com.tiagods.repository.helpers.filters.NegocioTarefaFilter;
import br.com.tiagods.repository.helpers.filters.ProtocoloEntradaFilter;
import br.com.tiagods.services.AlertaImplantacaoImpl;
import br.com.tiagods.util.ComboBoxAutoCompleteUtil;
import br.com.tiagods.util.TipoArquivo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.springframework.stereotype.Controller;

@Controller
public class MenuController extends UtilsController implements Initializable{
    @FXML
    private Pane pnCenter;
    @FXML
    private Label txNegociosPerfil;
    @FXML
    private Label txNegociosTodos;
    @FXML
    private Label txTarefasMes;
    @FXML
    private Label txTarefasTodos;
    @FXML
    private ListView listViewNegocios;
    @FXML
    private Label txProtocoloPerfil;
    @FXML
    private Label txProtocoloTodos;
    @FXML
    private FlowPane pnCalendario;
    @FXML
    private Label txContatos;

    @FXML
    private JFXButton btnCadastro;

    @FXML
    private JFXButton btnProtocolo;

    @FXML
    private JFXButton btnExtras;

    @FXML
    private JFXButton btnNegocios;

    @FXML
    private JFXButton btnImplantacao;

    @FXML
    private Label lbUsuarioNome;

    @FXML
    private Label lbUsuarioNome2;

    @FXML
    private Tab tabNegocios;

    @FXML
    private Tab tabProtocolo;

    @FXML
    private TableView<ProtocoloEntrada> tbProtocoloEntrada;

    @FXML
    private Tab tabProcesso;

    @FXML
    private TableView<ImplantacaoProcessoEtapa> tbProcesso;

    @FXML
    private JFXComboBox<ImplantacaoProcesso> cbProcesso;

    @FXML
    private JFXComboBox<Departamento> cbProcessoDepartamento;

    @FXML
    private JFXComboBox<ImplantacaoAtividade> cbProcessoAtividade;

    @FXML
    private JFXComboBox<ImplantacaoEtapa.Etapa> cbProcessoEtapa;

    @FXML
    private JFXComboBox<ImplantacaoProcessoEtapa.Status> cbProcessoStatus;

    private ContatosImpl contatos;
    private NegociosTarefasImpl tarefas;
    private NegocioPropostaImpl propostas;
    private ImplantacaoProcessosImpl processos;
    private ImplantacaoProcessoEtapasImpl etapas;
    private DepartamentosImpl departamentos;
    private ImplantacaoAtividadesImpl atividades;
    private boolean desabilitarAcaoCombos = false;

    void atualizar(){
        try{
            desabilitarAcaoCombos = true;

            loadFactory();
            propostas = new NegocioPropostaImpl(getManager());
            tarefas = new NegociosTarefasImpl(getManager());
            contatos = new ContatosImpl(getManager());
            processos = new ImplantacaoProcessosImpl(getManager());
            etapas = new ImplantacaoProcessoEtapasImpl(getManager());
            departamentos = new DepartamentosImpl(getManager());
            atividades = new ImplantacaoAtividadesImpl(getManager());

            preencherNegocios();
            preencherTarefas();
            preencherProtocolos();
            preencherProcessos();

            desabilitarAcaoCombos = false;
        }catch (Exception e){
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao atualizar","Falha ao atualizar registros do menu",e,true);
        }finally {
            close();
        }
    }

    void abrirNegocio(NegocioPropostaFilter filter){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.NEGOCIO_PESQUISA);
            loader.setController(new NegocioPesquisaController(filter,stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequest(stage);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.NEGOCIO_PESQUISA,e,true);
        }
    }
    void botaoNegocios(NegocioProposta c, String style){
        JFXButton button = new JFXButton();
        button.setText(c.getId()+"-"+c.getNome()+"=>Responsavel: "+c.getAtendente().getNomeResumido()+"=>"+(c.getTarefas().size())+" Tarefa(s)"+(c.getDataFim()!=null?"=>Prazo limite: "+sdf.format(c.getDataFim().getTime()):""));
        button.getStyleClass().add(style);
        try {
            buttonMin(button, c.getTipoEtapa().getIco());
        } catch (IOException e) {}
        Tooltip tooltip = new Tooltip(button.getText());
        button.setTooltip(tooltip);
        button.setOnAction(event -> {
            Stage stage1 = abrirNegocioProposta(c);
            stage1.setOnHiding(e -> atualizar());
        });
        listViewNegocios.getItems().add(button);
    }

    void combos() throws IndexOutOfBoundsException{
        btnProtocolo.setOnAction(this::protocolo);
        ChangeListener processoListener = (observable, oldValue, newValue) -> {
            if(desabilitarAcaoCombos) return;
            try{
                loadFactory();
                etapas = new ImplantacaoProcessoEtapasImpl(getManager());
                filtrarProcessos();
            }catch (Exception e){
                alert(Alert.AlertType.ERROR, "Erro", "Erro ao filtrarMultProcessos","Falha ao filtrarMultProcessos registros da tabela de processos",e,true);
            } finally {
                close();
            }
        };
        cbProcesso.valueProperty().addListener(processoListener);
        cbProcessoDepartamento.valueProperty().addListener(processoListener);
        cbProcessoAtividade.valueProperty().addListener(processoListener);
        cbProcessoStatus.valueProperty().addListener(processoListener);
    }
    @FXML
    void contato(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CONTATO_PESQUISA);
            loader.setController(new ContatoPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequest(stage);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.CONTATO_PESQUISA,e,true);
        }

    }
    @FXML
    void departamento(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.DEPARTAMENTO);
            loader.setController(new DepartamentoController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequest(stage);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.DEPARTAMENTO,e,true);
        }
    }

    private void filtrarProcessos(){
            etapas = new ImplantacaoProcessoEtapasImpl(getManager());
            tbProcesso.getItems().clear();
            List<ImplantacaoProcessoEtapa> list = ordenar(etapas
                    .filtrar(cbProcessoDepartamento.getValue(),
                            cbProcesso.getValue(),
                            cbProcessoAtividade.getValue(),
                            null,
                            cbProcessoStatus.getValue(),
                            true));
            tbProcesso.getItems().addAll(list);
    }
    @FXML
    void franquia(ActionEvent event) {
    	try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.FRANQUIA_PESQUISA);
            loader.setController(new FranquiaPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequest(stage);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.FRANQUIA_PESQUISA,e,true);
        }
    }
    @Override
	public void initialize(URL location, ResourceBundle resources) throws IndexOutOfBoundsException{
        cbProcessoEtapa.setVisible(false);

        combos();
        atualizar();
        menu();

        lbUsuarioNome.setText(UsuarioLogado.getInstance().getUsuario()!=null?UsuarioLogado.getInstance().getUsuario().getNomeResumido().toUpperCase():"{usuario}");
        lbUsuarioNome2.setText(lbUsuarioNome.getText());
        pnCalendario.setVgap(6);
        pnCalendario.setHgap(6);

        String[] week = new String[]{"Domingo","Segunda","Terça","Quarta","Quinta","Sexta","Sábado"};
        for(String s : week){
            Label label = new Label(s);
            label.setMinSize(110,10);
            pnCalendario.getChildren().add(label);
        }
        LocalDate localDate = LocalDate.now();
        for (int i=1;i<=30;i++){
            ListView listView = new ListView();
            listView.getItems().add(new Label(""+i));
            listView.setMaxSize(110,100);
            listView.setMinSize(110,100);
            if(localDate.getDayOfMonth()==i)
                listView .getStyleClass().add("btRed");
            pnCalendario.getChildren().add(listView);
        }
        pnCalendario.setDisable(true);
    }
	void onCloseRequest(Stage stage){ stage.setOnHiding(event -> atualizar());}


	private void menu(){
        final ContextMenu cmNegocios = new ContextMenu();
        MenuItem miContato = new MenuItem("Contato");
        iconMenuItem(miContato,30,30, IconsEnum.MENU_CONTATO);
        miContato.setOnAction(this::contato);

        MenuItem miFranquia = new MenuItem("Franquia");
        iconMenuItem(miFranquia,30,30,IconsEnum.MENU_FRANQUIA);
        miFranquia.setOnAction(this::franquia);

        MenuItem miNegocio = new MenuItem("Negocios");
        iconMenuItem(miNegocio,30,30,IconsEnum.MENU_NEGOCIO);
        miNegocio.setOnAction(this::negocio);

        MenuItem miTarefa = new MenuItem("Tarefas");
        iconMenuItem(miTarefa,30,30,IconsEnum.MENU_TAREFA);
        miTarefa.setOnAction(this::tarefa);

        cmNegocios.getItems().addAll(miContato, miFranquia, miNegocio,miTarefa);
        btnNegocios.setContextMenu(cmNegocios);
        btnNegocios.setOnAction(e->
                cmNegocios.show(btnNegocios.getScene().getWindow(),
                        btnNegocios.getScene().getWindow().getX()+50,
                        btnNegocios.getScene().getWindow().getY()+btnNegocios.getLayoutY()+100));
        cmNegocios.getItems().forEach(c->c.setStyle("-fx-text-fill: #000000;"));

        final ContextMenu cmCadastros = new ContextMenu();
        MenuItem miUsuario = new MenuItem("Usuarios");
        iconMenuItem(miUsuario,30,30, IconsEnum.MENU_USUARIO);
        miUsuario.setOnAction(this::usuario);

        MenuItem miDepartamento = new MenuItem("Departamento");
        iconMenuItem(miDepartamento,30,30, IconsEnum.MENU_PEOPLE);
        miDepartamento.setOnAction(this::departamento);

        cmCadastros.getItems().addAll(miUsuario,miDepartamento);
        btnCadastro.setContextMenu(cmCadastros);
        btnCadastro.setOnAction(e->
                cmCadastros.show(btnCadastro.getScene().getWindow(),
                        btnCadastro.getScene().getWindow().getX()+50,
                        btnCadastro.getScene().getWindow().getY()+btnCadastro.getLayoutY()+100));
        cmCadastros.getItems().forEach(c->c.setStyle("-fx-text-fill: #000000;"));

        final ContextMenu cmExtras = new ContextMenu();
        MenuItem miCheckList = new MenuItem("CheckList");
        iconMenuItem(miCheckList,30,30, IconsEnum.MENU_CHECKLIST);
        miCheckList.setOnAction(event -> {
            abrirArquivo("checklist.jar");
        });

        MenuItem miSobre = new MenuItem("Sobre");
        iconMenuItem(miSobre,30,30,IconsEnum.BUTTON_VIEW);
        miSobre.setOnAction(this::sobre);

        MenuItem miManual = new MenuItem("Manual do Sistema");
        iconMenuItem(miManual,30,30, IconsEnum.BUTTON_VIEW);
        miManual.setOnAction(event -> abrirArquivo("manual.docx"));

        cmExtras.getItems().addAll(miCheckList,miManual,miSobre);
        btnExtras.setContextMenu(cmExtras);
        btnExtras.setOnAction(e->
                cmExtras.show(btnExtras.getScene().getWindow(),
                        btnExtras.getScene().getWindow().getX()+50,
                        btnExtras.getScene().getWindow().getY()+btnExtras.getLayoutY()+100));
        cmExtras.getItems().forEach(c->c.setStyle("-fx-text-fill: #000000;"));

        final ContextMenu cmImplantacao = new ContextMenu();
        MenuItem miPacote = new MenuItem("Pacotes");
        iconMenuItem(miPacote,30,30, IconsEnum.MENU_PACOTE);
        miPacote.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = loaderFxml(FXMLEnum.IMPLATACAO_PACOTE_PESQUISA);
                loader.setController(new ImplantacaoPacoteController(stage));
                initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
                onCloseRequest(stage);
            }catch(IOException e) {
                alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                        "Falha ao localizar o arquivo "+FXMLEnum.IMPLATACAO_PACOTE_PESQUISA,e,true);
            }
        });
        MenuItem miProcessos = new MenuItem("Processos");
        iconMenuItem(miProcessos,30,30,IconsEnum.MENU_USUARIO);
        miProcessos.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = loaderFxml(FXMLEnum.IMPLANTACAO_PROCESSO_PESQUISA);
                loader.setController(new ImplantacaoProcessoPesquisaController(stage));
                initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
                onCloseRequest(stage);
            }catch(IOException e) {
                alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                        "Falha ao localizar o arquivo "+FXMLEnum.IMPLANTACAO_PROCESSO_PESQUISA,e,true);
            }
        });
        cmImplantacao.getItems().addAll(miPacote,miProcessos);
        btnImplantacao.setContextMenu(cmImplantacao);

        btnImplantacao.setOnAction(e->
                cmImplantacao.show(btnImplantacao.getScene().getWindow(),
                        btnImplantacao.getScene().getWindow().getX()+50,
                        btnImplantacao.getScene().getWindow().getY()+btnImplantacao.getLayoutY()+100));
        cmImplantacao.getItems().forEach(c->c.setStyle("-fx-text-fill: #000000;"));
    }

    private void abrirArquivo(String s) {
        Path path = Paths.get(System.getProperty("user.dir"),s);
        Runnable run = () -> {
            try {
                Desktop.getDesktop().open(path.toFile());
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Não foi possivel iniciar o programa");
                alert.setContentText("Falha ao abrir o aplicativo\n"+e);
                alert.showAndWait();
            }
        };
        new Thread(run).start();
    }
    @FXML
    void exportarImplantacao(ActionEvent event){
        try {
            List<TipoArquivo> arquivos = Arrays.asList(new TipoArquivo[]{TipoArquivo.XLS,TipoArquivo.HTML});
            ChoiceDialog<TipoArquivo> dialog = new ChoiceDialog<>(arquivos.get(0), arquivos);
            dialog.setTitle("Relatorio");
            dialog.setHeaderText("Relatorio para formato de arquivo");
            dialog.setContentText("Escolha uma opção:");

            final Optional<TipoArquivo> result = dialog.showAndWait();
            if (result.isPresent()) {
                FXMLLoader loader = new FXMLLoader(FXMLEnum.PROGRESS_SAMPLE.getLocalizacao());
                Alert progress = new Alert(Alert.AlertType.INFORMATION);
                progress.setHeaderText("");
                DialogPane dialogPane = new DialogPane();
                dialogPane.setContent(loader.load());
                progress.setDialogPane(dialogPane);
                Stage sta = (Stage) dialogPane.getScene().getWindow();
                Task<Void> run = new Task<Void>() {
                    {
                        setOnFailed(a -> sta.close());
                        setOnSucceeded(a -> sta.close());
                        setOnCancelled(a -> sta.close());
                    }

                    @Override
                    protected Void call() {
                        Platform.runLater(() -> sta.show());
                        try {
                            AlertaImplantacaoImpl alertaImplantacao = new AlertaImplantacaoImpl();
                            File arquivo = null;
                            if(result.get().equals(TipoArquivo.XLS))
                                arquivo = alertaImplantacao.gerarExcel(cbProcesso.getValue(),
                                        cbProcessoDepartamento.getValue(),cbProcessoAtividade.getValue(),
                                        null,false);
                            else if(result.get().equals(TipoArquivo.HTML))
                                arquivo = alertaImplantacao.gerarHtml(cbProcesso.getValue(),
                                        cbProcessoDepartamento.getValue(),cbProcessoAtividade.getValue(),
                                        null,false);
                            Desktop.getDesktop().open(arquivo);
                        } catch (Exception e1) {
                            Platform.runLater(() -> alert(Alert.AlertType.ERROR, "Erro", "", "Erro ao criar a planilha ", e1, true));
                        }
                        return null;
                    }
                };
                Thread thread = new Thread(run);
                thread.start();
                sta.setOnCloseRequest(ae -> {
                    try {
                        thread.interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }catch (IOException e){
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o progresso", "O arquivo nao foi localizado",null,false);
        }
    }
    @FXML
    void negocio(ActionEvent event) {
        abrirNegocio(null);
    }

    private void preencherNegocios() throws Exception{
        listViewNegocios.getItems().clear();
        NegocioPropostaFilter propostaFilter = new NegocioPropostaFilter();
        propostaFilter.setStatus(NegocioProposta.TipoStatus.ANDAMENTO);
        Pair<List<NegocioProposta>,Paginacao> propostaList = propostas.filtrar(null,propostaFilter);
        long n1 = propostaList.getKey().size();
        txNegociosTodos.setText(String.valueOf(n1));
        txNegociosTodos.setOnMouseClicked(event -> abrirNegocio(propostaFilter));

        List<NegocioProposta> semData = new ArrayList<>();
        List<NegocioProposta> vencidas = new ArrayList<>();
        List<NegocioProposta> aVencer = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        propostaList.getKey().forEach(c->{
            if(c.getDataFim()==null) semData.add(c);
            else if(calendar.getTime().after(c.getDataFim().getTime())) vencidas.add(c);
            else aVencer.add(c);
        });

        Comparator<NegocioProposta> comparator = (o1, o2) -> {
            if (o1.getDataFim().getTime().before(o2.getDataFim().getTime())) return -1;
            else if(o1.getDataFim().getTime().after(o2.getDataFim().getTime())) return 1;
            else return 0;
        };
        Collections.sort(vencidas, comparator);
        Collections.sort(aVencer, comparator);

        Label lbNegVencidos = new Label("NEGOCIOS VENCIDOS");
        lbNegVencidos.getStyleClass().add("label-card2");
        if(!vencidas.isEmpty()) listViewNegocios.getItems().add(lbNegVencidos);
        vencidas.forEach(c->botaoNegocios(c,"btRed"));

        Label lbSemData = new Label("NEGOCIOS SEM PRAZO");
        lbSemData.getStyleClass().add("label-card2");
        if(!semData.isEmpty()) listViewNegocios.getItems().add(lbSemData);
        semData.forEach(c->botaoNegocios(c,"btYellow"));

        Label lbAVencer = new Label("DENTRO DO PRAZO");
        lbAVencer.getStyleClass().add("label-card2");
        if(!aVencer.isEmpty()) listViewNegocios.getItems().add(lbAVencer);
        aVencer.forEach(c->botaoNegocios(c,c.getTipoEtapa().getStyle()));

        propostaFilter.setAtendente(UsuarioLogado.getInstance().getUsuario());
        long n2 = propostas.count(propostaFilter);
        txNegociosPerfil.setText(String.valueOf(n2));
        txNegociosPerfil.setOnMouseClicked(event -> abrirNegocio(propostaFilter));
    }
    private void preencherProcessos() throws Exception{
        cbProcesso.getItems().clear();
        ImplantacaoProcesso pro = new ImplantacaoProcesso(-1L);
        cbProcesso.getItems().add(pro);
        cbProcesso.getSelectionModel().selectFirst();

        cbProcesso.getItems().addAll(processos.listarAtivos(false));

        cbProcessoDepartamento.getItems().clear();
        Departamento departamento = new Departamento(-1L,"Todos");
        cbProcessoDepartamento.getItems().add(departamento);
        cbProcessoDepartamento.setValue(UsuarioLogado.getInstance().getUsuario().getDepartamento());

        cbProcessoDepartamento.getItems().addAll(departamentos.getAllByName());

        cbProcessoAtividade.getItems().clear();
        ImplantacaoAtividade atividade = new ImplantacaoAtividade(-1L,"Todos");
        cbProcessoAtividade.getItems().add(atividade);
        cbProcessoAtividade.getItems().addAll(atividades.getAllByName());
        cbProcessoAtividade.setValue(atividade);
        new ComboBoxAutoCompleteUtil<>(cbProcessoAtividade);

        cbProcessoStatus.getItems().clear();
        cbProcessoStatus.getItems().addAll(ImplantacaoProcessoEtapa.Status.values());
        cbProcessoStatus.setValue(ImplantacaoProcessoEtapa.Status.ABERTO);

        TabelaProcessosEtapa tabelaProcessosEtapa = new TabelaProcessosEtapa(tbProcesso);
        tabelaProcessosEtapa.tabela();

        filtrarProcessos();
    }
    private List<ImplantacaoProcessoEtapa> ordenar(List<ImplantacaoProcessoEtapa> lista){
        Comparator<ImplantacaoProcessoEtapa> comparator =
                Comparator.comparingLong(c->c.getProcesso().getCliente().getId());

        Collections.sort(lista, comparator
                        .thenComparing(c->c.getEtapa().getAtividade().getNome())
                        .thenComparingInt(c->c.getEtapa().getEtapa().getValor())
        );
        return lista;
    }
    private void preencherProtocolos() throws Exception{
        JFXRadioButton rbComum = new JFXRadioButton();
        rbComum.setSelected(true);

        UsuariosImpl usuarios = new UsuariosImpl(getManager());
        ProtocolosEntradasImpl protocolosEntradas = new ProtocolosEntradasImpl(getManager());
        TabelaProtocoloEntrada protocoloEntrada = new TabelaProtocoloEntrada(null,tbProtocoloEntrada,new JFXRadioButton(),rbComum);
        protocoloEntrada.tabela();

        protocoloEntrada.setUsuarioAtivos(usuarios.listarAtivos());
        List<ProtocoloEntrada> list = protocoloEntrada.filtrar(null,getManager());

        ProtocoloEntradaFilter protocoloEntradaFilter = new ProtocoloEntradaFilter();
        protocoloEntradaFilter.setDevolucao(ProtocoloEntrada.StatusDevolucao.DEVOLVIDO);
        protocoloEntradaFilter.setRecebimento(ProtocoloEntrada.StatusRecebimento.STATUS);
        protocoloEntradaFilter.setClassificacao(ProtocoloEntrada.Classificacao.USUARIO);
        Pair<List<ProtocoloEntrada>,Paginacao> result = protocolosEntradas
                .filtrar(null,protocoloEntradaFilter);

        tbProtocoloEntrada.itemsProperty().addListener(observable -> atualizar());

        txProtocoloPerfil.setText(""+list.size());
        txProtocoloTodos.setText(""+result.getKey().size());
    }
    private void preencherTarefas() throws Exception{
        LocalDate localDate = LocalDate.now();

        LocalDateTime inicio = localDate.withDayOfMonth(1).atTime(0,0,0);
        LocalDateTime fim = localDate.withDayOfMonth(localDate.lengthOfMonth()).atTime(23,59,59);

        NegocioTarefaFilter tarefaFilter = new NegocioTarefaFilter();
        tarefaFilter.setAtendente(UsuarioLogado.getInstance().getUsuario());
        tarefaFilter.setFinalizado(0);
        long t2 = tarefas.getQuantidade(tarefaFilter);
        tarefaFilter.setDataEventoInicial(GregorianCalendar.from(inicio.atZone(ZoneId.systemDefault())));
        tarefaFilter.setDataEventoFinal(GregorianCalendar.from(fim.atZone(ZoneId.systemDefault())));
        long t1 = tarefas.getQuantidade(tarefaFilter);

        txTarefasMes.setText(String.valueOf(t1));
        txTarefasTodos.setText(String.valueOf(t2));
    }
    @FXML
    void protocolo(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.PROTOCOLO_ENTRADA_PESQUISA);
            loader.setController(new ProtocoloEntradaPesquisaController(stage, new ProtocoloEntradaFilter()));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequest(stage);
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo " + FXMLEnum.PROTOCOLO_ENTRADA_PESQUISA, e, true);
        }
    }
    @FXML
    void sair(ActionEvent event) {
	    System.exit(0);
    }

    @FXML
    void sobre(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.SOBRE);
            loader.setController(new SobreController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequest(stage);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.SOBRE,e,true);
        }
    }
    @FXML
    void tarefa(ActionEvent event) {
    	try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.TAREFA_PESQUISA);
            loader.setController(new TarefaPesquisaController(null,stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequest(stage);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.TAREFA_PESQUISA,e,true);
        }
    }

    @FXML
    void usuario(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.USUARIO_PESQUISA);
            loader.setController(new UsuarioPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequest(stage);
        }catch(IOException e) {
        	 alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                     "Falha ao localizar o arquivo "+FXMLEnum.USUARIO_PESQUISA,e,true);
        }
    }

}