package sample.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import sample.model.FileUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 10/21/2016.
 */
public class SplashController extends ControllerManager implements Initializable{

    @FXML private StackPane stackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new SplashScreen().start();
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

                Platform.runLater(() -> {
                    FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), stackPane);
                    fadeOut.setFromValue(1);
                    fadeOut.setToValue(0);
                    fadeOut.setCycleCount(1);

                    fadeOut.play();

                    fadeOut.setOnFinished(event -> {
                        closeWindow();
                        newWindow("../view/main.fxml", "Free Apples", StageStyle.DECORATED, getStylePath());
                    });
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
