package com.westlyf.user;

/**
 * Created by Yves on 9/19/2016.
 */
public class Users {

    private int userId;
    private String username;
    private String password;
    private String name;
    private int age;
    private String sex;
    private String school;
    private String yearLevel;
    private String profilePicturePath;
    private String currentModuleId;
    private String currentLessonId;
    private String currentExamId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getYearLevel() {
        return yearLevel;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getCurrentModuleId() {
        return currentModuleId;
    }

    public void setCurrentModuleId(String currentModuleId) {
        this.currentModuleId = currentModuleId;
    }

    public String getCurrentLessonId() {
        return currentLessonId;
    }

    public void setCurrentLessonId(String currentLessonId) {
        this.currentLessonId = currentLessonId;
    }

    public String getCurrentExamId() {
        return currentExamId;
    }

    public void setCurrentExamId(String currentExamId) {
        this.currentExamId = currentExamId;
    }

    public String toString(){
        return "username: " + getUsername() + "\n" +
                "password: " + getPassword() + "\n" +
                "name: " + getName() + "\n" +
                "age: " + getAge() + "\n" +
                "sex: " + getSex() + "\n" +
                "school: " + getSchool() + "\n" +
                "year level: " + getYearLevel() + "\n" +
                "profile picture path: " + getProfilePicturePath() + "\n" +
                "current lesson: " + getCurrentLessonId() + "\n" +
                "current module: " + getCurrentModuleId() + "\n" +
                "current exam: " + getCurrentExamId() + "\n";
    }
}
