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

        //TODO - fix not working
        if (level.hasSubLevels()) {
            //return sublevels percentage
            double sumPercentage = 0; //just add without division of (size * 100)
            double totalPercentage= 0; // sumPercentage / (size * 100);
            int counter = 0;
            ArrayList<Level> subLevels = level.getSubLevels();
            for (int i = 0; i < subLevels.size(); i++) {
                double levelExercisePercentage = getLevelExercisePercentage(subLevels.get(i));
                if (!Double.isNaN(levelExercisePercentage)) {
                    sumPercentage += levelExercisePercentage;
                    counter++;
                }
            }

            totalPercentage = sumPercentage / (counter * 100);
            return totalPercentage * 100;
        } else {
            return computeExercisePercentage(level);
        }
    }

    private double computeExercisePercentage(Level level) {
        String levelId = level.getLevel();
        LessonsLevel lessonLevel = lessonsLevels.get(levelId);

        if (lessonLevel == null) return 0.0 / 0.0;

        ArrayList<String> lessonIds = lessonsLevels.get(levelId).getLessonIds();

        double sumPercentage = 0; //just add without division of (size * 100)
        double totalPercentage = 0; // sumPercentage / (size * 100);
        int counter = 0;

        for (int i = 0; i < lessonIds.size(); i++) {
            Event event = events.get(lessonIds.get(i));

            if (event.getEventType() == EventType.EXAM) continue;
            if (event.getEventType() == EventType.PRACTICAL_EXERCISE || event.getEventType() == EventType.QUIZ_EXERCISE) {
                sumPercentage += event.getTotalPercentage();
                counter++;
            }
        }

        totalPercentage = sumPercentage / (counter * 100);
        return totalPercentage * 100;
    }

    public double getLevelExamPercentage(Level level) {

        //TODO - fix not working
        if (level.hasSubLevels()) {
            //return sublevels percentage
            double sumPercentage = 0; //just add without division of (size * 100)
            double totalPercentage= 0; // sumPercentage / (size * 100);
            int counter = 0;
            ArrayList<Level> subLevels = level.getSubLevels();
            for (int i = 0; i < subLevels.size(); i++) {

                double levelExamPercentage = getLevelExamPercentage(subLevels.get(i));
                if (!Double.isNaN(levelExamPercentage)) {
                    sumPercentage += levelExamPercentage;
                    counter++;
                }
            }

            totalPercentage = sumPercentage / (counter * 100);
            return totalPercentage * 100;
        } else {
            return computeExamPercentage(level);
        }
    }

    private double computeExamPercentage(Level level) {
        String levelId = level.getLevel();
        ArrayList<String> lessonIds = lessonsLevels.get(levelId).getLessonIds();

        double sumPercentage = 0; //just add without division of (size * 100)
        double totalPercentage = 0; // sumPercentage / (size * 100);
        int counter = 0;

        for (int i = 0; i < lessonIds.size(); i++) {
            Event event = events.get(lessonIds.get(i));

            if (event.getEventType() == EventType.EXAM) {
                sumPercentage += event.getTotalPercentage();
                counter++;
            }
        }

        totalPercentage = sumPercentage / (counter * 100);
        return totalPercentage * 100;
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
