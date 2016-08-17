package com.westlyf.database;

import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.domain.exercise.quiz.QuizItemSerializable;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.util.LessonUtil;
import javafx.scene.control.Alert;

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
            GET_QUIZ_EXERCISE_USING_TITLE = "SELECT * FROM quiz_exercise where title = ?",
            GET_QUIZ_EXERCISES_USING_TAGS_EXACTLY = "SELECT * FROM quiz_exercise where tags = ?";


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

    public static QuizExercise getQuizExercise(String param, final String STATEMENT) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        QuizExercise quizExercise = new QuizExercise();
        try {
            ps = exerciseConn.prepareStatement(STATEMENT);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (rs.next()) {
                quizExercise.setID(rs.getString("lid"));
                quizExercise.setTitle(rs.getString("title"));
                quizExercise.setTags(LessonUtil.tagsToStringProperty(rs.getString("tags")));

                quizExercise.setTotalItems(rs.getInt("totalItems"));
                quizExercise.setTotalScore(rs.getInt("totalScore"));

                ArrayList<QuizItemSerializable> quizItemsSerializable = (ArrayList<QuizItemSerializable>)Database.deserialize(rs.getBytes("quizItems"));
                ArrayList<QuizItem> quizItems = new ArrayList<>();

                for (QuizItemSerializable quizItemSerializable : quizItemsSerializable) {
                    quizItems.add(new QuizItem(quizItemSerializable));
                }

                quizExercise.setQuizItems(quizItems);
            } else {
                System.err.println("No exercises match with param: " + param);
                return null;
            }

        } catch (SQLException e) {

            /* TODO
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + ": " + e.getMessage());
            alert.show();
            */
            e.printStackTrace();
        } finally {

            try {
                ps.close();
                exerciseConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return quizExercise;
    }

    public static QuizExercise getQuizExerciseUsingLID(String lid) {
        return getQuizExercise(lid,GET_QUIZ_EXERCISE_USING_ID);
    }

    public static QuizExercise getQuizExerciseUsingTitle(String title) {
        return getQuizExercise(title,GET_QUIZ_EXERCISE_USING_TITLE);
    }

    public static ArrayList<QuizExercise> getQuizExercisesUsingTagsExactly(String tags) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        QuizExercise quizExercise = new QuizExercise();
        ArrayList<QuizExercise> quizExercises = new ArrayList<>();
        try {
            ps = exerciseConn.prepareStatement(GET_QUIZ_EXERCISES_USING_TAGS_EXACTLY);
            ps.setString(1,tags);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.err.println("No exercises match with param: " + tags);
                return null;
            }

            while (rs.next()) {
                quizExercise.setID(rs.getString("lid"));
                quizExercise.setTitle(rs.getString("title"));
                quizExercise.setTags(LessonUtil.tagsToStringProperty(rs.getString("tags")));

                quizExercise.setTotalItems(rs.getInt("totalItems"));
                quizExercise.setTotalScore(rs.getInt("totalScore"));

                ArrayList<QuizItemSerializable> quizItemsSerializable = (ArrayList<QuizItemSerializable>)Database.deserialize(rs.getBytes("quizItems"));
                ArrayList<QuizItem> quizItems = new ArrayList<>();

                for (QuizItemSerializable quizItemSerializable : quizItemsSerializable) {
                    quizItems.add(new QuizItem(quizItemSerializable));
                }

                quizExercise.setQuizItems(quizItems);
                quizExercises.add(quizExercise);
            }

        } catch (SQLException e) {

            /* TODO
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + ": " + e.getMessage());
            alert.show();
            */
            e.printStackTrace();
        } finally {

            try {
                ps.close();
                exerciseConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return quizExercises;
    }
}
