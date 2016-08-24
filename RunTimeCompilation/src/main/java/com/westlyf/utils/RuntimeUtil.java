package com.westlyf.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by robertoguazon on 24/08/2016.
 */
public class RuntimeUtil {
    public static final PrintStream CONSOLE_STREAM = System.out;
    public static final ByteArrayOutputStream STRING_OUTPUT = new ByteArrayOutputStream();
    public static final PrintStream STRING_STREAM = new PrintStream(STRING_OUTPUT);

    public static void setOutStream(final PrintStream stream) {
        System.out.flush();
        System.setOut(stream);
    }

}
