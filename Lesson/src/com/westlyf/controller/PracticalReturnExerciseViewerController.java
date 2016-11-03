package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.exercise.practical.DataType;
import com.westlyf.domain.exercise.practical.PracticalReturnExercise;
import com.westlyf.domain.exercise.practical.PracticalReturnValidator;
import com.westlyf.user.UserExercise;
import com.westlyf.user.Users;
import com.westlyf.utils.DataTypeUtil;
import com.westlyf.utils.RuntimeUtil;
import com.westlyf.utils.StringUtil;
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
import sample.controller.ControllerManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class PracticalReturnExerciseViewerController extends ControllerManager implements Initializable, Disposable {

    @FXML private VBox codePane;
    @FXML private HBox statusPane;

    @FXML private Label titleLabel;
    @FXML private Label statusLabel;
    @FXML private TextArea instructionsTextArea;

    @FXML private TextArea codeTextArea;
    @FXML private Button resetCodeButton;


    @FXML private Button executeSampleInputButton;
    @FXML private FlowPane sampleInputFlowPane;

    @FXML private TextArea outputTextArea;
    @FXML private Button clearOutputButton;

    @FXML private Button submitButton;
    @FXML private Label responseText;

    private PracticalReturnExercise practicalReturnExercise;
    private String initialCode;

    public void setPracticalReturnExercise(PracticalReturnExercise practicalReturnExercise) {
        this.practicalReturnExercise = practicalReturnExercise;

        titleLabel.setText(practicalReturnExercise.getTitle());
        instructionsTextArea.setText(practicalReturnExercise.getInstructions());

        initialCode = practicalReturnExercise.getCode();
        codeTextArea.setText(initialCode);
        practicalReturnExercise.codeProperty().bind(codeTextArea.textProperty());
    }

    public PracticalReturnExercise getPracticalReturnExercise() {
        return practicalReturnExercise;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {reset();}

    public void reset(){
        codePane.toFront();
        submitButton.setDisable(true);
        responseText.setText("");
        responseText.getParent().setStyle("");
        statusLabel.setText("");
        statusPane.setStyle("");
        clearOutput();
    }

    @FXML
    private void resetCode() {
        this.codeTextArea.setText(initialCode);
    }

    @FXML
    private void execute() {
        if (practicalReturnExercise != null) {
            try {
                clearOutput();
                if (compileCode()) {

                    outputStream(RuntimeUtil.CONSOLE_OUTPUT.toString());

                    // -1 means no error; returns index of CString
                    int errorCStringIndex = practicalReturnExercise.checkCGroup(codeTextArea.textProperty());
                    if (errorCStringIndex == -1) {
                        responseText.setText("Correct!");
                        responseText.getParent().setStyle("-fx-background-color: #00C853");
                        statusLabel.setText("Click the Submit Button to save your work \nand proceed to the next lesson.");
                        statusPane.setStyle("-fx-background-color: rgba(158, 158, 158, 0.7);");
                        statusPane.toFront();
                        codeTextArea.setEditable(false);
                        resetCodeButton.setDisable(true);
                        executeSampleInputButton.setDisable(true);
                        clearOutputButton.setDisable(true);
                        submitButton.setDisable(false);
                    } else {
                        //responseText.setText(practicalReturnExercise.getExplanation());
                        responseText.setText(practicalReturnExercise.getCStringTip(errorCStringIndex));
                        responseText.getParent().setStyle("-fx-background-color: #F44336");
                    }
                } else {
                    System.out.println("Error: compilation or value");

                    String errorString = RuntimeUtil.CONSOLE_ERR_OUTPUT.toString();
                    errorString = StringUtil.replaceLineMatch(errorString, RuntimeUtil.LOGGER_SLF4J, ""); //remove log

                    if (!errorString.isEmpty()) {
                        outputError(errorString); //output errors
                        responseText.setText("Error: Compilation");
                        responseText.getParent().setStyle("-fx-background-color: #F44336");
                        return;
                    } else {
                        //if value validators not match
                        outputStream(RuntimeUtil.CONSOLE_OUTPUT.toString());
                        responseText.setText("Incorrect: did not match expected return values");
                        responseText.getParent().setStyle("-fx-background-color: #F44336");
                    }

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
        Users loggedUser = Agent.getLoggedUser();
        if (loggedUser != null) {
            if (Agent.containsPracticalExercise(practicalReturnExercise)) {
                if (Agent.updateUserExercise(practicalReturnExercise.getCode()) < 0) {
                    return;
                }
            } else {
                if (Agent.addUserExercise(new UserExercise(loggedUser.getUserId(),
                        practicalReturnExercise.getTitle(), practicalReturnExercise.getCode())) < 0) {
                    return;
                }
            }
            Agent.setIsExerciseCleared(true);
        }
        reset();
        child.fireEvent(new WindowEvent(child, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private boolean compileCode() {
        try {
            RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STRING_STREAM);
            RuntimeUtil.setErrStream(RuntimeUtil.CONSOLE_ERR_STRING_STREAM);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_OUTPUT);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_ERR_OUTPUT);

            RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STRING_STREAM);
            RuntimeUtil.reset(RuntimeUtil.CONSOLE_OUTPUT);
            return RuntimeUtil.compile(practicalReturnExercise);

        } catch (Exception e) {
            e.printStackTrace();

            return false;
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

    @Override
    public void dispose() {
        reset();
    }

}
