package sample.controller;

import com.westlyf.domain.exercise.quiz.QuizExercise;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 10/08/2016.
 */
public class ExerciseViewerController implements Initializable {

    @FXML private Label quizTitleLabel;
    @FXML private Label quizTagsLabel;
    @FXML private VBox quizItemVBox;
    @FXML private Button submitButton;

    private QuizExercise quiz;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void submit() {
        //TODO - for submission
    }

    public void setQuiz(QuizExercise quiz) {
        this.quiz = quiz;
        quizTitleLabel.setText(quiz.getTitle());
        quizTagsLabel.setText(quiz.getTagsString());

        quizItemVBox.getChildren().add(quiz.getQuizExercise());
    }
}
