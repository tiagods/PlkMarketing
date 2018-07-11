package br.com.tiagods;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.tiagods.config.JPAConfig;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.controller.LoginController;
import br.com.tiagods.util.Atualizador;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartApp extends Application {
	private static Logger log = LoggerFactory.getLogger(StartApp.class);
	Atualizador atualizador = new Atualizador();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		if(atualizador.atualizacaoPendente()) {
			log.debug("Sistema desatualizado");
			atualizador.iniciarAtualizacao();
		}
		else {
			log.debug("Sistema atualizado");
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
			alert.show();
			Stage sta = (Stage) dialogPane.getScene().getWindow();
			
			Runnable run = new Runnable() {
				@Override
				public void run() {
					EntityManager manager = null;
					try {
						log.debug("Abrindo conexao");
						JPAConfig jpa = JPAConfig.getInstance();
						manager = jpa.createManager();
						log.debug("Concluindo conexao");
						Platform.runLater(() -> sta.close());
						log.debug("Invocando login");
						invocarLogin();
					} catch (ExceptionInInitializerError | PersistenceException | ServiceException | JDBCConnectionException e) {
						alert("Falha ao estabelecer conexao com o banco de dados, verifique sua conexao com a internet\n"
								+ e);
						Platform.runLater(() -> sta.close());
					} finally {
						if (manager != null)
							manager.close();
					}
				}
			};
			new Thread(run).start();
		} catch (Exception e) {
			alert("Falha ao localizar o arquivo " + FXMLEnum.PROGRESS_SAMPLE);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void invocarLogin() {
		Runnable run2 = new Runnable() {
			@Override
			public void run() {
				try {
					log.debug("Stage");
					Stage stage = new Stage();
					log.debug("Abrindo loader");
					final FXMLLoader loader = new FXMLLoader(FXMLEnum.LOGIN.getLocalizacao());
					log.debug("Controller");
					loader.setController(new LoginController(stage));
					log.debug("Loader");
					Parent root = loader.load();
					log.debug("Scene");
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Acesso");
					stage.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
					log.debug("show");
					stage.show();
				} catch (IOException e) {
					alert("Falha ao abrir login");
				}
			}
		};
		Platform.runLater(run2);
	}

	private void alert(String mensagem) {
		log.debug(mensagem);
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.getDialogPane().setExpanded(true);
			alert.getDialogPane().setMinSize(400, 200);
			alert.setTitle("Erro");
			alert.setContentText(mensagem);
			alert.showAndWait();
		});
	}
}
