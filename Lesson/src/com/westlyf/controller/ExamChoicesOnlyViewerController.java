package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.agent.LoadType;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.utils.array.ArrayUtil;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.controller.ControllerManager;
import sample.controller.ResultController;
import sample.model.AlertBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by robertoguazon on 24/09/2016.
 */
public class ExamChoicesOnlyViewerController extends ControllerManager implements Initializable, Disposable {

    @FXML private BorderPane pane;
    @FXML private VBox hintPane;
    @FXML private VBox choicesVBox;

    @FXML private Label examTitleLabel;
    @FXML private Slider timeLeftSlider;
    @FXML private TextArea questionTextArea;
    @FXML private Button hintButton;
    @FXML private TextArea hintTextArea;
    @FXML private Button submitExamButton;

    @FXML private Slider itemsSlider;
    @FXML private Button itemsSliderLeftButton;
    @FXML private Button itemsSliderRightButton;

    private Exam exam;
    private ArrayList<QuizItem> randomizedQuizItems = new ArrayList<>();

    //choices gui fields
    private float buttonPrefWidth = 620f;
    private float buttonPrefHeight = 250f;

    //item track
    private IntegerProperty currentItem = new SimpleIntegerProperty(1);
    private int itemsSize = 0;

    private Timer timer;
    private Timer minutesTimer;

    private DoubleProperty minutesProperty = new SimpleDoubleProperty();

    private long delayMinute = 1_000 * 60; //60_000 (seconds in delta time) * 60 (to minutes) * 60 (to hour)
    private static final int passingGrade = 60; //60% passing grade
    private int rawGrade;
    private int totalItems;
    private int percentGrade;
    private String status;

