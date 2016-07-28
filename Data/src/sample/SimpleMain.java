package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import org.datafx.controller.flow.Flow;

/**
 * Created by robertoguazon on 27/07/2016.
 */
public class SimpleMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Flow(SimpleController.class).startInStage(primaryStage);
    }
}
