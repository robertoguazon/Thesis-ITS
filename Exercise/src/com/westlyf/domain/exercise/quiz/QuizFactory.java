package com.westlyf.domain.exercise.quiz;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 19/06/2016.
 */
public class QuizFactory {

    public static QuizExercise createQuiz(String title, ArrayList<String> tags){
        QuizExercise quiz = new QuizExercise();
        quiz.setQuizTitle(title);
        quiz.setTags(tags);
        return quiz;
    }

}
