package sample.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 24/09/2016.
 */
public class TextLessonHtmlViewerController implements Initializable {

    @FXML private HTMLEditor textLessonHtmlEditor;
    @FXML private Button viewButton;

    @FXML private WebView textLessonWebView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML private void view() {
        WebEngine engine = textLessonWebView.getEngine();
        engine.loadContent(textLessonHtmlEditor.getHtmlText());
    }
}
