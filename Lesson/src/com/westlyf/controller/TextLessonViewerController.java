package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.agent.LoadType;
import com.westlyf.domain.exercise.mix.VideoPracticalExercise;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.user.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sample.controller.ControllerManager;
import sample.model.AlertBox;
import sample.model.ConfirmBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class TextLessonViewerController extends ControllerManager implements Initializable {

    @FXML private VBox lessonsVBox;
    @FXML private Label textLessonLabel;
    @FXML private WebView textLessonWebView;
    @FXML private Button back;
    @FXML private Button exerciseButton;
    @FXML private Hyperlink[] lesson;

    private ArrayList<TextLesson> lessonsInModule;
    private int currentLessonNo;
    private String currentModule;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCurrentLessonNo(int currentLessonNo) {
        this.currentLessonNo = currentLessonNo;
    }

    public void setTextLesson(TextLesson textLesson) {
        if (Agent.getLoggedUser() != null) {
            Agent.setLesson(textLesson);
        }
        textLessonLabel.setText(textLesson.getTitle());
        String url = new File(textLesson.getText()).toURI().toString();
        textLessonWebView.getEngine().load(url);
        setExercise(textLesson);
    }

    public void setExercise(TextLesson textLesson){
        String[] lessonTags = textLesson.getTagsString().split(",");
        for (int i=0; i<lessonTags.length; i++) {
            if (Agent.loadExercise(lessonTags[0], lessonTags[1]) == null) {
                exerciseButton.setText("Next Lesson");
                return;
            } else {
                exerciseButton.setText("Exercise");
                return;
            }
        }
    }

    public void setLessonList(String module){
        currentModule = module;
        if (Agent.getLoggedUser() != null) {
            lessonsInModule = Agent.getLessonsInModule(currentModule);
            lesson = new Hyperlink[lessonsInModule.size()];
            for (int i = 0; i < lesson.length; i++) {
                lesson[i] = new Hyperlink(i==0?"Introduction":"Lesson " + i);
                final int finalI = i;
                lesson[i].setOnAction(event -> {
                    setTextLesson(lessonsInModule.get(finalI));
                    currentLessonNo = finalI;
                });
                if (Agent.getLoggedUser().getCurrentModuleId().compareTo(currentModule) == 0) {
                    if (Agent.getLoggedUser().getCurrentLessonId().compareTo("lesson" + finalI) < 0) {
                        lesson[i].setDisable(true);
                    }
                }
            }
            lessonsVBox.getChildren().addAll(lesson);
        }
    }

    public void openExercise(){
        VideoPracticalExercise videoPracticalExercise = Agent.getExercise();
        if (videoPracticalExercise != null){
            Node node = Controllers.getNode(ControllerType.VIDEO_PRACTICAL_EXERCISE_VIEWER, videoPracticalExercise);
            newChildWindow(node, videoPracticalExercise.getTitle());
        }
    }

    public void unlock(int i){
        if (i < lesson.length) {
            if (lesson[i].isDisabled()) {
                lesson[i].setDisable(false);
                Agent.getLoggedUser().setCurrentLessonId("lesson" + i);
                AlertBox.display("Unlocked", "Unlocked new lesson " + i, "Click \"ok\" to close this alert box.");
            }
        }else {
            if (Agent.getLoggedUser().getCurrentExamId() == null) {
                if (Agent.getLoggedUser().getCurrentModuleId().equals(currentModule)) {
                    Agent.getLoggedUser().setCurrentExamId(currentModule);
                    Boolean answer = ConfirmBox.display("Take Exam",
                            "Congratulations!\nYou have completed the entire module.\n" +
                                    "You are now ready to take the exam.",
                            "Do you wish to go back to the main menu to take the exam?");
                    Agent.load(LoadType.EXAM);
                    if (answer) {
                        Agent.clearLessonsInModule();
                        lessonsVBox.getChildren().clear();
                        changeScene("../../../sample/view/user.fxml");
                    }
                }
            }
        }
    }

    @FXML public void handleAction(ActionEvent event){
        if (event.getSource() == back){
            Agent.clearLessonsInModule();
            lessonsVBox.getChildren().clear();
            changeScene("../../../sample/view/modules.fxml");
        }else if (event.getSource() == exerciseButton){
            if (exerciseButton.getText().equals("Next Lesson")){
                unlock(++currentLessonNo);
                setTextLesson(lessonsInModule.get(currentLessonNo));
            } else {openExercise();}
        }
    }

    public void closeChildWindow(){
        if (Agent.isCleared()){
            Agent.setIsExerciseCleared(false);
            disposeVideoLesson();
            disposePracticalExercise();
            child.close();
            unlock(++currentLessonNo);
        }else {
            Boolean answer = ConfirmBox.display("Close window",
                    "Are you sure you want to close this window?", "All changes will not be saved.");
            if (answer){
                disposeVideoLesson();
                disposePracticalExercise();
                child.close();
            }
        }
    }

    public void disposeVideoLesson(){
        VideoLessonViewerController videoLessonViewerController =
                (VideoLessonViewerController) Controllers.getController(ControllerType.VIDEO_LESSON_VIEWER);
        if (videoLessonViewerController instanceof Disposable) {
            videoLessonViewerController.dispose();
        }
    }

    public void disposePracticalExercise(){
        PracticalPrintExerciseViewerController practicalPrintExerciseViewerController =
                (PracticalPrintExerciseViewerController) Controllers.getController(ControllerType.PRACTICAL_PRINT_EXERCISE_VIEWER);
        if (practicalPrintExerciseViewerController instanceof Disposable) {
            practicalPrintExerciseViewerController.dispose();
        }
    }
}
