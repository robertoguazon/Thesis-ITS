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

    public void clearParametersTypes() {
        this.parametersTypes.clear();
    }

    public void removeReturnValidator(PracticalReturnValidator returnValidator) {
        this.returnValidators.remove(returnValidator);
    }

    public void clearReturnValidators() {
        this.returnValidators.clear();
    }

    @Override
    public boolean isValid() {
        if (!super.isValid()) return false;
        if (returnValidators == null) return false;
        if (code == null || code.isEmpty().get()) return false;
        for (int i = 0; i < returnValidators.size(); i++) {
            if (!returnValidators.get(i).isValid()) return false;
        }



        return true;
    }

    public ArrayList<PracticalReturnValidator> getReturnValidators() {
        return returnValidators;
    }

    public void copy(PracticalReturnExercise practicalReturnExercise) {
        super.copy(practicalReturnExercise);

        this.setReturnValidators(practicalReturnExercise.getReturnValidators());
        this.setReturnType(practicalReturnExercise.getReturnType());
        this.setParameterTypes(practicalReturnExercise.getParameterTypes());
    }

    public PracticalReturnExercise clone() {
        PracticalReturnExercise clone = new PracticalReturnExercise();
        clone.copy(this);

        return clone;
    }

    @Override
    public String check() {
        boolean returnValidatorsValid = true;
        if (returnValidators.isEmpty()) {
            returnValidatorsValid = false;
        } else {
            for (int i = 0; i < returnValidators.size(); i++) {
                if (!returnValidators.get(i).isValid()) {
                    returnValidatorsValid = false;
                    break;
                }
            }
        }

        boolean parametersTypesValid = true;
        if (parametersTypes.isEmpty()) {
            parametersTypesValid = false;
        } else {
            for (int i = 0; i < parametersTypes.size(); i++) {
                if (parametersTypes.get(i) == null) {
                    parametersTypesValid = false;
                    break;
                }
            }
        }

        return super.check() + "\n" +
                "returnValidators: " + ((returnValidatorsValid) ? "valid" : "not valid") + "\n" +
                "returnType: " + ((returnType == null) ? "printValidator" : returnType) + "\n" +
                "parameterTypes: " + ((parametersTypesValid) ? "valid" : "not valid");
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "practicalType: RETURN \n" +
                "returnType: " + returnType + "\n" +
                "parameterSize: " + parametersTypes.size() + "\n" +
                "parametersTypes: " + parametersTypes.toString() + "\n" +
                "returnValidators: " + returnValidators.toString();
    }
}
