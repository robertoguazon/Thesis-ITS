package com.westlyf.database;

import java.io.*;

/**
 * Created by robertoguazon on 10/08/2016.
 */
public class Database {

    //TODO - constants sql statements and such

    public static byte[] serialize(Serializable object) {
        ByteArrayOutputStream baos;
        ObjectOutputStream oos;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.close();

            return baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object deserialize(byte[] bytes) {
        ObjectInputStream ois;

        try {

            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Object object = ois.readObject();
            ois.close();

            return object;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
