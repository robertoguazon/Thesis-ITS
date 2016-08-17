package sample.controller;

import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
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
        if (quiz != null && quiz.isValidAnsweredFormat()) {
            System.out.println("Valid Quiz");

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
            confirmation.setTitle("CONFIRM");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    //TODO - updload to database if confirmed
                    System.out.println("sample upload confirm test");
                }
            });

        }
        else {
            System.out.println("Invalid Quiz");
            Alert error = new Alert(Alert.AlertType.ERROR, quiz.errorAnsweredFormat());
            error.setTitle("INVALID");
            error.show();
        }
    }

    public void setQuiz(QuizExercise quiz) {
        this.quiz = quiz;
        quizTitleLabel.setText(quiz.getTitle());
        quizTagsLabel.setText(quiz.getTagsString());

        quizItemVBox.getChildren().add(quiz.getQuizExercise());
    }
}
