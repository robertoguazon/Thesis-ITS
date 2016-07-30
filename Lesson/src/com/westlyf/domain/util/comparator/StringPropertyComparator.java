package com.westlyf.domain.util.comparator;

import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by robertoguazon on 30/07/2016.
 */
public class StringPropertyComparator implements Comparator<StringProperty>, Serializable {

    public int compare(StringProperty s1, StringProperty s2) {
        int n1 = s1.getValue().length();
        int n2 = s2.getValue().length();
        int min = Math.min(n1, n2);
        for (int i = 0; i < min; i++) {
            char c1 = s1.getValue().charAt(i);
            char c2 = s2.getValue().charAt(i);
            if (c1 != c2) {
                c1 = Character.toUpperCase(c1);
                c2 = Character.toUpperCase(c2);
                if (c1 != c2) {
                    c1 = Character.toLowerCase(c1);
                    c2 = Character.toLowerCase(c2);
                    if (c1 != c2) {
                        // No overflow because of numeric promotion
                        return c1 - c2;
                    }
                }
            }
        }
        return n1 - n2;
    }

}
