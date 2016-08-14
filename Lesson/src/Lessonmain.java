import com.westlyf.domain.util.LessonUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.controller.LessonMainController;

/**
 * Created by robertoguazon on 03/06/2016.
 */
public class Lessonmain extends Application {

    private BorderPane root;
    private FXMLLoader rootLoader;
    private LessonMainController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        test(); // for testing puposes
        rootLoader = new FXMLLoader(getClass().getResource("sample/view/LessonMain.fxml"));
        root = rootLoader.load();

        controller = rootLoader.getController();

        Scene scene = new Scene(root);
        primaryStage.setTitle("Lesson - main sample");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void test() {
        //for testing

    }
}
