package br.com.tiagods.util;

import br.com.tiagods.util.storage.Storage;
import javafx.scene.control.Alert;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFileUtil {

    public static void visualizarDocumento(String text, Storage storage){
        try {
            File file = storage.downloadFile(text);
            if (file != null)
                Desktop.getDesktop().open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File salvarTemp(String extensao){
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        File file = new File(System.getProperty("java.io.tmpdir")+"/file-"+sdf.format(new Date())+"."+extensao);
        return file;
    }

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
