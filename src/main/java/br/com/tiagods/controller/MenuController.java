package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.*;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.NegocioProposta;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.ContatosImpl;
import br.com.tiagods.repository.helpers.NegocioPropostaImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasImpl;
import br.com.tiagods.repository.helpers.filters.NegocioPropostaFilter;
import br.com.tiagods.repository.helpers.filters.NegocioTarefaFilter;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import javax.swing.text.html.ImageView;

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
    private ListView listView1;

    @FXML
    private Label txContatos;


    @FXML
    private Label lbUsuarioNome;

    private ContatosImpl contatos;
    private NegociosTarefasImpl tarefas;
    private NegocioPropostaImpl propostas;

    void atualizar(){
        try{
            listView1.getItems().clear();

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
            if(!vencidas.isEmpty()) listView1.getItems().add(lbNegVencidos);
            vencidas.forEach(c->botaoNegocios(c,"btRed"));

            Label lbSemData = new Label("NEGOCIOS SEM PRAZO");
            lbSemData.getStyleClass().add("label-card2");
            if(!semData.isEmpty()) listView1.getItems().add(lbSemData);
            semData.forEach(c->botaoNegocios(c,"btYellow"));

            Label lbAVencer = new Label("DENTRO DO PRAZO");
            lbAVencer.getStyleClass().add("label-card2");
            if(!aVencer.isEmpty()) listView1.getItems().add(lbAVencer);
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
            onCloseRequestt(stage);
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
        listView1.getItems().add(button);
    }
    @FXML
    void contato(ActionEvent event) {
    	try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.CONTATO_PESQUISA);
            loader.setController(new ContatoPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequestt(stage);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.CONTATO_PESQUISA,e,true);
        }
    }
    @FXML
    void franquia(ActionEvent event) {
    	try {
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.FRANQUIA_PESQUISA);
            loader.setController(new FranquiaPesquisaController(stage));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            onCloseRequestt(stage);
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.FRANQUIA_PESQUISA,e,true);
        }
    }
    @FXML
    void negocio(ActionEvent event) {
    	abrirNegocio(null);
    }
    @Override
	public void initialize(URL location, ResourceBundle resources) {
        atualizar();
        lbUsuarioNome.setText(UsuarioLogado.getInstance().getUsuario()!=null?UsuarioLogado.getInstance().getUsuario().getLogin():"{usuario}");
	}
	void onCloseRequestt(Stage stage){ stage.setOnHiding(event -> atualizar());}

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
            onCloseRequestt(stage);
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
            onCloseRequestt(stage);
        }catch(IOException e) {
        	 alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                     "Falha ao localizar o arquivo "+FXMLEnum.USUARIO_PESQUISA,e,true);
        }
    }
}