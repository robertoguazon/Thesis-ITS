package com.westlyf.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by robertoguazon on 24/07/2016.
 */
public class DatabaseConnection {

    public static Connection conn = null;

    public static void connect() {
        connect("jdbc:sqlite:resources/database/database.db");
    }

    public static void connect(String url) {

        try {
            conn = DriverManager.getConnection(url);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() {
        if (conn != null) {
            return conn;
        }

        connect();
        return conn;
    }

}
