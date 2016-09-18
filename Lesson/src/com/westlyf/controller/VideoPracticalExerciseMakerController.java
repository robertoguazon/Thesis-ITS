package com.westlyf.controller;

import com.westlyf.database.ExerciseDatabase;
import com.westlyf.domain.exercise.mix.Using;
import com.westlyf.domain.exercise.mix.VideoPracticalExercise;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 18/09/2016.
 */
public class VideoPracticalExerciseMakerController implements Initializable{

    @FXML private TextField titleTextField;
    @FXML private Button addTagButton;
    @FXML private FlowPane tagFlowPane;
    @FXML private Button createButton;

    @FXML private ComboBox<Using> videoLessonUsingComboBox;
    @FXML private TextField videoLessonTitleTextField;
    @FXML private TextField videoLessonIdTextField;

    @FXML private ComboBox<Using> practicalExerciseUsingComboBox;
    @FXML private TextField practicalExerciseTitleTextField;
    @FXML private TextField practicalExerciseIdTextField;


    private VideoPracticalExercise videoPracticalExercise;

    private ArrayList<StringProperty> tags;
    private BorderPane root;

    public static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tags = new ArrayList<>();
        addTagButton.setDisable(false);
        createButton.setDisable(false);
        videoPracticalExercise = new VideoPracticalExercise();

        videoPracticalExercise.titleProperty().bind(titleTextField.textProperty());

        videoPracticalExercise.videoLessonTitleProperty().bind(videoLessonTitleTextField.textProperty());
        videoPracticalExercise.videoLessonIdProperty().bind(videoLessonIdTextField.textProperty());

        videoPracticalExercise.practicalExerciseTitleProperty().bind(practicalExerciseTitleTextField.textProperty());
        videoPracticalExercise.practicalExerciseIdProperty().bind(practicalExerciseIdTextField.textProperty());

        videoLessonUsingComboBox.getItems().addAll(
                Using.ID,
                Using.TITLE
        );

        videoLessonUsingComboBox.valueProperty().addListener (
                new ChangeListener<Using>() {
                    @Override
                    public void changed(ObservableValue<? extends Using> observable, Using oldValue, Using newValue) {
                        videoPracticalExercise.setVideoLessonUsing(newValue);
                        switch (newValue) {
                            case ID:
                                videoLessonTitleTextField.clear();
                                videoLessonTitleTextField.setDisable(true);
                                videoLessonIdTextField.setDisable(false);

                                break;
                            case TITLE:
                                videoLessonIdTextField.clear();
                                videoLessonIdTextField.setDisable(true);
                                videoLessonTitleTextField.setDisable(false);
                                break;
                        }
                    }

                });

        practicalExerciseUsingComboBox.getItems().addAll(
                Using.ID,
                Using.TITLE
        );

        practicalExerciseUsingComboBox.valueProperty().addListener (
                new ChangeListener<Using>() {
                    @Override
                    public void changed(ObservableValue<? extends Using> observable, Using oldValue, Using newValue) {
                        videoPracticalExercise.setPracticalExerciseUsing(newValue);
                        switch (newValue) {
                            case ID:
                                practicalExerciseTitleTextField.clear();
                                practicalExerciseTitleTextField.setDisable(true);
                                practicalExerciseIdTextField.setDisable(false);
                                break;
                            case TITLE:
                                practicalExerciseIdTextField.clear();
                                practicalExerciseIdTextField.setDisable(true);
                                practicalExerciseTitleTextField.setDisable(false);
                                break;
                        }
                    }

                });

        videoLessonUsingComboBox.setValue(Using.TITLE);
        practicalExerciseUsingComboBox.setValue(Using.TITLE);
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
        videoPracticalExercise.setTags(tags);
        videoPracticalExercise.setTags(tags);

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
    private void create() {

        //TODO create method of LessonMakerController

        videoPracticalExercise.makeID();
        if (videoPracticalExercise != null && videoPracticalExercise.isValidMaker()) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, videoPracticalExercise.toString());
            confirmation.setTitle("CONFIRM");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ExerciseDatabase.storeData(videoPracticalExercise);
                    System.out.println("data was pushed to database");
                }
            });
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR, videoPracticalExercise.check());
            error.setTitle("INVALID");
            error.show();
        }
    }

    public static void setStage(Stage s) {
        stage = s;
    }

}
