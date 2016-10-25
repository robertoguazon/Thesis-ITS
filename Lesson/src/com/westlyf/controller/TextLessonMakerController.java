package com.westlyf.controller;

import com.westlyf.utils.FileUtils;
import com.westlyf.utils.file.FileUtil;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Path;
import javafx.scene.web.HTMLEditor;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 30/07/2016.
 */
public class TextLessonMakerController implements Initializable {

    @FXML private TextField pathTextField;
    @FXML private Button chooseButton;
    @FXML private Button clearButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML private void choose(){
        pathTextField.setText(FileUtils.chooseHtmlFile(LessonMakerController.stage).getPath());
    }

    @FXML private void clear(){
        pathTextField.clear();
    }

    public void bindPath(StringProperty path) {
        path.bind(pathTextField.textProperty());
    }
}
