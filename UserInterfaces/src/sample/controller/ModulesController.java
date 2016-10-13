package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.controller.TextLessonViewerController;
import com.westlyf.domain.lesson.TextLesson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 9/24/2016.
 */
public class ModulesController implements Initializable{

    @FXML private Button module1;
    @FXML private Button module2;
    @FXML private Button module3;
    @FXML private Button module4;
    @FXML private Button module5;
    @FXML private Button module6;
    @FXML private Button module7;
    @FXML private Hyperlink backToMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Agent.getLoggedUser() != null){
            switch (Agent.getLoggedUser().getCurrentModuleId()){
                case "module1":
                    module2.setDisable(true);
                case "module2":
                    module3.setDisable(true);
                case "module3":
                    module4.setDisable(true);
                case "module4":
                    module5.setDisable(true);
                case "module5":
                    module6.setDisable(true);
                case "module6":
                    module7.setDisable(true);
                    break;
                default:
                    break;
            }
        }/*else {
            try {
                System.out.println(backToMenu.getScene());
                Stage stage = (Stage) backToMenu.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader();
                Pane root = fxmlLoader.load(getClass().getResource("../view/loadprofile.fxml").openStream());
                LoginController loginController = fxmlLoader.getController();
                loginController.setErrorMessage("Session Timed Out.");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    @FXML
    public void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == backToMenu){
            root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
            stage = (Stage)backToMenu.getScene().getWindow();
        }else {
            if (event.getSource() == module1) {
                openModule("module1");
            } else if (event.getSource() == module2) {
                openModule("module2");
            } else if (event.getSource() == module3) {
                openModule("module3");
            } else if (event.getSource() == module4) {
                openModule("module4");
            } else if (event.getSource() == module5) {
                openModule("module5");
            } else if (event.getSource() == module6) {
                openModule("module6");
            } else if (event.getSource() == module7) {
                openModule("module7");
            } else {return;}
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../com/westlyf/view/TextLessonViewer.fxml"));
            Node node = loader.load();
            TextLessonViewerController textLessonViewerController = loader.getController();
            textLessonViewerController.setTextLesson(Agent.getLesson());
            textLessonViewerController.setExerciseButton(Agent.getCurrentLesson());
            root = (Parent) node;
            stage = (Stage) module1.getScene().getWindow();
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void openModule(String module){
        if (Agent.getLoggedUser().getCurrentModuleId().equals(module)) {
            Agent.loadLesson(module, Agent.getLoggedUser().getCurrentLessonId());
        }else {
            Agent.loadLesson(module, "lesson0");
        }
        Agent.setCurrentModule(module);
        Agent.setCurrentLesson(Agent.getLesson().getTagsString());
    }
}
