package com.westlyf.domain.exercise.quiz.gui;

import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.domain.exercise.quiz.QuizType;
import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 24/07/2016.
 */
public class ItemGUI {

    private TextArea question;
    private QuizType quizType;
    private ArrayList<CheckBox> checkBoxesChoices;
    private ToggleGroup choicesToggleGroup;
    private ArrayList<TextArea> validAnswers;
    private ArrayList<TextArea> choices;
    private TextArea explanation;
    private TextArea hint;

    public ItemGUI() {
        checkBoxesChoices = new ArrayList<>();
        choicesToggleGroup = new ToggleGroup();
        validAnswers = new ArrayList<>();
        choices = new ArrayList<>();
    }

    public void setExplanation(TextArea explanation) {
        this.explanation = explanation;
    }

    public void setHint(TextArea hint) {
        if (hint != null) {
            this.hint = hint;
        }
    }

    //question
    public void setQuestion(TextArea question) {
        this.question = question;
    }

    //quiz type
    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    //choices
    public void setChoicesCheckBoxes(ArrayList<CheckBox> checkBoxesChoices) {
        this.checkBoxesChoices = checkBoxesChoices;
    }

    public void addChoice(CheckBox checkBoxChoice) {
        this.checkBoxesChoices.add(checkBoxChoice);
    }

    //answers
    public void setAnswers(ArrayList<TextArea> validAnswers) {
        this.validAnswers = validAnswers;
    }

    public void addValidAnswer(TextArea answer) {
        this.validAnswers.add(answer);
    }

    public void removeCheckBoxChoice(CheckBox checkBoxChoice) {
        this.checkBoxesChoices.remove(checkBoxChoice);
    }

    public void clearCheckBoxChoices() {
        this.checkBoxesChoices.clear();
    }

    public void setChoicesToggleGroup(ToggleGroup choicesToggleGroup) {
        this.choicesToggleGroup = choicesToggleGroup;
    }

    public void completeItems() {

        clearChoices();
        clearValidAnswers();

        switch (quizType) {
            case TEXTFIELD:
            case CHECKBOX:

                for (int i = 0; i < checkBoxesChoices.size(); i++) {
                    CheckBox checkBox = checkBoxesChoices.get(i);
                    TextArea choice = (TextArea) checkBox.getUserData();

                    addChoice(choice);

                    if (checkBox.isSelected()) {
                        addValidAnswer(choice);
                    }
                }

                break;
            case RADIOBUTTON:

                int n = choicesToggleGroup.getToggles().size();

                for (int i = 0; i < n; i++) {
                    Toggle toggle = choicesToggleGroup.getToggles().get(i);

                    TextArea textArea = (TextArea)toggle.getUserData();
                    addChoice(textArea);
                }

                TextArea textArea = (TextArea)choicesToggleGroup.getSelectedToggle().getUserData();
                addValidAnswer(textArea);

                break;
        }
    }

    public void setChoices(ArrayList<TextArea> choices) {
        this.choices = choices;
    }

    public void addChoice(TextArea choice) {
        this.choices.add(choice);
    }

    public void printItem() {
        System.out.println("Item");
        System.out.println("Question: " + question.getText());
        System.out.println("Quiz type: " + quizType);

        for (int i = 0; i < choices.size(); i++) {
            System.out.println("-" + choices.get(i).getText());
        }

        for (int i = 0; i < validAnswers.size(); i++) {
            System.out.println("Answers: " + validAnswers.get(i).getText());
        }
    }

    public void clearChoices() {
        this.choices.clear();
    }

    public void clearValidAnswers() {
        this.validAnswers.clear();
    }

    public QuizItem exportItem() {
        QuizItem quizItem = new QuizItem();

        //set quiz
        quizItem.setQuestion(question.getText());
        quizItem.setType(quizType);

        for (TextInputControl choice: choices) {
            quizItem.addChoice(choice.getText());
        }

        for (TextInputControl validAnswer: validAnswers) {
            quizItem.addValidAnswer(validAnswer.getText());
        }

        quizItem.setExplanation(explanation.getText());
        if (hint != null) {
            quizItem.setHint(hint.getText());
        }

        //validAnswers = correct answers
        //answers = answers by users
        return quizItem;
    }

}
