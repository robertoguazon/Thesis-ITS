package com.westlyf.domain.exercise.practical;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 28/08/2016.
 */
public class PracticalReturnValidator {

    protected StringProperty expectedReturn = new SimpleStringProperty();
    protected ArrayList<InputParameter> inputs = new ArrayList<>();

    public PracticalReturnValidator() {

    }

    public void addInput(String input, DataType inputType) {
        addInput(new SimpleStringProperty(input), inputType);
    }

    public void addInput(InputParameter inputParameter) {
        this.inputs.add(inputParameter);
    }

    public void addInput(StringProperty input, DataType inputType) {
        this.inputs.add(new InputParameter(input, inputType));
    }

    public void setInputs(ArrayList<InputParameter> inputs) {
        this.inputs = inputs;
    }

    public void bindAndAddTextField(TextField textField, DataType inputType) {
        StringProperty newInput = new SimpleStringProperty();
        newInput.bind(textField.textProperty());
        this.inputs.add(new InputParameter(newInput, inputType));
    }

    public void clearInput(StringProperty input) {
        this.inputs.remove(input);
    }

    public void clearInputs() {
        this.inputs.clear();
    }

    public void setInputTypeOfInputParameter(InputParameter inputParameter, DataType inputType) {
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).equals(inputParameter)) {
                inputs.get(i).setInputType(inputType);
            }
        }
    }

    public void setExpectedReturn(String expectedReturn) {
        this.expectedReturn.set(expectedReturn);
    }

    public StringProperty expectedReturnProperty() {
        return this.expectedReturn;
    }

    public String getExpectedReturn() {
        return this.expectedReturn.get();
    }


    public boolean isValid() {
        if (expectedReturn == null || expectedReturn.isEmpty().get()) return false;
        if (inputs == null) return false;
        for (int i = 0; i < inputs.size(); i++) {
            if (!inputs.get(i).isValid()) return false;
        }

        return true;
    }

    @Override
    public String toString() {

        String inputsString = "";
        for (InputParameter input : inputs) {
            inputsString += input.toString() + "\n";
        }

        return "expectedReturn: " + expectedReturn.get() + "\n" +
                "inputs: \n" + inputsString;
    }
}
