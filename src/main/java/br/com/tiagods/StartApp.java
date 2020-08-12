package br.com.tiagods;

import br.com.tiagods.config.FxmlView;
import br.com.tiagods.config.StageManager;
import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.util.Atualizador;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.spi.ServiceException;
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
		displayInitialScene();
		if(habilitarVerificacaoAtualizaca){
			if (atualizador.atualizacaoPendente()) {
				log.info("Sistema desatualizado");
				//atualizador.iniciarAtualizacao();
			} else {
				log.info("Sistema atualizado");
			}
		}
		else {
			log.debug("Verificacao de atualizacao foi ignorada");
		}
	}

	@Override
	public void stop() throws Exception {
		springContext.close();
	}

	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.LOGIN, false);
	}

	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(StartApp.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}
}
