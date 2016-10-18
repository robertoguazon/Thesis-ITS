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

    @FXML private BorderPane pane;
    @FXML private Label title;
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
        //pane.getStyleClass().add("background");
        title.getStyleClass().add("label-header");
        Users loggedUser = Agent.getLoggedUser();
        if (loggedUser != null){
            user.setText(loggedUser.getName());
            if (loggedUser.getCurrentExamId() == loggedUser.getCurrentModuleId()){
                exam.setDisable(true);
            }
            if (loggedUser.getCurrentExamId() == null || loggedUser.getCurrentModuleId().equals("module1")) {
                grades.setDisable(true);
            }
            challenge.setDisable(true);

        }/*else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/loadprofile.fxml"));
                Parent root = fxmlLoader.load();
                LoginController loginController = fxmlLoader.getController();

                loginController.setErrorMessage("Session Timed Out.");
                Scene scene = new Scene(root);
                Stage stage = new Stage();//(Stage) logout.getScene().getWindow();
                System.out.println(logout.getScene());
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    @FXML
    private void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == learn){
            stage = (Stage)learn.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/modules.fxml"));
        }else if (event.getSource() == exam){
            if (confirmTakeExam()) {
                stage = (Stage) exam.getScene().getWindow();
                root = (Parent)openExam();
            }else {return;}
        }else if (event.getSource() == grades){
            stage = (Stage)grades.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/grades.fxml"));
        }else if (event.getSource() == challenge){
            stage = (Stage)challenge.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/challenges.fxml"));
        }else if (event.getSource() == logout){
            logout();
            stage = (Stage)logout.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/loadprofile.fxml"));
        }else {return;}
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(pane.getScene().getStylesheets());
        stage.setScene(scene);
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
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(pane.getScene().getStylesheets());
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
        loadFER(examChoicesOnlyViewerController);
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
                        "Topic: " + Agent.getCurrentModule() + "\n\n" +
                        "Length: This exam has a time limit of 1 hour.\n\n" +
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
