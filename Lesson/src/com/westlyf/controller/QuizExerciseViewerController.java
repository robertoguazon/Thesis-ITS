package com.westlyf.controller;

import com.westlyf.domain.exercise.quiz.QuizExercise;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class QuizExerciseViewerController implements Initializable {

    @FXML private Label quizExerciseLabel;
    @FXML private VBox quizItemVBox;
    @FXML private Button submitButton;

    private QuizExercise quizExercise;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void submit() {
        //TODO - for submission
        if (quizExercise != null && quizExercise.isValidAnsweredFormat()) {
            System.out.println("Valid Quiz");

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
            confirmation.setTitle("CONFIRM");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    //TODO - updload to database if confirmed
                    System.out.println("sample upload confirm test");

                    System.out.println("Score: " + quizExercise.evaluate() + "/" + quizExercise.getQuizItems().size());
                }
            });

        }
        else {
            System.out.println("Invalid Quiz");
            Alert error = new Alert(Alert.AlertType.ERROR, quizExercise.errorAnsweredFormat());
            error.setTitle("INVALID");
            error.show();
        }
    }

    public void setQuizExercise(QuizExercise quiz) {
        this.quizExercise = quiz;
        quizExerciseLabel.setText(quiz.getTitle());

        quizItemVBox.getChildren().add(quiz.getQuizExercise());
        System.out.println(quizExercise);
    }

    public QuizExercise getQuizExercise() {
        return quizExercise;
    }
}
