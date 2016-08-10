package com.westlyf.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by robertoguazon on 24/07/2016.
 */
public class DatabaseConnection {

    public static Connection lessonConn = null;
    public static Connection userConn = null;
    public static final String defaultUserPath = "jdbc:sqlite:resources/database/user.db";
    public static final String defautLessonPath = "jdbc:sqlite:resources/database/lesson.db";

    public static Connection connectUser() {return connectUser(defaultUserPath);}
    public static Connection connectLesson() {return connectLesson(defautLessonPath);}

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

    public static Connection getUserConnection() {
        if (userConn != null) {
            return userConn;
        }

        userConn = connectUser();
        return userConn;
    }

    public static Connection getLessonConn() {
        if (lessonConn != null) {
            return lessonConn;
        }

        lessonConn = connectLesson();
        return lessonConn;
    }

}
