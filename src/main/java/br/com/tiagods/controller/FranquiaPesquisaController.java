package br.com.tiagods.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.persistence.PersistenceException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.model.negocio.Franquia;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.FranquiasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FranquiaPesquisaController extends UtilsController implements Initializable{
	  @FXML
	    private HBox pnCheckBox;

	    @FXML
	    private JFXComboBox<Franquia.Tipo> cbTipo;

	    @FXML
	    private JFXComboBox<Usuario> cbAtendente;

	    @FXML
	    private HBox pnCheckBox1;

	    @FXML
	    private JFXTextField txPesquisa;

	    @FXML
	    private TableView<Franquia> tbPrincipal;
	    private FranquiasImpl franquias;
	    private Stage stage;
	    
	    public FranquiaPesquisaController(Stage stage) {
	    	this.stage=stage;
	    }	    
	    private void abrirCadastro(Franquia t) {
	    	try {
				loadFactory();
	            Stage stage = new Stage();
	            FXMLLoader loader = loaderFxml(FXMLEnum.FRANQUIA_CADASTRO);
	            loader.setController(new FranquiaCadastroController(stage,t));
	            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
	            stage.setOnHiding(event -> {
	            	try {
	        			loadFactory();
	        			filtrar();
	        		}catch(Exception e) {
	        			e.printStackTrace();
	        		}finally {
	        			close();
	        		}
	            });
	        }catch(IOException e) {
	            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
	                    "Falha ao localizar o arquivo"+FXMLEnum.FRANQUIA_CADASTRO,e,true);
	        }finally {
	        	close();
	        }
	    	
		}
	    void combos() {
	    	Usuario usuario = new Usuario(-1L, "Atendente");
	    	UsuariosImpl usuarios = new UsuariosImpl(getManager());
	    	
	    	cbAtendente.getItems().add(usuario);
	    	
	    	cbTipo.getItems().addAll(Franquia.Tipo.values());
	    	cbAtendente.getItems().addAll(usuarios.getAll());
	    	
	    	cbTipo.getSelectionModel().select(Franquia.Tipo.TODOS);
	    	cbAtendente.getSelectionModel().selectFirst();
	    	
	    	ChangeListener<Object> change = new ChangeListener<Object>() {
				@Override
				public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
					try {
						loadFactory();
						filtrar();
					}catch(PersistenceException e) {
						alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
					}finally {
						close();
					}	
				}
	    		
	    	};
	    	cbTipo.valueProperty().addListener(change);
	    	cbAtendente.valueProperty().addListener(change);
	    	cbAtendente.setVisible(false);
	    }
	    
	    @FXML
	    void exportar(ActionEvent event) {
	    	
	    }
	    void filtrar() {
	    	franquias = new FranquiasImpl(getManager());
	    	tbPrincipal.getItems().clear();
	    	tbPrincipal.getItems().addAll(franquias.filtrar(txPesquisa.getText(),cbTipo.getValue(),cbAtendente.getValue()));
	    }
	    @Override
		public void initialize(URL location, ResourceBundle resources) {
	    	tabela();
			try {
				loadFactory();
				franquias = new FranquiasImpl(getManager());	
				combos();
				tbPrincipal.getItems().addAll(franquias.getAll());
			}catch(PersistenceException e) {
				alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
			}finally {
				close();
			}	
		}
	    @FXML
	    void novo(ActionEvent event) {
	    	abrirCadastro(null);
	    }
	    @FXML
	    void pesquisar(KeyEvent event) {
	    	try {
				loadFactory();
				filtrar();
			}catch(PersistenceException e) {
				alert(AlertType.ERROR, "Erro", "Erro na consulta","Erro ao realizar consulta", e, true);
			}finally {
				close();
			}	
	    }
	    @FXML
	    void sair(ActionEvent event) {
	    	this.stage.close();
	    }
	    private boolean salvarStatus(Franquia franquia,boolean status){
			try{
				loadFactory();
					franquias = new FranquiasImpl(getManager());
					Franquia t = franquias.findById(franquia.getId());
					t.setAtivo(status);
					t.setLastUpdate(Calendar.getInstance());
					franquias.save(t);
					return true;
			}catch (Exception e){
				alert(AlertType.ERROR,"Erro",null,"Erro ao salvar",e,true);
				return false;
			}finally {
				close();
			}
		}
	    
	    void tabela() {
		TableColumn<Franquia, String> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		TableColumn<Franquia, Boolean> colunaAtivo = new  TableColumn<>("Ativo");
		colunaAtivo.setCellValueFactory(new PropertyValueFactory<>("ativo"));
		colunaAtivo.setCellFactory(param -> new TableCell<Franquia,Boolean>(){
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item?"Sim":"Não");
				}
			}
		});
		TableColumn<Franquia, Franquia.Tipo> colunaTipo = new  TableColumn<>("Tipo");
		colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		
		TableColumn<Franquia, Calendar> colunaUpdate = new  TableColumn<>("Atualização");
		colunaUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
		colunaUpdate.setCellFactory(param -> new TableCell<Franquia,Calendar>(){
			@Override
			protected void updateItem(Calendar item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(sdfH.format(item.getTime()));
				}
			}
		});
		
		TableColumn<Franquia, Boolean> colunaStatus = new  TableColumn<>("Status");
		colunaStatus.setCellValueFactory(new PropertyValueFactory<>("ativo"));
		colunaStatus.setCellFactory(param -> new TableCell<Franquia,Boolean>(){
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					JFXButton rb = new JFXButton();
					rb.setText(item?"Ativo":"Inativo");
					rb.getStyleClass().add(item?"btGreen":"btRed");

					rb.onActionProperty().set(event -> {

						Dialog dialog = new Dialog();
						dialog.setTitle("Alteração de Status");
						dialog.setHeaderText("Selecione um status");

						VBox stackPane = new VBox();
						stackPane.setSpacing(15);

						Map<JFXRadioButton,Integer> map = new HashMap<>();
						ToggleGroup group = new ToggleGroup();

						int value = item?1:0;
						for(int i=0;i<2;i++) {
							JFXRadioButton jfxRadioButton = new JFXRadioButton(i==0?"Inativo":"Ativo");
							jfxRadioButton.setSelectedColor(Color.GREEN);
							jfxRadioButton.setUnSelectedColor(Color.RED);
							if(value==i) jfxRadioButton.setSelected(true);
							group.getToggles().add(jfxRadioButton);
							stackPane.getChildren().add(jfxRadioButton);
							map.put(jfxRadioButton,i);
						}
						ButtonType ok = new ButtonType("Alterar");
						ButtonType cancelar = new ButtonType("Cancelar");
						dialog.getDialogPane().getButtonTypes().addAll(ok,cancelar);
						dialog.getDialogPane().setContent(stackPane);
						dialog.initModality(Modality.APPLICATION_MODAL);
						dialog.initStyle(StageStyle.UNDECORATED);
						Optional<ButtonType> result = dialog.showAndWait();
						if(result.get() == ok){
							Franquia franquia = tbPrincipal.getItems().get(getIndex());
							for(Node f : stackPane.getChildren()){
								if(f instanceof JFXRadioButton && ((JFXRadioButton) f).isSelected()) {
									Integer p = map.get(f);
									if (p!=value && salvarStatus(franquia, p==1)) {
										tbPrincipal.getItems().get(getIndex()).setAtivo(p==1);
										tbPrincipal.getItems().get(getIndex()).setLastUpdate(Calendar.getInstance());;
										tbPrincipal.refresh();
									}
									break;
								}
							};
						}
                    });
					setGraphic(rb);

				}
			}
		});
		
		TableColumn<Franquia, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<Franquia,Number>(){
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
					button.getStyleClass().add("btDefault");
					try {
						buttonTable(button, IconsEnum.BUTTON_EDIT);
					}catch (IOException e) {
					}
					button.setOnAction(event -> {
						abrirCadastro(tbPrincipal.getItems().get(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		tbPrincipal.getColumns().addAll(colunaNome,colunaAtivo,colunaTipo,colunaUpdate,colunaStatus,colunaEditar);
	}

}
