package br.com.tiagods;

import java.io.IOException;
import java.util.ArrayList;

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
		ArrayList<ArrayList> listaImpressao = new ArrayList<>();
		Integer[] colunasLenght = new Integer[] 
				{10,20,20,20,30,20,20,10,10,10,10,10,10,15,15,10,15,10,10,10,15,10,15,15,15,20,10,10,10,15,15,15,15,20,20,20,10,10,20,20,20};
		String[] cabecalho = new String[] { "Cod", "Periodo", "Empresa/Pessoa", "Tipo", "Nome", "Razao",
				"Apelido", "Responsavel", "Cnpj", "IM", "IE", "Telefone", "Celular", "E-Mail", "Site",
				"CEP", "Logradouro", "Nº", "Bairro", "Compl", "Cidade", "UF", "Data Criacao", "Atendente",
				"Criador", "Mala Direta", "Convite", "Material", "Newsletter", "Categoria", "Nivel",
				"Servico", "Origem", "Detalhes Origem","Resumo", "Apresentacao","Listas","Qtd Negocios","Status Negocio","Honorário","Servicos Contratados"};
		System.out.println("1>"+colunasLenght.length+" \t2>"+cabecalho.length);
		launch(args);

	}
}
