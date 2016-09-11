package com.westlyf.domain.exercise;

import com.westlyf.domain.lesson.Lesson;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Created by robertoguazon on 13/06/2016.
 */
public class Exercise extends Lesson implements Serializable {

    private IntegerProperty totalItems = new SimpleIntegerProperty();
    private IntegerProperty totalScore = new SimpleIntegerProperty();

    public Exercise() {
        super();
    }

    public int getTotalItems() {
        return totalItems.get();
    }

    public int getTotalScore() {
        return totalScore.get();
    }

    public void setTotalItems(int totalItems) {
        this.totalItems.set(totalItems);
    }

    public void setTotalScore(int totalScore) {
        this.totalScore.set(totalScore);
    }

    public String toString() {
        //TODO add the fields
        return super.toString();
    }

    public void copy(Exercise exercise) {
        super.copy(exercise);

        this.setTotalItems(exercise.getTotalItems());
        this.setTotalScore(exercise.getTotalScore());
    }

    public Exercise clone() {
        Exercise clone = new Exercise();
        clone.copy(this);

        return clone;
    }

    @Override
    public String check() {
        return super.check();
    }

    @Override
    public boolean isValid() {
        //TODO - fix
        return super.isValid();
    }

}
