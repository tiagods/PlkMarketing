package br.com.tiagods.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javax.persistence.PersistenceException;

import br.com.tiagods.repository.helpers.filters.NegocioTarefaFilter;
import br.com.tiagods.util.storage.Storage;
import br.com.tiagods.util.storage.StorageProducer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXToggleButton;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.enums.IconsEnum;
import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.model.negocio.Contato;
import br.com.tiagods.model.NegocioTarefa;
import br.com.tiagods.model.NegocioTarefa.TipoTarefa;
import br.com.tiagods.model.negocio.NegocioTarefaContato;
import br.com.tiagods.model.negocio.NegocioTarefaProposta;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.modelcollections.ConstantesTemporarias;
import br.com.tiagods.model.negocio.NegocioProposta;
import br.com.tiagods.repository.Paginacao;
import br.com.tiagods.repository.helpers.ContatosImpl;
import br.com.tiagods.repository.helpers.NegocioPropostaImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasContatosImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasImpl;
import br.com.tiagods.repository.helpers.NegociosTarefasPropostasImpl;
import br.com.tiagods.repository.helpers.UsuariosImpl;
import br.com.tiagods.util.ExcelGenerico;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class TarefaPesquisaController extends UtilsController implements Initializable{

    @FXML
    private JFXCheckBox ckEmail;

    @FXML
    private JFXCheckBox ckProposta;

    @FXML
    private JFXCheckBox ckReuniao;

    @FXML
    private JFXCheckBox ckTelefone;

    @FXML
    private JFXCheckBox ckWhatsApp;

    @FXML
    private JFXRadioButton rbTudo;

    @FXML
    private JFXRadioButton rbHoje;

    @FXML
    private JFXRadioButton rbSemana;

    @FXML
    private JFXRadioButton rbDefinir;

    @FXML
    private HBox pnDatas;

    @FXML
    private HBox pnRadio;
    
    @FXML
    private HBox pnCheckBox;

    @FXML
    private JFXDatePicker dataInicial;

    @FXML
    private JFXDatePicker dataFinal;

    @FXML
    private JFXToggleButton tggFinalizado;

    @FXML
    private JFXComboBox<Usuario> cbAtendente;
    
    @FXML
    private TableView<NegocioTarefa> tbPrincipal;

    @FXML
	private Pagination pagination;

	@FXML
	private JFXComboBox<Integer> cbLimite;

	private Paginacao paginacao;
    
    private Stage stage;
	private UsuariosImpl usuarios;
	private NegociosTarefasImpl tarefas;
	private NegociosTarefasContatosImpl tarefasContatos;
	private NegociosTarefasPropostasImpl tarefasPropostas;
	private NegocioPropostaImpl propostas;
	private ContatosImpl contatos;
	private Storage storage = StorageProducer.newConfig();
	private NegocioTarefaFilter filter;

	public TarefaPesquisaController (NegocioTarefaFilter filter, Stage stage) {
		this.filter = filter;
		this.stage=stage;
	}
	private void abrirCadastro(NegocioTarefa t) {
		try {
			loadFactory();
			if(t!=null) {
				tarefas = new NegociosTarefasImpl(getManager());
				t = tarefas.findById(t.getId());//refresh
			}
            Stage stage = new Stage();
            FXMLLoader loader = loaderFxml(FXMLEnum.TAREFA_CADASTRO);
            loader.setController(new TarefaCadastroController(stage,t,null));
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
            	try {
        			loadFactory();
        			filtrar(this.paginacao);
        		}catch(Exception e) {
        			e.printStackTrace();
        		}finally {
        			close();
        		}
            });
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo"+FXMLEnum.TAREFA_CADASTRO,e,true);
        }finally {
        	close();
        }
	}
	void abrirCadastroContatoOrProposta(NegocioTarefa t) {
		try {
			loadFactory();
            Stage stage = new Stage();
            FXMLLoader loader = null;
            if(t instanceof NegocioTarefaContato) {
            	Contato c = ((NegocioTarefaContato)t).getContato();
            	contatos = new ContatosImpl(getManager());
            	c = contatos.findById(c.getId());
            	loader = loaderFxml(FXMLEnum.CONTATO_CADASTRO);
            	loader.setController(new ContatoCadastroController(stage,c));	
            }
            else if(t instanceof NegocioTarefaProposta) {
            	NegocioProposta p = ((NegocioTarefaProposta)t).getProposta();
            	propostas = new NegocioPropostaImpl(getManager());
            	p = propostas.findById(p.getId());
            	loader = loaderFxml(FXMLEnum.NEGOCIO_CADASTRO);
            	loader.setController(new NegocioCadastroController(stage,p,null));
            }
            initPanel(loader, stage, Modality.APPLICATION_MODAL, StageStyle.DECORATED);
            stage.setOnHiding(event -> {
            	try {
        			loadFactory();
        			filtrar(this.paginacao);
        		}catch(Exception e) {
        			e.printStackTrace();
        		}finally {
        			close();
        		}
            });
        }catch(IOException e) {
            alert(Alert.AlertType.ERROR, "Erro", "Erro ao abrir o cadastro",
                    "Falha ao localizar o arquivo "+FXMLEnum.CONTATO_CADASTRO+" ou "+FXMLEnum.NEGOCIO_CADASTRO,e,true);
        }finally {
			close();
		}
	}
	
	void combos() {
		
		cbLimite.getItems().addAll(limiteTabela);
		cbLimite.getSelectionModel().selectFirst();
		
		rbHoje.setSelected(true);
		pnDatas.setVisible(false);
		
		ToggleGroup group = new ToggleGroup();
		group.getToggles().addAll(rbDefinir,rbHoje,rbSemana,rbTudo);
		usuarios = new UsuariosImpl(getManager());
		Usuario usuario = new Usuario(-1L,"Todos");
		cbAtendente.getItems().add(usuario);
		cbAtendente.getItems().addAll(usuarios.filtrar("", 1, ConstantesTemporarias.pessoa_nome));
		
		cbAtendente.getSelectionModel().select(UsuarioLogado.getInstance().getUsuario());
		
		ChangeListener<Object> changeListener = (observable, oldValue, newValue) -> {
            try {
                pnDatas.setVisible(rbDefinir.isSelected());
                loadFactory();
                paginacao = new Paginacao(cbLimite.getValue());
                filtrar(paginacao);
            }catch (PersistenceException e) {
                alert(AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao executar a consulta", e, true);
            }finally {
                close();
            }
        };
		cbLimite.valueProperty().addListener(changeListener);
		
		pnCheckBox.getChildren().forEach(c->{
			if(c instanceof JFXCheckBox) {
				((JFXCheckBox)c).setSelected(true);
				((JFXCheckBox)c).selectedProperty().addListener(changeListener);
			}
		});
		
		pnRadio.getChildren().forEach(c->{
			if(c instanceof JFXRadioButton) ((JFXRadioButton)c).selectedProperty().addListener(changeListener);
		});
		dataInicial.valueProperty().addListener(changeListener);
		dataFinal.valueProperty().addListener(changeListener);
		
		cbAtendente.valueProperty().addListener(changeListener);
		
		tggFinalizado.setSelected(true);
		tggFinalizado.selectedProperty().addListener(changeListener);
		
		pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            paginacao.setPaginaAtual(newValue.intValue());
            try {
                loadFactory();
                filtrar(paginacao);
            } catch (PersistenceException e) {
                alert(AlertType.ERROR, "Erro", "Erro na consulta", "Erro ao realizar consulta", e, true);
            } finally {
                close();
            }
        });
		paginacao = new Paginacao(cbLimite.getValue());
	}
	boolean excluir(NegocioTarefa n) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Exclusão...");
		alert.setHeaderText(null);
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Tem certeza disso?");
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == ButtonType.OK) {
			try{
				loadFactory();
				tarefas = new NegociosTarefasImpl(getManager());
				NegocioTarefa t = tarefas.findById(n.getId());
				tarefas.remove(t);
				alert(AlertType.INFORMATION, "Sucesso", null, "Removido com sucesso!",null, false);
				return true;
			}catch(Exception e){
				super.alert(Alert.AlertType.ERROR, "Erro", null,"Falha ao excluir o registro", e,true);
				return false;
			}finally{
				super.close();
			}
		}
		else return false;
	}
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
    void exportar(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(FXMLEnum.PROGRESS_SAMPLE.getLocalizacao());
			Alert progress = new Alert(Alert.AlertType.INFORMATION);
			progress.setHeaderText("");
			DialogPane dialogPane = new DialogPane();
			dialogPane.setContent(loader.load());
			progress.setDialogPane(dialogPane);
			Stage sta = (Stage) dialogPane.getScene().getWindow();

			Task<Void> run = new Task<Void>() {
				{
					setOnFailed(a ->sta.close());
					setOnSucceeded(a ->sta.close());
					setOnCancelled(a ->sta.close());
				}
				@Override
				protected Void call() {

					File export = salvarTemp("xls");
					Platform.runLater(() -> sta.show());
					ArrayList<ArrayList> listaImpressao = new ArrayList<>();
					Integer[] colunasLenght = new Integer[] { 10, 18, 18, 14, 10, 30, 10, 10, 10, 10,20, 15, 15 };
					String[] cabecalho = new String[] { "Tarefa", "Mês", "Prazo", "Andamento", "Status", "Detalhes", "Tipo",
							"Registro", "Nome", "Ultimo Negocio","Status Negocio / Ultimo Negocio", "Atendente", "Criado_Por" };
					listaImpressao.add(new ArrayList<>());
					for (String c : cabecalho) {
						listaImpressao.get(0).add(c);
					}
					try {

						loadFactory();
						List<NegocioTarefa> finalList = filtrar(null);

						for (int i = 1; i <= finalList.size(); i++) {
							listaImpressao.add(new ArrayList<String>());
							NegocioTarefa c = finalList.get(i-1);
							listaImpressao.get(i).add(c.getId());
							listaImpressao.get(i).add(new SimpleDateFormat("MM/yyyy").format(c.getDataEvento().getTime()));
							listaImpressao.get(i).add(sdfH.format(c.getDataEvento().getTime()));
							listaImpressao.get(i).add(c.getTipoTarefa().getDescricao());
							listaImpressao.get(i).add(c.getFinalizado()==0?"Pendente":"Finalizado");
							
							listaImpressao.get(i).add(c.getDescricao());
							String classe = "Contato";
							long id = 0;
							String nome="";
							String ultimoNegocio = "";
							String statusUltimoNegocio= "";
							
							if(c instanceof NegocioTarefaProposta) {
								classe = "Proposta";
								id = ((NegocioTarefaProposta)c).getProposta().getId();
								nome = ((NegocioTarefaProposta)c).getProposta().getNome();
								statusUltimoNegocio = ((NegocioTarefaProposta)c).getProposta().getTipoStatus().toString();
							}
							else if(c instanceof NegocioTarefaContato) {
								classe = "Contato";
								id = ((NegocioTarefaContato)c).getContato().getId();
								nome = ((NegocioTarefaContato)c).getContato().getNome();
								NegocioProposta p = ((NegocioTarefaContato)c).getContato().getUltimoNegocio();
								if(p!=null) {
									statusUltimoNegocio = p.getTipoStatus().getDescricao();
									ultimoNegocio = ""+p.getId();
								}
							}
							listaImpressao.get(i).add(classe);
							listaImpressao.get(i).add(id);
							listaImpressao.get(i).add(nome);
							listaImpressao.get(i).add(ultimoNegocio);
							listaImpressao.get(i).add(statusUltimoNegocio);
							listaImpressao.get(i).add(c.getAtendente().getNome());
							listaImpressao.get(i).add(c.getCriadoPor()!=null?c.getCriadoPor().getNome():"");
						}
						ExcelGenerico planilha = new ExcelGenerico(export.getAbsolutePath(), listaImpressao, colunasLenght);
						planilha.gerarExcel();
						salvarLog(getManager(), "Negocio","Exportar","Exportou relatorio xls");
						Platform.runLater(() ->alert(AlertType.INFORMATION,"Sucesso", "Relatorio gerado com sucesso","",null,false));
						Desktop.getDesktop().open(export);
					} catch (Exception e1) {
						Platform.runLater(() ->alert(AlertType.ERROR,"Erro","","Erro ao criar a planilha",e1,true));
					} finally {
						close();
					}
					return null;
			}
    		
    	};
    	if (tbPrincipal.getItems().size() >= 1) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Exportação");
			alert.setContentText("Para exportar, realize um filtro antes!\nVocê ja filtrou os dados?");
			ButtonType ok = new ButtonType("Exportar e Abrir");
			ButtonType cancelar = new ButtonType("Cancelar");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().addAll(ok, cancelar);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ok) {
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
		} else {
			alert(AlertType.ERROR,"Erro","Parâmetros vazios","Nenhum registro foi encontrato",null,false);
		}
		}catch (IOException e){
			alert(AlertType.ERROR, "Erro", "Erro ao abrir o progresso", "O arquivo nao foi localizado",null,false);
		}
    	
    }
    private List<NegocioTarefa> filtrar(Paginacao paginacao) {
    	tarefas = new NegociosTarefasImpl(getManager());
		Calendar dataEventoInicial = Calendar.getInstance();
    	Calendar dataEventoFinal = Calendar.getInstance();
    	
    	Set<TipoTarefa> tipoTarefas = new HashSet<>();
    	if(!tipoTarefas.isEmpty()){
    		if(ckReuniao.isSelected()) tipoTarefas.add(TipoTarefa.REUNIAO);
    		if(ckProposta.isSelected()) tipoTarefas.add(TipoTarefa.PROPOSTA);
    		if(ckTelefone.isSelected()) tipoTarefas.add(TipoTarefa.TELEFONE);
    		if(ckEmail.isSelected()) tipoTarefas.add(TipoTarefa.EMAIL);
    		if(ckWhatsApp.isSelected()) tipoTarefas.add(TipoTarefa.WHATSAPP);
    	}
		if(rbHoje.isSelected()){
			dataEventoInicial=GregorianCalendar.from(LocalDate.now().atTime(0,0,0).atZone(ZoneId.systemDefault()));
			dataEventoFinal=GregorianCalendar.from(LocalDate.now().atTime(23, 59, 59).atZone(ZoneId.systemDefault()));
		}
		else if(rbSemana.isSelected()){
			Calendar[] datas = receberDatasSemana();
			if(datas!=null) {
				dataEventoInicial = datas[0];
				dataEventoFinal = datas[1];
			}
		}
		else if(rbDefinir.isSelected() && validarDatas()){
			dataEventoInicial=GregorianCalendar.from(dataInicial.getValue().atStartOfDay(ZoneId.systemDefault()));
			dataEventoFinal=GregorianCalendar.from(dataInicial.getValue().atTime(23, 59, 59).atZone(ZoneId.systemDefault()));
		}
		else if(rbTudo.isSelected() || !validarDatas()) {
			dataEventoInicial=null;
			dataEventoFinal = null;
		}
		int finalizado =-1;
		if(tggFinalizado.isSelected()) finalizado = 0;
		NegocioTarefaFilter filter = new NegocioTarefaFilter();
		filter.setFinalizado(finalizado);
		filter.setAtendente(cbAtendente.getValue());
		filter.setDataEventoInicial(dataEventoInicial);
		filter.setDataEventoFinal(dataEventoFinal);
		filter.setTipoTarefas(tipoTarefas);
		Pair<List<NegocioTarefa>, Paginacao> list = tarefas.filtrar(paginacao,filter);
		if(paginacao!=null) {
			tbPrincipal.getItems().clear();
			tbPrincipal.getItems().addAll(list.getKey());
		}
		return list.getKey();
    }
	private Label imageTableTipoTarefa(TipoTarefa item) throws IOException{
		IconsEnum icon = IconsEnum.BUTTON_TAREFA_EMAIL;
		if(item.equals(TipoTarefa.EMAIL)) icon = IconsEnum.BUTTON_TAREFA_EMAIL;
		else if(item.equals(TipoTarefa.PROPOSTA)) icon= IconsEnum.BUTTON_TAREFA_PROPOSTA;
		else if(item.equals(TipoTarefa.REUNIAO)) icon= IconsEnum.BUTTON_TAREFA_REUNIAO;
		else if(item.equals(TipoTarefa.TELEFONE)) icon= IconsEnum.BUTTON_TAREFA_FONE;
		else if(item.equals(TipoTarefa.WHATSAPP)) icon= IconsEnum.BUTTON_TAREFA_WHATSAPP;
		ImageView image =  createImage(30,30,icon);
		Label label = new Label();
		label.setGraphic(image);
		label.setTooltip(new Tooltip(item.getDescricao()));
		return label;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tabela();
		try {
			loadFactory();
			combos();
			filtrar(paginacao);
		}catch(Exception e) {
			alert(AlertType.ERROR, "Erro", "Erro ao carregar formulario","Erro ao realizar consulta", e, true);
		}finally {
			close();
		}
	}
	@FXML
    public void novo(ActionEvent event) {
		abrirCadastro(null);
    }
	private Calendar[] receberDatasSemana(){
		LocalDate dataHoje = LocalDate.now();
		DayOfWeek day = dataHoje.getDayOfWeek();
		switch(day.getValue()){
		case 1:
			return processarSemana(1,7);
		case 2:
			return processarSemana(0,6);
		case 3:
			return processarSemana(-1,5);
		case 4:
			return processarSemana(-2,4);
		case 5:
			return processarSemana(-3,3);
		case 6:
			return processarSemana(-4,2);
		case 7:
			return processarSemana(-5,1);
		default:
			return null;
		}
	}
	private Calendar[] processarSemana(int diaSegunda, int diaDomingo){
		LocalDate dataHoje = LocalDate.now();
		LocalDate novaDataHoje = dataHoje.plusDays(diaSegunda);
		Calendar data1 = Calendar.getInstance();
		Calendar data2 = Calendar.getInstance();
		data1.set(novaDataHoje.getYear(), novaDataHoje.getMonthValue()-1, novaDataHoje.getDayOfMonth()-1,0,0,0);
		LocalDate novaDataFimDeSemana = dataHoje.plusDays(diaDomingo);
		data2.set(novaDataFimDeSemana.getYear(), novaDataFimDeSemana.getMonthValue()-1, novaDataFimDeSemana.getDayOfMonth()-1,23,59,59);
		return new Calendar[] {data1,data2};
	}
    @FXML
    void sair(ActionEvent event) {
    	stage.close();
    }	
    private boolean salvarStatus(NegocioTarefa tarefa,int status){
		try{
			loadFactory();
			if(tarefa instanceof NegocioTarefaContato) {
				tarefasContatos = new NegociosTarefasContatosImpl(getManager());
				NegocioTarefaContato t = tarefasContatos.findById(tarefa.getId());
				t.setFinalizado(status);
				tarefasContatos.save(t);
				return true;
			}
			else if(tarefa instanceof NegocioTarefaProposta) {
				tarefasPropostas = new NegociosTarefasPropostasImpl(getManager());
				NegocioTarefaProposta t = tarefasPropostas.findById(tarefa.getId());
				t.setFinalizado(status);
				tarefasPropostas.save(t);
				return true;
			}
			return false;
		}catch (Exception e){
			alert(AlertType.ERROR,"Erro",null,"Erro ao salvar",e,true);
			return false;
		}finally {
			close();
		}
	}
    @SuppressWarnings("unchecked")
	void tabela() {
    	TableColumn<NegocioTarefa, Number> columnId = new  TableColumn<>("*");
		columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnId.setPrefWidth(40);
		
		TableColumn<NegocioTarefa, Calendar> colunaValidade = new  TableColumn<>("Prazo");
		colunaValidade.setCellValueFactory(new PropertyValueFactory<>("dataEvento"));
		colunaValidade.setCellFactory(param -> new TableCell<NegocioTarefa,Calendar>(){
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
		colunaValidade.setPrefWidth(130);
		TableColumn<NegocioTarefa, TipoTarefa> colunaTipo = new  TableColumn<>("Tipo");
		colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipoTarefa"));
		colunaTipo.setCellFactory(param -> new TableCell<NegocioTarefa,TipoTarefa>(){
			@Override
			protected void updateItem(TipoTarefa item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					try {
						setGraphic(imageTableTipoTarefa(item));
					} catch (IOException e) {
					}
				}
			}
		});
		colunaTipo.setPrefWidth(40);
		TableColumn<NegocioTarefa, Number> colunaNome = new  TableColumn<>("Nome");
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellFactory(param -> new TableCell<NegocioTarefa,Number>(){
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					NegocioTarefa t = tbPrincipal.getItems().get(getIndex());
					setText(t.toString());
				}
			}
		});
		colunaNome.setPrefWidth(150);
		TableColumn<NegocioTarefa, String> colunaDescricao = new  TableColumn<>("Descricao");
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		colunaDescricao.setCellFactory((TableColumn<NegocioTarefa, String> param) -> {
			final TableCell<NegocioTarefa, String> cell = new TableCell<NegocioTarefa, String>() {
				final JFXTextArea textArea = new JFXTextArea();
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					textArea.setEditable(false);
					textArea.setWrapText(true);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						textArea.setText(item);
						setGraphic(textArea);
						setText(null);
					}
				}
			};
			return cell;
		});
		colunaDescricao.setMinWidth(300);
		
		TableColumn<NegocioTarefa, Number> colunaStatus = new  TableColumn<>("Status");
		colunaStatus.setCellValueFactory(new PropertyValueFactory<>("finalizado"));
		colunaStatus.setCellFactory(param -> new TableCell<NegocioTarefa,Number>(){
			@Override
			protected void updateItem(Number item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					JFXButton rb = new JFXButton();
					rb.setText(item.intValue()==0?"Pendente":"Concluido");
					rb.getStyleClass().add(item.intValue()==0?"btRed":"btGreen");
					rb.setTooltip(new Tooltip("Possibilita alterar o status da tarefa \nde concluido para pendente e vice-versa"));
					rb.onActionProperty().set(event -> {

						Dialog dialog = new Dialog();
						dialog.setTitle("Alteração de Status");
						dialog.setHeaderText("Selecione um status");

						VBox stackPane = new VBox();
						stackPane.setSpacing(15);

						Map<JFXRadioButton,Integer> map = new HashMap<>();
						ToggleGroup group = new ToggleGroup();
						
						for(int i=0;i<2;i++) {
							JFXRadioButton jfxRadioButton = new JFXRadioButton(i==0?"Pendente":"Concluido");
							jfxRadioButton.setSelectedColor(Color.GREEN);
							jfxRadioButton.setUnSelectedColor(Color.RED);
							if(item.intValue()==i) jfxRadioButton.setSelected(true);
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
							NegocioTarefa tarefa = tbPrincipal.getItems().get(getIndex());
							for(Node f : stackPane.getChildren()){
								if(f instanceof JFXRadioButton && ((JFXRadioButton) f).isSelected()) {
									Integer p = map.get(f);
									if (p!=item.intValue() && salvarStatus(tarefa, p)) {
										tbPrincipal.getItems().get(getIndex()).setFinalizado(p);
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
		colunaStatus.setPrefWidth(100);
		TableColumn<NegocioTarefa, Usuario> columnAtendente = new  TableColumn<>("Atendente");
		columnAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		columnAtendente.setCellFactory(param -> new TableCell<NegocioTarefa,Usuario>(){
			@Override
			protected void updateItem(Usuario item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					setText(item.getLogin());
				}
			}
		});
		TableColumn<NegocioTarefa, String> colunaAnexo = new  TableColumn<>("");
		colunaAnexo.setCellValueFactory(new PropertyValueFactory<>("formulario"));
		colunaAnexo.setCellFactory(param -> new TableCell<NegocioTarefa,String>(){
			JFXButton button = new JFXButton("");
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if(item==null || item.equals("")){
					setStyle("");
					setText("");
					setGraphic(null);
				}
				else{
					button.getStyleClass().add("btDefault");
					try {
						IconsEnum ic = IconsEnum.BUTTON_CLIP;
						buttonTable(button, ic);
					}catch (IOException e) {
					}
					button.setOnAction(event -> {
						visualizarDocumento(item, storage);
					});
					setGraphic(button);
				}
			}
		});
		colunaAnexo.setMaxWidth(50);

		TableColumn<NegocioTarefa, Number> colunaAbrir = new  TableColumn<>("");
		colunaAbrir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaAbrir.setCellFactory(param -> new TableCell<NegocioTarefa,Number>(){
			JFXButton button = new JFXButton("");
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
						IconsEnum ic = IconsEnum.BUTTON_PROPOSTA;
						if(tbPrincipal.getItems().get(getIndex()) instanceof NegocioTarefaContato) 
							ic = IconsEnum.BUTTON_CONTATO;
						buttonTable(button, ic);
					}catch (IOException e) {
					}
					button.setOnAction(event -> {
						abrirCadastroContatoOrProposta(tbPrincipal.getItems().get(getIndex()));
					});
					setGraphic(button);
				}
			}
		});
		colunaAbrir.setMaxWidth(50);
		
		TableColumn<NegocioTarefa, Number> colunaEditar = new  TableColumn<>("");
		colunaEditar.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaEditar.setCellFactory(param -> new TableCell<NegocioTarefa,Number>(){
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
		colunaEditar.setMaxWidth(50);
		TableColumn<NegocioTarefa, Number> colunaExcluir = new  TableColumn<>("");
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaExcluir.setCellFactory(param -> new TableCell<NegocioTarefa,Number>(){
			JFXButton button = new JFXButton();//excluir
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
						buttonTable(button,IconsEnum.BUTTON_REMOVE);
					}catch (IOException e) {
					}
					final Tooltip tooltip = new Tooltip("Clique para remover o registro!");
					button.setTooltip(tooltip);
					button.setOnAction(event -> {
						boolean removed = excluir(tbPrincipal.getItems().get(getIndex()));
						if(removed) tbPrincipal.getItems().remove(getIndex());
					});
					setGraphic(button);
				}
			}
		});
		colunaExcluir.setMaxWidth(50);
		tbPrincipal.getColumns().addAll(colunaValidade,colunaTipo,colunaNome,colunaDescricao,
				columnAtendente,colunaStatus,colunaAnexo,colunaAbrir,colunaEditar,colunaExcluir);
		tbPrincipal.setTableMenuButtonVisible(true);
		tbPrincipal.setFixedCellSize(70);
    }
    private boolean validarDatas(){
		if(dataInicial.getValue() == null || dataFinal.getValue()==null ) return false;
		else return dataFinal.getValue().compareTo(dataInicial.getValue())>=0;
	}
    private boolean verificarSeHoje(LocalDate data1,LocalDate data2){
		return data1.compareTo(LocalDate.now())==0 && data1.compareTo(data2)==0;
	}
}
