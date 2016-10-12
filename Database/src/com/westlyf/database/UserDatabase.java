package com.westlyf.database;

import sample.model.Users;

import java.sql.*;
import java.util.Calendar;

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
            //", " +
            //"FOREIGN KEY (currentLessonId) REFERENCES text_lesson(id), " +
            //"FOREIGN KEY (currentModuleId) REFERENCES Modules(id), " +
            //"FOREIGN KEY (currentExamId) REFERENCES exam(id)" +
            ")";
    private static final String IS_USER_AVAILABLE = "SELECT * FROM users WHERE username = ? AND password = ?";

    private static final String ADD_NEW_PROFILE = "INSERT INTO Users(currentModuleId, currentLessonId, currentExamId, " +
            "username, password, name, age, sex, school, yearLevel, profilePicturePath, dateModified, dateCreated) " +
            "values(?,?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP)";

    private static final String UPDATE_USER = "UPDATE Users SET currentModuleId=?, currentLessonId=?, currentExamId=?, " +
            "username=?, password=?, name=?, age=?, sex=?, school=?, yearLevel=?, profilePicturePath=?, " +
            "dateModified=CURRENT_TIMESTAMP WHERE userId=?";

    public static int createUsersDatabase(){
        return Database.createTable(Database.USER, UserDatabase.CREATE_USERS_TABLE);
    }

    public static Users isUserAvailable(String username, String password){
        userConn = DatabaseConnection.getUserConnection();
        if(userConn != null){
            try {
                PreparedStatement preparedStatement = userConn.prepareStatement(IS_USER_AVAILABLE);
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
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void addNewProfile(String currentModuleId, String currentLessonId, String currentExamId,
                                     String username, String password, String name, int age, String sex,
                                     String school, String yearLevel, String profilePicturePath){
        userConn = DatabaseConnection.getUserConnection();
        if (userConn != null){
            try {
                PreparedStatement preparedStatement = preparedStatement = userConn.prepareStatement(ADD_NEW_PROFILE);
                preparedStatement.setString(1, currentModuleId);
                preparedStatement.setString(2, currentLessonId);
                preparedStatement.setString(3, currentExamId);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, name);
                preparedStatement.setInt(7, age);
                preparedStatement.setString(8, sex);
                preparedStatement.setString(9, school);
                preparedStatement.setString(10, yearLevel);
                preparedStatement.setString(11, profilePicturePath);
                preparedStatement.executeUpdate();
                preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateUser(int userId, String currentModuleId, String currentLessonId, String currentExamId,
                                  String username, String password, String name, int age, String sex,
                                  String school, String yearLevel, String profilePicturePath){
        userConn = DatabaseConnection.getUserConnection();
        if (userConn != null){
            try {
                PreparedStatement preparedStatement = preparedStatement = userConn.prepareStatement(UPDATE_USER);
                preparedStatement.setString(1, currentModuleId);
                preparedStatement.setString(2, currentLessonId);
                preparedStatement.setString(3, currentExamId);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, name);
                preparedStatement.setInt(7, age);
                preparedStatement.setString(8, sex);
                preparedStatement.setString(9, school);
                preparedStatement.setString(10, yearLevel);
                preparedStatement.setString(11, profilePicturePath);
                preparedStatement.setInt(12, userId);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
