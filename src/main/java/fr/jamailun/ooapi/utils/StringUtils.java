package fr.jamailun.ooapi.utils;

import java.util.StringJoiner;

public final class StringUtils {
    private StringUtils() {}
    public static String escape(String s){
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("'", "\\'")
                .replace("\"", "\\\"");
    }

    public static String toStringWhenNull(Object... objs) {
        StringJoiner sj = new StringJoiner(", ");
        for(int i = 0; i < objs.length - 1; i += 2) {
            if(objs[i] == null)
                sj.add(objs[i+1].toString());
        }
        return "[" + sj + "]";
    }

    public static int findOccurrences(String string, String toFind) {
        int lastIndex = 0;
        int count = 0;
        while(lastIndex != -1){
            lastIndex = string.indexOf(toFind,lastIndex);
            if(lastIndex != -1){
                count ++;
                lastIndex += toFind.length();
            }
        }
        return count;
    }

    public static int toIntOrZero(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean isXmlCompatible(String string) {
        return string != null && string.matches("^[A-Za-z0-9\\-]+$");
    }
}
