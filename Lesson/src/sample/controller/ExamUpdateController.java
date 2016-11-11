package sample.controller;

import com.westlyf.database.ExamDatabase;
import com.westlyf.database.ExerciseDatabase;
import com.westlyf.database.LessonDatabase;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.domain.exercise.quiz.QuizType;
import com.westlyf.utils.Convert;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 11/11/2016.
 */
public class ExamUpdateController implements Initializable {


    @FXML private Button loadButton;
    @FXML private Button updateButton;
    @FXML private FlowPane itemsFlowPane;
    @FXML private TextField loadTextField;
    @FXML private Label statusLabel;

    private Exam currentExam;
    private boolean isQuiz = true;

    private StringProperty statusProperty;

    private Paint red;
    private Paint green;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusProperty = new SimpleStringProperty();
        statusLabel.textProperty().bind(statusProperty);
        red = new Color(0.5,0,0,1);
        green = new Color(0,0.5,0,1);
        statusLabel.setTextFill(green);

    }

    @FXML
    private void load() {
        itemsFlowPane.getChildren().clear();
        statusProperty.set("Loading exam...");
        currentExam = ExamDatabase.getExamUsingTitle(loadTextField.getText());


        if (currentExam != null) {
            statusLabel.setTextFill(green);
            statusProperty.set("Exam loaded");
            display(currentExam);
        } else {
            statusLabel.setTextFill(red);
            statusProperty.set("No match found");
            currentExam = null;
        }
    }

    @FXML
    private void update() {
        System.out.println(currentExam);
        ExamDatabase.updateExamUsingTitle(currentExam.getTitle(), currentExam);
        statusProperty.set("updated exam");
    }

    private void display(QuizExercise quiz) {
        ArrayList<QuizItem> quizItems = quiz.getQuizItems();

        for (int i = 0; i < quizItems.size(); i++) {
            QuizItem quizItem = quizItems.get(i);

            //bind questions to textareas
            Label questionLabel = createLabel("Question: " + (i+1));
            TextArea questionTextArea = createTextArea(550,200);
            questionTextArea.textProperty().bindBidirectional(quizItem.questionProperty());

            //bind choices to textAreas and display choices with radio buttons
            VBox choicesVBox = createChoicesAnswers(quizItem);

            //add to vbox
            VBox vBox = new VBox();
            vBox.getChildren().addAll(questionLabel,questionTextArea);


            //add to flowpane
            Separator separator = new Separator();
            separator.setOrientation(Orientation.HORIZONTAL);
            separator.setPrefSize(400f, 50f);
            itemsFlowPane.getChildren().addAll(vBox, choicesVBox, separator);
        }
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        return label;
    }

    private TextArea createTextArea(float width, float height) {
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefWidth(width);
        textArea.setPrefHeight(height);
        return textArea;
    }

    private VBox createChoicesAnswers(QuizItem quizItem) {
        ArrayList<StringProperty> choices = quizItem.getChoices();
        ArrayList<StringProperty> validAnswers = quizItem.getValidAnswers();

        VBox vBox = new VBox();
        ToggleGroup toggleGroup = new ToggleGroup();

        for (int i = 0; i < choices.size(); i++) {
            StringProperty choice = choices.get(i);
            RadioButton radioButton = new RadioButton();
            radioButton.setUserData(choice);
            radioButton.setToggleGroup(toggleGroup);
            if (isValidAnswer(choice, validAnswers)) {
                radioButton.setSelected(true);
            }
            TextArea textArea = createTextArea(300,80);
            textArea.textProperty().bindBidirectional(choice);

            HBox hBox = new HBox();

            hBox.getChildren().addAll(radioButton, textArea);
            vBox.getChildren().add(hBox);
        }

        if (quizItem.getType().equals(QuizType.RADIOBUTTON)) {

            toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    quizItem.clearValidAnswers();
                    quizItem.addValidAnswer((StringProperty) newValue.getUserData());
                }

            });
        }

        return vBox;
    }

    private boolean isValidAnswer(StringProperty choice, ArrayList<StringProperty> validAnswers) {
        for (int i = 0; i < validAnswers.size(); i++) {
            if (choice.get().equals(validAnswers.get(i).get())) return true;
        }

        return false;
    }

}
