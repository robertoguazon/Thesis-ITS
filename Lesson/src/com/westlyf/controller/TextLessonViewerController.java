package com.westlyf.controller;

import com.westlyf.agent.Agent;
import com.westlyf.domain.lesson.TextLesson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class TextLessonViewerController implements Initializable {

    @FXML private Label textLessonLabel;
    @FXML private WebView textLessonWebView;
    @FXML private VBox lessonsVBox;
    @FXML private Button back;
    @FXML private Button next;
    @FXML private Hyperlink[] lesson;
    //@FXML private TextArea textLessonTextArea; //!got changed because of htmleditor

    private TextLesson textLesson;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Agent.getLoggedUser() != null) {
            ArrayList<TextLesson> lessonsInModule = Agent.getLessonsInModule(Agent.getCurrentModule());
            lesson = new Hyperlink[lessonsInModule.size()];
            for (int i = 0; i < lesson.length; i++) {
                lesson[i] = new Hyperlink("Lesson " + (i + 1));
                lesson[i].setFont(Font.font("System", FontWeight.NORMAL, 18));
                final int finalI = i;
                lesson[i].setOnAction(event -> {
                    setTextLesson(lessonsInModule.get(finalI));
                });
            }
            lessonsVBox.getChildren().addAll(lesson);
        }
    }

    public void setTextLesson(TextLesson textLesson) {
        this.textLesson = textLesson;
        textLessonLabel.setText(textLesson.getTitle());
        textLessonWebView.getEngine().loadContent(textLesson.getText());
        //textLessonTextArea.setText(textLesson.getText()); //!got changed because of htmleditor
    }

    public TextLesson getTextLesson() {
        return textLesson;
    }

    @FXML
    private void handleChangeSceneAction(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        if (event.getSource() == next){
            stage = (Stage)next.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../../../sample/view/exercise.fxml"));
        }else if (event.getSource() == back){
            Agent.clearLessonsInModule();
            stage = (Stage)back.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("../../../sample/view/modules.fxml"));
        }
        else {return;}
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void openExercise(){

    }
}
