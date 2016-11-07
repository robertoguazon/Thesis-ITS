package sample.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Yves on 10/20/2016.
 */
public class ResultController extends ControllerManager implements Initializable {

    @FXML private Label titleLabel;
    @FXML private Label gradeLabel;
    @FXML private Button exerciseResultButton;
    @FXML private TableView<QuizItem> tableView;
    @FXML private TableColumn<QuizItem, String> choicesColumn;
    @FXML private TableColumn<QuizItem, String> correctAnswerColumn;
    @FXML private TableColumn<QuizItem, String> userAnswerColumn;
    @FXML private TableColumn<QuizItem, String> questionColumn;
    @FXML private TableColumn<QuizItem, String> explanationColumn;
    private ObservableList<QuizItem> quizItemlist;
    private Exam exam;
    private int rawGrade;
    private int totalItems;
    private int percentGrade;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quizItemlist = FXCollections.observableArrayList();
        quizItemlist.addAll(Agent.getExam().getQuizItems());
        choicesColumn.setCellValueFactory(cell -> new SimpleStringProperty(choiceListToString(cell.getValue().getChoices())));
        correctAnswerColumn.setCellValueFactory(cell -> new SimpleStringProperty(answerListToString(cell.getValue().getValidAnswers())));
        userAnswerColumn.setCellValueFactory(cell -> new SimpleStringProperty(answerListToString(cell.getValue().getAnswers())));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        explanationColumn.setCellValueFactory(new PropertyValueFactory<>("explanation"));
        tableView.setItems(quizItemlist);
        tableView.setRowFactory(row -> new TableRow<QuizItem>(){
            @Override
            protected void updateItem(QuizItem item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                }else {
                    setWrapText(true);
                    if (item.isCorrect()) {
                        setStyle("-fx-background-color: #00C853;");
                    } else {
                        setStyle("-fx-background-color: #F44336;");
                    }
                }
            }
        });
        setWrapTextChoicesColumn();
        setWrapTextCorrectAnswerColumn();
        setWrapTextUserAnswerColumn();
        setWrapTextQuestionColumn();
        setWrapTextExplanationColumn();
    }

    public void setWrapTextChoicesColumn(){
        choicesColumn.setCellFactory(param -> {
            return new TableCell<QuizItem, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        this.setText(null);
                        this.setStyle("");
                    } else {
                        Text text = new Text(item);
                        text.setStyle("-fx-padding: 5px 30px 5px 5px;" +
                                "-fx-text-alignment:justify;" +
                                "-fx-fill: white;");
                        text.wrappingWidthProperty().bind(param.widthProperty());
                        this.setGraphic(text);
                    }
                }
            };
        });
    }

    public void setWrapTextCorrectAnswerColumn(){
        correctAnswerColumn.setCellFactory(param -> {
            return new TableCell<QuizItem, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        this.setText(null);
                        this.setStyle("");
                    } else {
                        Text text = new Text(item);
                        text.setStyle("-fx-padding: 5px 30px 5px 5px;" +
                                "-fx-text-alignment:justify;" +
                                "-fx-fill: white;");
                        text.wrappingWidthProperty().bind(param.widthProperty());
                        this.setGraphic(text);
                    }
                }
            };
        });
    }

    public void setWrapTextUserAnswerColumn(){
        userAnswerColumn.setCellFactory(param -> {
            return new TableCell<QuizItem, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        this.setText(null);
                        this.setStyle("");
                    } else {
                        Text text = new Text(item);
                        text.setStyle("-fx-padding: 5px 30px 5px 5px;" +
                                "-fx-text-alignment:justify;" +
                                "-fx-fill: white;");
                        text.wrappingWidthProperty().bind(param.widthProperty());
                        this.setGraphic(text);
                    }
                }
            };
        });
    }

    public void setWrapTextQuestionColumn(){
        questionColumn.setCellFactory(param -> {
            return new TableCell<QuizItem, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        this.setText(null);
                        this.setStyle("");
                    } else {
                        Text text = new Text(item);
                        text.setStyle("-fx-padding: 5px 30px 5px 5px;" +
                                "-fx-text-alignment:justify;" +
                                "-fx-fill: white;");
                        text.wrappingWidthProperty().bind(param.widthProperty());
                        this.setGraphic(text);
                    }
                }
            };
        });
    }

    public void setWrapTextExplanationColumn(){
        explanationColumn.setCellFactory(param -> {
            return new TableCell<QuizItem, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        this.setText(null);
                        this.setStyle("");
                    } else {
                        Text text = new Text(item);
                        text.setStyle("-fx-padding: 5px 30px 5px 5px;" +
                                "-fx-text-alignment:justify;" +
                                "-fx-fill: white;");
                        text.wrappingWidthProperty().bind(param.widthProperty());
                        this.setGraphic(text);
                    }
                }
            };
        });
    }

    public String choiceListToString(ArrayList list){
        String stringList = "";
        for (int i=0; i<list.size(); i++){
            stringList = stringList + "Choice " + (i+1) + ": \n" + list.get(i) + "\n\n";
        }
        return stringList;
    }

    public String answerListToString(ArrayList list){
        String stringList = "";
        for (int i=0; i<list.size(); i++){
            stringList = stringList + list.get(i) + "\n\n";
        }
        return stringList;
    }

    @FXML
    public void openExerciseResult(){
        changeScene("../view/exerciseresult.fxml");
    }

    public void setExam(Exam exam){
        this.exam = exam;
        titleLabel.setText(exam.getTitle());
        gradeLabel.setText("Score: " + rawGrade + " / " + totalItems + " = " + percentGrade + "%");
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
