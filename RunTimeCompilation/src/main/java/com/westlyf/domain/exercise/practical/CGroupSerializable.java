package com.westlyf.domain.exercise.practical;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robertoguazon on 14/10/2016.
 */
public class CGroupSerializable implements Serializable {
    static final long serialVersionUID = 0x201;

    ArrayList<CStringSerializable> cStrings;

    public CGroupSerializable(CGroup cGroup) {
        cStrings = new ArrayList<>();
        cGroup.getCStrings().forEach(cString -> cStrings.add(new CStringSerializable(cString)));
    }

    public ArrayList<CStringSerializable> getCStrings() {
        return cStrings;
    }
}
