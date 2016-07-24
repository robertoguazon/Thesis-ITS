package com.westlyf.domain.exercise.quiz.gui;

import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizFactory;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 24/07/2016.
 */
public class QuizGUI {

    private TextField title;
    private ArrayList<TextField> tags;
    private ArrayList<ItemGUI> items;

    public QuizGUI() {
        tags = new ArrayList<>();
        items = new ArrayList<>();
    }

    //tags
    public void setTags(ArrayList<TextField> tags) {
        this.tags = tags;
    }

    public void addTag(TextField tag) {
        this.tags.add(tag);
    }

    //items
    public void setItems(ArrayList<ItemGUI> items) {
        this.items = items;
    }

    public void addItem(ItemGUI item) {
        this.items.add(item);
    }

    public void setTitle(TextField title) {
        this.title = title;
    }

    public void removeTag(TextField tag) {
        this.tags.remove(tag);
    }

    public void clearTags() {
        this.tags.clear();
    }

    public void removeItem(ItemGUI itemGUI) {
        this.items.remove(itemGUI);
    }

    public void clearItems() {
        this.items.clear();
    }

    public void completeItems() {
        for (ItemGUI item: items) {
            item.completeItems();
        }
    }

    public void printQuiz() {
        System.out.println("Quiz: " + title.getText());

        System.out.print("Tags: ");
        for (int i = 0; i < tags.size(); i++) {
            System.out.print(tags.get(i).getText() + " ");
        }
        System.out.println();

        for (int i = 0; i < items.size(); i++) {
            items.get(i).printItem();
        }

        System.out.println("------------");
    }

    public QuizExercise exportQuiz(){
        QuizExercise quizExercise = new QuizExercise();

        //set title
        quizExercise.setQuizTitle(title.getText());
        //set tag
        for (TextField tag: tags) {
            quizExercise.addTag(tag.getText());
        }

        //set choices and answers
        for (ItemGUI item: items) {
            quizExercise.addItem(item.exportItem());
        }

        return quizExercise;
    }

}
