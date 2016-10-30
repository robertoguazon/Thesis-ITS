package com.westlyf.utils;

import com.westlyf.domain.exercise.practical.DataType;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 31/08/2016.
 */
public class DataTypeUtil {

    public static Class[] toClassArray(ArrayList<DataType> parametersTypes) {
        Class[] parametersTypesArray = new Class[parametersTypes.size()];

        for (int i = 0; i < parametersTypes.size(); i++) {
            switch (parametersTypes.get(i)) {
                case DOUBLE:
                    parametersTypesArray[i] = double.class;
                    break;
                case INT:
                    parametersTypesArray[i] = int.class;
                    break;
                case FLOAT:
                    parametersTypesArray[i] = float.class;
                    break;
                case STRING:
                    parametersTypesArray[i] = String.class;
                    break;
                case CHAR:
                    parametersTypesArray[i] = char.class;
                    break;
                case INT_ARRAY:
                    parametersTypesArray[i] = int[].class;
                    break;
                default:
                    parametersTypesArray = null;
                    break;
            }
        }

        return parametersTypesArray;
    }

    public static Object cast(String value, DataType valueType) {
        switch (valueType) {
            case DOUBLE:
                return Double.parseDouble(value);

            case INT:
                return Integer.parseInt(value);

            case FLOAT:
                return Float.parseFloat(value);

            case STRING:
                return value;

            case CHAR:
                return value.charAt(0);
            case INT_ARRAY:
                return toIntArray(value);
            default:
                return null;
        }
    }

    public static int[] toIntArray(String string) {
        try {
            String[] stringArray = string.split(",");
            int[] intArray = new int[stringArray.length];
            for (int i = 0; i < intArray.length; i++) {
                intArray[i] = Integer.parseInt(stringArray[i]);
            }

            return intArray;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String[] toStringArray(ArrayList<String> strings) {
        if (strings == null) return null;

        return (String[])strings.toArray();
    }

    public static String[] toString(StringProperty[] stringProperties) {
        if (stringProperties == null) return null;

        String[] stringsArray = new String[stringProperties.length];
        for (int i = 0; i < stringProperties.length; i++) {
            stringsArray[i] = stringProperties[i].get();
        }

        return stringsArray;
    }
}
