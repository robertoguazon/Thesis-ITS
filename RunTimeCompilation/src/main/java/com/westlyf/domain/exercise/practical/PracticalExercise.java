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
    protected String tempID;
    protected StringProperty explanation = new SimpleStringProperty();

    protected CGroup cGroup;

    public PracticalExercise() {
        super();
        makeTempID();
        cGroup = new CGroup();
    }

    public String getExplanation() {
        return explanation.get();
    }

    public void setExplanation(String explanation) {
        this.explanation.set(explanation);
    }

    public StringProperty explanationProperty() {
        return explanation;
    }

    public void makeTempID() {
        this.tempID = "tid" + System.nanoTime();
    }

    public String getTempID() {
        return tempID;
    }

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

    public boolean isValid() {
        if (!super.isValid()) return false;

        if (instructions == null || instructions.isEmpty().get()) return false;
        //if (code == null || code.isEmpty().get()) return false;
        if (className == null || className.isEmpty().get()) return false;
        if (methodName == null || methodName.isEmpty().get()) return false;

        return true;
    }

    @Override
    public String check() {
        return super.check() + "\n" +
                "instructions: " + ((instructions == null || instructions.isEmpty().get()) ? "empty" : instructions.get()) + "\n" +
                "code: " + ((code == null || code.isEmpty().get()) ? "empty" : code.get()) + "\n" +
                "className: " + ((className == null || className.isEmpty().get()) ? "empty" : className.get()) + "\n" +
                "methodName: " + ((methodName == null || methodName.isEmpty().get()) ? "empty" : methodName.get());
    }


    public void copy(PracticalExercise practicalExercise) {
        super.copy(practicalExercise);

        this.setInstructions(practicalExercise.getInstructions());
        this.setCode(practicalExercise.getCode());
        this.setClassName(practicalExercise.getClassName());
        this.setMethodName(practicalExercise.getMethodName());
        this.setExplanation(practicalExercise.getExplanation());
        this.setCGroup(practicalExercise.getCGroup());
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "className: " + className.get() + "\n" +
                "methodName: " + methodName.get() + "\n" +
                "instructions: " + instructions.get() + "\n" +
                "code: " + code.get() + "\n" +
                "explanation: " + explanation + "\n" +
                "cGroup: " + cGroup.toString();
    }

    public void setCGroup(CGroup cGroup) {
        this.cGroup = cGroup;
    }

    public CGroup getCGroup() {
        return cGroup;
    }

    public void addCString(CString cString) {
        if (cGroup == null) {
            cGroup = new CGroup();
        }
        this.cGroup.addCString(cString);
    }

    public void removeCString(CString cString) {
        this.cGroup.removeCString(cString);
    }

    public void clearCGroup() {
        this.cGroup.clearAll();
    }

    public int getCGroupSize() {
        return cGroup.getSize();
    }

    public int checkCGroup(StringProperty codeText) {
        return cGroup.check(codeText);
    }

    public String getCStringTip(int i) {
        return cGroup.getCStringTip(i);
    }


}
