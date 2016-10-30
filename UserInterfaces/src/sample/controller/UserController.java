package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.controller.BackgroundProcess;
import com.westlyf.controller.ExamChoicesOnlyViewerController;
import com.westlyf.user.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.ConfirmBox;

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
    private void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        if (event.getSource() == learn){
            scene = learn.getScene();
            stage = (Stage)scene.getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/modules.fxml"));
        }else if (event.getSource() == exam){
            if (confirmTakeExam()) {
                scene = exam.getScene();
                stage = (Stage)scene.getWindow();
                root = (Parent)openExam();
            }else {return;}
        }else if (event.getSource() == grades){
            scene = grades.getScene();
            stage = (Stage)scene.getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/grades.fxml"));
        }else if (event.getSource() == challenge){
            scene = challenge.getScene();
            stage = (Stage)scene.getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/challenges.fxml"));
        }else if (event.getSource() == logout){
            logout();
            scene = logout.getScene();
            stage = (Stage)scene.getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/loadprofile.fxml"));
        }else {return;}
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleNewWindowAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = null;
        if (event.getSource() == settings){
            root = FXMLLoader.load(getClass().getResource("../view/settings.fxml"));
        }else if (event.getSource() == about){
            root = FXMLLoader.load(getClass().getResource("../view/about.fxml"));
        }else {return;}
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(settings.getScene().getStylesheets());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(settings.getScene().getWindow());
        stage.showAndWait();
    }

    private Node openExam() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../com/westlyf/view/ExamChoicesOnlyViewer.fxml"));
        Node node = loader.load();
        ExamChoicesOnlyViewerController examChoicesOnlyViewerController = loader.getController();
        Agent.loadExam();
        examChoicesOnlyViewerController.setExam(Agent.getExam());
        //loadFER(examChoicesOnlyViewerController);

        //TODO start timer when exam is opened
        ExamChoicesOnlyViewerController examController = loader.getController();
        examController.startTimer();
        return node;
    }

    private void loadFER(ExamChoicesOnlyViewerController examChoicesOnlyViewerController){
        BackgroundProcess.setExamChoicesOnlyViewerController(examChoicesOnlyViewerController);
        Agent.startBrowser();
        Agent.startBackground();
    }

    private void logout(){
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
