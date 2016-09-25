package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.controller.ControllerType;
import com.westlyf.controller.Controllers;
import com.westlyf.controller.TextLessonViewerController;
import com.westlyf.database.DatabaseConnection;
import com.westlyf.database.LessonDatabase;
import com.westlyf.database.UserDatabase;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.controller.LoginController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
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

    private static Connection userConn;
    private static String currentModuleId;
    private static String currentLessonId;
    private static String currentExamId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Agent.getLoggedUser() == null){
            try {
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
        }else {
            userConn = DatabaseConnection.getUserConnection();
            if (userConn != null){
                currentModuleId = Agent.getLoggedUser().getCurrentModuleId();
                currentLessonId = Agent.getLoggedUser().getCurrentLessonId();
                currentExamId = Agent.getLoggedUser().getCurrentExamId();
                switch (currentModuleId){
                    case "module1":
                        module2.setDisable(true);
                        module3.setDisable(true);
                        module4.setDisable(true);
                        module5.setDisable(true);
                        module6.setDisable(true);
                        module7.setDisable(true);
                        break;
                    case "module2":
                        module3.setDisable(true);
                        module4.setDisable(true);
                        module5.setDisable(true);
                        module6.setDisable(true);
                        module7.setDisable(true);
                        break;
                    case "module3":
                        module4.setDisable(true);
                        module5.setDisable(true);
                        module6.setDisable(true);
                        module7.setDisable(true);
                        break;
                    case "module4":
                        module5.setDisable(true);
                        module6.setDisable(true);
                        module7.setDisable(true);
                        break;
                    case "module5":
                        module6.setDisable(true);
                        module7.setDisable(true);
                        break;
                    case "module6":
                        module7.setDisable(true);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @FXML
    public void openModule(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == module1 || event.getSource() == module2 || event.getSource() == module3 || event.getSource() == module4
                || event.getSource() == module5 || event.getSource() == module6 || event.getSource() == module7) {
            FXMLLoader loader = new FXMLLoader();
            TextLessonViewerController textLessonViewerController = loader.getController();
            Controllers.load(ControllerType.TEXT_LESSON_VIEWER, "../view/TextLessonViewer.fxml");
            Node node = null;
            if (currentModuleId != null && currentLessonId != null) {
                node = Controllers.getNode(ControllerType.TEXT_LESSON_VIEWER, LessonDatabase.getTextLessonUsingLID(currentLessonId));
            } else if (currentLessonId == null) {
                node = Controllers.getNode(ControllerType.TEXT_LESSON_VIEWER, LessonDatabase.getTextLessonUsingLID("lid525810847783767"));
            } else if (currentModuleId != null && currentLessonId == null) {
                ArrayList<TextLesson> lessons = LessonDatabase.getTextLessonsUsingTagsExactly(currentModuleId);
            }
            stage = (Stage) module1.getScene().getWindow();
            root = (Parent) node;
        }else if (event.getSource() == backToMenu){
            stage = (Stage)module2.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
        }else {return;}
        stage.setScene(new Scene(root));
        stage.show();
    }
}
