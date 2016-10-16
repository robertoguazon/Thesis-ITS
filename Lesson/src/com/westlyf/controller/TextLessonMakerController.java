package com.westlyf.controller;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 30/07/2016.
 */
public class TextLessonMakerController implements Initializable {

    //@FXML private TextArea lessonTextArea; //!got changed because of htmleditor
    @FXML private HTMLEditor textLessonHtmlEditor;
    @FXML private Button clearButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textLessonHtmlEditor.setHtmlText("<html><head></head><body style='background-color: #FAFAFA;' contenteditable=\"false\"></body></html>");
    }

    @FXML
    private void clear() {

        //lessonTextArea.clear(); //!got changed because of htmleditor
        textLessonHtmlEditor.setHtmlText("<html><head></head><body style='background-color: #FAFAFA;' contenteditable=\"false\"></body></html>");
    }

    /*!got changed because of htmleditor
    public void bindTextLesson(StringProperty text) {

        text.bind(lessonTextArea.textProperty());
    }
    */

    public String getHtml() {
        return textLessonHtmlEditor.getHtmlText();
    }

}
