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
        super();
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

    @Override
    public String check() {
        return super.check() + "\n" +
                "text: " + ((text == null || text.get().equals("")) ? "empty" : text.get());
    }

    public boolean isValid() {
        if (!super.isValid()) return false;
        if (text == null || text.get().equals("")) return false;

        return true;
    }

    public void copy(TextLesson textLesson) {
        super.copy(textLesson);

        this.setText(textLesson.getText());
    }

    public TextLesson clone() {
        TextLesson clone = new TextLesson();
        clone.copy(this);

        return clone;
    }

}
