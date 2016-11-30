package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.user.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 11/20/2016.
 */
public class ViewProfileController extends ControllerManager implements Initializable{

    @FXML private Label usernameLabel;
    @FXML private Label currentModuleLabel;
    @FXML private Label currentLessonLabel;
    @FXML private Label nameLabel;
    @FXML private Label schoolLabel;
    @FXML private Label yearLevelLabel;
    @FXML private Label sexLabel;
    @FXML private Label ageLabel;
    @FXML private ImageView profilePictureImageView;
    @FXML private Button profilePictureButton;

    private Image image;
    private Users loggedUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedUser = Agent.getLoggedUser();
        if (loggedUser != null){
            usernameLabel.setText(loggedUser.getUsername());
            currentModuleLabel.setText(loggedUser.getCurrentModuleId());
            currentLessonLabel.setText(loggedUser.getCurrentLessonId());
            nameLabel.setText(loggedUser.getName());
            schoolLabel.setText(loggedUser.getSchool());
            yearLevelLabel.setText(loggedUser.getYearLevel());
            sexLabel.setText(loggedUser.getSex());
            ageLabel.setText(String.valueOf(loggedUser.getAge()));
            loadImage();
        }
    }

    public void loadImage(){
        profilePictureImageView.imageProperty().bindBidirectional(Agent.imageProperty());
        if (loggedUser.getProfilePicturePath() != null){
            try {
                FileInputStream input = new FileInputStream(loggedUser.getProfilePicturePath());
                image = new Image(input);
                input.close();
                profilePictureImageView.imageProperty().bindBidirectional(Agent.imageProperty());
                profilePictureImageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML public void viewImage(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/viewimage.fxml"));
            Scene scene1 = new Scene(root);
            scene1.getStylesheets().addAll(scene.getStylesheets());
            Stage stage1 = new Stage();
            stage1.setTitle("View Image");
            stage1.setScene(scene1);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.initOwner(stage);
            stage1.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
