package com.westlyf.user;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 08/09/2016.
 */
public class LessonsLevel {

    private String levelId;
    private ArrayList<String> lessonIds;

    public LessonsLevel(String levelId) {
        this(levelId, null);
    }

    public LessonsLevel(String levelId, String lessonId) {
        lessonIds = new ArrayList<>();

        if (lessonId != null) {
            lessonIds.add(lessonId);
        }
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public void addLessonId(String lessonId) {
        if (!lessonIds.contains(lessonId)) {
            lessonIds.add(lessonId);
        }
    }

    public boolean contains(String lessonId) {
        if (lessonIds.contains(lessonId)) return true;
        else return false;
    }

    public ArrayList<String> getLessonIds() {
        return lessonIds;
    }

}
