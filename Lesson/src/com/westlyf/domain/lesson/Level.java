package com.westlyf.domain.lesson;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by robertoguazon on 04/06/2016.
 */
public class Level implements Serializable {

    private StringProperty levelId = new SimpleStringProperty();
    private StringProperty title = new SimpleStringProperty();
    private ArrayList<Level> subLevels = new ArrayList<>();
    private BooleanProperty unlocked = new SimpleBooleanProperty();

    private ArrayList<String> tags;
    private Node graphics;

    public Level() {}

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getLevel() {
        return levelId.get();
    }

    public StringProperty levelProperty() {
        return levelId;
    }

    public void setLevel(String level) {
        this.levelId.set(level);
    }

    public void addSubLevel(Level subLevel) {
        this.subLevels.add(subLevel);
    }

    public void addSubLevels(Level ... subLevel) {
        for (int i = 0; i < subLevel.length; i++) {
            this.subLevels.add(subLevel[i]);
        }
    }

    public Level getSubLevel(int i) {
        if (this.subLevels.get(i) != null) return this.subLevels.get(i);
        else return null;
    }

    public ArrayList<Level> getSubLevels() {
        return this.subLevels;
    }

    public String toString() {
        return this.getTitle();
    }

    public boolean hasSubLevels() {
        return this.subLevels != null || !this.subLevels.isEmpty();
    }

    public String getTagsString() {
        if (this.tags == null || this.tags.equals("")) return null;

        Collections.sort(this.tags, String.CASE_INSENSITIVE_ORDER);

        String stringTags = "";
        for (String s : this.tags) {
            stringTags += s + ", ";
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

    public boolean isUnlocked() {
        return unlocked.get();
    }

    public BooleanProperty unlockedProperty() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked.set(unlocked);
    }

    public void setGraphics(Image image) {
        this.graphics = new ImageView(image);
    }

    public Node getGraphics() {
        return this.graphics;
    }

    public boolean hasGraphics() {
        return this.graphics != null;
    }
}
