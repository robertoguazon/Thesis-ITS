package sample.controller;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 30/07/2016.
 */
public class TextLessonMakerController implements Initializable {

    @FXML private TextArea lessonTextArea;
    @FXML private Button clearButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void clear() {
        lessonTextArea.clear();
    }

    public void bindTextLesson(StringProperty text) {
        text.bind(lessonTextArea.textProperty());
    }

}
