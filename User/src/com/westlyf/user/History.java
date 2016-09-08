package com.westlyf.user;

import com.westlyf.domain.lesson.Lesson;

import java.util.HashMap;

/**
 * Created by robertoguazon on 08/09/2016.
 */
public class History {

    HashMap<String, Event> events;

    public History() {
        events = new HashMap<>();
    }

    public void addEvent(Lesson lesson, int score, int total) {
        if (lesson == null) return;

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
}
