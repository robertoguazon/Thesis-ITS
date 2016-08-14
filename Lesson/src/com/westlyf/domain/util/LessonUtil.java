package com.westlyf.domain.util;

import com.westlyf.domain.lesson.Lesson;
import com.westlyf.domain.lesson.Level;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 08/06/2016.
 */
public class LessonUtil {

    //use sql to get lessons per tag
    public static ArrayList<Lesson> findLessonsExactly(Level level, ArrayList<Lesson> lessons) {
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

    public static ArrayList<Lesson> findLessons(Level level, ArrayList<Lesson> lessons) {
        ArrayList<StringProperty> levelTags = level.getTags();
        ArrayList<Lesson> matchingLessons = new ArrayList<>();

        for (Lesson l : lessons) {
            if (level.getTagsString() == null || l.getTagsString() == null) return null;

            ArrayList<StringProperty> lTags = l.getTags();
            for (StringProperty tag : lTags) {
                if (!levelTags.contains(tag)) {
                    break;
                } else {
                    matchingLessons.add(l);
                }
            }

        }

        return matchingLessons;
    }

    public static ArrayList<StringProperty> tagsToStringProperty(String s) {

        if (s == null || s.equals("")) {
            return null;
        }

        s = s.trim();
        String[] tags = s.split(",");
        ArrayList<StringProperty> newTags = new ArrayList<>();

        for (int i = 0; i < tags.length; i++) {
            newTags.add(new SimpleStringProperty(tags[i]));
        }

        return newTags;
    }

}
