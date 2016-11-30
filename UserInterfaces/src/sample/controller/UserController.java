package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.controller.ControllerType;
import com.westlyf.controller.Controllers;
import com.westlyf.user.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.model.ConfirmBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 9/22/2016.
 */
public class UserController extends ControllerManager implements Initializable{

    @FXML private Label nameLabel;
    @FXML private Button learn;
    @FXML private Button exam;
    @FXML private Button grades;
    @FXML private Button challenge;
    @FXML private Button logout;
    @FXML private Button viewProfileButton;
    @FXML private Hyperlink settings;
    @FXML private Hyperlink about;
    @FXML private ImageView profilePictureImageView;
    private Image image;
    private Users loggedUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedUser = Agent.getLoggedUser();
        if (loggedUser != null){
            nameLabel.setText(loggedUser.getName());
            loadImage();
            if (loggedUser.getCurrentExamId() == null ||
                    !loggedUser.getCurrentExamId().contains(loggedUser.getCurrentModuleId())){
                exam.setDisable(true);
            }
            if (Agent.getExamGrades().isEmpty()){
                grades.setDisable(true);
            }
            if (loggedUser.getCurrentExamId() == null ||
                    !loggedUser.getCurrentExamId().contains("challenge")) {
                challenge.setDisable(true);
            }
        }
    }

    public void loadImage(){
        profilePictureImageView.imageProperty().bindBidirectional(Agent.imageProperty());
        if (loggedUser.getProfilePicturePath() != null) {
            readImage(loggedUser.getProfilePicturePath());
        }else {
            readImage("resources/profile_pictures/create_new_profile.png");
            profilePictureImageView.setImage(image);
        }
    }

    public void readImage(String imagePath){
        try {
            FileInputStream input = new FileInputStream(imagePath);
            image = new Image(input);
            input.close();
            profilePictureImageView.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAction(ActionEvent event) {
        if (event.getSource() == learn){
            changeScene("/sample/view/modules.fxml");
        }else if (event.getSource() == exam){
            if (confirmTakeExam()) {
               openExam();
            }else {return;}
        }else if (event.getSource() == grades){
            changeScene("/sample/view/grades.fxml");
        }else if (event.getSource() == challenge){
            changeScene("/sample/view/challenges.fxml");
        }else if (event.getSource() == logout){
            logout();
            changeScene("/sample/view/loadprofile.fxml");
        }else if (event.getSource() == viewProfileButton){
            newChildWindow("/sample/view/viewprofile.fxml", "View Profile");
        }else if (event.getSource() == settings){
            newChildWindow("/sample/view/settings.fxml", "Settings");
        }else if (event.getSource() == about){
            newChildWindow("/sample/view/about.fxml", "About");
        }else {return;}
    }

    private void openExam() {
        Agent.countTries();
        Node node = Controllers.getNode(ControllerType.EXAM_CHOICES_ONLY_VIEWER, Agent.loadExam());
        String[] examTags = Agent.getExam().getTagsString().split(",");
        Agent.loadExamExercise(examTags[0], examTags[1]);
        changeScene(node);
        loadFER();
    }

    private void loadFER(){
        Agent.startBrowser();
        Agent.startBackground();
    }

    private void logout(){
        Controllers.disposeAll(); //TODO -fix
        Agent.removeLoggedUser();
    }

    private boolean confirmTakeExam(){
        return ConfirmBox.display("Are you sure you want to take the exam?", "Exam Description: ",
                        "Topic: " + Agent.getLoggedUser().getCurrentExamId().toUpperCase() + "\n\n" +
                        "Timer Setting: This exam will save and submit automatically \n" +
                            "\tupon time expiry.\n\n" +
                        "Submit Exam: Saves and submits all answers.\n" +
                        " *Note: Unanswered questions will be counted as 0 pts.*\n\n" +
                        "Hints: hints may be given when you are unable to answer \n" +
                            "\ta question in the exam.\n\n" +
                        "Passing Grade: 75% \n" +
                            "*Note: 2 or more tries will give an upper limit to your grade.\n" +
                            "\t= 1 point per question \n" +
                            "\t= 5 points on the coding exercise\n\n" +
                        "Click \"Ok\" to start the exam, and \"Cancel\" to return to go back.\n"
        );
    }

    @Override
    public void closeChildWindow() {
        child.close();
    }
}
