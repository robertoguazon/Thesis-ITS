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
        computeModuleGrades();
        averageGradesListView.setItems(aveGradeList);
        examTitleColumn.setCellValueFactory(new PropertyValueFactory<>("exam_title"));
        rawGradeColumn.setCellValueFactory(new PropertyValueFactory<>("rawGrade"));
        totalItemsColumn.setCellValueFactory(new PropertyValueFactory<>("totalItems"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        percentGradeColumn.setCellValueFactory(new PropertyValueFactory<>("percentGrade"));
        tableView.setItems(gradesList);
    }

    public void computeModuleGrades(){
        int overallGrade = 0;
        int l = 0;
        for (int i=1; i<=7; i++){
            int moduleGrade = 0;
            boolean b = false;
            for (int j=0; j<gradesList.size(); j++){
                ExamGrade temp = gradesList.get(j);
                if (temp.getExam_title().contains("Module "+i)){
                    if (moduleGrade <= temp.getPercentGrade()){
                        moduleGrade = temp.getPercentGrade();
                    }
                    b = true;
                }
            }
            if (b){
                overallGrade = overallGrade + moduleGrade;
                aveGradeList.add("Module " + i + " - " + moduleGrade + "%");
                l++;
            }
        }
        if (l != 0){
            overallGrade = overallGrade / l;
            overallLabel.setText(overallGrade + " / 100");
            if (overallGrade > 75){
                overallLabel.getParent().setStyle("-fx-background-color: #00C853");
            }else {
                overallLabel.getParent().setStyle("-fx-background-color: #F44336");
            }
        }
    }

    @FXML
    public void handleAction(ActionEvent event) {
        if (event.getSource() == backToMenu){
            changeScene("/sample/view/user.fxml");
        }
    }
}
