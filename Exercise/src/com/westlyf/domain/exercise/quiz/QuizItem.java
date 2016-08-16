package com.westlyf.domain.exercise.quiz;

import com.westlyf.utils.array.ArrayUtil;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.Label;
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

    //radio button by default
    private QuizType type = QuizType.RADIOBUTTON;

    //if one answer only is allowed use radio button
    //true on default
    //B not sure if needed
    private BooleanProperty oneAnswer = new SimpleBooleanProperty(true);

    public QuizItem() {}

    public QuizItem(QuizItemSerializable quizItemsSerializable) {
        this.question.set(quizItemsSerializable.getQuestion());
        this.choices = quizItemsSerializable.getChoices();
        this.validAnswers = quizItemsSerializable.getValidAnswers();
        this.points.set(quizItemsSerializable.getPoints());
        this.pointsPerCorrect.set(quizItemsSerializable.getPointsPerCorrect());
        this.answers = quizItemsSerializable.getAnswers();
    }

    public boolean isValid() {
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

    public String getQuestion() {
        return question.get();
    }

    public StringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public boolean getOneAnswer() {
        return oneAnswer.get();
    }

    //B not sure if oneAnswer is needed - just in case
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

    //B not checked
    public boolean isCorrect(ArrayList<String> answers) {

        points.set(0);
        for (int i = 0; i < answers.size(); i++) {

            boolean isFound = false;
            for (int j = 0; j < validAnswers.size(); j++) {

                if (answers.get(i).equals(validAnswers.get(j))) {
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                points.set(0);
                return false;
            }
        }

        points.add(getPointsPerCorrect());
        return true;
    }

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
                "\tpoints: " + points.get() + "\n";
    }

}
