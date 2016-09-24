package com.westlyf.controller;

import com.westlyf.database.ExamDatabase;
import com.westlyf.database.ExerciseDatabase;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.domain.exercise.quiz.QuizType;
import com.westlyf.domain.exercise.quiz.gui.ItemGUI;
import com.westlyf.domain.exercise.quiz.gui.QuizGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by robertoguazon on 24/09/2016.
 */
public class ExamChoicesOnlyMakerController implements Initializable {

    @FXML
    private TextField examTitleTextField;
    @FXML private Button addTagButton;
    @FXML private RadioButton radioButtonItemType;
    @FXML private RadioButton checkBoxItemType;
    @FXML private RadioButton textFieldItemType;
    @FXML private ToggleGroup itemType;
    @FXML private Button addItemButton;
    @FXML private Button createQuizButton;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox parentBox;

    @FXML private FlowPane tagFlowPane;

    @FXML private VBox itemsParentBox;

    @FXML private ToggleGroup lessonType;
    @FXML private RadioButton quizLessonType;
    @FXML private RadioButton examLessonType;

    private ArrayList<TextField> tagTextFields;

    private ArrayList<String> tags;
    private ArrayList<QuizItem> items;
    private QuizExercise quiz;

    private ArrayList<TextArea> questionTextAreas;
    private ArrayList<QuizType> quizTypeGroup;
    private ArrayList<ArrayList<CheckBox>> checkBoxGroups;
    private ArrayList<ToggleGroup> toggleGroups;

    private QuizGUI quizGUI;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //radioButtonItemType.setSelected(true);
        //checkBoxItemType.setSelected(false);
        //textFieldItemType.setSelected(false);
        //radioButtonItemType.setUserData(QuizType.RADIOBUTTON);
        //checkBoxItemType.setUserData(QuizType.CHECKBOX);
        //textFieldItemType.setUserData(QuizType.TEXTFIELD);

        tagTextFields = new ArrayList<>();
        tags = new ArrayList<>();
        items = new ArrayList<>();
        quiz = new QuizExercise();
        checkBoxGroups = new ArrayList<>();
        toggleGroups = new ArrayList<>();
        questionTextAreas = new ArrayList<>();
        quizTypeGroup = new ArrayList<>();

