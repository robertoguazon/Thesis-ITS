package sample.controller;

import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.database.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    @FXML
    private Hyperlink loadProfile;
    @FXML
    private Hyperlink newProfile;
    @FXML
    private Hyperlink importProfile;
    @FXML
    private Hyperlink exportProfile;
    @FXML
    private Hyperlink settings;
    @FXML
    private Hyperlink about;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserDatabase.createUsersDatabase();
        ExerciseDatabase.createQuizExerciseTable();
        LessonDatabase.createTextLessonTable();
        LessonDatabase.createVideoLessonTable();
    }

    @FXML
    private void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == loadProfile){
            stage = (Stage)loadProfile.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/loadprofile.fxml"));
        }else if (event.getSource() == newProfile){
            stage = (Stage)newProfile.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/newprofile.fxml"));
        }else if (event.getSource() == importProfile){
            stage = (Stage)importProfile.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
        }else if (event.getSource() == exportProfile){
            stage = (Stage)exportProfile.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
        }else {return;}
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleNewWindowAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root;
        if (event.getSource() == settings){
            root = FXMLLoader.load(getClass().getResource("../view/settings.fxml"));
        }else if (event.getSource() == about){
            root = FXMLLoader.load(getClass().getResource("../view/about.fxml"));
        }else {return;}
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settings.getScene().getWindow());
        stage.showAndWait();
    }
}
