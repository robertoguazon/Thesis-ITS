package com.westlyf.user;

import com.westlyf.domain.lesson.Lesson;

/**
 * Created by robertoguazon on 08/09/2016.
 */
public class Event {

    private final String lessonId;

    private int totalItems;
    private int totalScores;

    public Event(Lesson lesson, int score, int items) {
        this.lessonId = lesson.getLessonId();
        totalScores += score;
        totalItems += items;
    }

    public void update(int score, int items) {
        totalScores += score;
        totalItems += items;
    }

    public String getLessonId() {
        return lessonId;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalScores() {
        return totalScores;
    }

    public double getTotalPercentage() {
        return (double)(Math.round((totalScores/totalItems) * 100.0) / 100.0);
    }

}
