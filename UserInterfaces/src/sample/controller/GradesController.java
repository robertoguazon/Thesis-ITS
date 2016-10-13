package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.user.ExamGrade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 10/13/2016.
 */
public class GradesController implements Initializable{

    ObservableList<ExamGrade> gradesList;
    @FXML private ListView<ExamGrade> gradesListView;
    @FXML private Button backToMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gradesList = FXCollections.observableArrayList(Agent.getExamGrades());
        gradesListView.setItems(gradesList);
    }

    @FXML
    private void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == backToMenu){
            stage = (Stage) backToMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
        }else {return;}
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
