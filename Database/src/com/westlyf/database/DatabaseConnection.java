package com.westlyf.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by robertoguazon on 24/07/2016.
 */
public class DatabaseConnection {

    public static Connection lessonConn = null;
    public static Connection userConn = null;
    public static Connection exerciseConn = null;
    public static Connection examConn = null;

    public static final String defaultUserPath = "jdbc:sqlite:resources/database/user.db";
    public static final String defautLessonPath = "jdbc:sqlite:resources/database/lesson.db";
    public static final String defaultExercisePath = "jdbc:sqlite:resources/database/exercise.db";
    public static final String defaultExamPath = "jdbc:sqlite:resources/database/exam.db";

    public static Connection connectUser() {return connectUser(defaultUserPath);}
    public static Connection connectLesson() {return connectLesson(defautLessonPath);}
    public static Connection connectExercise() {return connectExercise(defaultExercisePath); }
    public static Connection connectExam() {return connectExam(defaultExamPath); }

    private static Connection connect(String url, Connection conn) {
        try {
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static Connection connectUser(String url) {
        return connect(url,userConn);
    }

    public static Connection connectLesson(String url) {
        return connect(url, lessonConn);
    }

    public static Connection connectExercise(String url) {return connect(url, exerciseConn); }

    public static Connection connectExam(String url) {return connect(url, examConn); }

    public static Connection getUserConnection() {
        try {
            if (userConn != null && !userConn.isClosed()) {
                return userConn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userConn = connectUser();
        return userConn;
    }

    public static Connection getLessonConn() {
        try {
            if (lessonConn != null && !lessonConn.isClosed()) {
                return lessonConn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lessonConn = connectLesson();
        return lessonConn;
    }

    public static Connection getExerciseConn() {
        try {
            if (exerciseConn != null && !exerciseConn.isClosed()) {
                return exerciseConn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        exerciseConn = connectExercise();
        return exerciseConn;
    }

    public static Connection getExamConn() {
        try {
            if (examConn != null && !examConn.isClosed()) {
                return examConn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        examConn = connectExam();
        return examConn;
    }
    //TODO
}
