package br.com.tiagods;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.config.init.JPAConfig;
import br.com.tiagods.controller.LoginController;
import br.com.tiagods.repository.Registers;
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
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.Date;

@Slf4j
@SpringBootApplication
public class StartApp extends Application {

	boolean habilitarVerificacaoAtualizaca = false;
	private Atualizador atualizador = new Atualizador();

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stageManager = springContext.getBean(StageManager.class, stage);

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
			displayInitialScene();
		}
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
	}

	/**
	 * Useful to override this method by sub-classes wishing to change the first
	 * Scene to be displayed on startup. Example: Functional tests on main
	 * window.
	 */
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.LOGIN, null);
	}

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(StartApp.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
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
						log.debug("Concluindo conexao "+new Date());
						log.debug("Invocando login");
						//invocarLogin();
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
