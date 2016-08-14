package com.westlyf.database;

import com.westlyf.domain.lesson.Lesson;
import com.westlyf.domain.lesson.TextLesson;
import com.westlyf.domain.lesson.VideoLesson;
import com.westlyf.domain.util.LessonUtil;
import javafx.scene.control.Alert;

import java.sql.*;

/**
 * Created by robertoguazon on 10/08/2016.
 */
public class LessonDatabase {

    //TODO - put all methods for storing and getting data from and into the database

    //CREATING TABLES
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

    //PUSHING DATA
    public static final String INSERT_TEXT_LESSON = "INSERT INTO text_lesson(lid,title,tags,text) VALUES(?,?,?,?)";
    public static final String INSERT_VIDEO_LESSON = "INSERT INTO video_lesson(lid,title,tags,pathLocation) VALUES(?,?,?,?)";

    //PULLING DATA
    public static final String GET_TEXT_LESSON_USING_LID = "SELECT * FROM text_lesson WHERE lid = ?";
    public static final String GET_VIDEO_LESSON_USING_LID = "SELECT * FROM video_lesson WHERE lid = ?";

    public static final String GET_TEXT_LESSON_USING_TITLE = "SELECT * FROM text_lesson WHERE title = ?";
    public static final String GET_VIDEO_LESSON_USING_TITLE = "SELECT * FROM video_lesson WHERE title = ?";

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
                lessonConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public static TextLesson getTextLesson(String param, final String STATEMENT) {
        Connection lessonConn = DatabaseConnection.getLessonConn();

        if (lessonConn == null) {
            System.err.println("Error connecting to lesson database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        TextLesson textLesson = new TextLesson();
        try {
            ps = lessonConn.prepareStatement(STATEMENT);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (rs.next()) {
                textLesson.setID(rs.getString("lid"));
                textLesson.setTitle(rs.getString("title"));
                textLesson.setTags(LessonUtil.tagsToStringProperty(rs.getString("tags")));
                textLesson.setText(rs.getString("text"));
            } else {
                System.err.println("No text lessons match with param: " + param);
                return null;
            }

        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + ": " + e.getMessage());
            alert.show();
            e.printStackTrace();
        } finally {

            try {
                ps.close();
                lessonConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return textLesson;
    }

    public static VideoLesson getVideoLesson(String param, final String STATEMENT) {
        Connection lessonConn = DatabaseConnection.getLessonConn();

        if (lessonConn == null) {
            System.err.println("Error connecting to lesson database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        VideoLesson videoLesson = new VideoLesson();
        try {
            ps = lessonConn.prepareStatement(STATEMENT);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (rs.next()) {
                videoLesson.setID(rs.getString("lid"));
                videoLesson.setTitle(rs.getString("title"));
                videoLesson.setTags(LessonUtil.tagsToStringProperty(rs.getString("tags")));
                videoLesson.setPathLocation(rs.getString("pathLocation"));
            } else {
                System.err.println("No video lessons match with param: " + param);
                return null;
            }

        } catch (SQLException e) {

            /*
                TODO
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + ": " + e.getMessage());
                alert.show();
                */
            e.printStackTrace();
        } finally {

            try {
                ps.close();
                lessonConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        return videoLesson;
    }

    public static TextLesson getTextLessonUsingLID(String lid) {
        return getTextLesson(lid, GET_TEXT_LESSON_USING_LID);
    }

    public static TextLesson getTextLessonUsingTitle(String title) {
        return getTextLesson(title, GET_TEXT_LESSON_USING_TITLE);
    }

    public static VideoLesson getVideoLessonUsingLID(String lid) {
        return getVideoLesson(lid, GET_VIDEO_LESSON_USING_LID);
    }

    public static VideoLesson getVideoLessonUsingTitle(String title) {
        return getVideoLesson(title, GET_VIDEO_LESSON_USING_TITLE);
    }

}
