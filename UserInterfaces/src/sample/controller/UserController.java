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
import sample.model.ConfirmBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 9/22/2016.
 */
public class UserController extends ControllerManager implements Initializable{

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
        Users loggedUser = Agent.getLoggedUser();
        if (loggedUser != null){
            user.setText(loggedUser.getName());
            if (loggedUser.getCurrentExamId() == null ||
                    !loggedUser.getCurrentExamId().contains(loggedUser.getCurrentModuleId())){
                exam.setDisable(true);
            }
            if (Agent.getExamGrades().isEmpty()){
                grades.setDisable(true);
            }
            challenge.setDisable(true);
        }
    }

    @FXML
    public void handleAction(ActionEvent event) {
        if (event.getSource() == learn){
            changeScene("../view/modules.fxml");
        }else if (event.getSource() == exam){
            if (confirmTakeExam()) {
               openExam();
            }else {return;}
        }else if (event.getSource() == grades){
            changeScene("../view/grades.fxml");
        }else if (event.getSource() == challenge){
            changeScene("../view/challenges.fxml");
        }else if (event.getSource() == logout){
            logout();
            changeScene("../view/loadprofile.fxml");
        }else if (event.getSource() == settings){
            newChildWindow("../view/settings.fxml", "Settings");
        }else if (event.getSource() == about){
            newChildWindow("../view/about.fxml", "About");
        }else {return;}
    }

    private void openExam() {
        Node node = Controllers.getNode(ControllerType.EXAM_CHOICES_ONLY_VIEWER, Agent.loadExam());
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
                        "Topic: " + Agent.getLoggedUser().getCurrentExamId() + "\n\n" +
                        "Length: This exam has a time limit of 15mins.\n\n" +
                        "Timer Setting: This exam will save and submit automatically \n" +
                        "\tupon time expiry.\n\n" +
                        "Submit Exam: Saves and submits all answers.\n" +
                        " *Note: Unanswered questions will be counted as 0 pts.*\n\n" +
                        "Hints: hints may be given when you are unable to answer \n" +
                        "\ta question in the exam.\n\n" +
                        "Click \"Ok\" to start the exam, and \"Cancel\" to return to go back.\n"
        );
    }
}
