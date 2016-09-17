package com.westlyf.database;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by robertoguazon on 10/08/2016.
 */
public class Database {

    //TODO - constants sql statements and such

    /**
     * Constant int variables for DatabaseType
     * */
    public static final int
        USER = 1,
        LESSON = 2,
        EXERCISE = 3,
        EXAM = 4;

    public static byte[] serialize(Serializable object) {
        ByteArrayOutputStream baos;
        ObjectOutputStream oos;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.close();

            return baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object deserialize(byte[] bytes) {
        ObjectInputStream ois;

        try {

            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object object = ois.readObject();
            ois.close();

            return object;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    //general methods for databases
    public static int createTable(final int DATABASE_TYPE,final String STATEMENT) {

        Connection conn = null;
        switch (DATABASE_TYPE) {
            case USER:
                conn = DatabaseConnection.getUserConnection();
                break;
            case LESSON:
                conn = DatabaseConnection.getLessonConn();
                break;
            case EXERCISE:
                conn = DatabaseConnection.getExerciseConn();
                break;
            case EXAM:
                conn = DatabaseConnection.getExamConn();
        }
        Statement stmt = null;

        if (conn == null) {
            System.err.println("Error connecting to the database");
            return -1;
        }

        try {

            stmt = conn.createStatement();
            stmt.executeUpdate(STATEMENT);

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


}
