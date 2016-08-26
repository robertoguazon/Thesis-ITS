package com.westlyf.domain.exercise;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by robertoguazon on 24/08/2016.
 */
public class PracticalExercise extends Exercise {


    private StringProperty instructions = new SimpleStringProperty();
    private StringProperty code = new SimpleStringProperty();
    private StringProperty className = new SimpleStringProperty();
    private StringProperty methodName = new SimpleStringProperty();

    private PracticalType practicalType = PracticalType.PRINT;
    private ArrayList<StringProperty[][]> returnValidators = new ArrayList<>();
    private StringProperty printValidator = new SimpleStringProperty();

    public PracticalExercise() {super();}

    public void setInstructions(String instructions) {
        this.instructions.set(instructions);
    }

    public StringProperty instructionsProperty() {
        return instructions;
    }

    public String getInstructions() {
        return instructions.get();
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public void setReturnValidators(ArrayList<StringProperty[][]> returnValidators) {
        this.returnValidators = returnValidators;
    }

    public void addReturnValidator(String pass, String expectedOutput) {
        addReturnValidator(new SimpleStringProperty(pass), new SimpleStringProperty(expectedOutput));
    }

    public void addReturnValidator(StringProperty pass, StringProperty expectedOutput) {
        this.returnValidators.add(new StringProperty[][] {{pass,expectedOutput}});
    }

    public void setPrintValidator(String printValidator) {
        this.printValidator.set(printValidator);
    }

    public String getPrintValidator() {
        return printValidator.get();
    }

    public StringProperty printValidatorProperty() {
        return printValidator;
    }

    public String getClassName() {
        return className.get();
    }

    public StringProperty classNameProperty() {
        return className;
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public String getMethodName() {
        return methodName.get();
    }

    public StringProperty methodNameProperty() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName.set(methodName);
    }
}
