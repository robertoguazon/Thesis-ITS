package com.westlyf.domain.exercise.practical;

import com.westlyf.domain.exercise.Exercise;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by robertoguazon on 24/08/2016.
 */
public abstract class PracticalExercise extends Exercise {


    protected StringProperty instructions = new SimpleStringProperty();
    protected StringProperty code = new SimpleStringProperty();
    protected StringProperty className = new SimpleStringProperty();
    protected StringProperty methodName = new SimpleStringProperty();

    protected PracticalType practicalType = PracticalType.PRINT;

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

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "className: " + className.get() + "\n" +
                "methodName: " + methodName.get() + "\n" +
                "practicalType: " + practicalType + "\n" +
                "instructions: " + instructions.get() + "\n" +
                "code: " + code.get();

    }
}
