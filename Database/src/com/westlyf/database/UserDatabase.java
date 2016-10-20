package com.westlyf.database;

import com.westlyf.user.ExamGrade;
import com.westlyf.user.UserExercise;
import com.westlyf.user.Users;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 10/08/2016.
 */
public class UserDatabase {
    //TODO - put all methods for storing and getting data from and into the database

    private static Connection userConn;

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users(" +
            "userId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "currentModuleId TEXT NOT NULL, " +
            "currentLessonId TEXT, " +
            "currentExamId TEXT, " +
            "username TEXT NOT NULL UNIQUE, " +
            "password TEXT NOT NULL, " +
            "name TEXT NOT NULL, " +
            "age INTEGER NOT NULL, " +
            "sex TEXT NOT NULL," +
            "school TEXT NOT NULL, " +
            "yearLevel TEXT NOT NULL, " +
            "profilePicturePath TEXT, " +
            "dateModified DATETIME NOT NULL, " +
            "dateCreated DATETIME NOT NULL" +
            ")";
    private static final String CREATE_USER_EXERCISES_TABLE = "CREATE TABLE IF NOT EXISTS user_exercises(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userId INTEGER NOT NULL, " +
            "exercise_title TEXT NOT NULL, " +
            "code TEXT NOT NULL, " +
            "dateModified DATETIME NOT NULL, " +
            "dateCreated DATETIME NOT NULL" +
            ")";
    private static final String CREATE_EXAM_GRADES_TABLE = " CREATE TABLE IF NOT EXISTS exam_grades(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "userId INTEGER NOT NULL, " +
            "exam_title TEXT NOT NULL, " +
            "grade INTEGER NOT NULL, " +
            "dateModified DATETIME NOT NULL, " +
            "dateCreated DATETIME NOT NULL" +
            ")";

    private static final String ADD_USER = "INSERT INTO users(currentModuleId, currentLessonId, currentExamId, " +
            "username, password, name, age, sex, school, yearLevel, profilePicturePath, dateModified, dateCreated) " +
            "values(?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
    private static final String ADD_USER_EXERCISE = "INSERT INTO user_exercises(userId, exercise_title, code, " +
            "dateModified, dateCreated) values(?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";
    private static final String ADD_EXAM_GRADE = "INSERT INTO exam_grades(userId, exam_title, grade, dateModified, dateCreated) " +
            "values(?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

    private static final String UPDATE_USER = "UPDATE users SET currentModuleId=?, currentLessonId=?, currentExamId=?, " +
            "username=?, password=?, name=?, age=?, sex=?, school=?, yearLevel=?, profilePicturePath=?, " +
            "dateModified=CURRENT_TIMESTAMP WHERE userId=?";
    private static final String UPDATE_USER_EXERCISE = "UPDATE user_exercises SET userId=?, exercise_title=?, code=?, " +
            "dateModified=CURRENT_TIMESTAMP WHERE id=?";
    private static final String UPDATE_EXAM_GRADE = "UPDATE exam_grade SET userId=?, exam_title=?, grade=?, " +
            "dateModified=CURRENT_TIMESTAMP WHERE id=?";

    private static final String GET_USER_USING_CREDENTIALS = "SELECT * FROM users WHERE username = ? AND password = ?";
    private static final String GET_USER_EXERCISES_USING_USER_ID = "SELECT * FROM user_exercises WHERE userId=?";
    private static final String GET_EXAM_GRADES_USING_USER_ID = "SELECT * FROM exam_grades WHERE userId=?";

    public static int createUsersTable(){
        return Database.createTable(Database.USER, CREATE_USERS_TABLE);
    }

    public static int createUserExercisesTable(){
        return Database.createTable(Database.USER, CREATE_USER_EXERCISES_TABLE);
    }

    public static int createExamGradesTable(){
        return Database.createTable(Database.USER, CREATE_EXAM_GRADES_TABLE);
    }

    public static Users getUserUsingCredentials(String username, String password){
        userConn = DatabaseConnection.getUserConnection();
        if(userConn != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = userConn.prepareStatement(GET_USER_USING_CREDENTIALS);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    Users user = new Users();
                    user.setUserId(resultSet.getInt("userId"));
                    user.setCurrentModuleId(resultSet.getString("currentModuleId"));
                    user.setCurrentLessonId(resultSet.getString("currentLessonId"));
                    user.setCurrentExamId(resultSet.getString("currentExamId"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setAge(resultSet.getInt("age"));
                    user.setSex(resultSet.getString("sex"));
                    user.setSchool(resultSet.getString("school"));
                    user.setYearLevel(resultSet.getString("yearLevel"));
                    user.setProfilePicturePath(resultSet.getString("profilePicturePath"));
                    System.out.println("user retrieved: " + user);
                    return user;
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + " : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    userConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static int addUser(Users user){
        userConn = DatabaseConnection.getUserConnection();
        if (userConn != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = userConn.prepareStatement(ADD_USER);
                preparedStatement.setString(1, user.getCurrentModuleId());
                preparedStatement.setString(2, user.getCurrentLessonId());
                preparedStatement.setString(3, user.getCurrentExamId());
                preparedStatement.setString(4, user.getUsername());
                preparedStatement.setString(5, user.getPassword());
                preparedStatement.setString(6, user.getName());
                preparedStatement.setInt(7, user.getAge());
                preparedStatement.setString(8, user.getSex());
                preparedStatement.setString(9, user.getSchool());
                preparedStatement.setString(10, user.getYearLevel());
                preparedStatement.setString(11, user.getProfilePicturePath());
                int update = preparedStatement.executeUpdate();
                if (update > 0){
                    System.out.println("Successful Insert: user");
                    return update;
                }else return update;
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + " : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    userConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return -1;
    }

    public static int addUserExercise(UserExercise userExercise){
        userConn = DatabaseConnection.getUserConnection();
        if (userConn != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = userConn.prepareStatement(ADD_USER_EXERCISE);
                preparedStatement.setInt(1, userExercise.getUserId());
                preparedStatement.setString(2, userExercise.getExercise_title());
                preparedStatement.setString(3, userExercise.getCode());
                int update = preparedStatement.executeUpdate();
                if (update > 0){
                    System.out.println("Successful Insert: user_exercise");
                    return update;
                }else return update;
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + " : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    userConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return -1;
    }

    public static int addExamGrade(ExamGrade examGrade){
        userConn = DatabaseConnection.getUserConnection();
        if (userConn != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = userConn.prepareStatement(ADD_EXAM_GRADE);
                preparedStatement.setInt(1, examGrade.getUserId());
                preparedStatement.setString(2, examGrade.getExam_title());
                preparedStatement.setInt(3, examGrade.getGrade());
                int update = preparedStatement.executeUpdate();
                if (update > 0){
                    System.out.println("Successful Insert: exam_grade");
                    return update;
                }else return update;
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + " : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    userConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return -1;
    }

    public static int updateUser(Users user){
        userConn = DatabaseConnection.getUserConnection();
        if (userConn != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = userConn.prepareStatement(UPDATE_USER);
                preparedStatement.setString(1, user.getCurrentModuleId());
                preparedStatement.setString(2, user.getCurrentLessonId());
                preparedStatement.setString(3, user.getCurrentExamId());
                preparedStatement.setString(4, user.getUsername());
                preparedStatement.setString(5, user.getPassword());
                preparedStatement.setString(6, user.getName());
                preparedStatement.setInt(7, user.getAge());
                preparedStatement.setString(8, user.getSex());
                preparedStatement.setString(9, user.getSchool());
                preparedStatement.setString(10, user.getYearLevel());
                preparedStatement.setString(11, user.getProfilePicturePath());
                preparedStatement.setInt(12, user.getUserId());
                int update = preparedStatement.executeUpdate();
                if (update > 0){
                    System.out.println("Successful Update: user");
                    return update;
                }else return update;
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + " : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    userConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return -1;
    }

    public static int updateUserExercise(UserExercise userExercise){
        userConn = DatabaseConnection.getUserConnection();
        if (userConn != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = userConn.prepareStatement(UPDATE_USER_EXERCISE);
                preparedStatement.setInt(1, userExercise.getUserId());
                preparedStatement.setString(2, userExercise.getExercise_title());
                preparedStatement.setString(3, userExercise.getCode());
                preparedStatement.setInt(4, userExercise.getId());
                int update = preparedStatement.executeUpdate();
                if (update > 0){
                    System.out.println("Successful Update: user_exercise");
                    return update;
                }else return update;
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + " : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    userConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return -1;
    }

    public static int updateExamGrade(ExamGrade examGrade){
        userConn = DatabaseConnection.getUserConnection();
        if (userConn != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = userConn.prepareStatement(UPDATE_EXAM_GRADE);
                preparedStatement.setInt(1, examGrade.getUserId());
                preparedStatement.setString(2, examGrade.getExam_title());
                preparedStatement.setInt(3, examGrade.getGrade());
                preparedStatement.setInt(4, examGrade.getId());
                int update = preparedStatement.executeUpdate();
                if (update > 0){
                    System.out.println("Successful Update: exam_grade");
                    return update;
                }else {return update;}
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + " : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    userConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return -1;
    }

    public static ArrayList<UserExercise> getUserExercisesUsingUserId(int userId){
        userConn = DatabaseConnection.getUserConnection();
        if(userConn != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = userConn.prepareStatement(GET_USER_EXERCISES_USING_USER_ID);
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<UserExercise> userExercises = new ArrayList<>();
                while (resultSet.next()){
                    UserExercise userExercise = new UserExercise();
                    userExercise.setId(resultSet.getInt("id"));
                    userExercise.setUserId(resultSet.getInt("userId"));
                    userExercise.setExercise_title(resultSet.getString("exercise_title"));
                    userExercise.setCode(resultSet.getString("code"));
                    userExercises.add(userExercise);
                }
                System.out.println("Retrieved User Exercises:\n" + userExercises);
                return userExercises;
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + " : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    userConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static ArrayList<ExamGrade> getExamGradesUsingUserId(int userId){
        userConn = DatabaseConnection.getUserConnection();
        if(userConn != null){
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = userConn.prepareStatement(GET_EXAM_GRADES_USING_USER_ID);
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<ExamGrade> examGrades = new ArrayList<>();
                while (resultSet.next()){
                    ExamGrade examGrade = new ExamGrade();
                    examGrade.setId(resultSet.getInt("id"));
                    examGrade.setUserId(resultSet.getInt("userId"));
                    examGrade.setExam_title(resultSet.getString("exam_title"));
                    examGrade.setGrade(resultSet.getInt("grade"));
                    examGrades.add(examGrade);
                }
                System.out.println("Retrieved Exam Grades:\n" + examGrades);
                return examGrades;
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getErrorCode() + " : " + e.getMessage());
                alert.showAndWait();
                e.printStackTrace();
            } finally {
                try {
                    preparedStatement.close();
                    userConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
