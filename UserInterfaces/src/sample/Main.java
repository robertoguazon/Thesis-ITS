package sample;/**
 * Created by Yves on 8/31/2016.
 */

import com.westlyf.agent.Agent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.model.ConfirmBox;
import sample.model.FileUtil;

import java.io.IOException;

public class Main extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        //change text format remove anti-aliasing
        System.setProperty("prism.lcdtext", "false");
        //System.setProperty("prism.text", "t2k");

        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("view/splash.fxml"));
        Scene scene = new Scene(root);
        window.setTitle("Free Apples");
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.show();
    }

}
