package sample.controller;

import com.westlyf.database.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    private String username;
    @FXML
    private String password;
    @FXML
    private Button login;
    @FXML
    private Button backToMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        if (event.getSource() == login){
            if(UserDatabase.isUserAvailable(username, password)){
                stage = (Stage)login.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
            }else {
                errorMessage.setText("Invalid Credentials.");
            }
        }else {
            stage = (Stage)login.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
        }
        stage.setScene(new Scene(root));
        stage.show();
    }
}
