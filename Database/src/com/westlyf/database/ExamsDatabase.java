package com.westlyf.database;

import java.sql.Connection;

/**
 * Created by Yves on 9/19/2016.
 */
public class ExamsDatabase {

    private static Connection examsConnection;

    private static final String CREATE_EXAMS_TABLE = "CREATE TABLE IF NOT EXISTS exams(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "examName TEXT UNIQUE NOT NULL, " +
            "tags TEXT NOT NULL, " +
            "grade INTEGER, " +
            "overall INTEGER NOT NULL, " +
            "dateTaken DATETIME NOT NULL, " +
            "weight INTEGER NOT NULL, " +
            "dateModified DATETIME NOT NULL, " +
            "dateCreated DATETIME NOT NULL" +
            ")";

    private static final String CREATE_EXAM_CONTENT_TABLE = "CREATE TABLE IF NOT EXISTS exam_content(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "examsId INTEGER NOT NULL, " +
            "examQuestionsId NOT NULL, " +
            "FOREIGN KEY(examsId) REFERENCES exams(id), " +
            "FOREIGN KEY(examQuestionsId) REFERENCES exam_questions(id)" +
            ")";

    private static final String CREATE_EXAM_QUESTIONS_TABLE = "CREATE TABLE IF NOT EXISTS exam_questions(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "questionName TEXT UNIQUE NOT NULL, " +
            "tags TEXT NOT NULL, " +
            "questionContent TEXT NOT NULL, " +
            "choiceTags TEXT NOT NULL, " +
            "" +
            ")";
}
