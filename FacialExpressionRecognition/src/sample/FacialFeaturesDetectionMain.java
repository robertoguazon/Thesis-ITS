package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.opencv.core.Core;
import sample.controller.FacialFeaturesDetectionController;

/**
 * Created by robertoguazon on 27/07/2016.
 */
public class FacialFeaturesDetectionMain extends Application {

    private FacialFeaturesDetectionController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/FacialFeaturesDetection.fxml"));
        GridPane root = loader.load();

        this.controller = loader.getController();
        this.controller.setStage(primaryStage);

        Scene scene = new Scene(root, 640, 480);

        primaryStage.setTitle("F_FaceFeatures");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    @Override
    public void stop() {

    }

}
