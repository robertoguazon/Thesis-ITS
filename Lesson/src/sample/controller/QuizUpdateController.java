package sample.controller;

import com.westlyf.database.ExamDatabase;
import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizItem;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 11/11/2016.
 */
public class QuizUpdateController implements Initializable {


    @FXML private Button loadButton;
    @FXML private Button updateButton;
    @FXML private FlowPane itemsFlowPane;
    @FXML private TextField loadTextField;
    @FXML private Label statusLabel;

    @FXML private ToggleGroup quizType;

    @FXML private RadioButton quizRadioButton;
    @FXML private RadioButton examRadioButton;

    private QuizExercise currentQuiz;

    private StringProperty statusProperty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quizRadioButton.setSelected(true);
        examRadioButton.setSelected(false);

        statusProperty = new SimpleStringProperty();
        statusLabel.textProperty().bind(statusProperty);
    }

    @FXML
    private void load() {
        if (quizRadioButton.isSelected()) {
            statusProperty.set("Loading quiz...");
            currentQuiz = ExerciseDatabase.getQuizExerciseUsingTitle(loadTextField.getText());
        } else if (examRadioButton.isSelected()) {
            statusProperty.set("Loading exam...");
            currentQuiz = ExamDatabase.getExamUsingTitle(loadTextField.getText());
        }


        if (currentQuiz != null) {
            statusProperty.set("");
            display(currentQuiz);
        } else {
            statusProperty.set("No match found");
        }
    }

    @FXML
    private void update() {
        System.out.println(currentQuiz);
    }

    private void display(QuizExercise quiz) {
        ArrayList<QuizItem> quizItems = quiz.getQuizItems();

        for (int i = 0; i < quizItems.size(); i++) {
            //bind questions to textareas
            Label questionLabel = createLabel("Question: ");
            TextArea questionTextArea = createTextArea();
            questionTextArea.textProperty().bindBidirectional(quizItems.get(i).questionProperty());

            //bind choices to textAreas and display choices with radio buttons


            //add to vbox
            VBox vBox = new VBox();
            vBox.getChildren().addAll(questionLabel,questionTextArea);


            //add to flowpane
            Separator separator = new Separator();
            separator.setOrientation(Orientation.HORIZONTAL);
            separator.setPrefSize(400f, 50f);
            itemsFlowPane.getChildren().addAll(vBox, separator);
        }
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        return label;
    }

    private TextArea createTextArea() {
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        return textArea;
    }


}
