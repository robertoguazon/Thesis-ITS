package com.westlyf.user;

/**
 * Created by Yves on 10/13/2016.
 */
public class ExamGrade {

    private int id;
    private int userId;
    private String exam_title;
    private int grade;

    public String toString(){
        return "\nid: " + getId() + "\n" +
                "userId: " + getUserId() + "\n" +
                "exam_title: " + getExam_title() + " grade: " + getGrade();
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

    public String getExam_title() {
        return exam_title;
    }

    public void setExam_title(String exam_title) {
        this.exam_title = exam_title;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
