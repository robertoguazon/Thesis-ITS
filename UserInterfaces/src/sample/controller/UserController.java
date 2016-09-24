package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 9/22/2016.
 */
public class UserController implements Initializable{

    @FXML private Label user;
    @FXML private Button learn;
    @FXML private Button exam;
    @FXML private Button grades;
    @FXML private Button challenge;
    @FXML private Button logout;
    @FXML private Hyperlink settings;
    @FXML private Hyperlink about;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == learn){
            stage = (Stage)learn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/LessonMain.fxml"));
        }else if (event.getSource() == exam){
            stage = (Stage)exam.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/exam.fxml"));
        }else if (event.getSource() == challenge){
            stage = (Stage)challenge.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/challenges.fxml"));
        }else if (event.getSource() == logout){
            stage = (Stage)logout.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/loadprofile.fxml"));
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
        }else if (event.getSource() == grades){
            root = FXMLLoader.load(getClass().getResource("../view/grades.fxml"));
        }else {return;}
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settings.getScene().getWindow());
        stage.showAndWait();
    }

    private void logout(){
        //unset user
    }
}
