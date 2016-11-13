package sample;
/**
 * Created by Yves on 8/31/2016.
 */

import com.westlyf.controller.Controllers;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.controller.ControllerManager;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ControllerManager controllerManager = new ControllerManager();
        controllerManager.newWindow("/sample/view/splash.fxml", "Free Apples", StageStyle.UNDECORATED, null);
    }

    @Override
    public void stop() {
        //dispose everything just to be sure
        System.out.println("Disposing...");
        Controllers.disposeAll(); //TODO -fix
    }
}
