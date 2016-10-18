package com.westlyf.controller;

import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import com.westlyf.utils.RuntimeUtil;
import com.westlyf.utils.StringUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class PracticalPrintExerciseViewerController implements Initializable {

    @FXML
    private Label titleLabel;
    @FXML private TextArea instructionsTextArea;

    @FXML private TextArea codeTextArea;
    @FXML private Button clearCodeButton;
    @FXML private Button runCodeButton;

    @FXML private TextArea outputTextArea;
    @FXML private Button clearOutputButton;

    @FXML private Button submitButton;
    @FXML private Label responseText;

    private PracticalPrintExercise practicalPrintExercise;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submitButton.setDisable(true);
    }

    public void setPracticalPrintExercise(PracticalPrintExercise practicalPrintExercise) {
        this.practicalPrintExercise = practicalPrintExercise;

        titleLabel.setText(practicalPrintExercise.getTitle());
        instructionsTextArea.setText(practicalPrintExercise.getInstructions());

        codeTextArea.setText(practicalPrintExercise.getCode());
        practicalPrintExercise.codeProperty().bind(codeTextArea.textProperty());

    }

    public PracticalPrintExercise getPracticalPrintExercise() {
        return practicalPrintExercise;
    }

    @FXML
    private void clearCode() {
        this.codeTextArea.clear();
    }

    @FXML
    private void runCode() {
        if (practicalPrintExercise != null) {
            compileCode();
            /*if ( RuntimeUtil.STRING_OUTPUT.toString().isEmpty()) {
                outputError("use System.out.println() to output something");
            }*/
            outputStream(RuntimeUtil.CONSOLE_OUTPUT.toString());

            String errorString = RuntimeUtil.CONSOLE_ERR_OUTPUT.toString();
            errorString = StringUtil.replaceLineMatch(errorString, RuntimeUtil.LOGGER_SLF4J, ""); //remove log

            if (!errorString.isEmpty()) {
                outputError(errorString); //output errors
                responseText.setText("Error: Compilation");
                return;
            }

            if (practicalPrintExercise.evaluate(RuntimeUtil.CONSOLE_OUTPUT.toString())){
                if(practicalPrintExercise.checkCGroup(codeTextArea.textProperty())){
                    responseText.setText("Correct");
                } else responseText.setText("Incorrect: Cheating");
            }else {
                responseText.setText("Incorrect: Wrong Output");
            }

            System.out.println("code: " + codeTextArea.textProperty());
            System.out.println("ccheck: " + practicalPrintExercise.getCGroup());
            System.out.println("output: " + RuntimeUtil.CONSOLE_OUTPUT.toString());
            System.out.println("err: " + errorString);
        }
    }

    @FXML
    private void clearOutput() {

        try {
            this.outputTextArea.clear();
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_OUTPUT);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_ERR_OUTPUT);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void submit() {
        //TODO evaluate and get score and push to database
        compileCode();
    }

    private void compileCode() {
        try {
            System.out.println("try: ");
            RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STRING_STREAM);
            RuntimeUtil.setErrStream(RuntimeUtil.CONSOLE_ERR_STRING_STREAM);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_OUTPUT);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_ERR_OUTPUT);

            RuntimeUtil.compile(practicalPrintExercise);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STREAM);
            RuntimeUtil.setErrStream(RuntimeUtil.CONSOLE_ERR_STREAM);
        }
    }

    private void outputStream(String string) {
        outputTextArea.appendText(string);
    }

    private void outputLine(String string) {
        outputTextArea.appendText(string + "\n");
    }

    private void outputError(String string) {
        outputTextArea.appendText("Error: " + string + "\n");
    }
}
