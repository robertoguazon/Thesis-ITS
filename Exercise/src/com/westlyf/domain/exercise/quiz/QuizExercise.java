package com.westlyf.domain.exercise.quiz;

import com.westlyf.domain.exercise.Exercise;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 13/06/2016.
 */
public class QuizExercise extends Exercise implements Serializable {

    private ArrayList<QuizItem> quizItems = new ArrayList();

    public boolean addItem(QuizType type, String question, ArrayList<String>choices, ArrayList<String> validAnswers) {
        QuizItem quizItem = new QuizItem();
        quizItem.setType(type);
        quizItem.setQuestion(question);
        quizItem.setChoices(choices);
        quizItem.setValidAnswers(validAnswers);

        this.quizItems.add(quizItem);
        return true;
    }

    public boolean addItem(String question, ArrayList<String>choices, ArrayList<String> validAnswers) {
        QuizItem quizItem = new QuizItem();
        quizItem.setType(QuizType.RADIOBUTTON);
        quizItem.setQuestion(question);
        quizItem.setChoices(choices);
        quizItem.setValidAnswers(validAnswers);

        this.quizItems.add(quizItem);
        return true;
    }

    public ArrayList<QuizItem> getQuizItems() {
        return quizItems;
    }

    public ScrollPane getQuizExercise() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVisible(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox parentBox = new VBox();
        parentBox.setVisible(true);
        for (int i = 0; i < quizItems.size(); i++) {
            VBox childBox = quizItems.get(i).getItemBox();
            parentBox.getChildren().add(childBox);
            childBox.prefWidthProperty().bind(parentBox.prefWidthProperty());
        }

        parentBox.prefWidthProperty().bind(scrollPane.prefWidthProperty());
        scrollPane.setContent(parentBox);
        return scrollPane;
    }

    public void addItem(QuizItem quizItem) {
        this.quizItems.add(quizItem);
    }

    public void printQuiz() {
        System.out.println("Quiz: " + title.getValue());

        System.out.print("Tags: ");
        for (String tag: tags) {
            System.out.print(tag + " ");
        }
        System.out.println();

        for (QuizItem quizItem : quizItems) {
            quizItem.printItem();
        }

        System.out.println("---------");
    }

}
