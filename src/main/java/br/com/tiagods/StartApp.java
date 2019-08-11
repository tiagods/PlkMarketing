package br.com.tiagods;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.tiagods.config.init.JPAConfig;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.controller.LoginController;
import br.com.tiagods.util.Atualizador;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartApp extends Application {

	boolean habilitarVerificacaoAtualizaca = false;
	private static Logger log = LoggerFactory.getLogger(StartApp.class);
	private Atualizador atualizador = new Atualizador();

	@Override
	public void start(Stage primaryStage) throws Exception {
		if(habilitarVerificacaoAtualizaca){
			if (atualizador.atualizacaoPendente()) {
				log.debug("Sistema desatualizado");
				atualizador.iniciarAtualizacao();
			} else {
				log.debug("Sistema atualizado");
				iniciar();
			}
		}
		else {
			log.debug("Verificacao de atualizacao foi ignorada");
			iniciar();
		}
	}

	private void iniciar() {
		try {
			FXMLLoader loader = new FXMLLoader(FXMLEnum.PROGRESS_SAMPLE.getLocalizacao());
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("");
			DialogPane dialogPane = new DialogPane();
			dialogPane.setContent(loader.load());
			alert.setDialogPane(dialogPane);
			Stage sta = (Stage) dialogPane.getScene().getWindow();
			sta.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
			Task<Void> run = new Task<Void>() {
                {
                    setOnFailed(a ->sta.close());
                    setOnSucceeded(a ->sta.close());
                    setOnCancelled(a ->sta.close());
                }
				@Override
				protected Void call() throws Exception {
					EntityManager manager = null;
					try {
						Platform.runLater(()->alert.show());
						log.debug("Abrindo conexao");
						manager = JPAConfig.getInstance().createManager();
						log.debug("Concluindo conexao");
						log.debug("Invocando login");
						invocarLogin();
					} catch (ExceptionInInitializerError | PersistenceException | ServiceException
							| JDBCConnectionException e) {
						alert("Falha ao estabelecer conexao com o banco de dados, verifique sua conexao com a internet\n"
								+ e);
					} finally {
						if (manager != null)
							manager.close();
					}
					return null;
				}
			};
			Thread thread = new Thread(run);
			thread.start();
			sta.setOnCloseRequest(event -> {
				try {
					thread.interrupt();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			alert("Falha ao localizar o arquivo " + FXMLEnum.PROGRESS_SAMPLE);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void invocarLogin() {
		Task<Void> run2 = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Stage stage = new Stage();
					final FXMLLoader loader = new FXMLLoader(FXMLEnum.LOGIN.getLocalizacao());
					loader.setController(new LoginController(stage));
					Parent root = loader.load();
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
					stage.show();
				} catch (IOException e) {
					alert("Falha ao abrir login");
				}
				return null;
			}
		};
		Platform.runLater(run2);
	}
 	private void alert(String mensagem) {
		log.debug(mensagem);
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.getDialogPane().setExpanded(true);
		alert.getDialogPane().setMinSize(400, 200);
		alert.setTitle("Erro");
		alert.setContentText(mensagem);
		alert.showAndWait();
	}
}
