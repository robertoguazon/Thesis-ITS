package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.WindowEvent;
import sample.controller.ControllerManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class ExamExerciseViewerController extends ControllerManager implements Initializable, Disposable{

    @FXML private Label titleLabel;
    @FXML private TextArea instructionsTextArea;

    @FXML private TextArea codeTextArea;
    @FXML private Button clearCodeButton;

    @FXML private Button submitButton;

    private PracticalPrintExercise practicalPrintExercise;
    private String initialCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {reset();}

    public void reset(){
    }

    public void setPracticalPrintExercise(PracticalPrintExercise practicalPrintExercise) {
        this.practicalPrintExercise = practicalPrintExercise;
        titleLabel.setText(practicalPrintExercise.getTitle());
        instructionsTextArea.setText(practicalPrintExercise.getInstructions());

        initialCode = practicalPrintExercise.getCode();
        codeTextArea.setText(initialCode);
        practicalPrintExercise.codeProperty().bind(codeTextArea.textProperty());
    }

    public PracticalPrintExercise getPracticalPrintExercise() {
        return practicalPrintExercise;
    }

    @FXML
    private void clearCode() {
        this.codeTextArea.setText(initialCode);
    }

    @FXML
    private void submit() {
        Agent.setIsExerciseCleared(true);
        child.fireEvent(new WindowEvent(child, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @Override
    public void dispose() {
        reset();
    }
}
