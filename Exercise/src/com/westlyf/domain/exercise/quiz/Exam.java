package com.westlyf.domain.exercise.quiz;

/**
 * Created by robertoguazon on 09/09/2016.
 */
public class Exam extends QuizExercise {

    public Exam() {}

    public Exam(QuizExercise quizExercise) {
        this.copy(quizExercise);
    }

    public void copy(Exam exam) {

        super.copy(exam);
    }

    @Override
    public Exam clone() {
        Exam exam = new Exam();
        exam.copy(this);
        return exam;
    }

    public String toString() {
        return super.toString();
    }
}
