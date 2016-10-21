package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Yves on 10/20/2016.
 */
public class ResultController implements Initializable {

    @FXML private BorderPane pane;
    @FXML private Label titleLabel;
    @FXML private Label gradeLabel;
    @FXML private Button back;
    @FXML private TableView<QuizItem> tableView;
    @FXML private TableColumn<QuizItem, String> pointsColumn;
    @FXML private TableColumn<QuizItem, String> choicesColumn;
    @FXML private TableColumn<QuizItem, String> correctAnswerColumn;
    @FXML private TableColumn<QuizItem, String> userAnswerColumn;
    @FXML private TextArea questionTextArea;
    @FXML private TextArea explanationTextArea;

    private ObservableList<QuizItem> quizItemlist;
    private Exam exam;
    private int rawGrade;
    private int totalItems;
    private int percentGrade;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quizItemlist = FXCollections.observableArrayList();
        quizItemlist.addAll(Agent.getExam().getQuizItems());
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        choicesColumn.setCellValueFactory(new PropertyValueFactory<>("choices"));
        correctAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("validAnswers"));
        userAnswerColumn.setCellValueFactory(new PropertyValueFactory<>("answers"));
        tableView.setItems(quizItemlist);
        tableView.setRowFactory(param -> {
            TableRow<QuizItem> row = new TableRow<QuizItem>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    QuizItem rowData = row.getItem();
                    //System.out.println(rowData);
                    questionTextArea.setText(rowData.getQuestion());
                    explanationTextArea.setText(rowData.getExplanation());
                }
            });
            return row ;
        });

    }

    @FXML private void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == back){
            root = FXMLLoader.load(getClass().getResource("../view/user.fxml"));
            stage = (Stage) back.getScene().getWindow();
        }else {return;}
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(pane.getScene().getStylesheets());
        stage.setScene(scene);
        stage.show();
    }

    public void setExam(Exam exam){
        this.exam = exam;
        titleLabel.setText(exam.getTitle());
        gradeLabel.setText("Score: " + rawGrade + " / " + totalItems + " = " + percentGrade);
    }

    public void setRawGrade(int rawGrade) {
        this.rawGrade = rawGrade;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setPercentGrade(int percentGrade) {
        this.percentGrade = percentGrade;
    }
}
