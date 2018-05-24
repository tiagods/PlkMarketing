package br.com.tiagods;

import br.com.tiagods.config.UsuarioLogado;
import br.com.tiagods.controller.MenuController;
import br.com.tiagods.controller.TarefaPesquisaController;
import br.com.tiagods.exception.FXMLNaoEncontradoException;
import br.com.tiagods.model.Usuario;
import br.com.tiagods.repository.helpers.NegociosTarefasImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            loader.setController(new MenuController());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Acesso");
            //stage.getIcons().add(new Image(estilo.getIcon().toString()));
            stage.show();
    	}catch (FXMLNaoEncontradoException ex) {
    		
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
