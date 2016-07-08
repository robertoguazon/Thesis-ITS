import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by robertoguazon on 11/06/2016.
 */
public class VideoPlayerMain extends Application {

    private FXMLLoader mainLoader;

    @Override
    public void init() {
        mainLoader = new FXMLLoader(getClass().getResource("sample/view/Main.fxml"));
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = this.mainLoader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
