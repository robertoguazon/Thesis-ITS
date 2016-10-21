package com.westlyf.user;

/**
 * Created by Yves on 10/12/2016.
 */
public class UserExercise {

    private int id;
    private int userId;
    private String exercise_title;
    private String code;

    public UserExercise() {
    }

    public UserExercise(int userId, String exercise_title, String code) {
        this.userId = userId;
        this.exercise_title = exercise_title;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getExercise_title() {
        return exercise_title;
    }

    public void setExercise_title(String exercise_title) {
        this.exercise_title = exercise_title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString(){
        return "id: " + getId() + "\n" +
                "userId: " + getUserId() + "\n" +
                "exercise_title: " + getExercise_title() + "\n" +
                "code: " + getCode();
    }
}
