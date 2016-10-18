package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.exercise.practical.DataType;
import com.westlyf.domain.exercise.practical.PracticalReturnExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnValidator;
import com.westlyf.utils.DataTypeUtil;
import com.westlyf.utils.RuntimeUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class PracticalReturnExerciseViewerController implements Initializable {

    @FXML private VBox codePane;
    @FXML private HBox statusPane;

    @FXML private Label titleLabel;
    @FXML private Label statusLabel;
    @FXML private TextArea instructionsTextArea;

    @FXML private TextArea codeTextArea;
    @FXML private Button clearCodeButton;


    @FXML private Button executeSampleInputButton;
    @FXML private FlowPane sampleInputFlowPane;

    @FXML private TextArea outputTextArea;
    @FXML private Button clearOutputButton;

    @FXML private Button submitButton;
    @FXML private Label responseText;

    private PracticalReturnExercise practicalReturnExercise;
    private StringProperty[] parametersArray;
    private String initialCode;

    public void setPracticalReturnExercise(PracticalReturnExercise practicalReturnExercise) {
        this.practicalReturnExercise = practicalReturnExercise;

        titleLabel.setText(practicalReturnExercise.getTitle());
        instructionsTextArea.setText(practicalReturnExercise.getInstructions());

        initialCode = practicalReturnExercise.getCode();
        codeTextArea.setText(initialCode);
        practicalReturnExercise.codeProperty().bind(codeTextArea.textProperty());

        int parametersSize = practicalReturnExercise.getParametersSize();
        parametersArray = new StringProperty[parametersSize];
        for (int i = 0; i < parametersSize; i++) {
            TextField textField = new TextField();

            StringProperty stringProperty = new SimpleStringProperty();
            stringProperty.bind(textField.textProperty());
            parametersArray[i] = stringProperty;
            sampleInputFlowPane.getChildren().add(textField);
        }
    }

    public PracticalReturnExercise getPracticalReturnExercise() {
        return practicalReturnExercise;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codePane.toFront();
        submitButton.setDisable(true);
    }

    @FXML
    private void clearCode() {
        this.codeTextArea.setText(initialCode);
    }

    @FXML
    private void executeSampleInput() {
        clearOutput();
        if (practicalReturnExercise != null) {
            try {
                outputTextArea.setText(RuntimeUtil.compile(practicalReturnExercise, DataTypeUtil.toString(parametersArray)));        if (compileCode()) {
                    if (practicalReturnExercise.checkCGroup(codeTextArea.textProperty())) {
                        responseText.setText("Correct!");
                        responseText.getParent().setStyle("-fx-background-color: #00C853");
                        statusLabel.setText("Click the Submit Button to save your work \nand proceed to the next lesson.");
                        statusPane.setStyle("-fx-background-color: rgba(158, 158, 158, 0.7);");
                        statusPane.toFront();
                        codeTextArea.setEditable(false);
                        clearCodeButton.setDisable(true);
                        executeSampleInputButton.setDisable(true);
                        clearOutputButton.setDisable(true);
                        submitButton.setDisable(false);
                    } else {
                        responseText.setText(practicalReturnExercise.getExplanation());
                        responseText.getParent().setStyle("-fx-background-color: #F44336");
                    }
                } else {
                    System.out.println("Correct: false");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("code: " + codeTextArea.textProperty());
        System.out.println("ccheck: " + practicalReturnExercise.getCGroup());
        System.out.println("output: " + RuntimeUtil.CONSOLE_OUTPUT.toString());
    }

    @FXML
    private void clearOutput() {
        try {
            this.outputTextArea.clear();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void submit() {
        //TODO - fix
        if (Agent.containsPracticalExercise(practicalReturnExercise)){
            Agent.updateUserExercise(practicalReturnExercise);
        }else {
            Agent.addUserExercise(practicalReturnExercise);
        }
        Agent.setIsExerciseCleared(true);
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private boolean compileCode() {
        try {
            return RuntimeUtil.compile(practicalReturnExercise);

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }


}
