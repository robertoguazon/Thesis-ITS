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
import java.util.ResourceBundle;

/**
 * Created by Yves on 9/15/2016.
 */
public class LoginController implements Initializable{

    @FXML
    private Label errorMessage;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;
    @FXML
    private Button backToMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection.getUserConnection();
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == loginButton){
            if (validateFields()){
                Users user = UserDatabase.isUserAvailable(username.getText(), password.getText());
                if(user != null){
                    Agent.setLoggedUser(user);
                    stage = (Stage) loginButton.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
                }else {
                    errorMessage.setText("Invalid Credentials.");
                    errorMessage.setTextFill(Color.RED);
                    return;
                }
            }else {
                errorMessage.setText("Please fill out all fields.");
                errorMessage.setTextFill(Color.RED);
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
        if (usernameText.isEmpty() && passwordText.isEmpty()){
            return false;
        }
        return true;
    }
}
