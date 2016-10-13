package com.westlyf.utils;

/**
 * Created by robertoguazon on 13/10/2016.
 */
public class StringUtil {

    public static final String REGEX_REMOVE_WHITESPACE = "\\s+(?=((\\\\[\\\\\"]|[^\\\\\"])*\"(\\\\[\\\\\"]|[^\\\\\"])*\")*(\\\\[\\\\\"]|[^\\\\\"])*$)";

    public static String removeWhiteSpaces(String string) {
        return string.replaceAll(REGEX_REMOVE_WHITESPACE,"");
    }
}
