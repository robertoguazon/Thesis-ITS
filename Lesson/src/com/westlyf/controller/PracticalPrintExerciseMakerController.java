package com.westlyf.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 28/08/2016.
 */
public class PracticalPrintExerciseMakerController implements Initializable {

    @FXML private CheckBox mustMatchCheckBox;
    @FXML private TextArea expectedOutputTextArea;
    @FXML private Button clearExpectedOutputButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void clearExpectedOutput() {
        expectedOutputTextArea.clear();
    }

    public void bindPrintValidator(StringProperty expectedOutput) {

        expectedOutput.bind(expectedOutputTextArea.textProperty());
    }

    public void bindMustMatch(BooleanProperty mustMatchProperty) {
        mustMatchProperty.bind(mustMatchCheckBox.selectedProperty());
    }
}
