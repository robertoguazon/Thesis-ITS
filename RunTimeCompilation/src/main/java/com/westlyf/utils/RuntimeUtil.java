package com.westlyf.utils;

import com.westlyf.domain.exercise.practical.PracticalPrintExercise;
import net.openhft.compiler.CachedCompiler;
import net.openhft.compiler.CompilerUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public static void compile(PracticalPrintExercise practicalPrintExercise) throws Exception {
        practicalPrintExercise.makeTempID();
        String className = "com.westlyf.sample." + practicalPrintExercise.getTempID() + "." + practicalPrintExercise.getClassName();
        String javaCode =
                "package com.westlyf.sample." + practicalPrintExercise.getTempID() + ";\n"  + practicalPrintExercise.getCode();

        Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
        Object obj = aClass.newInstance();
        Method main = aClass.getMethod(practicalPrintExercise.getMethodName(), String[].class);
        String[] args = new String[1];
        Object s = main.invoke(obj,args);
    }

    public static void reset(final ByteArrayOutputStream stream) throws IOException {
        stream.reset();
    }

}
