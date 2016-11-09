package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.model.LoginTask;

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
    @FXML private Label progressLabel;
    @FXML private ProgressBar progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleAction(ActionEvent event) {
        if (event.getSource() == loginButton){
            LoginTask loginTask = new LoginTask(username,password,errorMessage);
            progressLabel.textProperty().bind(loginTask.messageProperty());
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
