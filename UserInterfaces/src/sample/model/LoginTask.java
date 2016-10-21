package sample.model;

import com.westlyf.agent.Agent;
import com.westlyf.user.Users;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by robertoguazon on 22/10/2016.
 */
public class LoginTask extends Task {


    private Label errorMessage;
    private TextField username;
    private TextField password;

    private final long MAX = 100;

    public LoginTask(TextField username, TextField password, Label errorMessage) {
        this.username = username;
        this.password = password;
        this.errorMessage = errorMessage;
    }

    @Override
    protected Boolean call() throws Exception {

        updateMessage("Login: validating fields...");
        updateProgress(20,MAX);
        if (validateFields()){

            updateMessage("Login: checking user credentials...");
            updateProgress(80,MAX);
            Users user = Agent.getUserUsingCredentials(username.getText(), password.getText());
            if(user != null){
                new Agent(user);
                updateProgress(100,MAX);
                return true;
            }else {
                Platform.runLater(() -> {setErrorMessage("Invalid Username or Password.");});
            }
        }else {
            Platform.runLater(() -> {setErrorMessage("Please fill out all fields.");});
        }
        updateProgress(100,MAX);
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
        errorMessage.setTextFill(Color.web("#b4120f"));
    }
}
