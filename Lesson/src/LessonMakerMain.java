import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controller.LessonMakerController;

/**
 * Created by robertoguazon on 29/07/2016.
 */
public class LessonMakerMain extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            this.stage = primaryStage;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample/view/LessonMaker.fxml"));
            BorderPane root = loader.load();

            LessonMakerController controller = loader.getController();
            controller.setStage(primaryStage);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
