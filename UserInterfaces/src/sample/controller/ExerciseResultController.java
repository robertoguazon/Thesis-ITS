package sample.controller;

import com.westlyf.agent.Agent;
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
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yves on 11/10/2016.
 */
public class ExerciseResultController implements Initializable{

    @FXML private Label titleLabel;
    @FXML private Label pointsLabel;
    @FXML private Label responseText;
    @FXML private TextArea instructionsTextArea;
    @FXML private TextArea codeTextArea;
    @FXML private TextArea outputTextArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleLabel.setText(Agent.getExamExercise().getTitle());
        instructionsTextArea.setText(Agent.getExamExercise().getInstructions());
        codeTextArea.setText(Agent.getExamExercise().getCode());
        outputTextArea.setText(Agent.getOutput());
        responseText.setText(Agent.getResponse());
        if (responseText.getText().contains("Correct")){
            responseText.getParent().setStyle("-fx-background-color: #00C853");
            pointsLabel.setText("5 points");
            pointsLabel.getParent().setStyle("-fx-background-color: #00C853");
        }else {
            responseText.getParent().setStyle("-fx-background-color: #F44336");
            pointsLabel.setText("0 points");
            pointsLabel.getParent().setStyle("-fx-background-color: #F44336");
        }
    }
}
