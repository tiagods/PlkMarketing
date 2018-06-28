package br.com.tiagods;

import java.io.IOException;

import javax.persistence.EntityManager;

import br.com.tiagods.config.JPAConfig;
import br.com.tiagods.config.UsuarioLogado;
import br.com.tiagods.controller.MenuController;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.NegocioPropostaImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Atalho extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Usuario u  = new Usuario();
			u.setId(1L);			
            UsuarioLogado.getInstance().setUsuario(u);      
            Stage stage = new Stage();
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
            MenuController controller = new MenuController();
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Acesso");
            
            stage.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
            stage.show();
            
    	}catch (IOException ex) {
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
