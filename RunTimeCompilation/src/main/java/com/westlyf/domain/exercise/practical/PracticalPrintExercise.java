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

    @Override
    public boolean isValid() {
        if (!super.isValid()) return false;

        if (printValidator == null || printValidator.isEmpty().get()) return false;
        if (mustMatch == null) return false;

        return true;
    }

    public void copy(PracticalPrintExercise practicalPrintExercise) {
        super.copy(practicalPrintExercise);

        this.setPrintValidator(practicalPrintExercise.getPrintValidator());
        this.setMustMatch(practicalPrintExercise.getMustMatch());
    }

    public PracticalPrintExercise clone() {
        PracticalPrintExercise clone = new PracticalPrintExercise();
        clone.copy(this);

        return clone;
    }

    @Override
    public String check() {
        return super.check() +"\n" +
                "printValidator: " + ((printValidator == null || printValidator.isEmpty().get()) ? "printValidator" : printValidator.get()) + "\n" +
                "mustMatch: " + ((mustMatch == null) ? "mustMatch" : mustMatch.get());
    }

    //TODO - used for checking - to be fixed
    public boolean evaluate(String output) {
        if (mustMatch.get()) {
            output = output.trim();
            return (printValidator.get().equals(output));
        } else {
            String checker = printValidator.get().toLowerCase();
            String toBeChecked = output.toLowerCase();

            System.out.println("contains: " + toBeChecked.contains(checker)); //TODO -delete
            if (toBeChecked.contains(checker)) return true;
            else return false;
        }
    }

    public String toString() {
        return super.toString() + "\n" +
                "practicalType: PRINT \n" +
                "mustMatch: " + mustMatch.get() + "\n" +
                "printValidator: " + printValidator.get();
    }
}
