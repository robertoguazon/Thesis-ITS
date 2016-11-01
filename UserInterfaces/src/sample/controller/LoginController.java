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
import javafx.scene.control.*;
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
public class LoginController extends ControllerManager implements Initializable{

    @FXML private Label errorMessage;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button loginButton;
    @FXML private Hyperlink backToMenu;
    @FXML private ProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleChangeSceneAction(ActionEvent event) throws IOException {
        if (event.getSource() == loginButton){
            LoginTask loginTask = new LoginTask(username,password,errorMessage);
            progressBar.progressProperty().bind(loginTask.progressProperty());
            Thread loginThread = new Thread(loginTask);
            loginThread.start();
            loginTask.setOnSucceeded((stateEvent) -> {
                if ((boolean)loginTask.getValue()) {
                    goToHome();
                } else return;
            });

        }else if (event.getSource() == backToMenu){
            changeScene("../view/main.fxml");
        }else {return;}
    }

    public void goToHome(){
        changeScene("../view/user.fxml");
    }
}
