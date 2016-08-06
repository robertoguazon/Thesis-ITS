package sample.controller;

import com.westlyf.domain.lesson.Lesson;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 25/07/2016.
 */
public class LessonMakerController implements Initializable {

    @FXML private TextField titleTextField;
    @FXML private Button addTagButton;
    @FXML private FlowPane tagFlowPane;
    @FXML private ToggleGroup lessonTypeGroup;
    @FXML private RadioButton textLessonsRadioButton;
    @FXML private RadioButton videoLessonsRadioButton;
    @FXML private BorderPane containerBorderPane;
    @FXML private Button createButton;

    private TextLesson textLesson;
    private VideoLesson videoLesson;
    private Lesson lesson;

    private ArrayList<StringProperty> tags;
    private BorderPane root;

    public static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tags = new ArrayList<>();
        addTagButton.setDisable(true);
        textLessonsRadioButton.setSelected(true);
        createButton.setDisable(true);
        textLesson = new TextLesson();
        videoLesson = new VideoLesson();
        lesson = new Lesson();
    }

    @FXML
    private void addTag() {
        TextField tagTextField = new TextField();
        tagTextField.setMinSize(100,20);
        tagTextField.setMaxSize(100,20);

        //add to string property then add to tag lists
        StringProperty stringProperty = new SimpleStringProperty();
        stringProperty.bind(tagTextField.textProperty());
        tags.add(stringProperty);
        textLesson.setTags(tags);
        videoLesson.setTags(tags);

        Button removeButton = new Button("x");
        removeButton.setFocusTraversable(false);
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tagFlowPane.getChildren().remove(tagTextField);
                tagFlowPane.getChildren().remove(removeButton);
                tags.remove(stringProperty);
            }
        });

        tagFlowPane.getChildren().add(tagTextField);
        tagFlowPane.getChildren().add(removeButton);
    }

    @FXML
    private void ok() {
        if (addTagButton.isDisable()) {
            addTagButton.setDisable(false);
        }
        if (createButton.isDisable()) {
            createButton.setDisable(false);
        }

        containerBorderPane.getChildren().clear();



        if (textLessonsRadioButton.isSelected()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/TextLessonMaker.fxml"));
            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }

            TextLessonMakerController controller = loader.getController();

            textLesson.titleProperty().bind(titleTextField.textProperty());
            controller.bindTextLesson(textLesson.textProperty());

            lesson = textLesson;

        } else if (videoLessonsRadioButton.isSelected()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/VideoLessonMaker.fxml"));

            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }

            VideoLessonMakerController controller = loader.getController();

            videoLesson.titleProperty().bind(titleTextField.textProperty());
            controller.bindPath(videoLesson.pathLocationProperty());

            lesson = videoLesson;
        }

        try {
            containerBorderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void create() {

        //TODO create method of LessonMakerController

        if (lesson instanceof TextLesson) {
            TextLesson textLesson = (TextLesson)lesson;
            System.out.println(textLesson);
        } else if (lesson instanceof VideoLesson) {
            VideoLesson videoLesson = (VideoLesson)lesson;
            System.out.println(videoLesson);
        }


    }

    public static void setStage(Stage s) {
        stage = s;
    }

}
