package com.westlyf.domain.lesson;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 04/06/2016.
 */
public class LessonFactory {

    public static Level createLevel(String levelId, String title) {
        Level level = new Level();
        level.setLevel(levelId);
        level.setTitle(title);

        return level;
    }

    public static TextLesson createTextLesson(String text) {
        TextLesson textLesson = new TextLesson();
        textLesson.setText(text);
        return textLesson;
    }

    public static TextLesson createTextLesson(String text, ArrayList<String> tags) {
        TextLesson textLesson = new TextLesson();
        textLesson.setText(text);
        textLesson.setTags(tags);
        return textLesson;
    }
}
