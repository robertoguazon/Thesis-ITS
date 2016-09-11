package com.westlyf.user;

import com.westlyf.domain.exercise.practical.PracticalExercise;
import com.westlyf.domain.exercise.quiz.Exam;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.lesson.Lesson;
import com.westlyf.domain.lesson.Level;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by robertoguazon on 08/09/2016.
 */
public class User implements Serializable {
    static final long serialVersionUID = 0x101;

    //TODO insert additional profile details here
    private String name;
    private int age;
    private long birthDate;


    private int textLessonCounter;
    private int videoLessonCounter;
    private int quizExerciseCounter;
    private int practicalExerciseCounter;
    private int examCounter;

    //TODO add variable for storing current opened exercises for each level, update database.

    private History history;

    public User() {
        history = new History();
    }

    public void addHistory(Level level, Lesson lesson, int score, int total) {
        addCount(lesson);
        history.addEvent(level, lesson, score, total);
    }

    private void addCount(Lesson lesson) {
        if (lesson instanceof Exam) examCounter++;
        else if (lesson instanceof TextLesson) textLessonCounter++;
        else if (lesson instanceof VideoLesson) videoLessonCounter++;
        else if (lesson instanceof QuizExercise) quizExerciseCounter++;
        else if (lesson instanceof PracticalExercise) practicalExerciseCounter++;
    }

    public double getLevelExercisePercentage(Level level) {
        return history.getLevelExercisePercentage(level);
    }

    public double getLevelExamPercentage(Level level) {
        return history.getLevelExamPercentage(level);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public int getTextLessonCounter() {
        return textLessonCounter;
    }

    public int getVideoLessonCounter() {
        return videoLessonCounter;
    }


    public int getQuizExerciseCounter() {
        return quizExerciseCounter;
    }

    public int getPracticalExerciseCounter() {
        return practicalExerciseCounter;
    }

    public int getExamCounter() {
        return examCounter;
    }

    public int getTotalLessonViews() {
        return textLessonCounter + videoLessonCounter;
    }

    private int getTotalExerciseViews() {
        return quizExerciseCounter + practicalExerciseCounter;
    }

    @Override
    public String toString() {
        return "User: " + name + "\n" +
                "history: " + history + "\n" +
                "total lesson views: " + getTotalLessonViews() + "\n" +
                "total exercise views: " + getTotalExerciseViews() + "\n" +
                "total exam views: " + examCounter;
    }
}
