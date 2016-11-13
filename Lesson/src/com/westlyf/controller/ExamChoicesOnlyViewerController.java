package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.agent.LoadType;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.user.ExamGrade;
import com.westlyf.utils.array.ArrayUtil;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controller.ControllerManager;
import sample.controller.ResultController;
import sample.model.AlertBox;
import sample.model.ConfirmBox;

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

    @FXML private VBox hintPane;
    @FXML private VBox choicesVBox;

    @FXML private Label examTitleLabel;
    @FXML private Slider timeLeftSlider;
    @FXML private TextArea questionTextArea;
    @FXML private Button hintButton;
    @FXML private TextArea hintTextArea;
    @FXML private Button examExerciseButton;
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
    private static final int passingGrade = 75; //75% passing grade
    private int rawGrade;
    private int totalItems;
    private int percentGrade;
    private String status, title, message, module;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemsSliderRightButton.setDisable(false);
        itemsSliderLeftButton.setDisable(true);
    }

    public void reset() {
        choicesVBox.getChildren().clear();
        hintPane.toBack();

        examTitleLabel.setText("");
        timeLeftSlider.setValue(0);
        questionTextArea.setText("");
        hintTextArea.setText("");
        examExerciseButton.setDisable(false);
        examExerciseButton.setStyle("");
        setHintButtonDisable(true);
        setHintTextAreaVisible(false);

        exam.clearAllAnswers(); //clear the user's answers
        exam = null; // set the reference to null
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

    public void startTimer(int minutes) {
        setTimer(minutes); // set minutes
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
                        Platform.runLater(() -> submit());
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
        for (QuizItem randomizedQuizItem : randomizedQuizItems) {
            quizItem = randomizedQuizItem;
            quizItem.setChoices(ArrayUtil.randomizeArrayList(quizItem.getChoices()));
        }
        examTitleLabel.setText(exam.getTitle()); //set the title
        itemsSize = exam.getQuizItems().size(); //set the number of items

        itemsSlider.setMax(itemsSize);
        itemsSlider.setMin(1);
        itemsSlider.valueProperty().bind(currentItem);
        itemsSliderLeftButton.setDisable(true);

        setQuizItem();

        totalItems = exam.getQuizItems().size() + 5;
        startTimer(totalItems * 2);
    }

    private void setQuizItem() {
        choicesVBox.getChildren().clear(); //erase nodes //TODO put this in the future in reset

        QuizItem quizItem = randomizedQuizItems.get(currentItem.get() - 1); //for array

        questionTextArea.setText(quizItem.getQuestion());
        hintTextArea.setText(quizItem.getHint());

        choicesVBox.getChildren().add(quizItem.getExamChoicesOnlyBox(buttonPrefWidth,buttonPrefHeight));
        hintPane.toBack();
        setHintButtonDisable(true);
        setHintTextAreaVisible(false);
    }

    public void setHintButtonDisable(boolean disable){
        hintButton.setDisable(disable);
    }

    public void setHintTextAreaVisible(boolean visible) {
        hintTextArea.setVisible(visible);
    }

    @FXML public void slideLeft() {
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

    @FXML public void slideRight() {
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

    @FXML public void hintButtonAction(){
        hintPane.toFront();
        setHintTextAreaVisible(true);
    }

    @FXML private void openExamExercise(){
        Node node = Controllers.getNode(ControllerType.EXAM_EXERCISE_VIEWER, Agent.getExamExercise());
        newChildWindow(node, "Exam Exercise");
    }

    @FXML public void submitExam() {
        if (!Agent.isCleared()){
            AlertBox.display("Answer Exam Exercise first", "Please answer the exercise first.", "Click \"ok\" to close this window.");
            openExamExercise();
        }else {
            submit();
        }
    }

    private void stopExam() {
        stopTimer();
        if (child != null) {
            if (child.isShowing()) {
                disposeExamExercise();
                child.close();
            }
        }
        Agent.stopBackground();
        Agent.setIsExerciseCleared(false);
        System.out.println("Stopped Exam -test");
    }

    private void submit(){
        stopExam();
        String currentModule = Agent.getLoggedUser().getCurrentModuleId();
        int moduleNo = Integer.parseInt(String.valueOf(currentModule.charAt(currentModule.length() - 1)));
        module = "module" + moduleNo;
        computeGrade();
        if (Agent.getLoggedUser() != null) {
            saveRecords();
            if (status.equals("Passed")) {
                Boolean answer = ConfirmBox.display("Exam Finished", title, message);
                if (answer) { viewResults(); }
                if (++moduleNo <= 7) { unlockNextModule("module" + moduleNo); }
                else { unlockChallenge(); }
                Agent.clearExams();
                Agent.clearExamExercises();
            } else {
                //AlertBox.display("Exam Finished", title, message);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exam Finished");
                alert.setHeaderText(title);
                alert.setContentText(message);
                alert.showAndWait();
                Agent.getLoggedUser().setCurrentExamId(module);
            }
            Agent.setExam(null);
            Agent.setExamExercise(null);
            Agent.setResponse("");
            Agent.setOutput("");
        }
        reset();
        changeScene("/sample/view/user.fxml");
    }

    private void computeGrade(){
        rawGrade = exam.evaluate();
        if (Agent.runCode(Agent.getExamExercise())){
            rawGrade = rawGrade + 5;
        }
        percentGrade = 50 * rawGrade / totalItems + 50;
        message = "Raw grade: " + rawGrade + "\n" +
                "Total Items: " + totalItems + "\n" +
                "Percent grade: " + percentGrade + "%";
        if (percentGrade >= passingGrade) {
            status = "Passed";
            title = "Congratulations! You have passed the exam.\n" +
                    "Click \"ok\" to view your results.\n" +
                    "Click \"cancel\" to return to home.";
            int upperLimit = computeUpperLimit();
            if (percentGrade > upperLimit) {
                message = message + " => " + upperLimit + "%\n" +
                        "Your grade has been deducted corresponding to the number of tries in this module's exam.";
                percentGrade = upperLimit;
            }
        }else {
            status = "Failed";
            title = "Your grade didn't reach the passing score of " +
                    passingGrade + "%.\n Please review your " + module + " then try again next time.";
        }
    }

    private int computeUpperLimit(){
        int tries = Agent.getTries() + 1;
        int upperLimit = 100 - (tries - 1) * (tries + 4);
        return upperLimit>76?upperLimit:76;
    }

    private void saveRecords(){
        if (Agent.getLoggedUser() != null) {
            if (Agent.addExamGrade(new ExamGrade(Agent.getLoggedUser().getUserId(),
                    exam.getTitle(), rawGrade, totalItems, percentGrade, status)) < 0) {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/view/results.fxml"));
            Parent root = loader.load();
            ResultController resultController = loader.getController();
            resultController.setRawGrade(rawGrade);
            resultController.setTotalItems(totalItems);
            resultController.setPercentGrade(percentGrade);
            resultController.setExam(exam);
            Scene scene2 = new Scene(root);
            Stage stage1 = new Stage();
            scene2.getStylesheets().addAll(scene.getStylesheets());
            stage1.setTitle("Exam Results");
            stage1.setOnCloseRequest(event -> {
                event.consume();
                closeResultWindow(stage1);
            });
            stage1.setScene(scene2);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.initOwner(stage);
            stage1.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeResultWindow(Stage stage1) {
        Boolean answer = ConfirmBox.display("Confirm Exit", "Close window?", "Are you sure you want to close this window?");
        if (answer){
            stage1.close();
        }
    }

    @Override
    public void closeChildWindow() {
        if (Agent.isCleared()){
            examExerciseButton.setStyle("-fx-background-color: #00C853;");
            disposeExamExercise();
            child.close();
        }else {
            Boolean answer = ConfirmBox.display("Close window",
                    "Are you sure you want to close this window?",
                    "All changes will not be saved.");
            if (answer) {
                disposeExamExercise();
                child.close();
            }
        }
    }

    public void disposeExamExercise(){
        ExamExerciseViewerController examExerciseViewerController;
        examExerciseViewerController = (ExamExerciseViewerController) Controllers.getController(ControllerType.EXAM_EXERCISE_VIEWER);
        if (examExerciseViewerController != null){
            examExerciseViewerController.dispose();
        }
    }

    @Override
    public void dispose() {
        stopExam();
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
}
