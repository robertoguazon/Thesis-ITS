package com.westlyf.domain.exercise.quiz;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 09/09/2016.
 */
public class QuizExerciseSerializable implements Serializable {
    static final long serialVersionUID = 0x012;

    private ArrayList<QuizItemSerializable> quizItems = new ArrayList<>();

    public QuizExerciseSerializable(QuizExercise quizExercise) {
        ArrayList<QuizItem> quizItemsB = quizExercise.getQuizItems();
        for(int i = 0; i < quizItemsB.size(); i++) {
            quizItems.add(new QuizItemSerializable(quizItemsB.get(i)));
        }
    }

    public ArrayList<QuizItemSerializable> getQuizItems() {
        return quizItems;
    }



}
