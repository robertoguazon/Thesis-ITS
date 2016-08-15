package com.westlyf.database;

import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.domain.exercise.quiz.QuizItemSerializable;
import com.westlyf.domain.lesson.TextLesson;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 13/08/2016.
 */
public class ExerciseDatabase {

    //TODO - put all methods for storing and getting data from and into the database

    /**
     * String statements for creating exercise tables
     * */
    public static final String CREATE_EXERCISE_QUIZ_TABLE = "CREATE TABLE IF NOT EXISTS quiz_exercise(" +
            "lid TEXT NOT NULL UNIQUE," +
            "title TEXT PRIMARY KEY NOT NULL," +
            "tags TEXT NOT NULL," +
            "totalItems INT NOT NULL," +
            "totalScore INT NOT NULL," +
            "quizItems BLOB NOT NULL" +
            ")";

    /**
     * String statements for pushing exercises to sqlite
     * */
    //PUSHING DATA
    public static final String INSERT_QUIZ_EXERCISE = "INSERT INTO " +
            "quiz_exercise(lid,title,tags,totalItems,totalScore,quizItems) VALUES " +
            "(?,?,?,?,?,?)";

    /**
     * String statements for pulling exercises from sqlite
     * */
    public static final String
            GET_QUIZ_EXERCISE_USING_ID = "SELECT * FROM quiz_exercise where lid = ?",
            GET_QUIZ_EXERCISE_USING_TITLE = "SELECT * FROM quiz_exercise where title = ?";


    public static int createQuizExerciseTable() {
        return Database.createTable(Database.EXERCISE,CREATE_EXERCISE_QUIZ_TABLE);
    }

    public static int storeData(QuizExercise quizExercise) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return -1;
        }

        PreparedStatement ps = null;
        String lid = quizExercise.getLessonId();
        String title = quizExercise.getTitle();
        String tags = quizExercise.getTagsString();
        int totalItems = quizExercise.getTotalItems();
        int totalScore = quizExercise.getTotalScore();

        ArrayList<QuizItem> origQuizItems = quizExercise.getQuizItems();
        ArrayList<QuizItemSerializable> quizItems = new ArrayList<>();
        for (QuizItem origQuizItem : origQuizItems) {
            quizItems.add(new QuizItemSerializable(origQuizItem));
        }


        try {

            ps = exerciseConn.prepareStatement(INSERT_QUIZ_EXERCISE);

            ps.setString(1,lid);
            ps.setString(2,title);
            ps.setString(3,tags);
            ps.setInt(4,totalItems);
            ps.setInt(5,totalScore);
            ps.setBytes(6,Database.serialize(quizItems));
            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getErrorCode() == SQLiteError.SQLITE_ERROR) {
                if (ps == null) {
                    System.out.println("Quiz exercise table does not exist...Creating table...");
                    createQuizExerciseTable();
                    try {
                        ps = exerciseConn.prepareStatement(INSERT_QUIZ_EXERCISE);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                storeData(quizExercise);
            } else {
                /*
                TODO
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + ": " + e.getMessage());
                alert.show();
                */
                e.printStackTrace();
            }

            return e.getErrorCode();

        } finally {
            try {
                ps.close();
                exerciseConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getErrorCode());
            }
        }

        return 0;
    }
}
