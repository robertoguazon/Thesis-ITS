package sample.controller;

import com.westlyf.agent.Agent;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.model.ConfirmBox;
import sample.model.FileUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 10/21/2016.
 */
public class SplashController implements Initializable{

    Stage window;
    @FXML private StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SplashScreen().start();
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

    class SplashScreen extends Thread{
        @Override
        public void run(){
            try {
                Thread.sleep(1000);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), stackPane);
                        fadeOut.setFromValue(1);
                        fadeOut.setToValue(0);
                        fadeOut.setCycleCount(1);

                        fadeOut.play();

                        fadeOut.setOnFinished(event -> {
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Scene scene = new Scene(root);
                            scene.getStylesheets().add(getStylePath());
                            window = new Stage();
                            window.setTitle("Free Apples");
                            window.setOnCloseRequest(e -> {
                                e.consume();
                                closeProgram();
                            });
                            window.setScene(scene);
                            window.show();
                            stackPane.getScene().getWindow().hide();
                        });
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
