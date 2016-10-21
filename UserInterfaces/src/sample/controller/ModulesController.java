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
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 9/24/2016.
 */
public class ModulesController implements Initializable{

    @FXML private Label title;
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
        title.getStyleClass().add("label-header");
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
        }
    }

    @FXML
    public void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        if (event.getSource() == backToMenu){
            scene = backToMenu.getScene();
            stage = (Stage)scene.getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
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
            scene = module1.getScene();
            stage = (Stage)scene.getWindow();
            root = (Parent) loadTextLessonNode();
        }
        scene.setRoot(root);
        stage.setScene(scene);
        stage.show();
    }

    public String openModule(String module){
        String lesson;
        if (Agent.getLoggedUser().getCurrentModuleId().equals(module)) {
            lesson = Agent.getLoggedUser().getCurrentLessonId();
            Agent.loadLesson(module, lesson);
        }else {
            lesson = "lesson0";
            Agent.loadLesson(module, lesson);
        }
        Agent.setCurrentModule(module);
        Agent.setCurrentLesson(lesson);
        return lesson;
    }

    public Node loadTextLessonNode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../com/westlyf/view/TextLessonViewer.fxml"));
        Node node = loader.load();
        TextLessonViewerController textLessonViewerController = loader.getController();
        String currentLesson = Agent.getCurrentLesson();
        int i = Integer.parseInt(String.valueOf(currentLesson.charAt(currentLesson.length()-1)));
        textLessonViewerController.openLesson(i);
        return node;
    }
}
