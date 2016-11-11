package com.westlyf.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 11/11/2016.
 */
public class Convert {

    public static ArrayList<String> convertToString(ArrayList<StringProperty> stringProperties) {
        ArrayList<String> strings = new ArrayList<String>();
        stringProperties.forEach(stringProperty -> strings.add(stringProperty.get()));
        return strings;
    }

    public static ArrayList<StringProperty> convertToStringProperty(ArrayList<String> strings) {
        ArrayList<StringProperty> stringProperties = new ArrayList<>();
        strings.forEach(string -> stringProperties.add(new SimpleStringProperty(string)));
        return stringProperties;
    }

    public static int indexOfEqualsString(ArrayList<StringProperty> list, String toFind) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get().equals(toFind)) {
                return i;
            }
        }

        return -1;
    }
}
