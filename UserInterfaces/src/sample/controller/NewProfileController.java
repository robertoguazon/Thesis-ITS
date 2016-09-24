package sample.controller;

import com.westlyf.database.DatabaseConnection;
import com.westlyf.database.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Yves on 9/18/2016.
 */
public class NewProfileController implements Initializable{

    @FXML private Label errorMessage;
    @FXML private TextField usernameText;
    @FXML private PasswordField passwordText;
    @FXML private PasswordField confirmPasswordText;
    @FXML private TextField nameText;
    @FXML private TextField schoolText;
    @FXML private TextField ageText;
    @FXML private RadioButton sexText;
    @FXML private ComboBox yearLevelText;
    @FXML private Button backToMenu;
    @FXML private Button createNewProfile;


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
            String name = nameText.getText();
            String age = ageText.getText();
            String sex = sexText.getText();
            String school = schoolText.getText();
            String yearLevel = "4th year";
            if (isFieldEmpty(username, password, confirmPassword, name, age, sex, school, yearLevel)){
                if (isFieldMatch(username, password, confirmPassword, name, age, sex, school, yearLevel)){
                    int ageNum = Integer.parseInt(age);
                    if (isAge(ageNum)){
                        if (confirmPassword(password, confirmPassword)){
                            UserDatabase.addNewProfile(1, 1, 1, username, password, name, ageNum, sex, school, yearLevel, null);
                            stage = (Stage)backToMenu.getScene().getWindow();
                            root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
                        }else {
                            errorMessage.setText("Password is not the same.");
                            errorMessage.setTextFill(Color.RED);
                            return;
                        }
                    }else {
                        errorMessage.setText("Age must not be below 14.");
                        errorMessage.setTextFill(Color.RED);
                        return;
                    }
                }else {
                    //errorMessage.setText("Invalid input.");
                    errorMessage.setTextFill(Color.RED);
                    return;
                }
            }else {
                errorMessage.setText("Please fill out all the fields.");
                errorMessage.setTextFill(Color.RED);
                return;
            }
        }else if (event.getSource() == backToMenu){
            stage = (Stage)backToMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
        }else {return;}
        stage.setScene(new Scene(root));
        stage.show();
    }

    public boolean isFieldEmpty(String username, String password, String confirmPassword,
                                String name, String age, String sex, String school, String yearLevel){
        username = username.trim();
        password = password.trim();
        confirmPassword = confirmPassword.trim();
        name = name.trim();
        sex = sex.trim();
        school = school.trim();
        yearLevel = yearLevel.trim();
        if (!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !age.isEmpty() &&
                !name.isEmpty() && !sex.isEmpty() && !school.isEmpty() && !yearLevel.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean isFieldMatch(String username, String password, String confirmPassword,
                                String name, String age, String sex, String school, String yearLevel){
        int b = 0;
        String text = "Invalid ";
        if (!username.matches("[a-zA-Z0-9\\s]*")) {
            usernameText.setStyle("-fx-text-box-border: red;");
            text = text + "username, ";
            b++;
        }
        if (!password.matches("[a-zA-Z0-9]*")){
            passwordText.setStyle("-fx-text-box-border: red;");
            text = text + "password, ";
            b++;
        }
        if (!confirmPassword.matches("[a-zA-Z0-9]*")){
            confirmPasswordText.setStyle("-fx-text-box-border: red;");
            text = text + "confirm password, ";
            b++;
        }
        if (!name.matches("[a-zA-Z]*")){
            nameText.setStyle("-fx-text-box-border: red;");
            text = text + "name, ";
            b++;
        }
        if (!age.matches("[0-9]{2}")){
            ageText.setStyle("-fx-text-box-border: red;");
            text = text + "age, ";
            b++;
        }
        if (!sex.matches("[a-zA-Z]*")){
            sexText.setStyle("-fx-text-box-border: red;");
            text = text + "sex, ";
            b++;
        }
        if (!school.matches("[a-zA-Z]*")){
            schoolText.setStyle("-fx-text-box-border: red;");
            text = text + "school, ";
            b++;
        }
        if (!yearLevel.matches("[a-zA-Z0-9\\s]*")){
            //yearLevelText.setStyle("-fx-text-box-border: red;");
            text = text + "year level, ";
            b++;
        }
        if (b > 0){
            text = text.substring(0, text.length()-2);
            errorMessage.setText(text + ".");
            return false;
        }
        return true;
    }

    public boolean isAge(int age){
        if (age >= 14) {
            return true;
        }
        ageText.setStyle("-fx-text-box-border: red;");
        return false;
    }

    public boolean confirmPassword(String password, String confirmPassword){
        if (password.equals(confirmPassword)) {
            return true;
        }
        passwordText.setStyle("-fx-text-box-border: red;");
        confirmPasswordText.setStyle("-fx-text-box-border: red;");
        return false;
    }
}
