package com.westlyf.utils.array;

import java.util.ArrayList;

/**
 * Created by robertoguazon on 14/06/2016.
 */
public class ArrayUtil {

    public static <E> ArrayList<E> randomizeArrayList(ArrayList<E> arrayList) {
        ArrayList<E> clone = (ArrayList<E>)arrayList.clone();
        ArrayList<E> temp = new ArrayList<>();

        while (!clone.isEmpty()) {
            int random = (int)(Math.random() * clone.size());
            temp.add(clone.get(random));
            clone.remove(random);
        }

        return temp;
    }

    public static <E> void printArrayList(ArrayList<E> arrayList) {

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(arrayList.get(i));
        }

    }

}
