package com.westlyf.domain.exercise.practical;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Created by robertoguazon on 01/09/2016.
 */
public class InputParameterSerializable implements Serializable {
    static final long serialVersionUID = 0x023;

    protected String input;
    protected DataType inputType = DataType.STRING;

    public InputParameterSerializable(String input, DataType inputType) {
        this.input = input;
        this.inputType = inputType;
    }

    public InputParameterSerializable(StringProperty input, DataType inputType) {
        this(input.get(), inputType);
    }

    public InputParameterSerializable(InputParameter inputParameter) {
        this(inputParameter.getInput(), inputParameter.getInputType());
    }

    public String getInput() {
        return input;
    }

    public DataType getInputType() {
        return inputType;
    }
}
