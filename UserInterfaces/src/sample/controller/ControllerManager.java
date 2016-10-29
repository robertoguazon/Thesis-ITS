package sample.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Yves on 10/27/2016.
 */
public class ControllerManager {

    private static Scene scene;

    public void newWindow(String resource, String title, StageStyle stageStyle){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.getStylesheets().addAll(this.scene.getStylesheets());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newChildWindow(String resource, String title){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.getStylesheets().addAll(this.scene.getStylesheets());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(this.scene.getWindow());
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(String resource){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            if (scene == null){
                scene = new Scene(null);
            }
            Stage stage = (Stage)scene.getWindow();
            scene.setRoot(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
