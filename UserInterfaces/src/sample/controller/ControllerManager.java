package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.controller.*;
import com.westlyf.controller.PracticalReturnExerciseViewerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.model.ConfirmBox;

import java.io.IOException;

/**
 * Created by Yves on 10/27/2016.
 */
public class ControllerManager{

    protected static Scene scene;
    protected static Stage stage;
    protected static Stage child;

    public void newWindow(String resource, String title, StageStyle stageStyle, String stylesheetPath){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            scene = new Scene(root);
            if (stylesheetPath != null) {
                scene.getStylesheets().addAll(stylesheetPath);
            }
            stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(false);
            stage.initStyle(stageStyle);
            stage.setOnCloseRequest(event -> {
                event.consume();
                closeProgram();
            });
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newChildWindow(String resource, String title){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            Scene scene2 = new Scene(root);
            child = new Stage();
            scene2.getStylesheets().addAll(this.scene.getStylesheets());
            child.setTitle(title);
            child.setOnCloseRequest(event -> {
                event.consume();
                closeChildWindow();
            });
            child.setScene(scene2);
            child.initModality(Modality.APPLICATION_MODAL);
            child.initOwner(stage);
            child.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO copy from original to reset
    public void newChildWindow(Node node, ControllerType controllerTYpe, String title) {
        Scene scene2 = new Scene((Parent) node);
        child = new Stage();
        scene2.getStylesheets().addAll(this.scene.getStylesheets());
        child.setTitle(title);
        child.setOnCloseRequest(event -> {
            event.consume();
            closeChildWindow();

            //TODO -temporary fix?
            PracticalReturnExerciseViewerController controller = (com.westlyf.controller.PracticalReturnExerciseViewerController)
                    Controllers.getController(ControllerType.PRACTICAL_RETURN_EXERCISE_VIEWER);
            controller.reset();
            //TODO -temporary fix?
            if (scene2 != null) {
                scene2.setRoot(null);
            }
        });
        child.setScene(scene2);
        child.initModality(Modality.APPLICATION_MODAL);
        child.initOwner(stage);
        child.showAndWait();
    }

    public void newChildWindow(Node node, String title){
        Scene scene2 = new Scene((Parent) node);
        child = new Stage();
        scene2.getStylesheets().addAll(this.scene.getStylesheets());
        child.setTitle(title);
        child.setOnCloseRequest(event -> {
            event.consume();
            closeChildWindow();

            //TODO -temporary fix?
            if (scene2 != null) {
                scene2.setRoot(null);
            }
        });
        child.setScene(scene2);
        child.initModality(Modality.APPLICATION_MODAL);
        child.initOwner(stage);
        child.showAndWait();
    }

    public void changeScene(String resource){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(resource));
            if (scene == null){
                scene = new Scene(null);
            }
            scene.setRoot(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeScene(Node node){
        if (scene == null){
            scene = new Scene(null);
        }
        scene.setRoot((Parent) node);
        stage.setScene(scene);
        stage.show();
    }

    public void closeWindow(){
        stage.close();
    }

    public void closeChildWindow(){
        Boolean answer = ConfirmBox.display("Confirm Exit", "Close window?", "Are you sure you want to close this window?");
        if (answer){
            child.close();
        }
    }

    public void closeProgram(){
        Boolean answer = ConfirmBox.display("Confirm Exit", "Exit Application?", "Are you sure you want to exit?");
        if (answer){
            Agent.removeLoggedUser();
            closeWindow();
        }
    }
}
