package com.westlyf.domain.exercise.practical;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 14/10/2016.
 */
public class CStringSerializable implements Serializable {

    static final long serialVersionUID = 0x202;

    ArrayList<String> equivalents;

    public CStringSerializable(CString cString) {
        equivalents = new ArrayList<>();
        cString.getEquivalents().forEach(stringProperty -> equivalents.add(stringProperty.get()));
    }

    public ArrayList<String> getEquivalents() {
        return equivalents;
    }

}
