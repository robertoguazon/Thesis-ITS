package com.westlyf.user;

import com.westlyf.domain.exercise.practical.PracticalExercise;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.lesson.Lesson;
import com.westlyf.domain.lesson.Level;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;

import java.util.HashMap;

/**
 * Created by robertoguazon on 08/09/2016.
 */
public class User {
    private int textLessonCounter;
    private int videoLessonCounter;
    private int quizExerciseCounter;
    private int practicalExerciseCounter;
    private int examCounter;

    private History history;

    public void addHistory(Level level, Lesson lesson, int score, int total) {
        if (lesson.getTags().contains("exam")) examCounter++;
        else if (lesson instanceof TextLesson) textLessonCounter++;
        else if (lesson instanceof VideoLesson) videoLessonCounter++;
        else if (lesson instanceof QuizExercise) quizExerciseCounter++;
        else if (lesson instanceof PracticalExercise) practicalExerciseCounter++;


        history.addEvent(level, lesson, score, total);
    }
}
