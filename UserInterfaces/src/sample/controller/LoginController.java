package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.database.DatabaseConnection;
import com.westlyf.database.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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

    private static Connection userConn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userConn = DatabaseConnection.getUserConnection();
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == loginButton){
            if (validateFields()){
                Users user = UserDatabase.isUserAvailable(username.getText(), password.getText());
                if(user != null){
                    if (userConn != null){
                        Agent.setLoggedUser(user);
                        stage = (Stage) loginButton.getScene().getWindow();
                        root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
                    }else {
                        setErrorMessage("Unable to Connect to User Database.");
                        return;
                    }
                }else {
                    setErrorMessage("Invalid Username or Password.");
                    return;
                }
            }else {
                setErrorMessage("Please fill out all fields.");
                return;
            }

        }else if (event.getSource() == backToMenu){
            stage = (Stage) backToMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
        }else {return;}
        stage.setScene(new Scene(root));
        stage.show();
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
