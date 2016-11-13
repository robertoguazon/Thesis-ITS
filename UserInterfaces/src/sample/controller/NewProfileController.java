package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.user.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.model.AlertBox;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Yves on 9/18/2016.
 */
public class NewProfileController extends ControllerManager implements Initializable{

    ObservableList<String> yearLevelList = FXCollections
            .observableArrayList("HS 1st Year", "HS 2nd Year", "HS 3rd Year", "HS 4th Year",
                    "College 1st Year", "College 2nd Year", "College 3rd Year", "College 4th Year");
    ObservableList<Integer> ageList = FXCollections.observableArrayList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);

    @FXML private Label errorMessage;
    @FXML private TextField usernameText;
    @FXML private PasswordField passwordText;
    @FXML private PasswordField confirmPasswordText;
    @FXML private TextField nameText;
    @FXML private TextField schoolText;
    @FXML private ComboBox ageComboBox;
    @FXML private RadioButton maleButton;
    @FXML private RadioButton femaleButton;
    @FXML private ComboBox yearLevelComboBox;
    @FXML private Button backToMenu;
    @FXML private Button createNewProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yearLevelComboBox.setValue("HS 1st Year");
        yearLevelComboBox.setItems(yearLevelList);
        ageComboBox.setValue(10);
        ageComboBox.setItems(ageList);
    }

    @FXML
    public void handleAction(ActionEvent event) {
        if (event.getSource() == createNewProfile){
            if (createNewProfile()){
                AlertBox.display("Successful Insert",
                        "You have been successfully registered as one of the users.",
                        "Going back to the start menu/sample.");
                changeScene("/sample/view/mainmenu.fxml");
            }else {return;}
        }else if (event.getSource() == backToMenu){
            changeScene("/sample/view/mainmenu.fxml");
        }else {return;}
    }

    public boolean createNewProfile(){
        String username = usernameText.getText().trim();
        String password = passwordText.getText().trim();
        String confirmPassword = confirmPasswordText.getText().trim();
        String name = nameText.getText().trim();
        String age = ageComboBox.getValue().toString();
        String sex = getSex();
        String school = schoolText.getText().trim();
        String yearLevel = yearLevelComboBox.getValue().toString();
        String currentModule = "module1";
        String currentLesson = "lesson0";
        String currentExam = null;
        String profilePicturePath = null;
        if (isFieldEmpty(username, password, confirmPassword, name, sex, school)){
            if (isFieldMatch(username, password, confirmPassword, name, school)){
                int ageNum = Integer.parseInt(age);
                if (confirmPassword(password, confirmPassword)){
                    if (Agent.addUser(new Users(username, password, name, ageNum, sex, school, yearLevel,
                            profilePicturePath, currentModule, currentLesson, currentExam)) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getSex(){
        if (maleButton.isSelected()){return "Male";}
        else if (femaleButton.isSelected()){return "Female";}
        else {return null;}
    }

    public boolean isFieldEmpty(String username, String password, String confirmPassword,
                                String name, String sex, String school){
        int b = 0;
        if(username.isEmpty()){usernameText.setStyle("-fx-background-color: #FFCC80;"); b++;}
        else{usernameText.setStyle("");}
        if(password.isEmpty()){passwordText.setStyle("-fx-background-color: #FFCC80;"); b++;}
        else{passwordText.setStyle("");}
        if(confirmPassword.isEmpty()){confirmPasswordText.setStyle("-fx-background-color: #FFCC80;"); b++;}
        else{confirmPasswordText.setStyle("");}
        if(name.isEmpty()){nameText.setStyle("-fx-background-color: #FFCC80;"); b++;}
        else{nameText.setStyle("");}
        if(sex.isEmpty()){
            maleButton.setStyle("-fx-text-fill: #FFCC80;");
            femaleButton.setStyle("-fx-text-fill: #FFCC80;");
            b++;
        }else{
            maleButton.setStyle("");
            femaleButton.setStyle("");
        }
        if(school.isEmpty()){schoolText.setStyle("-fx-background-color: #FFCC80;"); b++;}
        else {schoolText.setStyle("");}

        if (b == 0){
            return true;
        }else {
            setErrorMessage("Please fill out all the fields.");
            return false;
        }
    }

    public boolean isFieldMatch(String username, String password, String confirmPassword, String name, String school){
        int b = 0;
        String regex = "[a-zA-Z0-9]{4,}";
        String text = "Invalid: ";
        if (!username.matches(regex)) {
            usernameText.setStyle("-fx-background-color: #FF8A80;");
            text = text + "username(must be atleast 4 characters long), ";
            b++;
        }else{usernameText.setStyle("");}
        if (!password.matches(regex)){
            passwordText.setStyle("-fx-background-color: #FF8A80;");
            text = text + "password(must be atleast 4 characters long), ";
            b++;
        }else{passwordText.setStyle("");}
        if (!confirmPassword.matches(regex)){
            confirmPasswordText.setStyle("-fx-background-color: #FF8A80;");
            text = text + "confirm password(must be atleast 4 characters long), ";
            b++;
        }else{confirmPasswordText.setStyle("");}
        if (!name.matches("[a-zA-Z\\s]*")){
            nameText.setStyle("-fx-background-color: #FF8A80;");
            text = text + "name(must not contain numbers), ";
            b++;
        }else{nameText.setStyle("");}
        if (!school.matches("[a-zA-Z\\s]*")){
            schoolText.setStyle("-fx-background-color: #FF8A80;");
            text = text + "school(must not contain numbers), ";
            b++;
        }else{schoolText.setStyle("");}
        if (b == 0){
            return true;
        }else {
            text = text.substring(0, text.length()-2);
            setErrorMessage(text + ".");
            return false;
        }
    }

    public boolean confirmPassword(String password, String confirmPassword){
        if (password.equals(confirmPassword)) {
            return true;
        }
        setErrorMessage("Password and Confirm Password are not the same.");
        passwordText.setStyle("-fx-background-color: #FF8A80;");
        confirmPasswordText.setStyle("-fx-background-color: #FF8A80;");
        return false;
    }

    public void setErrorMessage(String message){
        errorMessage.setText(message);
        errorMessage.setStyle("-fx-text-fill: #FF8A80");
    }
}