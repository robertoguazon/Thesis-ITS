package com.westlyf.database;

import com.westlyf.domain.lesson.Lesson;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;

import java.sql.*;

/**
 * Created by robertoguazon on 10/08/2016.
 */
public class LessonDatabase {

    //TODO - put all methods for storing and getting data from and into the database

    public static final String CREATE_TEXT_LESSON_TABLE = "CREATE TABLE IF NOT EXISTS text_lesson(" +
            "lid TEXT NOT NULL UNIQUE," +
            "title TEXT PRIMARY KEY NOT NULL," +
            "tags TEXT NOT NULL," +
            "'text' TEXT NOT NULL" +
            ")";
    public static final String CREATE_VIDEO_LESSON_TABLE = "CREATE TABLE IF NOT EXISTS video_lesson(" +
            "lid TEXT NOT NULL UNIQUE," +
            "title TEXT PRIMARY KEY NOT NULL," +
            "tags TEXT NOT NULL," +
            "pathLocation TEXT NOT NULL" +
            ")";

    public static final String INSERT_TEXT_LESSON = "INSERT INTO text_lesson(lid,title,tags,text) VALUES(?,?,?,?)";
    public static final String INSERT_VIDEO_LESSON = "INSERT INTO video_lesson(lid,title,tags,pathLocation) VALUES(?,?,?,?)";

    public static int createTextLessonTable() {

        Connection lessonConn = DatabaseConnection.getLessonConn();
        Statement stmt = null;

        if (lessonConn == null) {
            System.err.println("Error connecting to the lesson database");
            return -1;
        }

        try {

            stmt = lessonConn.createStatement();
            stmt.executeUpdate(CREATE_TEXT_LESSON_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public static int storeData(TextLesson textLesson) {
        Connection lessonConn = DatabaseConnection.getLessonConn();

        if (lessonConn == null) {
            System.err.println("Error connecting to lesson database...");

            return -1;
        }

        PreparedStatement ps = null;
        String lid = textLesson.getLessonId();
        String title = textLesson.getTitle();
        String tags = textLesson.getTagsString();
        String text = textLesson.getText();

        try {

            ps = lessonConn.prepareStatement(INSERT_TEXT_LESSON);

            ps.setString(1,lid);
            ps.setString(2,title);
            ps.setString(3,tags);
            ps.setString(4,text);
            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getErrorCode() == SQLiteError.SQLITE_ERROR) {
                if (ps == null) {
                    System.out.println("Text lesson table does not exist...Creating table...");
                    createTextLessonTable();
                    try {
                        ps = lessonConn.prepareStatement(INSERT_TEXT_LESSON);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                storeData(textLesson);
            } else {
                e.printStackTrace();
            }

            return e.getErrorCode();

        } finally {
            try {
                ps.close();
                lessonConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getErrorCode());
            }
        }

        return 0;
    }

    public static int createVideoLessonTable() {

        Connection lessonConn = DatabaseConnection.getLessonConn();
        Statement stmt = null;

        if (lessonConn == null) {
            System.err.println("Error connecting to the lesson database");
            return -1;
        }

        try {

            stmt = lessonConn.createStatement();
            stmt.executeUpdate(CREATE_VIDEO_LESSON_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public static int storeData(VideoLesson videoLesson) {
        Connection lessonConn = DatabaseConnection.getLessonConn();

        if (lessonConn == null) {
            System.err.println("Error connecting to lesson database...");

            return -1;
        }

        PreparedStatement ps = null;
        String lid = videoLesson.getLessonId();
        String title = videoLesson.getTitle();
        String tags = videoLesson.getTagsString();
        String pathLocation = videoLesson.getPathLocation();

        try {

            ps = lessonConn.prepareStatement(INSERT_VIDEO_LESSON);

            ps.setString(1,lid);
            ps.setString(2,title);
            ps.setString(3,tags);
            ps.setString(4,pathLocation);
            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getErrorCode() == SQLiteError.SQLITE_ERROR) {
                if (ps == null) {
                    System.out.println("video lesson table does not exist...Creating table...");
                    createVideoLessonTable();
                    try {
                        ps = lessonConn.prepareStatement(INSERT_VIDEO_LESSON);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                storeData(videoLesson);
            } else {
                e.printStackTrace();
            }

            return e.getErrorCode();

        } finally {
            try {
                ps.close();
                lessonConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }


}
