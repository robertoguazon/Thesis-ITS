package com.westlyf.domain.exercise.practical;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 01/09/2016.
 */
public class PracticalReturnValidatorSerializable implements Serializable {
    static final long serialVersionUID = 0x022;

    protected String expectedReturn;
    protected ArrayList<InputParameterSerializable> inputs = new ArrayList<>();

    public PracticalReturnValidatorSerializable(String expectedReturn, ArrayList<InputParameter> inputs) {
        ArrayList<InputParameterSerializable> inputsSerializable = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            inputsSerializable.add(new InputParameterSerializable(inputs.get(i)));
        }

        this.expectedReturn = expectedReturn;
        this.inputs = inputsSerializable;
    }

    public PracticalReturnValidatorSerializable(StringProperty expectedReturn, ArrayList<InputParameter> inputs) {
        this(expectedReturn.get(),inputs);
    }

    public PracticalReturnValidatorSerializable(PracticalReturnValidator practicalReturnValidator) {
        this(practicalReturnValidator.getExpectedReturn(), practicalReturnValidator.getInputs());
    }

    public String getExpectedReturn() {
        return expectedReturn;
    }

    public ArrayList<InputParameterSerializable> getInputs() {
        return inputs;
    }

}
