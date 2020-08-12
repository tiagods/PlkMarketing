package br.com.tiagods.util;

import br.com.tiagods.config.enums.IconsEnum;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Slf4j
public class JavaFxUtil {

    static final Integer[] limiteTabela = new Integer[] {50,100,200};

    public static void iconMenuItem(MenuItem item, int x, int y, IconsEnum icon){
        item.setGraphic(createImage(x,y,icon));
    }

    public static ImageView createImage(int x, int y, IconsEnum icon) {
        Image image = new Image(icon.getLocalizacao().toString());
        ImageView imageview = new ImageView(image);
        imageview.setFitHeight(x);
        imageview.setFitWidth(y);
        imageview.setPreserveRatio(true);
        return imageview;
    }

    public static void buttonTable(JFXButton btn, IconsEnum icon) {
        try {
            buttonIcon(btn,icon,30);
        }catch (IOException ex){
            log.error(ex.getMessage());
        }
    }

    public static void buttonMin(JFXButton btn, IconsEnum icon) throws IOException{
        try {
            buttonIcon(btn,icon,22);
        }catch (IOException ex){
            log.error(ex.getMessage());
        }
    }

    static  int BUTTON_TABLE = 30;
    static int BUTTON_TABLE_MIN = 22;

    public static void buttonIcon(JFXButton btn,IconsEnum icon,int size) throws IOException{
        ImageView imageview = createImage(size,size,icon);
        btn.setGraphic(imageview);
        btn.setTooltip(icon.getTooltip());
    }

    public static Alert alert(Alert.AlertType alertType, String title, String header, String contentText) {
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
