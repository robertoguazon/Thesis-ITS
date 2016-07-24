package com.westlyf.domain.lesson;

import javafx.scene.control.TreeItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by robertoguazon on 03/06/2016.
 */
public class Lesson implements Serializable {

    protected ArrayList<String> tags;
    private final String lessonId;

    public Lesson() {
        //make id
        this.lessonId = "lid" + System.nanoTime();
        tags = new ArrayList<>();
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getTagsString() {
        if (this.tags == null || this.tags.equals("")) return null;

        Collections.sort(this.tags, String.CASE_INSENSITIVE_ORDER);

        String stringTags = "";
        for (String s : this.tags) {
            stringTags += s + ",";
        }

        return stringTags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void addTag(String tag) {
        if (this.tags == null) tags = new ArrayList<>();
        this.tags.add(tag);
    }

    public void removeTag(String tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (this.tags.get(i).equals(tag)) {
                this.tags.remove(i);
            }
        }
    }

}
