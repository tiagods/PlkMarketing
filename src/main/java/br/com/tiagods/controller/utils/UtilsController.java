package br.com.tiagods.controller.utils;

import br.com.tiagods.config.enums.FXMLEnum;
import br.com.tiagods.repository.helpers.NegociosPropostasImpl;
import br.com.tiagods.util.JavaFxUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;

public abstract class UtilsController {

	private boolean habilidarFiltroCidade = true;

	protected final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
	protected final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	protected final SimpleDateFormat sdfH = new SimpleDateFormat("dd/MM/yyyy HH:mm");


	private NegociosPropostasImpl propostas;

	protected Optional<String> cadastroRapido(){
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Cadastro rapido");
		dialog.setHeaderText("Crie um novo registro:");
		dialog.setContentText("Por favor entre com um novo nome");
		return dialog.showAndWait();
	}

	protected Stage startProgress(){
		try {
			FXMLLoader loader = new FXMLLoader(FXMLEnum.PROGRESS_SAMPLE.getLocalizacao());
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("");
			DialogPane dialogPane = new DialogPane();
			dialogPane.setContent(loader.load());
			alert.setDialogPane(dialogPane);
			alert.show();
			return (Stage) dialogPane.getScene().getWindow();
		}catch(IOException e) {
			JavaFxUtil.alert(AlertType.ERROR, "Erro", "Erro ao abrir Progresso", "");
			return null;
		}
	}
}
