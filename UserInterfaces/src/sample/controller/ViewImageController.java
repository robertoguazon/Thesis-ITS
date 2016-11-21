package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.user.Users;
import com.westlyf.utils.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.model.ConfirmBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 11/20/2016.
 */
public class ViewImageController extends ControllerManager implements Initializable {

    @FXML private BorderPane pane;
    @FXML private ImageView profilePictureImageView;
    @FXML private Button chooseButton;
    @FXML private Button saveButton;

    private Users loggedUser;
    private Image image;
    private String profilePicturePath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setDisable(true);
        loggedUser = Agent.getLoggedUser();
        profilePicturePath = loggedUser.getProfilePicturePath();
        loadImage();
    }

    public void loadImage(){
        if (profilePicturePath != null){
            try {
                FileInputStream input = new FileInputStream(profilePicturePath);
                image = new Image(input);
                input.close();
                profilePictureImageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML public void chooseImage() throws NullPointerException{
        profilePicturePath = FileUtils.chooseImageFile(stage).getPath();
        if (profilePicturePath != null && profilePicturePath != loggedUser.getProfilePicturePath()){
            saveButton.setDisable(false);
            loadImage();
            Stage stage1 = (Stage) chooseButton.getScene().getWindow();
            stage1.sizeToScene();
        } else {
            saveButton.setDisable(true);
        }
    }

    @FXML public void saveImage(){
        if (profilePicturePath != null && profilePicturePath != loggedUser.getProfilePicturePath()){
            boolean confirm = ConfirmBox.display("Save Changes", "Are you sure you want to save this image?",
                    "Click \"ok\" to save");
            if (confirm) {
                FileUtils.copyImageFileTo(profilePicturePath, loggedUser.getUsername());
                Agent.setImageProperty(profilePictureImageView.getImage());
                profilePicturePath = FileUtils.pathToImage(profilePicturePath, loggedUser.getUsername());
                Agent.getLoggedUser().setProfilePicturePath(profilePicturePath);
                Agent.updateUserProfilePicture();
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            }
        }
    }
}
