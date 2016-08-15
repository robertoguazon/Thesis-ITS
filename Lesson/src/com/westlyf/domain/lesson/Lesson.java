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
    private String lessonId;

    public Lesson() {
        //make id
        //this.lessonId = "lid" + System.nanoTime();
        tags = new ArrayList<>();
    }

    public void makeID() {
        this.lessonId = "lid" + System.nanoTime();
    }

    public void setID(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getTagsString() {
        if (this.tags == null || this.tags.equals("")) return null;

        Collections.sort(this.tags, new StringPropertyComparator());

        String stringTags = "";
        for (StringProperty s : this.tags) {
            stringTags += s.get() + ",";
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

    public void addTag(String tag) {
        addTag(new SimpleStringProperty(tag));
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

    public String toString() {
        return "title: " + title.get() + "\n" +
                "tags: " + getTagsString() + "\n" +
                "lessonid: " + lessonId;
    }

    public String check() {
        return "Invalid Lesson \n" +
                "title: " + ((title == null || title.get().equals("")) ? "empty" : title.get()) + "\n" +
                "tags: " + ((tags.isEmpty() || tags == null) ? "empty" : getTagsString()) + "\n" +
                "lessonId: " + ((lessonId == null || lessonId.equals("")) ? "empty" : lessonId);
    }

    public boolean isValid() {
        if (title == null || title.get().equals("")) return false;
        if (tags == null || tags.isEmpty()) return false;

        return true;
    }
}
