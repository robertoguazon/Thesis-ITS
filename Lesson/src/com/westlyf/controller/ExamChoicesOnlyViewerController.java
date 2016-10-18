package com.westlyf.controller;

import com.westlyf.database.ExamDatabase;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizItem;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by robertoguazon on 24/09/2016.
 */
public class ExamChoicesOnlyViewerController implements Initializable, Disposable {

    @FXML private Label examTitleLabel;
    @FXML private Slider timeLeftSlider;
    @FXML private TextArea questionTextArea;

    @FXML private Button stopExamButton;
    @FXML private Button submitExamButton;
    @FXML private VBox choicesVBox;
    @FXML private TextArea hintTextArea;

    @FXML private Slider itemsSlider;
    @FXML private Button itemsSliderLeftButton;
    @FXML private Button itemsSliderRightButton;

    private Exam exam;

    //choices gui fields
    private float buttonPrefWidth = 400f;
    private float buttonPrefHeight = 50f;

    //item track
    private IntegerProperty currentItem = new SimpleIntegerProperty(1);
    private int itemsSize = 0;

    private Timer timer;
    private Timer minutesTimer;

    private DoubleProperty minutes = new SimpleDoubleProperty();

    private long delay = 60_000 * 60 * 60; //60_000 (seconds in delta time) * 60 (to minutes) * 60 (to hour)

    //TODO time tracker and format to display slider

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeLeftSlider.setMin(0);
        timeLeftSlider.setMax(delay / 60_000.0f / 60.0f); //minutes slider

        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        stopExam();
                    }
                }, delay);

        minutesTimer = new Timer();
        minutesTimer.schedule(
                new TimerTask() {
                    int seconds = 0;
                    @Override
                    public void run() {
                        seconds++;
                        minutes.set(seconds / 60.0f);
                    }
                },0,1000
        );

        timeLeftSlider.valueProperty().bind(minutes);
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        examTitleLabel.setText(exam.getTitle()); //set the title
        itemsSize = exam.getQuizItems().size(); //set the number of items

        itemsSlider.setMax(itemsSize);
        itemsSlider.setMin(1);
        itemsSlider.valueProperty().bind(currentItem);

        setQuizItem();

        //TODO set timer and start
    }

    private void setQuizItem() {
        choicesVBox.getChildren().clear(); //erase nodes

        QuizItem quizItem = exam.getQuizItems().get(currentItem.get() - 1); //for array

        questionTextArea.setText(quizItem.getQuestion());
        hintTextArea.setText(quizItem.getHint());
        System.out.println(hintTextArea.getText());

        choicesVBox.getChildren().add(quizItem.getExamChoicesOnlyBox(buttonPrefWidth,buttonPrefHeight));
        setHintVisible(false);
    }

    @FXML private void slideLeft() {
        currentItem.setValue(currentItem.get() - 1);
        if (currentItem.get() < 1) {
            currentItem.set(1);
        }
        setQuizItem();
    }

    @FXML private void slideRight() {
        currentItem.setValue(currentItem.get() + 1);
        if (currentItem.get() > itemsSize) { // - 1 because of array
            currentItem.set(itemsSize);
        }
        setQuizItem();
    }

    public void setHintVisible(boolean visible) {
        System.out.println("setvisible: " + visible);
        hintTextArea.setVisible(visible);
    }

    @FXML private void stopExam() {
        //TODO - stop?
        stopTimer();
        System.out.println("Stopped Exam -test");
    }

    //TODO evaluate after submit
    @FXML private void submitExam() {
        System.out.println(exam.evaluate()); //TODO - catch score and record
    }

    @Override
    public void dispose() {
        stopTimer();
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();

        minutesTimer.cancel();
        minutesTimer.purge();
    }
    //TODO -dispose resources
}
