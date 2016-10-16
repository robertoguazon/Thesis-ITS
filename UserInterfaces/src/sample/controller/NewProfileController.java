package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.user.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Yves on 9/18/2016.
 */
public class NewProfileController implements Initializable{

    ObservableList<String> yearLevelList = FXCollections
            .observableArrayList("HS 1st Year", "HS 2nd Year", "HS 3rd Year", "HS 4th Year",
                    "Collage 1st Year", "Collage 2nd Year", "Collage 3rd Year", "Collage 4th Year");

    @FXML private BorderPane pane;
    @FXML private Label errorMessage;
    @FXML private TextField usernameText;
    @FXML private PasswordField passwordText;
    @FXML private PasswordField confirmPasswordText;
    @FXML private TextField nameText;
    @FXML private TextField schoolText;
    @FXML private TextField ageText;
    @FXML private RadioButton maleButton;
    @FXML private RadioButton femaleButton;
    @FXML private ComboBox yearLevelComboBox;
    @FXML private Button backToMenu;
    @FXML private Button createNewProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.getStyleClass().add("background");
        yearLevelComboBox.setValue("HS 1st Year");
        yearLevelComboBox.setItems(yearLevelList);
    }

    @FXML
    public void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == createNewProfile){
            if (createNewProfile()){
                stage = (Stage)backToMenu.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
            }else {return;}
        }else if (event.getSource() == backToMenu){
            stage = (Stage)backToMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
        }else {return;}
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(pane.getScene().getStylesheets());
        stage.setScene(scene);
        stage.show();
    }

    public boolean createNewProfile(){
        String username = usernameText.getText();
        String password = passwordText.getText();
        String confirmPassword = confirmPasswordText.getText();
        String name = nameText.getText();
        String age = ageText.getText();
        String sex = getSex();
        String school = schoolText.getText();
        String yearLevel = yearLevelComboBox.getValue().toString();
        System.out.println(yearLevel);
        String currentModule = "module1";
        String currentLesson = "lesson1";
        String currentExam = null;
        String profilePicturePath = null;
        if (isFieldEmpty(username, password, confirmPassword, name, age, sex, school)){
            if (isFieldMatch(username, password, confirmPassword, name, age, sex, school, yearLevel)){
                int ageNum = Integer.parseInt(age);
                if (isAge(ageNum)){
                    if (confirmPassword(password, confirmPassword)){
                            Agent.addUser(encapsulateUser(currentModule, currentLesson, currentExam, username, password,
                                    name, ageNum, sex, school, yearLevel, profilePicturePath));
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public String getSex(){
        String sex = "";
        if (maleButton.isSelected()){
            sex = "Male";
        }else if (femaleButton.isSelected()){
            sex = "Female";
        }
        return sex;
    }

    public boolean isFieldEmpty(String username, String password, String confirmPassword,
                                String name, String age, String sex, String school){
        int b = 0;
        username = username.trim();
        password = password.trim();
        confirmPassword = confirmPassword.trim();
        name = name.trim();
        school = school.trim();
        if(username.isEmpty()){usernameText.setStyle("-fx-text-box-border: orange;"); b++;}
        else{usernameText.setStyle("");}
        if(password.isEmpty()){passwordText.setStyle("-fx-text-box-border: orange;"); b++;}
        else{passwordText.setStyle("");}
        if(confirmPassword.isEmpty()){confirmPasswordText.setStyle("-fx-text-box-border: orange;"); b++;}
        else{confirmPasswordText.setStyle("");}
        if(name.isEmpty()){nameText.setStyle("-fx-text-box-border: orange;"); b++;}
        else{nameText.setStyle("");}
        if(age.isEmpty()){ageText.setStyle("-fx-text-box-border: orange;"); b++;}
        else{ageText.setStyle("");}
        if(sex.isEmpty()){
            maleButton.setStyle("-fx-text-fill: orange;");
            femaleButton.setStyle("-fx-text-fill: orange;");
            b++;
        }else{
            maleButton.setStyle("");
            femaleButton.setStyle("");
        }
        if(school.isEmpty()){schoolText.setStyle("-fx-text-box-border: orange;"); b++;}
        else {schoolText.setStyle("");}

        if (b == 0){
            return true;
        }else {
            setErrorMessage("Please fill out all the fields.");
            return false;
        }
    }

    public boolean isFieldMatch(String username, String password, String confirmPassword,
                                String name, String age, String sex, String school, String yearLevel){
        int b = 0;
        String text = "Invalid ";
        if (!username.matches("[a-zA-Z0-9]*")) {
            usernameText.setStyle("-fx-text-box-border: red;");
            text = text + "username, ";
            b++;
        }else{usernameText.setStyle("");}
        if (!password.matches("[a-zA-Z0-9]*")){
            passwordText.setStyle("-fx-text-box-border: red;");
            text = text + "password, ";
            b++;
        }else{passwordText.setStyle("");}
        if (!confirmPassword.matches("[a-zA-Z0-9]*")){
            confirmPasswordText.setStyle("-fx-text-box-border: red;");
            text = text + "confirm password, ";
            b++;
        }else{confirmPasswordText.setStyle("");}
        if (!name.matches("[a-zA-Z\\s]*")){
            nameText.setStyle("-fx-text-box-border: red;");
            text = text + "name, ";
            b++;
        }else{nameText.setStyle("");}
        if (!age.matches("[0-9]{2}")){
            ageText.setStyle("-fx-text-box-border: red;");
            text = text + "age, ";
            b++;
        }else{ageText.setStyle("");}
        if (!school.matches("[a-zA-Z\\s]*")){
            schoolText.setStyle("-fx-text-box-border: red;");
            text = text + "school, ";
            b++;
        }else{schoolText.setStyle("");}
        if (!yearLevel.matches("[a-zA-Z0-9\\s]*")){
            //yearLevelComboBox.setStyle("-fx-text-box-border: red;");
            text = text + "year level, ";
            b++;
        }//else{yearLevel.setStyle("");}
        if (b == 0){
            return true;
        }else {
            text = text.substring(0, text.length()-2);
            setErrorMessage(text + ".");
            return false;
        }
    }

    public boolean isAge(int age){
        if (age >= 14) {
            return true;
        }
        setErrorMessage("Age must not be below 14.");
        ageText.setStyle("-fx-text-box-border: red;");
        return false;
    }

    public boolean confirmPassword(String password, String confirmPassword){
        if (password.equals(confirmPassword)) {
            return true;
        }
        setErrorMessage("Password is not the same.");
        passwordText.setStyle("-fx-text-box-border: red;");
        confirmPasswordText.setStyle("-fx-text-box-border: red;");
        return false;
    }

    public Users encapsulateUser(String currentModuleId, String currentLessonId, String currentExamId,
                                 String username, String password, String name, int age, String sex,
                                 String school, String yearLevel, String profilePicturePath){
        Users user = new Users();
        user.setCurrentModuleId(currentModuleId);
        user.setCurrentLessonId(currentLessonId);
        user.setCurrentExamId(currentExamId);
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setAge(age);
        user.setSex(sex);
        user.setSchool(school);
        user.setYearLevel(yearLevel);
        user.setProfilePicturePath(profilePicturePath);
        return user;
    }

    public void setErrorMessage(String message){
        errorMessage.setText(message);
        errorMessage.setTextFill(Color.RED);
    }
}
