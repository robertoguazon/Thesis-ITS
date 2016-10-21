package sample.controller;

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

    private Service<Void> backgroundThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Pane root;
        if (event.getSource() == loginButton){
            //doBackgroundProcess();
            login();
        }else if (event.getSource() == backToMenu){
            scene = backToMenu.getScene();
            stage = (Stage) scene.getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
            scene.setRoot(root);
            stage.setScene(scene);
            stage.setHeight(root.getPrefHeight()+40);
            stage.setWidth(root.getPrefWidth()+16);
            stage.show();
        }else {return;}
    }

    public void doBackgroundProcess(){
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        login();
                        return null;
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
            }
        });
        backgroundThread.start();
    }

    public void login() throws IOException {
        if (checkCredentials()) {
            Scene scene = loginButton.getScene();
            Stage stage = (Stage) scene.getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
            scene.setRoot(root);
            stage.setScene(scene);
            stage.setHeight(root.getPrefHeight()+40);
            stage.setWidth(root.getPrefWidth()+16);
            stage.show();
        }else {return;}
    }

    public boolean checkCredentials(){
        if (validateFields()){
            Users user = Agent.getUserUsingCredentials(username.getText(), password.getText());
            if(user != null){
                new Agent(user);
                return true;
            }else {
                setErrorMessage("Invalid Username or Password.");
            }
        }else {
            setErrorMessage("Please fill out all fields.");
        }
        return false;
    }

    public boolean validateFields(){
        String usernameText = username.getText().trim();
        String passwordText = password.getText().trim();
        if (usernameText.isEmpty() || passwordText.isEmpty()){
            return false;
        }
        return true;
    }

    public void setErrorMessage(String message){
        errorMessage.setText(message);
        errorMessage.setTextFill(Color.RED);
    }
}
