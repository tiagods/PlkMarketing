package br.com.tiagods.controller;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.implantacao.*;
import br.com.tiagods.repository.helpers.ImplantacaoPacotesImpl;
import br.com.tiagods.repository.helpers.ImplantacaoProcessoEtapasImpl;
import br.com.tiagods.util.CalculoDePeriodo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TabelaProcessosEtapa extends UtilsController {

    private TableView<ImplantacaoProcessoEtapa> tbPrincipal;

    private CalculoDePeriodo calculo;
    private ImplantacaoProcessoEtapasImpl etapas;

    public TabelaProcessosEtapa(TableView<ImplantacaoProcessoEtapa> tbPrincipal){
        this.tbPrincipal=tbPrincipal;
    }

        private void cadastrarEtapas(ImplantacaoProcessoEtapa etapa, int index,boolean editar){
            try {
                Stage stage = new Stage();
                FXMLLoader loader = loaderFxml(FXMLEnum.IMPLANTACAO_ETAPA_STATUS);
                ImplantacaoProcessoEtapa etapa2 = recarregar(etapa);
                ImplantacaoEtapaStatusController controller = new ImplantacaoEtapaStatusController(etapa2,stage,editar);
                loader.setController(controller);
                initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
                stage.setOnHiding(event->{
                    ImplantacaoProcessoEtapa newEtapa = recarregar(etapa2);
                    if(newEtapa!=null) tbPrincipal.getItems().set(index,newEtapa);
                });
            } catch (IOException ex) {
                alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro", "Falha ao localizar o arquivo " + FXMLEnum.IMPLANTACAO_ETAPA_STATUS, ex, true);
            }
        }
        private ImplantacaoProcessoEtapa recarregar(ImplantacaoProcessoEtapa etapa){
            try{
                loadFactory();
                etapas = new ImplantacaoProcessoEtapasImpl(getManager());
                etapa = etapas.findById(etapa.getId());
                return etapa;
            } catch (Exception e){
                alert(Alert.AlertType.ERROR,"Erro","Erro ao carregar os registros","Ocorreu um erro ao carregar o registro",e,true);
                return null;
            }
            finally {
                close();
            }
        }
    private boolean salvarEConcluir(String descricao, ImplantacaoProcessoEtapa ip){
        try{
            loadFactory();
            etapas = new ImplantacaoProcessoEtapasImpl(getManager());

            ip = etapas.findById(ip.getId());

            ImplantacaoProcessoEtapaStatus status = new ImplantacaoProcessoEtapaStatus();
            status.setDescricao(descricao);
            status.setFinalizado(true);
            status.setProcessoEtapa(ip);

            Set<ImplantacaoProcessoEtapaStatus> statusSet = ip.getHistorico();
            statusSet.add(status);

            ip.setDataAtualizacao(Calendar.getInstance());
            ip.setStatus(ImplantacaoProcessoEtapa.Status.CONCLUIDO);
            ip = etapas.save(ip);

            ImplantacaoEtapa.Etapa etapa = ip.getEtapa().getEtapa();
            ImplantacaoEtapa.Etapa nextEtapa = null;
            for(ImplantacaoEtapa.Etapa s : ImplantacaoEtapa.Etapa.values()){
                if(s.getValor()==etapa.getValor()+1) {
                    nextEtapa = s;
                    break;
                }
            }
            if(nextEtapa!=null){
                List<ImplantacaoProcessoEtapa> list =
                        etapas.filtrar(null,null,ip.getEtapa().getAtividade(),nextEtapa);
                if(!list.isEmpty()){
                    ImplantacaoProcessoEtapa processoEtapa = list.get(0);
                    processoEtapa.setStatus(ImplantacaoProcessoEtapa.Status.ABERTO);
                    processoEtapa.setDataLiberacao(Calendar.getInstance());
                    processoEtapa.setDataAtualizacao(Calendar.getInstance());
                    etapas.save(processoEtapa);
                }
            }
            tbPrincipal.refresh();
            alert(Alert.AlertType.INFORMATION,"Sucesso","","Salvo com sucesso!",null,false);
            return true;
        }catch (Exception e){
            alert(Alert.AlertType.ERROR,"Erro","","Falha ao tentar salvar o registro!",e, true);
            return false;
        }finally {
            close();
        }
    }

    void tabela(){
        tbPrincipal.getColumns().clear();

        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoProcesso> colunaId = new TableColumn<>("*");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("processo"));

        TableColumn<ImplantacaoProcessoEtapa, Calendar> colunaData = new TableColumn<>("Data Liberaçao");
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataLiberacao"));
        colunaData.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, Calendar> param) -> new TableCell<ImplantacaoProcessoEtapa, Calendar>() {
            @Override
            protected void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                } else {
                    setText(sdf.format(item.getTime()));
                }
            }
        });

        TableColumn<ImplantacaoProcessoEtapa, Number> colunaPrazo = new TableColumn<>("Prazo");
        colunaPrazo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaPrazo.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, Number> param) -> new TableCell<ImplantacaoProcessoEtapa, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                } else {
                    ImplantacaoProcessoEtapa processoEtapa = tbPrincipal.getItems().get(getIndex());
                    setText(processoEtapa.getStatusVencimento());
                    if(processoEtapa.getVencido().equals(ImplantacaoProcessoEtapa.Vencido.VENCIDO))
                        setStyle("-fx-background-color: red; -fx-text-fill: white;");
                    else if(processoEtapa.getVencido().equals(ImplantacaoProcessoEtapa.Vencido.NO_PRAZO))
                        setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    else setStyle("");
                }
            }
        });
        colunaPrazo.setPrefWidth(150);

        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaEtapa = new TableColumn<>("Etapa");
        colunaEtapa.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaEtapa.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> param) -> new TableCell<ImplantacaoProcessoEtapa, ImplantacaoEtapa>() {
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                } else {
                    setText(item.getEtapa().toString());
                }
            }
        });
        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaAtividade = new TableColumn<>("Atividade");
        colunaAtividade.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaAtividade.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> param) -> new TableCell<ImplantacaoProcessoEtapa, ImplantacaoEtapa>() {
            final JFXTextArea area = new JFXTextArea();
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    area.setText(item.getAtividade().toString());
                    setGraphic(area);
                }
            }
        });
        colunaAtividade.setPrefWidth(150);
        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> colunaDescricao = new TableColumn<>("O que devo fazer?");
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("etapa"));
        colunaDescricao.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, ImplantacaoEtapa> param) -> new TableCell<ImplantacaoProcessoEtapa, ImplantacaoEtapa>() {
            final JFXTextArea area = new JFXTextArea();
            @Override
            protected void updateItem(ImplantacaoEtapa item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                    setStyle("");
                    setGraphic(null);
                } else {
                    area.setText(item.getDescricao());
                    setGraphic(area);
                }
            }
        });
        colunaDescricao.setPrefWidth(150);

        TableColumn<ImplantacaoProcessoEtapa, Number> colunaEditar = new  TableColumn<>("Historico");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa,Number>(){
            final JFXButton button = new JFXButton();
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else{
                    ImplantacaoProcessoEtapa ip = tbPrincipal.getItems().get(getIndex());
                    button.getStyleClass().add("btDefault");
                    setGraphic(button);
                    try {
                        if(ip.getStatus().equals(ImplantacaoProcessoEtapa.Status.CONCLUIDO))
                            buttonTable(button, IconsEnum.BUTTON_VIEW);
                        else if(ip.getStatus().equals(ImplantacaoProcessoEtapa.Status.ABERTO))
                            buttonTable(button, IconsEnum.BUTTON_EDIT);
                        else setGraphic(null);
                    }catch (IOException e) {}
                    final Tooltip tooltip = new Tooltip("Clique para criar uma nota!");
                    button.setTooltip(tooltip);
                    button.setOnAction(event -> cadastrarEtapas(ip,getIndex(),ip.getStatus().equals(ImplantacaoProcessoEtapa.Status.ABERTO)));
                }
            }
        });
        TableColumn<ImplantacaoProcessoEtapa, ImplantacaoProcessoEtapa.Status> colunaStatus = new TableColumn<>("Status");
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colunaStatus.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa,ImplantacaoProcessoEtapa.Status>(){
            JFXButton button = new JFXButton();
            @Override
            protected void updateItem(ImplantacaoProcessoEtapa.Status item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null){
                    setStyle("");
                    setText("");
                    setGraphic(null);
                }
                else{
                    ImplantacaoProcessoEtapa ip = tbPrincipal.getItems().get(getIndex());
                    button.setDisable(item.equals(ImplantacaoProcessoEtapa.Status.CONCLUIDO) ||
                            item.equals(ImplantacaoProcessoEtapa.Status.AGUARDANDO_ANTERIOR));
                    button.getStyleClass().add("");
                    try {
                        buttonTable(button, item.getIcon());
                    }catch (IOException e) {}
                    final Tooltip tooltip = new Tooltip("Clique para encerrar essa etapa");
                    button.setText(item.equals(ImplantacaoProcessoEtapa.Status.ABERTO)?"Baixar":item.toString());
                    button.setTooltip(tooltip);
                    button.setOnAction(event -> {
                        TextInputDialog dialog = new TextInputDialog("");
                        dialog.setTitle("Entrada de texto");
                        dialog.setHeaderText("Entre com uma observacao para finalizar essa etapa");
                        dialog.setContentText("Informe uma observacao:");
                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()){
                            if(salvarEConcluir(result.get(),ip))
                                tbPrincipal.getItems().remove(ip);
                        }
                    });
                    setGraphic(button);
                }
            }
        });
        colunaStatus.setPrefWidth(200);
        tbPrincipal.setFixedCellSize(70);
        tbPrincipal.getColumns().addAll(colunaId,colunaData,colunaPrazo,colunaEtapa,colunaAtividade,colunaDescricao,colunaEditar,colunaStatus);
    }
}
