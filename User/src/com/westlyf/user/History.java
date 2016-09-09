package com.westlyf.user;

import com.westlyf.domain.lesson.Lesson;
import com.westlyf.domain.lesson.Level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by robertoguazon on 08/09/2016.
 */
public class History implements Serializable {
    static final long serialVersionUID = 0x102;


    private HashMap<String, Event> events;
    private HashMap<String, LessonsLevel> lessonsLevels;


    public History() {
        events = new HashMap<>();
        lessonsLevels = new HashMap<>();
    }

    public void addEvent(Level level, Lesson lesson, int score, int total) {
        if (lesson == null) return;

        if (lessonsLevels.containsKey(level.getLevel())) {
            updateLessonsLevel(level, lesson);
        } else {
            addLessonsLevel(level, lesson);
        }

        if (events.containsKey(lesson.getLessonId())) {
            updateEvent(lesson,score,total);
        } else {
            events.put(lesson.getLessonId(), new Event(lesson,score,total));
        }
    }

    public void updateEvent(Lesson lesson, int score, int total) {
        if (lesson == null) return;

        Event event = events.get(lesson.getLessonId());
        event.update(score,total);
        events.replace(lesson.getLessonId(),event);
    }

    public void addLessonsLevel(Level level, Lesson lesson) {
        lessonsLevels.put(level.getLevel(), new LessonsLevel(level.getLevel(), lesson.getLessonId()));
    }

    public void updateLessonsLevel(Level level, Lesson lesson) {
        LessonsLevel lessonsLevel = lessonsLevels.get(level.getLevel());
        if (!lessonsLevel.contains(lesson.getLessonId())) {
            lessonsLevel.addLessonId(lesson.getLessonId());
            lessonsLevels.replace(level.getLevel(), lessonsLevel);
        }
    }

    public double getLevelExercisePercentage(Level level) {
        if (level.hasSubLevels()) {
            //return sublevels percentage
            double sumPercentage = 0; //just add without division of (size * 100)
            double totalPercentage= 0; // sumPercentage / (size * 100);

            ArrayList<Level> subLevels = level.getSubLevels();
            for (int i = 0; i < subLevels.size(); i++) {
                sumPercentage += computeExercisePercentage(subLevels.get(i));

            }

            totalPercentage = sumPercentage / (subLevels.size() * 100);
            return (Math.round(totalPercentage * 100.0)) / 100.0;
        } else {
            return computeExercisePercentage(level);
        }
    }

    private double computeExercisePercentage(Level level) {
        LessonsLevel lessonsLevel = lessonsLevels.get(level.getLevel());
        ArrayList<String> lessonIds = lessonsLevel.getLessonIds();

        double sumPercentage = 0; //just add without division of (size * 100)
        double totalPercentage = 0; // sumPercentage / (size * 100);

        for (int i = 0; i < lessonIds.size(); i++) {

            Event event = events.get(lessonIds.get(i));

            if (event.getEventType() == EventType.EXAM) continue; //only exercises are computed
            if (event.getEventType() == EventType.PRACTICAL_EXERCISE || event.getEventType() == EventType.QUIZ_EXERCISE) {
                sumPercentage += event.getTotalPercentage();
            }
        }

        totalPercentage = sumPercentage / (lessonIds.size() * 100);
        return (Math.round(totalPercentage * 100.0)) / 100.0;
    }

    public double getLevelExamPercentage(Level level) {
        if (level.hasSubLevels()) {
            //return sublevels percentage
            double sumPercentage = 0; //just add without division of (size * 100)
            double totalPercentage= 0; // sumPercentage / (size * 100);

            ArrayList<Level> subLevels = level.getSubLevels();
            for (int i = 0; i < subLevels.size(); i++) {
                sumPercentage += computeExamPercentage(subLevels.get(i));

            }

            totalPercentage = sumPercentage / (subLevels.size() * 100);
            return (Math.round(totalPercentage * 100.0)) / 100.0;
        } else {
            return computeExercisePercentage(level);
        }
    }

    private double computeExamPercentage(Level level) {
        LessonsLevel lessonsLevel = lessonsLevels.get(level.getLevel());
        ArrayList<String> lessonIds = lessonsLevel.getLessonIds();

        double sumPercentage = 0; //just add without division of (size * 100)
        double totalPercentage = 0; // sumPercentage / (size * 100);

        for (int i = 0; i < lessonIds.size(); i++) {

            Event event = events.get(lessonIds.get(i));

            if (event.getEventType() == EventType.EXAM) {
                sumPercentage += event.getTotalPercentage();
            }
        }

        totalPercentage = sumPercentage / (lessonIds.size() * 100);
        return (Math.round(totalPercentage * 100.0)) / 100.0;
    }

    public int getViewCount(String lessonId) {
        return events.get(lessonId).getViews();
    }

    @Override
    public String toString() {
        return  "events: " + events.toString() + "\n" +
                "lessonLevels: " + lessonsLevels.toString();
    }
}
