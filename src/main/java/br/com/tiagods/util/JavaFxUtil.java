package br.com.tiagods.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class JavaFxUtil {
    private static Alert alert(Alert.AlertType alertType, String title, String header, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        return alert;
    }
    public static void alert(Alert.AlertType alertType, String title, String header, String contentText, Exception ex, boolean print) {
        Alert alert = alert(alertType,title,header,contentText);
        alert.getDialogPane().setExpanded(true);
        alert.getDialogPane().setMinSize(600,150);
        if(alert.getAlertType()== Alert.AlertType.ERROR && ex!=null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();
            Label label = new Label("Mais detalhes do erro:");
            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);
            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

            if(print) {
                try {
                    LocalDateTime dateTime = LocalDateTime.now();
                    File log = new File(System.getProperty("user.dir") + "/log/" +
                            dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "-erro.txt");
                    if (!log.getParentFile().exists())
                        log.getParentFile().mkdir();
                    FileWriter fw = new FileWriter(log, true);
                    String line = System.getProperty("line.separator");
                    fw.write(
                            dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "=" +
                                    header + ":" +
                                    contentText +
                                    line +
                                    exceptionText
                    );
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        alert.showAndWait();
    }

    private static File salvarTemp(String extensao){
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        File file = new File(System.getProperty("java.io.tmpdir")+"/file-"+sdf.format(new Date())+"."+extensao);
        return file;
    }
}
