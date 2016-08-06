package sample.controller;

import com.westlyf.domain.lesson.VideoLesson;
import com.westlyf.utils.FileUtils;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 30/07/2016.
 */
public class VideoLessonMakerController implements Initializable {

    @FXML private TextField pathTextField;
    @FXML private Button chooseButton;
    @FXML private Button clearButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void choose() {
        File f = FileUtils.chooseFile(LessonMakerController.stage);
        pathTextField.setText(f.getPath());
    }

    @FXML
    private void clear() {
        pathTextField.clear();
    }

    public void bindPath(StringProperty path) {
        path.bind(pathTextField.textProperty());
    }

}
