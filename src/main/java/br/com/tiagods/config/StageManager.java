package br.com.tiagods.config;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Manages switching Scenes on the Primary Stage
 */
@Slf4j
public class StageManager {

    private final Stage primaryStage;
    private SpringFXMLLoader springFXMLLoader;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public Stage switchScene(final FxmlView view, Stage stage) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        if(stage != null) {
            stage.initModality(view.getModality());
        }
        show(viewRootNodeHierarchy, view.getTitle(), stage!=null ? stage : primaryStage);
        return stage;
    }

    private Stage show(final Parent rootnode, String title, Stage stage) {
        Scene scene = prepareScene(rootnode, stage);
        //scene.getStylesheets().add("/styles/Styles.css");
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.getIcons().add(new Image(getClass().getResource("/fxml/imagens/theme.png").toString()));
        try {
            stage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
        return stage;
    }
    
    private Scene prepareScene(Parent rootnode, Stage stage){
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }

    private void logAndExit(String errorMsg, Exception exception) {
        log.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }
}
