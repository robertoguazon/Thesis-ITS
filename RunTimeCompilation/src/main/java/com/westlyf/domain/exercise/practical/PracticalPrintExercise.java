package com.westlyf.domain.exercise.practical;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by robertoguazon on 28/08/2016.
 */
public class PracticalPrintExercise extends PracticalExercise {

    protected StringProperty printValidator = new SimpleStringProperty();
    protected BooleanProperty mustMatch = new SimpleBooleanProperty(false);

    public PracticalPrintExercise() {
        super();
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

    public boolean getMustMatch() {
        return mustMatch.get();
    }

    public BooleanProperty mustMatchProperty() {
        return mustMatch;
    }

    public void setMustMatch(boolean mustMatch) {
        this.mustMatch.set(mustMatch);
    }

    public String toString() {
        return super.toString() + "\n" +
                "mustMatch: " + mustMatch.get() + "\n" +
                "printValidator: " + printValidator.get();
    }
}
