package com.westlyf.domain.exercise.practical;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sun.reflect.generics.tree.ReturnType;

/**
 * Created by robertoguazon on 28/08/2016.
 */
public class InputParameter {

    protected StringProperty input = new SimpleStringProperty();
    protected DataType inputType = DataType.STRING;

    public InputParameter(String input, DataType inputType) {
        this(new SimpleStringProperty(input), inputType);
    }

    public InputParameter(StringProperty input, DataType inputType) {
        this.input = input;
        this.inputType = inputType;
    }

    public String getInput() {
        return input.get();
    }

    public StringProperty inputProperty() {
        return input;
    }

    public void setInput(String input) {
        this.input.set(input);
    }

    public DataType getInputType() {
        return inputType;
    }

    public void setInputType(DataType inputType) {
        this.inputType = inputType;
    }

    public boolean isValid() {
        if(input == null || input.isEmpty().get()) return false;
        if (inputType == null) return false;

        return true;
    }

    public String toString() {
        return "input: " + input.get() + "\n" +
                "inputType: " + inputType;
    }
}
