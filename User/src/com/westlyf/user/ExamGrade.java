package com.westlyf.user;

import java.util.Date;

/**
 * Created by Yves on 10/13/2016.
 */
public class ExamGrade {

    private int id;
    private int userId;
    private String exam_title;
    private int rawGrade;
    private int totalItems;
    private int percentGrade;
    private String status;
    private Date dateTaken;

    public ExamGrade() {
    }

    public ExamGrade(int userId, String exam_title, int rawGrade, int totalItems, int percentGrade, String status) {
        this.userId = userId;
        this.exam_title = exam_title;
        this.rawGrade = rawGrade;
        this.totalItems = totalItems;
        this.percentGrade = percentGrade;
        this.status = status;
    }

    public String toString(){
        return "\nid: " + getId() + "\n" +
                "userId: " + getUserId() + "\n" +
                "exam_title: " + getExam_title() + "\n" +
                "rawGrade: " + getRawGrade() + "\n" +
                "totalItems: " + getTotalItems() + "\n" +
                "percentGrade: " + getPercentGrade() + "\n" +
                "status: " + getStatus() + "\n" +
                "dateTaken: " + getDateTaken() + "\n";
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

    public int getRawGrade() {
        return rawGrade;
    }

    public void setRawGrade(int rawGrade) {
        this.rawGrade = rawGrade;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getPercentGrade() {
        return percentGrade;
    }

    public void setPercentGrade(int percentGrade) {
        this.percentGrade = percentGrade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }
}