        //gui holder
        quizGUI = new QuizGUI();
        quizGUI.setTitle(examTitleTextField);
    }

    @FXML
    private void addTag() {
        TextField tagTextField = new TextField();
        tagTextField.setMinSize(100,20);
        tagTextField.setMaxSize(100,20);

        Button removeButton = new Button("x");
        removeButton.setFocusTraversable(false);
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tagFlowPane.getChildren().remove(tagTextField);
                tagFlowPane.getChildren().remove(removeButton);
                tagTextFields.remove(tagTextField);
                quizGUI.removeTag(tagTextField);
            }
        });

        tagFlowPane.getChildren().add(tagTextField);
        tagFlowPane.getChildren().add(removeButton);

        this.tagTextFields.add(tagTextField);

        quizGUI.addTag(tagTextField);
    }

    @FXML
    private void addItem() {
        ItemGUI itemGUI = new ItemGUI();

        VBox itemBox = new VBox();
        Separator bottomLine = new Separator();
        bottomLine.setPadding(new Insets(10));

        Label itemLabel = new Label();
        itemLabel.setText("Item: ");
        Button removeItem = new Button("Remove item");
        HBox itemHeader = new HBox();
        itemHeader.getChildren().addAll(itemLabel,removeItem);

        Label questionLabel = new Label("Question: ");
        TextArea questionTextArea = new TextArea();
        itemGUI.setQuestion(questionTextArea);

        questionTextArea.setWrapText(true);
        questionTextArea.setPrefWidth(500);
        questionTextArea.setPrefHeight(50);
        questionTextArea.setMinWidth(500);
        HBox questionBox = new HBox();
        questionBox.getChildren().addAll(questionLabel,questionTextArea);

        //add quiztype and choicesButton
        HBox addChoiceBox = new HBox();
        Button addChoicesButton = new Button("Add choice");
        addChoiceBox.getChildren().addAll(addChoicesButton);

        VBox choiceParentBox = new VBox();

        Label hintLabel = new Label("Hint: ");
        TextArea hintTextArea = new TextArea();
        itemGUI.setHint(hintTextArea);

        hintTextArea.setWrapText(true);
        hintTextArea.setPrefWidth(500);
        hintTextArea.setPrefHeight(50);
        hintTextArea.setMinWidth(500);
        HBox hintBox = new HBox();
        hintBox.getChildren().addAll(hintLabel,hintTextArea);

        Label explanationLabel = new Label("Explanation: ");
        TextArea explanationTextArea = new TextArea();
        itemGUI.setExplanation(explanationTextArea);

        explanationTextArea.setWrapText(true);
        explanationTextArea.setPrefWidth(500);
        explanationTextArea.setPrefHeight(50);
        explanationTextArea.setMinWidth(500);
        HBox explanationBox = new HBox();
        explanationBox.getChildren().addAll(explanationLabel,explanationTextArea);

        //add all nodes
        itemBox.getChildren().addAll(itemHeader,questionBox,addChoiceBox,choiceParentBox,hintBox,explanationBox,bottomLine);
        itemsParentBox.getChildren().addAll(itemBox);

        //add itemBox to the

        //add listeners
        //add textfields for choices
        //QuizType type = (QuizType)itemType.getSelectedToggle().getUserData();
        QuizType type = QuizType.RADIOBUTTON;
        itemGUI.setQuizType(type);

        ToggleGroup toggleGroup = new ToggleGroup();
        ArrayList<CheckBox> checkBoxGroup = new ArrayList<>();

        //add to arraylists
        questionTextAreas.add(questionTextArea);
        toggleGroups.add(toggleGroup);
        checkBoxGroups.add(checkBoxGroup);
        quizTypeGroup.add(type);
        addChoicesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TextField choice = new TextField();
                Button removeButton = new Button("x");
                removeButton.setFocusTraversable(false);

                RadioButton radioButton = new RadioButton();
                CheckBox checkBox = new CheckBox();

                //add choice and remove button
                HBox choiceFormatBox = new HBox();

                switch (type) {
                    case RADIOBUTTON:
                        radioButton.setToggleGroup(toggleGroup);
                        radioButton.setUserData(choice);
                        choiceFormatBox.getChildren().addAll(radioButton,choice, removeButton);

                        break;

                    case CHECKBOX:
                        checkBox.setSelected(false);
                        checkBox.setUserData(choice);
                        checkBoxGroup.add(checkBox);
                        choiceFormatBox.getChildren().addAll(checkBox,choice, removeButton);

                        break;

                    case TEXTFIELD:
                        checkBox.setSelected(true);
                        checkBox.setDisable(true);
                        checkBox.setUserData(choice);
                        checkBoxGroup.add(checkBox);
                        choiceFormatBox.getChildren().addAll(checkBox,choice, removeButton);

                        break;

                    default:
                        break;
                }

                choiceParentBox.getChildren().add(choiceFormatBox);

                //remove textfield and radiobutton or checkbox when pressed
                removeButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        choiceFormatBox.getChildren().remove(choice);
                        choiceFormatBox.getChildren().remove(removeButton);

                        //remove radio or checkboxes
                        switch (type) {
                            case RADIOBUTTON:
                                radioButton.setToggleGroup(null);
                                choiceFormatBox.getChildren().remove(radioButton);
                                break;
                            case TEXTFIELD:
                            case CHECKBOX:
                                checkBoxGroup.remove(checkBox);
                                choiceFormatBox.getChildren().remove(checkBox);
                                break;

                            default:
                                break;
                        }
                    }
                });
            }
        });

        //removes the item
        removeItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                itemsParentBox.getChildren().remove(itemBox);

                //remove objects on arraylist
                questionTextAreas.remove(questionTextArea);
                quizTypeGroup.remove(type);
                checkBoxGroups.remove(checkBoxGroup);
                toggleGroups.remove(toggleGroup);
                quizGUI.removeItem(itemGUI);
            }
        });

        itemGUI.setChoicesToggleGroup(toggleGroup);
        itemGUI.setChoicesCheckBoxes(checkBoxGroup);
        quizGUI.addItem(itemGUI);
    }

    @FXML
    private void createExam() {
        try {
            QuizExercise quizExercise;
            quizGUI.completeItems();
            quizExercise = quizGUI.exportQuiz();
            quizExercise.makeID();
            if (quizExercise.isValidMaker()) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
                confirmation.setTitle("CONFIRM");
                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        Exam exam = new Exam(quizExercise);
                        ExamDatabase.storeData(exam);
                        System.out.println("data was pushed to database");
                    }
                });
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR, quizExercise.check());
                error.setTitle("INVALID");
                error.show();
            }
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR, e.getMessage());
            error.setTitle("ERROR");
            error.show();
        }


    }


}
