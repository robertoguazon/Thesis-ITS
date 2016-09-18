package com.westlyf.database;

import com.westlyf.domain.exercise.mix.Using;
import com.westlyf.domain.exercise.mix.VideoPracticalExercise;
import com.westlyf.domain.exercise.practical.*;
import com.westlyf.domain.exercise.quiz.QuizExercise;
import com.westlyf.domain.exercise.quiz.QuizItem;
import com.westlyf.domain.exercise.quiz.QuizItemSerializable;
import com.westlyf.domain.util.LessonUtil;
import javafx.beans.property.StringProperty;

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
    public static final String
            CREATE_EXERCISE_QUIZ_TABLE = "CREATE TABLE IF NOT EXISTS quiz_exercise(" +
                    "lid TEXT NOT NULL UNIQUE," +
                    "title TEXT PRIMARY KEY NOT NULL," +
                    "tags TEXT NOT NULL," +

                    "totalItems INT NOT NULL," +
                    "totalScore INT NOT NULL," +

                    "quizItems BLOB NOT NULL" +
                    ")",

            CREATE_EXERCISE_PRACTICAL_TABLE = "CREATE TABLE IF NOT EXISTS practical_exercise(" +
                    "lid TEXT NOT NULL UNIQUE," +
                    "title TEXT PRIMARY KEY NOT NULL," +
                    "tags TEXT NOT NULL," +

                    "totalItems INT NOT NULL," +
                    "totalScore INT NOT NULL," +

                    "instructions TEXT NOT NULL," +
                    "code TEXT NOT NULL," +
                    "className TEXT NOT NULL," +
                    "methodName TEXT NOT NULL," +

                    "printValidator TEXT," +
                    "mustMatch INT," +

                    "returnValidators BLOB," +
                    "returnType BLOB," +
                    "parametersTypes BLOB," +
                    "explanation TEXT," +

                    "practicalType BLOB NOT NULL" +
                    ")",
            CREATE_EXERCISE_VIDEO_PRACTICAL_TABLE = "CREATE TABLE IF NOT EXISTS video_practical_exercise(" +
                    "lid TEXT NOT NULL UNIQUE," +
                    "title TEXT PRIMARY KEY NOT NULL," +
                    "tags TEXT NOT NULL," +

                    "totalItems INT NOT NULL," +
                    "totalScore INT NOT NULL," +

                    "videoLessonTitle TEXT," +
                    "videoLessonId TEXT," +
                    "practicalExerciseTitle TEXT," +
                    "practicalExerciseId TEXT," +

                    "videoLessonUsing BLOB NOT NULL," +
                    "practicalExerciseUsing BLOB NOT NULL" +
                    ")";

    /**
     * String statements for pushing exercises to sqlite
     * */
    //PUSHING DATA
    public static final String
            INSERT_QUIZ_EXERCISE = "INSERT INTO " +
                    "quiz_exercise(lid,title,tags,totalItems,totalScore,quizItems) VALUES " +
                    "(?,?,?,?,?,?)",
            INSERT_PRACTICAL_EXERCISE = "INSERT INTO " +
                    "practical_exercise(lid,title,tags, totalItems,totalScore, instructions,code,className,methodName, " +
                    "printValidator,mustMatch, returnValidators,returnType,parametersTypes,explanation, practicalType) VALUES " +
                    "(?,?,?, ?,?, ?,?,?,?, ?,?, ?,?,?,?, ?)",
            INSERT_VIDEO_PRACTICAL_EXERCISE = "INSERT INTO " +
                    "video_practical_exercise(" +
                    "lid,title,tags, totalItems,totalScore,  " +
                    "videoLessonTitle, videoLessonId," +
                    "practicalExerciseTitle, practicalExerciseId," +
                    "videoLessonUsing, practicalExerciseUsing) VALUES " +
                    "(?,?,?,?,?, ?,?, ?,?, ?,?)";

    /**
     * String statements for pulling exercises from sqlite
     * */
    public static final String
            GET_QUIZ_EXERCISE_USING_ID = "SELECT * FROM quiz_exercise where lid = ?",
            GET_QUIZ_EXERCISE_USING_TITLE = "SELECT * FROM quiz_exercise where title = ?",
            GET_QUIZ_EXERCISES_USING_TAGS_EXACTLY = "SELECT * FROM quiz_exercise WHERE tags = ?",
            GET_QUIZ_EXERCISES_USING_TAGS_CONTAINS = "SELECT * FROM quiz_exercise WHERE tags LIKE ?",

            GET_PRACTICAL_EXERCISE_USING_ID = "SELECT * FROM practical_exercise where lid = ?",
            GET_PRACTICAL_EXERCISE_USING_TITLE = "SELECT * FROM practical_exercise where title = ?",
            GET_PRACTICAL_EXERCISES_USING_TAGS_EXACTLY = "SELECT * FROM practical_exercise WHERE tags = ?",
            GET_PRACTICAL_EXERCISES_USING_TAGS_CONTAINS = "SELECT * FROM practical_exercise WHERE tags LIKE ?",

            GET_VIDEO_PRACTICAL_EXERCISE_USING_ID = "SELECT * FROM video_practical_exercise where lid = ?",
            GET_VIDEO_PRACTICAL_EXERCISE_USING_TITLE = "SELECT * FROM video_practical_exercise where title = ?",
            GET_VIDEO_PRACTICAL_EXERCISES_USING_TAGS_EXACTLY = "SELECT * FROM video_practical_exercise WHERE tags = ?",
            GET_VIDEO_PRACTICAL_EXERCISES_USING_TAGS_CONTAINS = "SELECT * FROM video_practical_exercise WHERE tags LIKE ?";




    public static int createQuizExerciseTable() {
        return Database.createTable(Database.EXERCISE,CREATE_EXERCISE_QUIZ_TABLE);
    }

    public static int createPracticalExerciseTable() {
        return Database.createTable(Database.EXERCISE,CREATE_EXERCISE_PRACTICAL_TABLE);
    }

    public static int createVideoPracticalExerciseTable() {
        return Database.createTable(Database.EXERCISE,CREATE_EXERCISE_VIDEO_PRACTICAL_TABLE);
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
                if (ps != null) {
                    ps.close();
                }
                if (exerciseConn != null) {
                    exerciseConn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getErrorCode());
            }
        }

        return 0;
    }

    public static int storeData(PracticalExercise practicalExercise) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return -1;
        }

        PreparedStatement ps = null;
        String lid = practicalExercise.getLessonId();
        String title = practicalExercise.getTitle();
        String tags = practicalExercise.getTagsString();
        int totalItems = practicalExercise.getTotalItems();
        int totalScore = practicalExercise.getTotalScore();

        String instructions = practicalExercise.getInstructions();
        String code = practicalExercise.getCode();
        String className = practicalExercise.getClassName();
        String methodName = practicalExercise.getMethodName();

        String explanation = practicalExercise.getExplanation();

        //practical print exercise
        String printValidator = null;
        int mustMatch = 0;

        //practical return exercise
        ArrayList<PracticalReturnValidatorSerializable> returnValidatorsSerializables = null;
        DataType returnType = null;
        ArrayList<DataType> parametersTypes = null;

        PracticalType practicalType =
                (practicalExercise instanceof PracticalPrintExercise) ? PracticalType.PRINT : PracticalType.RETURN;

        switch (practicalType) {
            case PRINT:
                PracticalPrintExercise practicalPrintExercise = (PracticalPrintExercise) practicalExercise;

                printValidator = practicalPrintExercise.getPrintValidator();
                mustMatch = (practicalPrintExercise.getMustMatch()) ? 1 : 0;
                break;
            case RETURN:
                PracticalReturnExercise practicalReturnExercise = (PracticalReturnExercise) practicalExercise;

                returnValidatorsSerializables = new ArrayList<>();
                ArrayList<PracticalReturnValidator> returnValidators = practicalReturnExercise.getReturnValidators();
                for (int i = 0; i < returnValidators.size(); i++) {
                    returnValidatorsSerializables.add(new PracticalReturnValidatorSerializable(returnValidators.get(i)));
                }
                returnType = practicalReturnExercise.getReturnType();

                parametersTypes = practicalReturnExercise.getParameterTypes();
                break;
            default:
                break;
        }


        try {

            ps = exerciseConn.prepareStatement(INSERT_PRACTICAL_EXERCISE);

            ps.setString(1,lid);
            ps.setString(2,title);
            ps.setString(3,tags);

            ps.setInt(4,totalItems);
            ps.setInt(5,totalScore);

            ps.setString(6,instructions);
            ps.setString(7,code);
            ps.setString(8,className);
            ps.setString(9,methodName);

            ps.setString(10,printValidator);
            ps.setInt(11,mustMatch);

            ps.setBytes(12,Database.serialize(returnValidatorsSerializables));
            ps.setBytes(13,Database.serialize(returnType));
            ps.setBytes(14,Database.serialize(parametersTypes));
            ps.setString(15,explanation);

            ps.setBytes(16, Database.serialize(practicalType));
            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getErrorCode() == SQLiteError.SQLITE_ERROR) {
                if (ps == null) {
                    System.out.println("Practical exercise table does not exist...Creating table...");
                    createPracticalExerciseTable();
                }

                storeData(practicalExercise);
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
                if (exerciseConn != null) {
                    exerciseConn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getErrorCode());
            }
        }

        return 0;
    }

    public static int storeData(VideoPracticalExercise videoPracticalExercise) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return -1;
        }

        PreparedStatement ps = null;
        String lid = videoPracticalExercise.getLessonId();
        String title = videoPracticalExercise.getTitle();
        String tags = videoPracticalExercise.getTagsString();
        int totalItems = videoPracticalExercise.getTotalItems();
        int totalScore = videoPracticalExercise.getTotalScore();

        String videoLessonTitle = videoPracticalExercise.getVideoLessonTitle();
        String videoLessonId = videoPracticalExercise.getVideoLessonId();
        String practicalExerciseTitle = videoPracticalExercise.getPracticalExerciseTitle();
        String practicalExerciseId = videoPracticalExercise.getPracticalExerciseId();
        Using videoLessonUsing = videoPracticalExercise.getVideoLessonUsing();
        Using practicalExerciseUsing = videoPracticalExercise.getPracticalExerciseUsing();

        try {

            ps = exerciseConn.prepareStatement(INSERT_VIDEO_PRACTICAL_EXERCISE);

            ps.setString(1,lid);
            ps.setString(2,title);
            ps.setString(3,tags);

            ps.setInt(4,totalItems);
            ps.setInt(5,totalScore);

            ps.setString(6,videoLessonTitle);
            ps.setString(7,videoLessonId);
            ps.setString(8,practicalExerciseTitle);
            ps.setString(9,practicalExerciseId);
            ps.setBytes(10,Database.serialize(videoLessonUsing));
            ps.setBytes(11,Database.serialize(practicalExerciseUsing));

            ps.executeUpdate();

        } catch (SQLException e) {

            if (e.getErrorCode() == SQLiteError.SQLITE_ERROR) {
                if (ps == null) {
                    System.out.println("Video practical exercise table does not exist...Creating table...");
                    createVideoPracticalExerciseTable();
                }

                storeData(videoPracticalExercise);
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
                if (exerciseConn != null) {
                    exerciseConn.close();
                }
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
                quizExercise.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

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

    public static PracticalExercise getPracticalExercise(String param, final String STATEMENT) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        PracticalExercise practicalExercise = null;
        PracticalType practicalType = null;
        try {
            ps = exerciseConn.prepareStatement(STATEMENT);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (rs.next()) {
                practicalType = (PracticalType)Database.deserialize(rs.getBytes("practicalType"));
                practicalExercise = (practicalType == PracticalType.PRINT) ? new PracticalPrintExercise() : new PracticalReturnExercise();


                practicalExercise.setID(rs.getString("lid"));
                practicalExercise.setTitle(rs.getString("title"));
                practicalExercise.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

                practicalExercise.setTotalItems(rs.getInt("totalItems"));
                practicalExercise.setTotalScore(rs.getInt("totalScore"));

                practicalExercise.setInstructions(rs.getString("instructions"));
                practicalExercise.setCode(rs.getString("code"));
                practicalExercise.setClassName(rs.getString("className"));
                practicalExercise.setMethodName(rs.getString("methodName"));
                practicalExercise.setExplanation(rs.getString("explanation"));

                switch(practicalType) {
                    case PRINT:
                        PracticalPrintExercise practicalPrintExercise = new PracticalPrintExercise();
                        practicalPrintExercise.copy(practicalExercise);

                        practicalPrintExercise.setPrintValidator(rs.getString("printValidator"));
                        practicalPrintExercise.setMustMatch((rs.getInt("mustMatch") == 1) ? true : false);

                        practicalExercise = practicalPrintExercise;
                        break;
                    case RETURN:
                        PracticalReturnExercise practicalReturnExercise = new PracticalReturnExercise();
                        practicalReturnExercise.copy(practicalExercise);

                        ArrayList<PracticalReturnValidatorSerializable> returnValidatorSerializables =
                                (ArrayList<PracticalReturnValidatorSerializable>)Database.deserialize(rs.getBytes("returnValidators"));
                        ArrayList<PracticalReturnValidator> returnValidators = new ArrayList<>();
                        for (int i = 0; i < returnValidatorSerializables.size(); i++) {
                            returnValidators.add(new PracticalReturnValidator(returnValidatorSerializables.get(i)));
                        }
                        practicalReturnExercise.setReturnValidators(returnValidators);
                        practicalReturnExercise.setReturnType((DataType)Database.deserialize(rs.getBytes("returnType")));
                        practicalReturnExercise.setParameterTypes(
                                ((ArrayList<DataType>)Database.deserialize(rs.getBytes("parametersTypes"))));

                        practicalExercise = practicalReturnExercise;
                        break;
                    default:
                        break;
                }

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

        return practicalExercise;
    }

    public static VideoPracticalExercise getVideoPracticalExercise(String param, final String STATEMENT) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        VideoPracticalExercise videoPracticalExercise = null;
        PracticalType practicalType = null;
        try {
            ps = exerciseConn.prepareStatement(STATEMENT);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (rs.next()) {
                videoPracticalExercise = new VideoPracticalExercise();


                videoPracticalExercise.setID(rs.getString("lid"));
                videoPracticalExercise.setTitle(rs.getString("title"));
                videoPracticalExercise.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

                videoPracticalExercise.setTotalItems(rs.getInt("totalItems"));
                videoPracticalExercise.setTotalScore(rs.getInt("totalScore"));

                videoPracticalExercise.setVideoLessonId(rs.getString("videoLessonId"));
                videoPracticalExercise.setVideoLessonTitle(rs.getString("videoLessonTitle"));
                videoPracticalExercise.setPracticalExerciseId(rs.getString("practicalExerciseId"));
                videoPracticalExercise.setPracticalExerciseTitle(rs.getString("practicalExerciseTitle"));

                videoPracticalExercise.setVideoLessonUsing((Using)Database.deserialize(rs.getBytes("videoLessonUsing")));
                videoPracticalExercise.setPracticalExerciseUsing((Using)Database.deserialize(rs.getBytes("practicalExerciseUsing")));

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

        return videoPracticalExercise;
    }

    public static QuizExercise getQuizExerciseUsingLID(String lid) {
        return getQuizExercise(lid,GET_QUIZ_EXERCISE_USING_ID);
    }

    public static PracticalExercise getPracticalExerciseUsingLID(String lid) {
        return getPracticalExercise(lid,GET_PRACTICAL_EXERCISE_USING_ID);
    }

    public static VideoPracticalExercise getVideoPracticalExerciseUsingLID(String lid) {
        return getVideoPracticalExercise(lid,GET_VIDEO_PRACTICAL_EXERCISE_USING_ID);
    }

    public static QuizExercise getQuizExerciseUsingTitle(String title) {
        return getQuizExercise(title,GET_QUIZ_EXERCISE_USING_TITLE);
    }

    public static PracticalExercise getPracticalExerciseUsingTitle(String title) {
        return getPracticalExercise(title,GET_PRACTICAL_EXERCISE_USING_TITLE);
    }

    public static VideoPracticalExercise getVideoPracticalExerciseUsingTitle(String title) {
        return getVideoPracticalExercise(title,GET_VIDEO_PRACTICAL_EXERCISE_USING_TITLE);
    }

    public static ArrayList<QuizExercise> getQuizExercisesUsingTagsExactly(String tags) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
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
                QuizExercise quizExercise = new QuizExercise();
                quizExercise.setID(rs.getString("lid"));
                quizExercise.setTitle(rs.getString("title"));
                quizExercise.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

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

    public static ArrayList<PracticalExercise> getPracticalExercisesUsingTagsExactly(String tags) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<PracticalExercise> practicalExercises = new ArrayList<>();
        try {
            ps = exerciseConn.prepareStatement(GET_PRACTICAL_EXERCISES_USING_TAGS_EXACTLY);
            ps.setString(1,tags);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.err.println("No exercises match with param: " + tags);
                return null;
            }

            while (rs.next()) {
                PracticalType practicalType = (PracticalType)Database.deserialize(rs.getBytes("practicalType"));
                PracticalExercise practicalExercise = (practicalType == PracticalType.PRINT) ? new PracticalPrintExercise() : new PracticalReturnExercise();

                practicalExercise.setID(rs.getString("lid"));
                practicalExercise.setTitle(rs.getString("title"));
                practicalExercise.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

                practicalExercise.setTotalItems(rs.getInt("totalItems"));
                practicalExercise.setTotalScore(rs.getInt("totalScore"));

                practicalExercise.setInstructions(rs.getString("instructions"));
                practicalExercise.setCode(rs.getString("code"));
                practicalExercise.setClassName(rs.getString("className"));
                practicalExercise.setMethodName(rs.getString("methodName"));
                practicalExercise.setExplanation(rs.getString("explanation"));

                switch(practicalType) {
                    case PRINT:
                        PracticalPrintExercise practicalPrintExercise = new PracticalPrintExercise();
                        practicalPrintExercise.copy(practicalExercise);

                        practicalPrintExercise.setPrintValidator(rs.getString("printValidator"));
                        practicalPrintExercise.setMustMatch((rs.getInt("mustMatch") == 1) ? true : false);

                        practicalExercise = practicalPrintExercise;
                        break;
                    case RETURN:
                        PracticalReturnExercise practicalReturnExercise = new PracticalReturnExercise();
                        practicalReturnExercise.copy(practicalExercise);

                        ArrayList<PracticalReturnValidatorSerializable> returnValidatorSerializables =
                                (ArrayList<PracticalReturnValidatorSerializable>)Database.deserialize(rs.getBytes("returnValidators"));
                        ArrayList<PracticalReturnValidator> returnValidators = new ArrayList<>();
                        for (int i = 0; i < returnValidatorSerializables.size(); i++) {
                            returnValidators.add(new PracticalReturnValidator(returnValidatorSerializables.get(i)));
                        }
                        practicalReturnExercise.setReturnValidators(returnValidators);
                        practicalReturnExercise.setReturnType((DataType)Database.deserialize(rs.getBytes("returnType")));
                        practicalReturnExercise.setParameterTypes(
                                ((ArrayList<DataType>)Database.deserialize(rs.getBytes("parametersTypes"))));

                        practicalExercise = practicalReturnExercise;
                        break;
                    default:
                        break;
                }

                practicalExercises.add(practicalExercise);
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

        return practicalExercises;
    }

    public static ArrayList<VideoPracticalExercise> getVideoPracticalExercisesUsingTagsExactly(String tags) {
        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<VideoPracticalExercise> videoPracticalExercises = new ArrayList<>();
        try {
            ps = exerciseConn.prepareStatement(GET_VIDEO_PRACTICAL_EXERCISES_USING_TAGS_EXACTLY);
            ps.setString(1,tags);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.err.println("No exercises match with param: " + tags);
                return null;
            }

            while (rs.next()) {
                VideoPracticalExercise videoPracticalExercise = new VideoPracticalExercise();

                videoPracticalExercise.setID(rs.getString("lid"));
                videoPracticalExercise.setTitle(rs.getString("title"));
                videoPracticalExercise.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

                videoPracticalExercise.setTotalItems(rs.getInt("totalItems"));
                videoPracticalExercise.setTotalScore(rs.getInt("totalScore"));

                videoPracticalExercise.setVideoLessonId(rs.getString("videoLessonId"));
                videoPracticalExercise.setVideoLessonTitle(rs.getString("videoLessonTitle"));
                videoPracticalExercise.setPracticalExerciseId(rs.getString("practicalExerciseId"));
                videoPracticalExercise.setPracticalExerciseTitle(rs.getString("practicalExerciseTitle"));

                videoPracticalExercise.setVideoLessonUsing((Using)Database.deserialize(rs.getBytes("videoLessonUsing")));
                videoPracticalExercise.setPracticalExerciseUsing((Using)Database.deserialize(rs.getBytes("practicalExerciseUsing")));

                videoPracticalExercises.add(videoPracticalExercise);
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

        return videoPracticalExercises;
    }

    //tags contains
        //quiz exercises
    public static ArrayList<QuizExercise> getQuizExercisesUsingTagsContains(String tags) {
        return getQuizExercisesUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<QuizExercise> getQuizExercisesUsingPropertyTagsContains(ArrayList<StringProperty> tags) {
        return getQuizExercisesUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<QuizExercise> getQuizExercisesUsingTagsContains(ArrayList<String> tags) {
        if (tags == null || tags.isEmpty()) return null;

        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<QuizExercise> quizExercises = new ArrayList<>();
        String param = "%" + tags.get(0) + "%";
        try {
            ps = exerciseConn.prepareStatement(GET_QUIZ_EXERCISES_USING_TAGS_CONTAINS);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.err.println("No quiz exercises contains with param: " + param);
                return null;
            }

            while (rs.next()) {
                QuizExercise quizExercise = new QuizExercise();
                quizExercise.setID(rs.getString("lid"));
                quizExercise.setTitle(rs.getString("title"));
                quizExercise.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

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

            //check if every matches contains all the other tags

            for (int i = 0; i < quizExercises.size(); i++) {
                for (int j = 1; j < tags.size(); j++) {
                    QuizExercise match = quizExercises.get(i);
                    if (!match.getTagsString().contains(tags.get(j))) {
                        quizExercises.remove(match);
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
                exerciseConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return quizExercises;
    }

    public static ArrayList<QuizExercise> getQuizExercisesUsingTagsContains(String ... tags) {
        return getQuizExercisesUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<PracticalExercise> getPracticalExercisesUsingTagsContains(ArrayList<String> tags) {
        if (tags == null || tags.isEmpty()) return null;

        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<PracticalExercise> practicalExercises = new ArrayList<>();
        String param = "%" + tags.get(0) + "%";
        try {
            ps = exerciseConn.prepareStatement(GET_PRACTICAL_EXERCISES_USING_TAGS_CONTAINS);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.err.println("No practical exercises contains with param: " + param);
                return null;
            }

            while (rs.next()) {
                PracticalType practicalType = (PracticalType)Database.deserialize(rs.getBytes("practicalType"));
                PracticalExercise practicalExercise = (practicalType == PracticalType.PRINT) ? new PracticalPrintExercise() : new PracticalReturnExercise();

                practicalExercise.setID(rs.getString("lid"));
                practicalExercise.setTitle(rs.getString("title"));
                practicalExercise.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

                practicalExercise.setTotalItems(rs.getInt("totalItems"));
                practicalExercise.setTotalScore(rs.getInt("totalScore"));

                practicalExercise.setInstructions(rs.getString("instructions"));
                practicalExercise.setCode(rs.getString("code"));
                practicalExercise.setClassName(rs.getString("className"));
                practicalExercise.setMethodName(rs.getString("methodName"));

                practicalExercise.setExplanation("explanation");

                switch(practicalType) {
                    case PRINT:
                        PracticalPrintExercise practicalPrintExercise = new PracticalPrintExercise();
                        practicalPrintExercise.copy(practicalExercise);

                        practicalPrintExercise.setPrintValidator(rs.getString("printValidator"));
                        practicalPrintExercise.setMustMatch((rs.getInt("mustMatch") == 1) ? true : false);

                        practicalExercise = practicalPrintExercise;
                        break;
                    case RETURN:
                        PracticalReturnExercise practicalReturnExercise = new PracticalReturnExercise();
                        practicalReturnExercise.copy(practicalExercise);

                        ArrayList<PracticalReturnValidatorSerializable> returnValidatorSerializables =
                                (ArrayList<PracticalReturnValidatorSerializable>)Database.deserialize(rs.getBytes("returnValidators"));
                        ArrayList<PracticalReturnValidator> returnValidators = new ArrayList<>();
                        for (int i = 0; i < returnValidatorSerializables.size(); i++) {
                            returnValidators.add(new PracticalReturnValidator(returnValidatorSerializables.get(i)));
                        }
                        practicalReturnExercise.setReturnValidators(returnValidators);
                        practicalReturnExercise.setReturnType((DataType)Database.deserialize(rs.getBytes("returnType")));
                        practicalReturnExercise.setParameterTypes(
                                ((ArrayList<DataType>)Database.deserialize(rs.getBytes("parametersTypes"))));

                        practicalExercise = practicalReturnExercise;
                        break;
                    default:
                        break;
                }

                practicalExercises.add(practicalExercise);
            }

            //check if every matches contains all the other tags

            for (int i = 0; i < practicalExercises.size(); i++) {
                for (int j = 1; j < tags.size(); j++) {
                    PracticalExercise match = practicalExercises.get(i);
                    if (!match.getTagsString().contains(tags.get(j))) {
                        practicalExercises.remove(match);
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
                exerciseConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return practicalExercises;
    }

    public static ArrayList<PracticalExercise> getPracticalExercisesUsingTagsContains(String tags) {
        return getPracticalExercisesUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<PracticalExercise> getPracticalExercisesUsingPropertyTagsContains(ArrayList<StringProperty> tags) {
        return getPracticalExercisesUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<PracticalExercise> getPracticalExercisesUsingTagsContains(String ... tags) {
        return getPracticalExercisesUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<VideoPracticalExercise> getVideoPracticalExercisesUsingTagsContains(ArrayList<String> tags) {
        if (tags == null || tags.isEmpty()) return null;

        Connection exerciseConn = DatabaseConnection.getExerciseConn();

        if (exerciseConn == null) {
            System.err.println("Error connecting to exercise database...");

            return null;
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<VideoPracticalExercise> videoPracticalExercises = new ArrayList<>();
        String param = "%" + tags.get(0) + "%";
        try {
            ps = exerciseConn.prepareStatement(GET_VIDEO_PRACTICAL_EXERCISES_USING_TAGS_CONTAINS);
            ps.setString(1,param);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.err.println("No video practical exercises contains with param: " + param);
                return null;
            }

            while (rs.next()) {
                VideoPracticalExercise videoPracticalExercise = new VideoPracticalExercise();

                videoPracticalExercise.setID(rs.getString("lid"));
                videoPracticalExercise.setTitle(rs.getString("title"));
                videoPracticalExercise.setTags(LessonUtil.tagsToArrayListStringProperty(rs.getString("tags")));

                videoPracticalExercise.setTotalItems(rs.getInt("totalItems"));
                videoPracticalExercise.setTotalScore(rs.getInt("totalScore"));

                videoPracticalExercise.setVideoLessonId(rs.getString("videoLessonId"));
                videoPracticalExercise.setVideoLessonTitle(rs.getString("videoLessonTitle"));
                videoPracticalExercise.setPracticalExerciseId(rs.getString("practicalExerciseId"));
                videoPracticalExercise.setPracticalExerciseTitle(rs.getString("practicalExerciseTitle"));

                videoPracticalExercise.setVideoLessonUsing((Using)Database.deserialize(rs.getBytes("videoLessonUsing")));
                videoPracticalExercise.setPracticalExerciseUsing((Using)Database.deserialize(rs.getBytes("practicalExerciseUsing")));

                videoPracticalExercises.add(videoPracticalExercise);
            }

            //check if every matches contains all the other tags

            for (int i = 0; i < videoPracticalExercises.size(); i++) {
                for (int j = 1; j < tags.size(); j++) {
                    VideoPracticalExercise match = videoPracticalExercises.get(i);
                    if (!match.getTagsString().contains(tags.get(j))) {
                        videoPracticalExercises.remove(match);
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
                exerciseConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return videoPracticalExercises;
    }

    public static ArrayList<VideoPracticalExercise> getVideoPracticalExercisesUsingTagsContains(String tags) {
        return getVideoPracticalExercisesUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<VideoPracticalExercise> getVideoPracticalExercisesUsingPropertyTagsContains(ArrayList<StringProperty> tags) {
        return getVideoPracticalExercisesUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

    public static ArrayList<VideoPracticalExercise> getVideoPracticalExercisesUsingTagsContains(String ... tags) {
        return getVideoPracticalExercisesUsingTagsContains(LessonUtil.tagsToArrayList(tags));
    }

}
