package sample.model;

import com.westlyf.agent.Agent;
import com.westlyf.controller.Controllers;
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
        if (validateUsername() != null && validatePassword() != null){
            updateProgress(20,MAX);

            updateMessage("Login: checking user credentials...");
            Users user = Agent.getUserUsingCredentials(username.getText(), password.getText());
            updateProgress(60,MAX);
            if(user != null){
                updateMessage("Login: logging in to user");
                new Agent(user);
                updateProgress(80,MAX);
                Thread.sleep(200);

                updateMessage("Loading all controllers");
                Platform.runLater(() -> {Controllers.loadAllViewers();}); //TODO -fix
                updateProgress(100,MAX);
                Thread.sleep(200);

                return true;
            }else {
                Platform.runLater(() -> {
                    setErrorMessage("Invalid Username or Password.");
                    setErrorStyle(username);
                    setErrorStyle(password);
                });
            }
        }else {
            if (validateUsername() == null){
                Platform.runLater(() -> {
                    setErrorMessage("Please fill out all fields.");
                    setErrorStyle(username);
                });
            }else {Platform.runLater(() -> {clearStyle(username);});}
            if (validatePassword() == null){
                Platform.runLater(() -> {
                    setErrorMessage("Please fill out all fields.");
                    setErrorStyle(password);
                });
            }else {Platform.runLater(() -> {clearStyle(password);});}
        }
        updateMessage("");
        updateProgress(100,MAX);
        return false;

    }

    public String validateUsername(){
        String usernameText = username.getText().trim();
        if (usernameText.isEmpty()){
            return null;
        }
        return usernameText;
    }

    public String validatePassword(){
        String passwordText = password.getText().trim();
        if (passwordText.isEmpty()){
            return null;
        }else {password.setStyle("");}
        return passwordText;
    }

    public void setErrorMessage(String message){
        errorMessage.setText(message);
        errorMessage.setTextFill(Color.web("#FF8A80"));
    }
    public void setErrorStyle(TextField field){
        field.setStyle("-fx-background-color: #FFAB91;");
    }

    public void clearStyle(TextField field){
        field.setStyle("");
    }
}
