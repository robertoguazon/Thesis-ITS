package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 25/07/2016.
 */
public class LessonMakerController implements Initializable {

    @FXML private TextField titleTextField;
    @FXML private Button addTagButton;
    @FXML private FlowPane tagsFlowPane;
    @FXML private ToggleGroup lessonTypeGroup;
    @FXML private RadioButton textLessonsRadioButton;
    @FXML private RadioButton videoLessonsRadioButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addTag() {

    }

    @FXML
    private void ok() {

    }

}
