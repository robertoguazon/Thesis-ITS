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
    protected DataType returnType = DataType.STRING;
    protected ArrayList<DataType> parametersTypes = new ArrayList<>();


    public PracticalReturnExercise() {
        super();
    }

    public DataType getReturnType() {
        return returnType;
    }

    public void setReturnType(DataType returnType) {
        this.returnType = returnType;
    }

    public void setReturnValidators(ArrayList<PracticalReturnValidator> returnValidators) {
        this.returnValidators = returnValidators;
    }

    public void addReturnValidator(PracticalReturnValidator returnValidator) {
        this.returnValidators.add(returnValidator);
    }

    public int getParametersSize() {
        return parametersTypes.size();
    }

    public void setParameterTypes(ArrayList<DataType> parameterTypes) {
        this.parametersTypes = parameterTypes;
    }

    public ArrayList<DataType> getParameterTypes() {
        return this.parametersTypes;
    }

    public void addParameterType(DataType parameterType) {
        this.parametersTypes.add(parameterType);
    }


    @Override
    public String toString() {
        return super.toString() + "\n" +
                "returnType: " + returnType + "\n" +
                "parameterSize: " + parametersTypes.size() + "\n" +
                "parametersTypes: " + parametersTypes.toString() + "\n" +
                "returnValidators: " + returnValidators.toString();
    }
}
