package com.westlyf.domain.exercise.quiz;

import com.westlyf.domain.exercise.quiz.gui.ItemGUI;
import com.westlyf.domain.exercise.quiz.gui.QuizGUI;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 19/06/2016.
 */
public class QuizFactory {

    public static QuizExercise createQuiz(String title, ArrayList<StringProperty> tags){
        QuizExercise quiz = new QuizExercise();
        quiz.setTitle(title);
        quiz.setTags(tags);
        return quiz;
    }

    public static ItemGUI createItemGUI (TextArea question, QuizType quizType) {
        ItemGUI itemGUI = new ItemGUI();
        itemGUI.setQuestion(question);
        itemGUI.setQuizType(quizType);

        return itemGUI;
    }

    public static QuizGUI createQuizGUI(TextField title) {
        QuizGUI quizGUI = new QuizGUI();
        quizGUI.setTitle(title);

        return quizGUI;
    }

    public static QuizGUI createQuizGUI(TextField title, ArrayList<TextField> tags) {
        QuizGUI quizGUI = new QuizGUI();
        quizGUI.setTitle(title);
        quizGUI.setTags(tags);

        return quizGUI;
    }

    public static QuizGUI createQuizGUI(TextField title, ArrayList<TextField> tags, ArrayList<ItemGUI> items) {
        QuizGUI quizGUI = new QuizGUI();
        quizGUI.setTitle(title);
        quizGUI.setTags(tags);
        quizGUI.setItems(items);

        return quizGUI;
    }

}
