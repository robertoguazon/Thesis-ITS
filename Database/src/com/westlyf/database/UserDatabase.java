package com.westlyf.database;

import java.sql.*;
import java.util.Calendar;

/**
 * Created by robertoguazon on 10/08/2016.
 */
public class UserDatabase {
    //TODO - put all methods for storing and getting data from and into the database

    private static Connection userConn;

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS Users(" +
            "userId INT AUTO_INCREMENT PRIMARY KEY NOT NULL, " +
            "learningProfileId INT, " +
            "currentLessonId INT, " +
            "currentModuleId INT, " +
            "currentExamId INT, " +
            "username TEXT NOT NULL UNIQUE, " +
            "password TEXT NOT NULL, " +
            "name TEXT NOT NULL, " +
            "age INT NOT NULL, " +
            "sex TEXT NOT NULL," +
            "school TEXT NOT NULL, " +
            "yearLevel TEXT NOT NULL, " +
            "profilePicturePath TEXT, " +
            "diagnosticsGrade INT, " +
            "dateModified DATETIME NOT NULL, " +
            "dateCreated DATETIME NOT NULL, " +
            "FOREIGN KEY (learningProfileId) REFERENCES learningProfile(id), " +
            "FOREIGN KEY (currentLessonId) REFERENCES Lessons(id), " +
            "FOREIGN KEY (currentModuleId) REFERENCES Modules(id), " +
            "FOREIGN KEY (currentExamId) REFERENCES Exam(id)" +
            ")";
    private static final String IS_USER_AVAILABLE = "SELECT * FROM USERS WHERE username = ? AND password = ?";

    private static final String ADD_NEW_PROFILE = "INSERT INTO Users(learningProfileId, currentLessonId, currentModuleId, " +
            "username, password, name, age, sex, school, yearLevel, profilePicturePath, diagnosticsGrade, dateModified, dateCreated) " +
            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static int createUsersDatabase(){
        return Database.createTable(Database.USER, UserDatabase.CREATE_USERS_TABLE);
    }

    public static boolean isUserAvailable(String username, String password){
        userConn = DatabaseConnection.getUserConnection();
        if(userConn != null){
            try {
                PreparedStatement preparedStatement = userConn.prepareStatement(IS_USER_AVAILABLE);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()){
                    return true;
                }
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void addNewProfile(int learningProfileId, int currentLessonId, int currentModuleId,
                                     String username, String password, String name, int age, String sex,
                                     String school, int yearLevel, String profilePicturePath, int diagnosticsGrade){
        userConn = DatabaseConnection.getUserConnection();
        if (userConn != null){
            try {
                PreparedStatement preparedStatement = preparedStatement = userConn.prepareStatement(ADD_NEW_PROFILE);
                preparedStatement.setInt(1, learningProfileId);
                preparedStatement.setInt(2, currentLessonId);
                preparedStatement.setInt(3, currentModuleId);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, password);
                preparedStatement.setString(6, name);
                preparedStatement.setInt(7, age);
                preparedStatement.setString(8, sex);
                preparedStatement.setString(9, school);
                preparedStatement.setInt(10, yearLevel);
                preparedStatement.setString(11, profilePicturePath);
                preparedStatement.setInt(12, diagnosticsGrade);
                preparedStatement.setDate(13, (Date) Calendar.getInstance().getTime());;
                preparedStatement.setDate(14, (Date) Calendar.getInstance().getTime());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
