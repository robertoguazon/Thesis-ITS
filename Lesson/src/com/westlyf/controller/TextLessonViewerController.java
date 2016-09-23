package com.westlyf.controller;

import com.westlyf.domain.lesson.TextLesson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 04/09/2016.
 */
public class TextLessonViewerController implements Initializable {

    @FXML private Label textLessonLabel;
    @FXML private WebView textLessonWebView;
    //@FXML private TextArea textLessonTextArea; //!got changed because of htmleditor

    private TextLesson textLesson;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTextLesson(TextLesson textLesson) {
        this.textLesson = textLesson;
        textLessonLabel.setText(textLesson.getTitle());
        //textLessonTextArea.setText(textLesson.getText()); //!got changed because of htmleditor
        textLessonWebView.getEngine().loadContent(textLesson.getText());
    }

    public TextLesson getTextLesson() {
        return textLesson;
    }
}
