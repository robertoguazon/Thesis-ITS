package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.Core;
import sample.controller.FaceDetectionController;

/**
 * Created by robertoguazon on 26/07/2016.
 */
public class FaceDetectionMain extends Application {

    FaceDetectionController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/FaceDetection.fxml"));
        BorderPane root = loader.load();
        root.setStyle("-fx-background-color: whitesmoke;");
        Scene scene = new Scene(root, 800,600);
        scene.getStylesheets().add(getClass().getResource("style/application.css").toExternalForm());

        this.controller = loader.getController();

        primaryStage.setTitle("Face Detection and Tracking");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    @Override
    public void stop() {
        this.controller.stopCamera();
    }
}
