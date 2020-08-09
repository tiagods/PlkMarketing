package br.com.tiagods.util;

import javafx.scene.control.Alert;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyFileUtil {
    public static void abrirArquivo(String s) {
        Path path = Paths.get(System.getProperty("user.dir"),s);
        Runnable run = () -> {
            try {
                Desktop.getDesktop().open(path.toFile());
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Não foi possivel iniciar o programa");
                alert.setContentText("Falha ao abrir o aplicativo\n"+e);
                alert.showAndWait();
            }
        };
        new Thread(run).start();
    }
}
