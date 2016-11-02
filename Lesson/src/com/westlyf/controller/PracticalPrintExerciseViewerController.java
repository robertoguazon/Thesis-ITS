package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.agent.LoadType;
import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import com.westlyf.user.UserExercise;
import com.westlyf.user.Users;
import com.westlyf.utils.RuntimeUtil;
import com.westlyf.utils.StringUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.controller.ControllerManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class PracticalPrintExerciseViewerController extends ControllerManager implements Initializable {

    @FXML private VBox codePane;
    @FXML private HBox statusPane;

    @FXML private Label titleLabel;
    @FXML private Label statusLabel;
    @FXML private TextArea instructionsTextArea;

    @FXML private TextArea codeTextArea;
    @FXML private Button clearCodeButton;
    @FXML private Button runCodeButton;

    @FXML private TextArea outputTextArea;
    @FXML private Button clearOutputButton;

    @FXML private Button submitButton;
    @FXML private Label responseText;

    private PracticalPrintExercise practicalPrintExercise;
    private String initialCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void reset(){
        codePane.toFront();
        submitButton.setDisable(true);
        responseText.setText("");
        responseText.getParent().setStyle("");
        statusLabel.setText("");
        statusPane.setStyle("");
        clearOutput();
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
    private void runCode() {
        if (practicalPrintExercise != null) {
            clearOutput();
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
                responseText.getParent().setStyle("-fx-background-color: #F44336");
                return;
            }

            // -1 means no error; returns index of CString
            int errorCStringIndex = practicalPrintExercise.checkCGroup(codeTextArea.textProperty());
            boolean correctOutput = practicalPrintExercise.evaluate(RuntimeUtil.CONSOLE_OUTPUT.toString());
            if (errorCStringIndex == -1 && correctOutput) {
                responseText.setText("Correct");
                responseText.getParent().setStyle("-fx-background-color: #00C853");
                statusLabel.setText("Click the Submit Button to save your work \nand proceed to the next lesson.");
                statusPane.setStyle("-fx-background-color: rgba(158, 158, 158, 0.7);");
                statusPane.toFront();
                submitButton.setDisable(false);
            } else if (errorCStringIndex != -1) {
                //responseText.setText(practicalPrintExercise.getExplanation());
                responseText.setText(practicalPrintExercise.getCStringTip(errorCStringIndex));
                responseText.getParent().setStyle("-fx-background-color: #F44336");
            } else if (!correctOutput) {
                responseText.setText("Output not match: follow instructions");
                responseText.getParent().setStyle("-fx-background-color: #F44336");
            }

            /*
            if (practicalPrintExercise.evaluate(RuntimeUtil.CONSOLE_OUTPUT.toString())) {

                // -1 means no error; returns index of CString
                int errorCStringIndex = practicalPrintExercise.checkCGroup(codeTextArea.textProperty());
                if (errorCStringIndex == -1) {
                    responseText.setText("Correct");
                    responseText.getParent().setStyle("-fx-background-color: #00C853");
                    statusLabel.setText("Click the Submit Button to save your work \nand proceed to the next lesson.");
                    statusPane.setStyle("-fx-background-color: rgba(158, 158, 158, 0.7);");
                    statusPane.toFront();
                    codeTextArea.setEditable(false);
                    clearCodeButton.setDisable(true);
                    runCodeButton.setDisable(true);
                    clearOutputButton.setDisable(true);
                    submitButton.setDisable(false);
                } else {
                    //responseText.setText(practicalPrintExercise.getExplanation());
                    responseText.setText(practicalPrintExercise.getCStringTip(errorCStringIndex));
                    responseText.getParent().setStyle("-fx-background-color: #F44336");
                }
            } else {
                responseText.setText(practicalPrintExercise.getExplanation());
                responseText.getParent().setStyle("-fx-background-color: #F44336");
            }
             */
        }else {
            System.out.println("practicalPrintExercise is null");
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
        Users loggedUser = Agent.getLoggedUser();
        if (loggedUser != null) {
            if (Agent.containsPracticalExercise(practicalPrintExercise)) {
                if (Agent.updateUserExercise(practicalPrintExercise.getCode()) < 0) {
                    return;
                }
            } else {
                if (Agent.addUserExercise(new UserExercise(loggedUser.getUserId(),
                        practicalPrintExercise.getTitle(), practicalPrintExercise.getCode())) < 0) {
                    return;
                }
            }
            Agent.setIsExerciseCleared(true);
        }
        reset();
        child.fireEvent(new WindowEvent(child, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private void compileCode() {
        try {
            RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STRING_STREAM);
            RuntimeUtil.setErrStream(RuntimeUtil.CONSOLE_ERR_STRING_STREAM);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_OUTPUT);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_ERR_OUTPUT);

            RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STRING_STREAM);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_OUTPUT);

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
