import com.westlyf.utils.RuntimeUtil;
import com.westlyf.utils.StringUtil;
import net.openhft.compiler.CachedCompiler;
import net.openhft.compiler.CompilerUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {


        //test1(); //-check
        //test2(); //-check
        //test3(); //-check
        test4(); //-check regex whitespace
    }

    private static void test1() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        System.out.println("test3");
        // dynamically you can call
        String className = "mypackage.MyClass";
        String javaCode = "package mypackage;\n" +
                "public class MyClass implements Runnable {\n" +
                "    public void run() {\n" +
                "        System.out.println(\"Hello World\");\n" +
                "    }\n" +
                "       " +
                "    public int returnN(int n) {\n" +
                "        return n;\n" +
                "    }\n" +
                "}\n";

        Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
        Runnable runner = (Runnable) aClass.newInstance();
        Method returnN = aClass.getMethod("returnN", int.class);
        Object s = returnN.invoke(runner, 2);
        System.out.println(s);
        runner.run();
    }

    private static void test2() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        System.out.println("test2");
        String className = "com.westlyf.sample.Sample";
        String javaCode =
                "package com.westlyf.sample;\n" + "" +
                        "public class Sample {\n" +
                        "   public static void main(String[] args) {\n" +
                        "       System.out.println(\"Welcome to main method\");\n" +
                        "   }\n" +
                        "}\n";
        Class aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
        Object obj = aClass.newInstance();
        Method main = aClass.getMethod("main", String[].class);
        String[] args = new String[1];
        Object s = main.invoke(obj, args);
    }

    private static void test3() {
        System.out.println("test3");
        RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STRING_STREAM);
        System.out.println("6 + 9 * 3 = " + (6 + 9 * 3));
        System.out.println("what is your answer?");
        System.out.println("badooop case");
        String output = RuntimeUtil.CONSOLE_OUTPUT.toString();

        RuntimeUtil.setOutStream(RuntimeUtil.CONSOLE_STREAM);
        System.out.println(output);
        System.out.println(output);
    }

    private static void test4() {
        String test = "System.out.println(\" this is a sample check \");";
        String test2 = "System.out.println(4 + 6);";
        System.out.println("Before: " + test + ", After: " + StringUtil.removeWhiteSpaces(test));
        System.out.println("Before: " + test2 + ", After: " + StringUtil.removeWhiteSpaces(test2));
    }
}