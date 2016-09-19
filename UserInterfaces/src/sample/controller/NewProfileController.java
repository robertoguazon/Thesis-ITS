package sample.controller;

import com.westlyf.database.Database;
import com.westlyf.database.DatabaseConnection;
import com.westlyf.database.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 9/18/2016.
 */
public class NewProfileController implements Initializable{

    @FXML
    private Label errorMessage;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField confirmPasswordText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField schoolText;
    @FXML
    private TextField ageText;
    @FXML
    private RadioButton sexText;
    @FXML
    private ComboBox yearLevelText;
    @FXML
    private Button backToMenu;
    @FXML
    private Button createNewProfile;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatabaseConnection.getUserConnection();
    }

    @FXML
    public void createNewProfile(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == createNewProfile){
            String username = usernameText.getText();
            String password = passwordText.getText();
            String confirmPassword = confirmPasswordText.getText();
            if (password.equals(confirmPassword)){
                String name = nameText.getText();
                String school = schoolText.getText();
                int age = Integer.parseInt(ageText.getText());
                String sex = sexText.getText();
                String yearLevel = null;
                UserDatabase.addNewProfile(1, 1, 1, username, password, name, age, sex, school, yearLevel, null);
                stage = (Stage)backToMenu.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
            }else {
                errorMessage.setText("Password is not the same.");
                return;
            }
        }else if (event.getSource() == backToMenu){
            stage = (Stage)backToMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
        }else {return;}
        stage.setScene(new Scene(root));
        stage.show();
    }
}
