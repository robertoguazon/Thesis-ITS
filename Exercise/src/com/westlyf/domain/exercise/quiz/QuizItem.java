package com.westlyf.domain.exercise.quiz;

import com.westlyf.utils.array.ArrayUtil;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 13/06/2016.
 */
public class QuizItem implements Serializable {

    private StringProperty question = new SimpleStringProperty();
    //private IntegerProperty questionId = new SimpleIntegerProperty(-1);
    private ArrayList<String> choices = new ArrayList<>();
    private ArrayList<String> validAnswers = new ArrayList<>();
    private IntegerProperty points = new SimpleIntegerProperty();
    private IntegerProperty pointsPerCorrect = new SimpleIntegerProperty(1);
    private ArrayList<String> answers = new ArrayList<>();
    private StringProperty explanation = new SimpleStringProperty();

    private StringProperty hint = new SimpleStringProperty();

    //radio button by default
    private QuizType type = QuizType.RADIOBUTTON;

    //if one answer only is allowed use radio button
    //true on default
    //B not sure if needed
    private BooleanProperty oneAnswer = new SimpleBooleanProperty(true);

    public QuizItem() {}

    public String getHint() {
        return hint.get();
    }

    public StringProperty hintProperty() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint.set(hint);
    }

    //TODO check if the evaluation of answers are efficient
    public boolean isCorrect() {
        return isCorrect(this.answers);
    }

    //TODO - check if efficient
    public boolean isCorrect(ArrayList<String> answers) {

        switch (type) {
            case TEXTFIELD:
                String answer = answers.get(0).trim().toLowerCase();
                for (int i = 0; i < validAnswers.size(); i++) {
                    if (answer.equals(validAnswers.get(i).trim().toLowerCase())) {
                        return true;
                    }
                }
                return false;

            case CHECKBOX:
            case RADIOBUTTON:
                if (answers.size() != validAnswers.size()) {
                    return false;
                }
                for (int i = 0; i < validAnswers.size(); i++) {
                    if (!answers.contains(validAnswers.get(i))) {
                        return false;
                    }
                }
                return true;

            default:
                return false;
        }
    }



    public QuizItem(QuizItemSerializable quizItemsSerializable) {
        this.question.set(quizItemsSerializable.getQuestion());
        this.points.set(quizItemsSerializable.getPoints());
        this.pointsPerCorrect.set(quizItemsSerializable.getPointsPerCorrect());
        this.type = quizItemsSerializable.getType();

        this.choices = quizItemsSerializable.getChoices();
        this.validAnswers = quizItemsSerializable.getValidAnswers();
        this.answers = quizItemsSerializable.getAnswers();
        this.explanation.set(quizItemsSerializable.getExplanation());
        this.hint.set(quizItemsSerializable.getHint());
    }

    public boolean isValidMaker() {
        if (question == null || question.get().equals("")) return false;
        if (choices == null || choices.isEmpty()) return false;
        if (validAnswers == null || validAnswers.isEmpty())  return false;
        if (points == null) return false;
        if (pointsPerCorrect == null) return false;

        //TODO - fix points

        return true;
    }

    public boolean isValidAnsweredFormat() {
        if (question == null || question.get().equals("")) return false;
        if (choices == null || choices.isEmpty()) return false;
        if (validAnswers == null || validAnswers.isEmpty())  return false;
        if (points == null) return false;
        if (pointsPerCorrect == null) return false;
        if (answers == null || answers.isEmpty()) return false;

        //TODO - for points system
        return true;
    }

    public String check() {
        return "\n\tItem:\n" +
                "\tquestion: " + ((question == null || question.get().equals("")) ? "empty" : question.get()) + "\n" +
                "\tchoices: " + ((choices == null || choices.isEmpty()) ? "empty" : choices.toString()) + "\n" +
                "\tvalidAnswers: " + ((validAnswers == null || validAnswers.isEmpty()) ? "empty" : validAnswers.size())  + "\n" +
                "\tpoints per correct: " + ((pointsPerCorrect == null || pointsPerCorrect.get() == 0) ?
                    "empty" : pointsPerCorrect.get()) + "\n" +
                "\tanswers: " + ((answers == null || answers.isEmpty()) ? "empty" : answers.size()) + "\n" +
                "\tpoints: " + ((points == null) ? "empty" : points.get());
    }

    public String getExplanation() {
        return explanation.get();
    }

    public void setExplanation(String explanation) {
        this.explanation.set(explanation);
    }

    public StringProperty explanationProperty() {
        return explanation;
    }

    public String getQuestion() {
        return question.get();
    }

    public StringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    /* TODO - delete, not sure if oneAnswer is needed - just in case
    public boolean getOneAnswer() {
        return oneAnswer.get();
    }

    public BooleanProperty oneAnswerProperty() {
        return oneAnswer;
    }

    public void setOneAnswer(boolean oneAnswer) {
        this.oneAnswer.set(oneAnswer);
        if (oneAnswer) {
            this.type = QuizType.RADIOBUTTON;
        } else {
            this.type = QuizType.CHECKBOX;
        }
    }
    */

    public void setPointsPerCorrect (int value) {
        this.pointsPerCorrect.set(value);
    }

    public int getPointsPerCorrect() {
        return this.pointsPerCorrect.get();
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public void setValidAnswers(ArrayList<String> validAnswers) {
        this.validAnswers = validAnswers;
    }

    public void setType(QuizType type) {
        this.type = type;
    }

    public QuizType getType() {
        return this.type;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public ArrayList<String> getValidAnswers() {
        return validAnswers;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public int getPoints() {
        return points.get();
    }

    public VBox getItemBox() {
        VBox box = new VBox();

        Label questionLabel = new Label();
        questionLabel.setVisible(true);
        questionLabel.setWrapText(true);
        questionLabel.textProperty().bind(question);

        box.getChildren().add(questionLabel);

        switch (this.type) {

            case RADIOBUTTON:
                ArrayList<RadioButton> buttons = getChoicesRadioButtons();
                ToggleGroup choicesToggleGroup = getToggleGroup(buttons);

                choicesToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                    @Override
                    public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                        answers.clear();
                        answers.add((String)newValue.getUserData());
                    }

                });

                for (int i = 0; i < buttons.size(); i++) {
                    box.getChildren().add(buttons.get(i));
                }

                break;
            case CHECKBOX:
                ArrayList<CheckBox> choicesCheckBoxes = getChoicesCheckBoxes();
                for (int i = 0; i < choicesCheckBoxes.size(); i++) {

                    CheckBox choice = choicesCheckBoxes.get(i);
                    choice.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if (newValue) {
                                answers.add(choice.getText());
                            } else {
                                if (answers.contains(choice.getText())) {
                                    answers.remove(choice.getText());
                                }
                            }
                        }
                    });

                    box.getChildren().add(choice);

                }
                break;
            case TEXTFIELD:
                TextField blankTextField = new TextField();
                blankTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    answers.clear();
                    answers.add(newValue);
                });
                box.getChildren().add(blankTextField);
                break;

            default:
                break;
        }



        Separator line = new Separator();
        line.prefWidthProperty().bind(box.widthProperty());
        box.getChildren().add(line);

        return box;
    }

    public ArrayList<CheckBox> getChoicesCheckBoxes() {
        ArrayList<CheckBox> choicesArrayList = new ArrayList<>();
        ArrayList<String> randomizedChoices = ArrayUtil.randomizeArrayList(this.choices);

        for (int i = 0; i < randomizedChoices.size(); i++) {
            CheckBox choice = new CheckBox();
            choice.setVisible(true);
            choice.setText(randomizedChoices.get(i));
            choice.setIndeterminate(false);
            choicesArrayList.add(choice);
        }

        return choicesArrayList;
    }

    public ArrayList<RadioButton> getChoicesRadioButtons() {
        ArrayList<RadioButton> choicesArrayList = new ArrayList<>();
        ArrayList<String> randomizedChoices = ArrayUtil.randomizeArrayList(this.choices);
        for (int i = 0; i < randomizedChoices.size(); i++) {
            RadioButton choice = new RadioButton();
            choice.setText(randomizedChoices.get(i));
            choice.setUserData(randomizedChoices.get(i));
            choice.setVisible(true);
            choice.setSelected(false);

            choicesArrayList.add(choice);
        }

        return choicesArrayList;
    }

    public ToggleGroup getToggleGroup(ArrayList<RadioButton> buttons) {
        ToggleGroup group = new ToggleGroup();

        for (int i = 0; i < buttons.size(); i++) {
            RadioButton choice = buttons.get(i);
            choice.setToggleGroup(group);
        }

        return group;
    }

    public void addChoice(String choice) {
        this.choices.add(choice);
    }

    public void addValidAnswer(String validAnswer) {
        this.validAnswers.add(validAnswer);
    }

    public void printItem() {
        System.out.println("Item:");
        System.out.println("question: " + question.getValue());

        System.out.println("choices: ");
        for (String choice: choices) {
            System.out.println("-" + choice);
        }

        System.out.println("answers: ");
        for (String validAnswer: validAnswers) {
            System.out.println("-" + validAnswer);
        }

    }

    @Override
    public String toString() {
        String choicesString = "\tChoices:\n";
        for (int i = 0; i < choices.size(); i++) {
            choicesString += "\t\t- " + choices.get(i) + "\n";
        }

        String validAnswersString = "\tvalid answers:\n";
        for (int i = 0; i < validAnswers.size(); i++) {
            validAnswersString += "\t\t* " + validAnswers.get(i) + "\n";
        }

        String answersString = "\tanswers:\n";
        for (int i = 0; i < answers.size(); i++) {
            answersString += "\t\tO " + answers.get(i) + "\n";
        }

        return  "\nitem: " + "\n" +
                "\tquestion: " + question.get() + "\n" +
                choicesString +
                validAnswersString +
                answersString +
                "\tpoints per correct: " + pointsPerCorrect.get() + "\n" +
                "\tpoints: " + points.get() + "\n" +
                "\texplanation: " + explanation.get() + "\n" +
                "\thint: " + hint.get();
    }

    public VBox getExamChoicesOnlyBox() {
        return getExamChoicesOnlyBox(0,0,false);
    }

    public VBox getExamChoicesOnlyBox(float prefWidth, float prefHeight) {
        return getExamChoicesOnlyBox(prefWidth,prefHeight,true);
    }

    public VBox getExamChoicesOnlyBox(float prefWidth, float prefHeight, boolean setPref) {
        VBox box = new VBox();

        ArrayList<RadioButton> buttons = getChoicesRadioButtons();

        for (int i = 0; i < buttons.size(); i++) {
            RadioButton radioButton = buttons.get(i);
            String choice = (String) radioButton.getUserData();
            if (answers.contains(choice)) {
                radioButton.setSelected(true);
            }

            if (setPref) {
                radioButton.setPrefWidth(prefWidth);
                radioButton.setPrefHeight(prefHeight);
            }
        }

        ToggleGroup choicesToggleGroup = getToggleGroup(buttons);

        choicesToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                answers.clear();
                answers.add((String)newValue.getUserData());
            }

        });

        for (int i = 0; i < buttons.size(); i++) {
            box.getChildren().add(buttons.get(i));
        }

        return box;
    }
}
