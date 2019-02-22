package br.com.tiagods.controller;

import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.implantacao.ImplantacaoEtapa;
import br.com.tiagods.model.implantacao.ImplantacaoProcesso;
import br.com.tiagods.model.implantacao.ImplantacaoProcessoEtapa;
import br.com.tiagods.util.CalculoDePeriodo;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Calendar;

public class TabelaProcessosEtapa extends UtilsController {

    private TableView<ImplantacaoProcessoEtapa> tbPrincipal;

    private CalculoDePeriodo calculo;

    public TabelaProcessosEtapa(TableView<ImplantacaoProcessoEtapa> tbPrincipal){
        this.tbPrincipal=tbPrincipal;
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

        TableColumn<ImplantacaoProcessoEtapa, Calendar> colunaPrazo = new TableColumn<>("Prazo");
        colunaPrazo.setCellValueFactory(new PropertyValueFactory<>("dataAtualizacao"));
        colunaPrazo.setCellFactory((TableColumn<ImplantacaoProcessoEtapa, Calendar> param) -> new TableCell<ImplantacaoProcessoEtapa, Calendar>() {
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

        TableColumn<ImplantacaoProcessoEtapa, Number> colunaEditar = new  TableColumn<>("Historico");
        colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaEditar.setCellFactory(param -> new TableCell<ImplantacaoProcessoEtapa,Number>(){
            JFXButton button = new JFXButton();//Editar
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
                    button.setOnAction(event -> {
                        //abrirCadastro(tbPrincipal.getItems().get(getIndex()));
                    });
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
                    button.setDisable(item.equals(ImplantacaoProcessoEtapa.Status.CONCLUIDO) ||
                            item.equals(ImplantacaoProcessoEtapa.Status.AGUARDANDO_ANTERIOR));

                    button.getStyleClass().add("");
                    try {
                        buttonTable(button, item.getIcon());
                    }catch (IOException e) {
                    }
                    final Tooltip tooltip = new Tooltip("Clique para criar uma nota!");
                    button.setText(item.toString());
                    button.setTooltip(tooltip);
                    button.setOnAction(event -> {
                        //abrirCadastro(tbPrincipal.getItems().get(getIndex()));
                    });
                    setGraphic(button);
                }
            }
        });

        tbPrincipal.getColumns().addAll(colunaId,colunaData,colunaPrazo,colunaEtapa,colunaEditar,colunaStatus);
    }
}
