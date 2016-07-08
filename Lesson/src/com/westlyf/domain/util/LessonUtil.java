package com.westlyf.domain.util;

import com.westlyf.domain.lesson.Lesson;
import com.westlyf.domain.lesson.Level;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 08/06/2016.
 */
public class LessonUtil {

    public static ArrayList<Lesson> findLessons(Level level, ArrayList<Lesson> lessons) {
        String levelTagsString = level.getTagsString();
        ArrayList<Lesson> matchingLessons = new ArrayList<>();

        for (Lesson l : lessons) {
            if (levelTagsString == null || l.getTagsString() == null) return null;
            if (levelTagsString.equals(l.getTagsString())) {
                matchingLessons.add(l);
            }
        }

        return matchingLessons;
    }

}
