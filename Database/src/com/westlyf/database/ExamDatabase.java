package com.westlyf.database;

import com.westlyf.domain.exercise.quiz.*;
import com.westlyf.domain.util.LessonUtil;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 09/09/2016.
 */
public class ExamDatabase {

    /**
     * String statements for creating exams tables
     * */
    public static final String
            CREATE_EXAM_TABLE = "CREATE TABLE IF NOT EXISTS exam(" +
            "lid TEXT NOT NULL UNIQUE," +
            "title TEXT PRIMARY KEY NOT NULL," +
            "tags TEXT NOT NULL," +

            "exam BLOB" +
            ")";

    /**
     * String statements for pushing exams to sqlite
     * */
    //PUSHING DATA
    public static final String
            INSERT_EXAM_EXERCISE = "INSERT INTO " +
            "exam(lid,title,tags,exam) VALUES " +
            "(?,?,?,?)";

    /**
     * String statements for pulling exams from sqlite
     * */
    public static final String
            GET_EXAM_USING_ID = "SELECT * FROM exam where lid = ?",
            GET_EXAM_USING_TITLE = "SELECT * FROM exam where title = ?",
            GET_EXAMS_USING_TAGS_EXACTLY = "SELECT * FROM exam WHERE tags = ?",
            GET_EXAMS_USING_TAGS_CONTAINS = "SELECT * FROM exam WHERE tags LIKE ?";

    /**
     * String statements for updating exams
     * */
    public static final String
            UPDATE_EXAM_EXAMOBJECT_USING_TITLE = "UPDATE exam SET exam = ? WHERE title = ?";

    public static int createExamTable() {
        return Database.createTable(Database.EXAM, CREATE_EXAM_TABLE);
    }

    public static int updateExam(final String param, final String STATEMENT, Exam exam) {
        Connection examConn = DatabaseConnection.getExamConn();

        if (examConn == null) {
            System.err.println("Error connecting to exam database...");

            return -1;
        }

        PreparedStatement ps = null;
        try {

            ps = examConn.prepareStatement(STATEMENT);

            ps.setBytes(1,Database.serialize(new QuizExerciseSerializable(exam)));
            ps.setString(2,param);
            ps.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

            return e.getErrorCode();

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (examConn != null) {
                    examConn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getErrorCode());
            }
        }

        return 0;
    }

    public static int storeData(Exam exam) {
        Connection examConn = DatabaseConnection.getExamConn();

        if (examConn == null) {
            System.err.println("Error connecting to exam database...");

            return -1;
        }

        PreparedStatement ps = null;
        String lid = exam.getLessonId();
        String title = exam.getTitle();
        String tags = exam.getTagsString();

        try {

            ps = examConn.prepareStatement(INSERT_EXAM_EXERCISE);

            ps.setString(1,lid);
            ps.setString(2,title);
            ps.setString(3,tags);
            ps.setBytes(4,Database.serialize(new QuizExerciseSerializable(exam)));

            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getErrorCode() == SQLiteError.SQLITE_ERROR) {
                if (ps == null) {
                    System.out.println("Exam table does not exist...Creating table...");
                    createExamTable();
                }
                storeData(exam);
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
                if (ps != null) {
                    ps.close();
                }
                if (examConn != null) {
                    examConn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getErrorCode());
            }
        }

        return 0;
    }

    public static Exam getExam(String param, final String STATEMENT) {
        Connection examConn = DatabaseConnection.getExamConn();

        if (examConn == null) {
            System.err.println("Error connecting to exam database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        Exam exam = new Exam();
        try {
            ps = examConn.prepareStatement(STATEMENT);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (rs.next()) {
                QuizExerciseSerializable quizExerciseSerializable = (QuizExerciseSerializable)Database.deserialize(rs.getBytes("exam"));
                exam = new Exam((new QuizExercise(quizExerciseSerializable)));
                exam.setID(rs.getString("lid"));
                exam.setTitle(rs.getString("title"));
                exam.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));
            } else {
                System.err.println("No exams match with param: " + param);
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
                examConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return exam;
    }

    public static Exam getExamUsingLID(String lid) {
        return getExam(lid,GET_EXAM_USING_ID);
    }

    public static Exam getExamUsingTitle(String title) {
        return getExam(title,GET_EXAM_USING_TITLE);
    }

    public static ArrayList<Exam> getExamsUsingTagsExactly(String tags) {
        Connection examConn = DatabaseConnection.getExamConn();

        if (examConn == null) {
            System.err.println("Error connecting to exam database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Exam> exams = new ArrayList<>();
        try {
            ps = examConn.prepareStatement(GET_EXAMS_USING_TAGS_EXACTLY);
            ps.setString(1,tags);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.err.println("No exams match with param: " + tags);
                return null;
            }

            while (rs.next()) {
                Exam exam = new Exam();

                QuizExerciseSerializable quizExerciseSerializable = (QuizExerciseSerializable)Database.deserialize(rs.getBytes("exam"));
                exam = new Exam((new QuizExercise(quizExerciseSerializable)));

                exam.setID(rs.getString("lid"));
                exam.setTitle(rs.getString("title"));
                exam.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

                exams.add(exam);
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
                examConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return exams;
    }

    public static ArrayList<Exam> getExamsUsingTagsContains(ArrayList<String> tags) {
        if (tags == null || tags.isEmpty()) return null;

        Connection examConn = DatabaseConnection.getExamConn();

        if (examConn == null) {
            System.err.println("Error connecting to exam database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Exam> exams = new ArrayList<>();
        String param = "%" + tags.get(0) + "%";
        try {
            ps = examConn.prepareStatement(GET_EXAMS_USING_TAGS_CONTAINS);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.err.println("No exams contains with param: " + param);
                return null;
            }

            while (rs.next()) {
                Exam exam = new Exam();

                QuizExerciseSerializable quizExerciseSerializable = (QuizExerciseSerializable)Database.deserialize(rs.getBytes("exam"));
                exam = new Exam((new QuizExercise(quizExerciseSerializable)));

                exam.setID(rs.getString("lid"));
                exam.setTitle(rs.getString("title"));
                exam.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

                exams.add(exam);
            }

            //check if every matches contains all the other tags

            for (int i = 0; i < exams.size(); i++) {
                for (int j = 1; j < tags.size(); j++) {
                    QuizExercise match = exams.get(i);
                    if (!match.getTagsString().contains(tags.get(j))) {
                        exams.remove(match);
                        i--;
                        break;
                    }
                }
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
                examConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return exams;
    }

    public static ArrayList<Exam> getExamsUsingTagsContains(String tags) {
        return getExamsUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<Exam> getQuizExercisesUsingPropertyTagsContains(ArrayList<StringProperty> tags) {
        return getExamsUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<Exam> getExamsUsingTagsContains(String ... tags) {
        return getExamsUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static int updateExamUsingTitle(String title, Exam exam) {
        return updateExam(title, UPDATE_EXAM_EXAMOBJECT_USING_TITLE, exam);
    }

}
