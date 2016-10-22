package com.westlyf.domain.exercise.practical;

import com.westlyf.utils.StringUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by robertoguazon on 13/10/2016.
 * <br>
 * <em>Not serializable.</em>
 * <br>
 * CString is used together with CGroup for checking source code in practical exercises
 * whether the examinee is cheating or not.
 */
public class CString {


    private ArrayList<StringProperty> equivalents;
    private StringProperty tip = new SimpleStringProperty();

    public CString() {
        equivalents = new ArrayList<>();
    }

    public CString(StringProperty cString) {
        this();
        equivalents.add(cString);
    }

    public CString(StringProperty ... cString) {
        this();
        equivalents.addAll(Arrays.asList(cString));
    }

    public CString(CStringSerializable cStringSerializable) {
        this();
        cStringSerializable.getEquivalents().forEach(string -> equivalents.add(new SimpleStringProperty(string)));
        tip.set(cStringSerializable.getTip());
    }

    public ArrayList<StringProperty> getEquivalents() {
        return equivalents;
    }

    public void addEquivalent(StringProperty cString) {
        this.equivalents.add(cString);
    }

    public void removeEquivalents(StringProperty cString) {
        this.equivalents.remove(cString);
    }

    public void clearAll() {
        this.equivalents.clear();
        tip.set("");
    }

    public void clearAllExcept(StringProperty cString) {
        clearAll();
        addEquivalent(cString);
    }

    public StringProperty cStringProperty(int index) {
        return this.equivalents.get(index);
    }

    /**
    * used for checking whether certain strings are present in the code text
    * */
    public boolean check(StringProperty codeText) {
        String codeTextString = StringUtil.removeWhiteSpaces(codeText.get());
        for (StringProperty cString : equivalents) {
            if (codeTextString.contains(StringUtil.removeWhiteSpaces(cString.get()))) {
                return true;
            }
        }

        return false;
    }

    public String toString() {
        return  "tip: " + tip.get() + "\n" +
                "equivalents: " + equivalents.stream()
                          .map(property -> property.get())
                          .collect(Collectors.joining(", "));
    }

    public void setTip(String tip) {
        this.tip.set(tip);
    }

    public String getTip() {
        return tip.get();
    }

    public StringProperty tipProperty() {
        return tip;
    }
}
