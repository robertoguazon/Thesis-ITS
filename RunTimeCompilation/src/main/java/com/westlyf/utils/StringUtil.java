package com.westlyf.utils;

/**
 * Created by robertoguazon on 13/10/2016.
 */
public class StringUtil {

    public static final String REGEX_REMOVE_WHITESPACE = "(\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\")|(\\s+)";

    public static String removeWhiteSpaces(String string) {
        
        return string.replaceAll(REGEX_REMOVE_WHITESPACE,"");
    }

    /**
     *
     * If start of line matches: replace the whole line with the replacement
     */
    public static String replaceLineMatch(String string, String match, String replacement) {
        String prefix = "(?m)^";
        String suffix = ".*(?:\r?\n)?";
        String regex = prefix + match + suffix;

        return string.replaceAll(regex,replacement);
    }
}
