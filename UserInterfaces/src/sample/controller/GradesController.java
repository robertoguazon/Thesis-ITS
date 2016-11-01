package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.user.ExamGrade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 10/13/2016.
 */
public class GradesController extends ControllerManager implements Initializable{

    private ObservableList<ExamGrade> gradesList;
    @FXML private TableView<ExamGrade> tableView;
    @FXML private TableColumn<ExamGrade, String> examTitleColumn;
    @FXML private TableColumn<ExamGrade, Integer> rawGradeColumn;
    @FXML private TableColumn<ExamGrade, Integer> totalItemsColumn;
    @FXML private TableColumn<ExamGrade, String> statusColumn;
    @FXML private TableColumn<ExamGrade, String> percentGradeColumn;

    @FXML private Button backToMenu;
    @FXML private Label overallLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gradesList = FXCollections.observableArrayList();
        gradesList.addAll(Agent.getExamGrades());
        examTitleColumn.setCellValueFactory(new PropertyValueFactory<>("exam_title"));
        rawGradeColumn.setCellValueFactory(new PropertyValueFactory<>("rawGrade"));
        totalItemsColumn.setCellValueFactory(new PropertyValueFactory<>("totalItems"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        percentGradeColumn.setCellValueFactory(new PropertyValueFactory<>("percentGrade"));
        tableView.setItems(gradesList);
    }

    @FXML
    public void handleAction(ActionEvent event) {
        if (event.getSource() == backToMenu){
            changeScene("../view/user.fxml");
        }
    }
}
