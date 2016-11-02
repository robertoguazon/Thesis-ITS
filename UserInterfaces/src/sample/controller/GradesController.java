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
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Yves on 10/13/2016.
 */
public class GradesController extends ControllerManager implements Initializable{

    private ObservableList<ExamGrade> gradesList;
    private ObservableList<String> aveGradeList;
    @FXML private TableView<ExamGrade> tableView;
    @FXML private TableColumn<ExamGrade, String> examTitleColumn;
    @FXML private TableColumn<ExamGrade, Integer> rawGradeColumn;
    @FXML private TableColumn<ExamGrade, Integer> totalItemsColumn;
    @FXML private TableColumn<ExamGrade, String> statusColumn;
    @FXML private TableColumn<ExamGrade, String> percentGradeColumn;
    @FXML private ListView averageGradesListView;
    @FXML private Button backToMenu;
    @FXML private Label overallLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gradesList = FXCollections.observableArrayList();
        gradesList.addAll(Agent.getExamGrades());
        aveGradeList = FXCollections.observableArrayList();
        computeAverageGrades();
        averageGradesListView.setItems(aveGradeList);
        examTitleColumn.setCellValueFactory(new PropertyValueFactory<>("exam_title"));
        rawGradeColumn.setCellValueFactory(new PropertyValueFactory<>("rawGrade"));
        totalItemsColumn.setCellValueFactory(new PropertyValueFactory<>("totalItems"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        percentGradeColumn.setCellValueFactory(new PropertyValueFactory<>("percentGrade"));
        tableView.setItems(gradesList);
    }

    public void computeAverageGrades(){
        int overallGrade = 0;
        int l = 0;
        for (int i=1; i<=7; i++){
            int averageGrade = 0;
            int k = 0;
            for (int j=0; j<gradesList.size(); j++){
                if (gradesList.get(j).getExam_title().contains("Module "+i)){
                    averageGrade = averageGrade + gradesList.get(j).getPercentGrade();
                    k++;
                }
            }
            if (k != 0){
                averageGrade = averageGrade / k;
                overallGrade = overallGrade + averageGrade;
                aveGradeList.add("Module " + i + " - " + averageGrade);
                l++;
            }
        }
        if (l != 0){
            overallGrade = overallGrade / l;
            overallLabel.setText(overallGrade + " / 100");
            if (overallGrade > 60){
                overallLabel.getParent().setStyle("-fx-background-color: #00C853");
            }else {
                overallLabel.getParent().setStyle("-fx-background-color: #F44336");
            }
        }
    }

    @FXML
    public void handleAction(ActionEvent event) {
        if (event.getSource() == backToMenu){
            changeScene("../view/user.fxml");
        }
    }
}
