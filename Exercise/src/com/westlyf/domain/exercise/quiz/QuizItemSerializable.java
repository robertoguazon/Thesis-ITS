package com.westlyf.domain.exercise.quiz;

import javafx.beans.property.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 15/08/2016.
 * This class is used for serialization
 */
public class QuizItemSerializable implements Serializable {

    static final long serialVersionUID = 0x011;


    private String question;
    private int points;
    private int pointsPerCorrect;
    private QuizType type;
    private boolean oneAnswer;

    private ArrayList<String> choices;
    private ArrayList<String> validAnswers;
    private ArrayList<String> answers;

    public QuizItemSerializable(QuizItem quizItem) {

        question = quizItem.getQuestion();
        points = quizItem.getPoints();
        pointsPerCorrect = quizItem.getPointsPerCorrect();
        type = quizItem.getType();
        oneAnswer = quizItem.getOneAnswer();

        choices = quizItem.getChoices();
        validAnswers = quizItem.getValidAnswers();
        answers = quizItem.getAnswers();
    }

    public String getQuestion() {
        return question;
    }

    public int getPoints() {
        return points;
    }

    public int getPointsPerCorrect() {
        return pointsPerCorrect;
    }

    public QuizType getType() {
        return type;
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

}
