package com.westlyf.domain.lesson;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Created by robertoguazon on 10/06/2016.
 */
public class VideoLesson extends Lesson implements Serializable {

    public VideoLesson() {

    }

    private StringProperty pathLocation = new SimpleStringProperty();

    public String getPathLocation() {
        return pathLocation.get();
    }

    public StringProperty pathLocationProperty() {
        return pathLocation;
    }

    public void setPathLocation(String pathLocation) {
        this.pathLocation.set(pathLocation);
    }

    public String toString() {
        return super.toString() + "\npath location: " + pathLocation.get();
    }

    public String invalid() {
        return super.invalid() + "\n" +
                "path: " + (pathLocation == null || pathLocation.get().equals("") ? "empty" : pathLocation.get());
    }
}
