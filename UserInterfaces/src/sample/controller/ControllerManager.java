package sample.controller;

import com.westlyf.agent.Agent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
            stage.setMinHeight(520);
            stage.setMinWidth(660);
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

    public void newChildWindow(Node node, String title){
        Pane container = new Pane(node);
        if (node instanceof Pane){
            Pane pane = (Pane) node;
            container.setMinWidth(pane.getPrefWidth());
            container.setMinHeight(pane.getPrefHeight());
            pane.prefWidthProperty().bind(container.widthProperty());
            pane.prefHeightProperty().bind(container.heightProperty());
        }
        Scene scene2 = new Scene(container);
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
