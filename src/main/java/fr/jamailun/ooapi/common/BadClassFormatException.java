package fr.jamailun.ooapi.common;

public class BadClassFormatException extends Exception {
    public BadClassFormatException(Class<?> clazz, String error) {
        super("Bad class format for class " + clazz.getName()+". " + error);
    }
}