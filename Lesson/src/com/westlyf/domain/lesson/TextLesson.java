package com.westlyf.domain.lesson;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Created by robertoguazon on 03/06/2016.
 */
public class TextLesson extends Lesson implements Serializable {

    private StringProperty text = new SimpleStringProperty();

    public TextLesson() {
        //empty
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public String toString() {
        return super.toString() + "\nText: " + text.get();
    }

}
