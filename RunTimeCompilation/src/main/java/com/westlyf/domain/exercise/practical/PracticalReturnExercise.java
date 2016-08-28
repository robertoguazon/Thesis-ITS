package com.westlyf.domain.exercise.practical;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by robertoguazon on 28/08/2016.
 */
public class PracticalReturnExercise extends PracticalExercise {

    protected ArrayList<PracticalReturnValidator> returnValidators = new ArrayList<>();
    protected IntegerProperty parameterSize = new SimpleIntegerProperty();

    public PracticalReturnExercise() {
        super();
    }

    public void setReturnValidators(ArrayList<PracticalReturnValidator> returnValidators) {
        this.returnValidators = returnValidators;
    }

    public void addReturnValidator(PracticalReturnValidator returnValidator) {
        this.returnValidators.add(returnValidator);
    }

    public int getParameterSize() {
        return parameterSize.get();
    }

    public IntegerProperty parameterSizeProperty() {
        return parameterSize;
    }

    public void setParameterSize(int parameterSize) {
        this.parameterSize.set(parameterSize);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "parameterSize: " + parameterSize.get() + "\n" +
                "returnValidators: " + returnValidators.toString();
    }
}
