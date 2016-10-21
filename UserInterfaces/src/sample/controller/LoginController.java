package sample.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.westlyf.agent.Agent;
import com.westlyf.user.Users;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.model.LoginTask;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 9/15/2016.
 */
public class LoginController implements Initializable{

    @FXML private Label errorMessage;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button loginButton;
    @FXML private Button backToMenu;
    @FXML private ProgressBar progressBar;

    private Service<Boolean> backgroundThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleChangeSceneAction(ActionEvent event) throws IOException {
        final Stage stage;
        final Scene scene;
        final Pane root;
        if (event.getSource() == loginButton){
            LoginTask loginTask = new LoginTask(username,password,errorMessage);
            progressBar.progressProperty().bind(loginTask.progressProperty());
            Thread loginThread = new Thread(loginTask);
            loginThread.start();
            loginTask.setOnSucceeded((stateEvent) -> {
                if ((boolean)loginTask.getValue()) {
                    try {
                        goToHome();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else return;
            });

        }else if (event.getSource() == backToMenu){
            goBack();
        }else {return;}
    }

    public void goBack() throws IOException {
        Scene scene = backToMenu.getScene();
        Stage stage = (Stage) scene.getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
        goTo(scene,root,stage);
    }

    public void goToHome() throws IOException {
        Scene scene = loginButton.getScene();
        Stage stage = (Stage) scene.getWindow();
        Pane root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
        stage.setHeight(root.getPrefHeight() + 40);
        stage.setWidth(root.getPrefWidth() + 16);

        goTo(scene,root,stage);
    }

    public void goTo(Scene scene, Pane root, Stage stage) {
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    public void doBackgroundProcess(){
        backgroundThread = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {



                     return true;
                    }
                };
            }
        };
        //progressBar = new ProgressBar(0);
        progressBar.progressProperty().bind(backgroundThread.progressProperty());
        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                progressBar.progressProperty().unbind();
                progressBar.setProgress(1);
                backgroundThread.getValue();
            }
        });
        backgroundThread.start();
    }
}
