package com.westlyf.domain.lesson;

import com.westlyf.domain.util.comparator.StringPropertyComparator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by robertoguazon on 03/06/2016.
 */
public class Lesson implements Serializable {

    protected StringProperty title = new SimpleStringProperty();
    protected ArrayList<StringProperty> tags;
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

        Collections.sort(this.tags, new StringPropertyComparator());

        String stringTags = "";
        for (StringProperty s : this.tags) {
            stringTags += s + ",";
        }

        return stringTags;
    }

    public void setTags(ArrayList<StringProperty> tags) {
        this.tags = tags;
    }

    public ArrayList<StringProperty> getTags() {
        return this.tags;
    }

    public void addTag(StringProperty tag) {
        if (this.tags == null) tags = new ArrayList<>();
        this.tags.add(tag);
    }

    public void removeTag(StringProperty tag) {
        for (int i = 0; i < tags.size(); i++) {
            if (this.tags.get(i).equals(tag)) {
                this.tags.remove(i);
            }
        }
    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }

    public String getTitle() {
        return this.title.getValue();
    }

    public StringProperty titleProperty() {
        return this.title;
    }
}