    //TODO time tracker and format to display slider

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setHintButtonDisable(true);
        setHintTextAreaVisible(false);
    }

    public void reset() {
        choicesVBox.getChildren().clear();

        examTitleLabel.setText("");
        timeLeftSlider.setValue(0);
        questionTextArea.setText("");
        hintTextArea.setText("");

        exam = null;
        randomizedQuizItems.clear();

        itemsSlider.setValue(0);
        itemsSliderRightButton.setDisable(false);
        itemsSliderLeftButton.setDisable(true);

        currentItem.set(1);
        itemsSize = 0;

        timer = null;
        minutesTimer = null;
        minutesProperty.set(0);

        rawGrade = 0;
        totalItems = 0;
        percentGrade = 0;
        status = "";
    }

    public void startTimer() {
        setTimer(15); // 15minutes
    }

    private void setTimer(int minutes){
        long delay = delayMinute * minutes;

        timeLeftSlider.setMin(0);
        timeLeftSlider.setMax(delay / 1_000.0f / 60.0f); //minutes slider

        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        submitExam();
                    }
                }, delay);

        minutesTimer = new Timer();
        minutesTimer.schedule(
                new TimerTask() {
                    int seconds = 0;
                    @Override
                    public void run() {
                        seconds++;
                        minutesProperty.set(seconds / 60.0f);
                    }
                },0,1000
        );

        timeLeftSlider.valueProperty().bind(minutesProperty);
    }

    public void setExam(Exam exam) {
        this.exam = exam;
        randomizedQuizItems = ArrayUtil.randomizeArrayList(exam.getQuizItems());
        QuizItem quizItem;
        for (int i = 0; i < randomizedQuizItems.size(); i++){
            quizItem = randomizedQuizItems.get(i);
            quizItem.setChoices(ArrayUtil.randomizeArrayList(quizItem.getChoices()));;
        }
        examTitleLabel.setText(exam.getTitle()); //set the title
        itemsSize = exam.getQuizItems().size(); //set the number of items

        itemsSlider.setMax(itemsSize);
        itemsSlider.setMin(1);
        itemsSlider.valueProperty().bind(currentItem);
        itemsSliderLeftButton.setDisable(true);

        setQuizItem();

        startTimer();
        //TODO set timer and start
    }

    private void setQuizItem() {
        choicesVBox.getChildren().clear(); //erase nodes //TODO put this in the furture in reset

        QuizItem quizItem = randomizedQuizItems.get(currentItem.get() - 1); //for array

        questionTextArea.setText(quizItem.getQuestion());
        hintTextArea.setText(quizItem.getHint());

        choicesVBox.getChildren().add(quizItem.getExamChoicesOnlyBox(buttonPrefWidth,buttonPrefHeight));
        hintPane.toBack();
        setHintButtonDisable(true);
        setHintTextAreaVisible(false);
    }

    @FXML private void slideLeft() {
        currentItem.setValue(currentItem.get() - 1);
        if (currentItem.get() < 2) {
            currentItem.set(1);
            itemsSliderLeftButton.setDisable(true);
        }
        if (itemsSliderRightButton.isDisabled()){
            itemsSliderRightButton.setDisable(false);
        }
        setQuizItem();
    }

    @FXML private void slideRight() {
        currentItem.setValue(currentItem.get() + 1);
        if (currentItem.get() > itemsSize-1) { // - 1 because of array
            currentItem.set(itemsSize);
            itemsSliderRightButton.setDisable(true);
        }
        if (itemsSliderLeftButton.isDisabled()){
            itemsSliderLeftButton.setDisable(false);
        }
        setQuizItem();
    }

    @FXML private void hintButtonAction(){
        hintPane.toFront();
        setHintTextAreaVisible(true);
    }

    public void setHintButtonDisable(boolean disable){
        hintButton.setDisable(disable);
    }

    public void setHintTextAreaVisible(boolean visible) {
        hintTextArea.setVisible(visible);
    }

    @FXML private void stopExam() {
        //TODO - stop?
        stopTimer();
        System.out.println("Stopped Exam -test");
    }

    //TODO evaluate after submit
    @FXML private void submitExam() {
        System.out.println("Exam submitted");
        stopExam();
        rawGrade = exam.evaluate();
        totalItems = exam.getQuizItems().size();
        percentGrade = (int) 100 * rawGrade / totalItems;
        String currentModule = Agent.getLoggedUser().getCurrentModuleId();
        int moduleNo = Integer.parseInt(String.valueOf(currentModule.charAt(currentModule.length()-1)));
        String module = "module" + moduleNo;
        String title, message;
        if (percentGrade >= passingGrade) {
            status = "Passed";
            title = "Congratulations! You have passed the exam.";
            message = "Raw grade: " + rawGrade + "\n" +
                    "Total Items: " + totalItems + "\n" +
                    "Percent grade: " + percentGrade;
        }else {
            status = "Failed";
            title = "Your grade didn't reach the passing score of " +
                    passingGrade*100 + "%.\n Please review your " + module + " then try again next time.";
            message = "Raw grade: " + rawGrade + "\n" +
                    "Total Items: " + totalItems + "\n" +
                    "Percent grade: " + percentGrade;
        }
        if (Agent.getLoggedUser() != null){
            saveRecords();
            if (status.equals("Passed")){
                if (++moduleNo <= 7) {
                    unlockNextModule("module" + moduleNo);
                }else {unlockChallenge();}
            }else {
                Agent.getLoggedUser().setCurrentExamId(module);
            }
        }
        AlertBox.display("Exam Finished", title, message);
        Agent.stopBackground();
        Agent.clearExams();
        reset();
        changeScene("../../../sample/view/user.fxml");
    }

    private void saveRecords(){
        if (Agent.getLoggedUser() != null) {
            if (Agent.addExamGrade(exam.getTitle(), rawGrade, totalItems, percentGrade, status) < 0) {
                return;
            }
        }
    }

    private void unlockNextModule(String module){
        if (Agent.getLoggedUser() != null) {
            Agent.getLoggedUser().setCurrentModuleId(module);
            Agent.getLoggedUser().setCurrentLessonId("lesson0");
            Agent.getLoggedUser().setCurrentExamId(null);
            Agent.load(LoadType.LESSON, module);
            Agent.print(LoadType.LESSON);
            Agent.load(LoadType.EXERCISE, module);
            Agent.print(LoadType.EXERCISE);
            if (Agent.updateUser() > 0){
                AlertBox.display("Unlocked", "Unlocked " + module, "Click \"ok\" to close this alert box.");
            }
        }
    }

    private void unlockChallenge() {
        if (Agent.getLoggedUser() != null) {
            Agent.getLoggedUser().setCurrentExamId("challenge");
            Agent.load(LoadType.CHALLENGE, "challenge");
            Agent.print(LoadType.CHALLENGE);
            if (Agent.updateUser() > 0){
                AlertBox.display("Unlocked",
                        "Congratulations! You've cleared all the modules.\n" +
                        "Unlocked Challenges Area.",
                        "You have unlocked the Challenges Area.\n" +
                        "Take on different known algorithms and \n" +
                        "try to solve them with the knowledge you've acquired and \n" +
                        "apply what you have learned in the past modules.\n\n" +
                        "Click \"ok\" to close this alert box.");
            }
        }
    }

    private void viewResults(){
        Stage stage;
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../sample/view/results.fxml"));
            root = loader.load();
            ResultController resultController = loader.getController();
            resultController.setExam(exam);
            resultController.setRawGrade(rawGrade);
            resultController.setTotalItems(totalItems);
            resultController.setPercentGrade(percentGrade);
            stage = (Stage)submitExamButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(pane.getScene().getStylesheets());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        stopTimer();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        if (minutesTimer != null) {
            minutesTimer.cancel();
            minutesTimer.purge();
        }
    }
    //TODO -dispose resources
}
