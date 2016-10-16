package sample;/**
 * Created by Yves on 8/31/2016.
 */

import com.westlyf.agent.Agent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
        window.setTitle("Hello World");
        window.setOnCloseRequest(event -> {
            event.consume();
            closeProgram();
        });
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getStylePath());
        window.setScene(scene);
        window.show();
    }

    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Confirm Exit", "Exit Application?", "Are you sure you want to exit?");
        if (answer){
            Agent.removeLoggedUser();
            window.close();
        }
    }

    private String getStylePath(){
        FileUtil fileUtil = new FileUtil();
        return fileUtil.readFile();
    }
}
