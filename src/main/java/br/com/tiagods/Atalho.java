package br.com.tiagods;

import java.io.IOException;

import br.com.tiagods.config.init.UsuarioLogado;
import br.com.tiagods.controller.MenuController;
import br.com.tiagods.model.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
            stage.initModality(Modality.WINDOW_MODAL);
	        stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
            stage.show();
            
    	}catch (IOException ex) {
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
