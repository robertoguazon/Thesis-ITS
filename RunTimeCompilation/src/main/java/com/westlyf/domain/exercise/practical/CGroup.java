package com.westlyf.domain.exercise.practical;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by robertoguazon on 13/10/2016.
 * <br>
 * <em>Not serializable.</em>
 * <br>
 * CGroup is used together with CString for checking source code in practical exercises
 * whether the examinee is cheating or not.
 */
public class CGroup {

    private ArrayList<CString> cStrings;

    public CGroup() {
        cStrings = new ArrayList<>();
    }

    public CGroup(CString cString) {
        this();
        cStrings.add(cString);
    }

    public CGroup(CString ... cStringsArray) {
        this();
        cStrings.addAll(Arrays.asList(cStringsArray));
    }

    public CGroup(CGroupSerializable cGroupSerializable) {
        this();
        cGroupSerializable.getCStrings().forEach(cStringSerializable -> this.cStrings.add(new CString(cStringSerializable)));
    }

    public ArrayList<CString> getCStrings() {
        return cStrings;
    }

    public void addCString(CString cString) {
        if (cStrings == null) {
            cStrings = new ArrayList<>();
        }
        this.cStrings.add(cString);
    }


    public void removeCString(CString cString) {
        this.cStrings.remove(cString);
    }

    public void clearAll() {
        this.cStrings.clear();
    }

    public void clearAllExcept(CString cString) {
        clearAll();
        addCString(cString);
    }

    public int check(StringProperty codeText) {

        for (int i = 0; i < cStrings.size(); i++) {
            if (!cStrings.get(i).check(codeText)) {
                return i;
            }
        }

        return -1;
    }

    public int getSize() {
        return this.cStrings.size();
    }

    public String toString() {
        if (cStrings == null) {
            cStrings = new ArrayList<>();
        }

        String cs = cStrings.stream()
                            .map(cString -> cString.toString())
                            .collect(Collectors.joining("\n\t"));

        return "cStrings: \n\t" + cs;
    }

    public CString getCString(int i) {
        return cStrings.get(i);
    }

    public String getCStringTip(int i) {
        return cStrings.get(i).getTip();
    }

}
