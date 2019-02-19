package br.com.tiagods.controller;

import java.awt.*;
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
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.model.negocio.NegocioProposta;
import br.com.tiagods.model.protocolo.ProtocoloEntrada;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.*;
import br.com.tiagods.repository.helpers.filters.NegocioPropostaFilter;
import br.com.tiagods.repository.helpers.filters.NegocioTarefaFilter;
import br.com.tiagods.repository.helpers.filters.ProtocoloEntradaFilter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
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

    private ContatosImpl contatos;
    private NegociosTarefasImpl tarefas;
    private NegocioPropostaImpl propostas;
    private ImplantacaoProcessosImpl processos;

    void atualizar(){
        try{
            listViewNegocios.getItems().clear();

            loadFactory();
            propostas = new NegocioPropostaImpl(getManager());
            tarefas = new NegociosTarefasImpl(getManager());
            contatos = new ContatosImpl(getManager());

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
            Pair<List<ProtocoloEntrada>,Paginacao> result = protocolosEntradas.filtrar(null,protocoloEntradaFilter);

            tbProtocoloEntrada.itemsProperty().addListener(observable -> atualizar());

            txProtocoloPerfil.setText(""+list.size());
            txProtocoloTodos.setText(""+result.getKey().size());


            cbProcesso.getItems().clear();
            ImplantacaoProcesso pro = new ImplantacaoProcesso(-1L);
            cbProcesso.getItems().add(pro);
            cbProcesso.getSelectionModel().selectFirst();
            processos = new ImplantacaoProcessosImpl(getManager());
            cbProcesso.getItems().addAll(processos.listarAtivos());

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
        button.setText(c.getId()+"-"+c.getNome()+"=>Responsavel: "+c.getAtendente().getLogin()+"=>"+(c.getTarefas().size())+" Tarefa(s)"+(c.getDataFim()!=null?"=>Prazo limite: "+sdf.format(c.getDataFim().getTime()):""));
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

    void combos(){
        btnProtocolo.setOnAction(this::protocolo);

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
        iconMenuItem(miDepartamento,30,30, IconsEnum.MENU_USUARIO);
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
            Path path = Paths.get(System.getProperty("user.dir"),"checklist.jar");
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
        });

        cmExtras.getItems().addAll(miCheckList);
        btnExtras.setContextMenu(cmExtras);
        btnExtras.setOnAction(e->
                cmExtras.show(btnExtras.getScene().getWindow(),
                        btnExtras.getScene().getWindow().getX()+50,
                        btnExtras.getScene().getWindow().getY()+btnExtras.getLayoutY()+100));
        cmExtras.getItems().forEach(c->c.setStyle("-fx-text-fill: #000000;"));

        final ContextMenu cmImplantacao = new ContextMenu();
        MenuItem miPacote = new MenuItem("Pacotes");
        iconMenuItem(miPacote,30,30, IconsEnum.MENU_USUARIO);
        miPacote.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = loaderFxml(FXMLEnum.IMPLATACAO_PACOTE);
                loader.setController(new ImplantacaoPacoteController(stage));
                initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
                onCloseRequest(stage);
            }catch(IOException e) {
                alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                        "Falha ao localizar o arquivo "+FXMLEnum.IMPLATACAO_PACOTE,e,true);
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
	public void initialize(URL location, ResourceBundle resources) {
        atualizar();

        combos();

        lbUsuarioNome.setText(UsuarioLogado.getInstance().getUsuario()!=null?UsuarioLogado.getInstance().getUsuario().getLogin().toUpperCase():"{usuario}");
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


    @FXML
    void negocio(ActionEvent event) {
        abrirNegocio(null);
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