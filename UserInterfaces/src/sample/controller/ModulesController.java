package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.controller.ControllerType;
import com.westlyf.controller.Controllers;
import com.westlyf.controller.TextLessonViewerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 9/24/2016.
 */
public class ModulesController extends ControllerManager implements Initializable{

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
    public void handleAction(ActionEvent event) {
        if (event.getSource() == backToMenu){
            changeScene("../view/user.fxml");
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
        }
    }

    public void openModule(String module){
        String lesson;
        if (Agent.getLoggedUser().getCurrentModuleId().equals(module)) {
            lesson = Agent.getLoggedUser().getCurrentLessonId();
        }else {
            lesson = "lesson0";
        }
        TextLessonViewerController textLessonViewerController =
                (TextLessonViewerController) Controllers.getController(ControllerType.TEXT_LESSON_VIEWER);
        textLessonViewerController.setLessonList(module);
        textLessonViewerController.setCurrentLessonNo(Integer.parseInt(String.valueOf(lesson.charAt(lesson.length()-1))));
        Node node = Controllers.getNode(ControllerType.TEXT_LESSON_VIEWER, Agent.loadLesson(module, lesson));
        changeScene(node);
    }
}
