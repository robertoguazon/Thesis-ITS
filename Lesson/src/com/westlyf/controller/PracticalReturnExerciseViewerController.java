package com.westlyf.controller;

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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class PracticalReturnExerciseViewerController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML private TextArea instructionsTextArea;

    @FXML private TextArea codeTextArea;
    @FXML private Button clearCodeButton;


    @FXML private Button executeSampleInputButton;
    @FXML private FlowPane sampleInputFlowPane;

    @FXML private TextArea outputTextArea;
    @FXML private Button clearOutputButton;

    @FXML private Button submitButton;

    private PracticalReturnExercise practicalReturnExercise;
    private StringProperty[] parametersArray;

    public void setPracticalReturnExercise(PracticalReturnExercise practicalReturnExercise) {
        this.practicalReturnExercise = practicalReturnExercise;

        titleLabel.setText(practicalReturnExercise.getTitle());
        instructionsTextArea.setText(practicalReturnExercise.getInstructions());

        codeTextArea.setText(practicalReturnExercise.getCode());
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
    }

    @FXML
    private void clearCode() {
        this.codeTextArea.clear();
    }

    @FXML
    private void executeSampleInput() {
        clearOutput();
        if (practicalReturnExercise != null) {
            try {
                outputTextArea.setText(RuntimeUtil.compile(practicalReturnExercise, DataTypeUtil.toString(parametersArray)));
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
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
        if (compileCode()) {
            System.out.println("Correct: true");
        } else {
            System.out.println("Correct: false");
        }
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
