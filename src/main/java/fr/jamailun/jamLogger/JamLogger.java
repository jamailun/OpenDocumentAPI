package fr.jamailun.jamLogger;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public final class JamLogger {
    private JamLogger() {}

    private static String prefix = " ";
    private static PrintStream stream = System.out;
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    public static void log(Object object) {
        log(object.toString());
    }
    public static void log(String message) {
        stream.println(
                ConsoleColor.WHITE + "[>]" + prefix
                        + ConsoleColor.CYAN + "[" + ConsoleColor.WHITE_BRIGHT + formatter.format(new Date()) + ConsoleColor.CYAN + "]"
                        + ConsoleColor.CYAN + " > " + ConsoleColor.RESET + message+ ConsoleColor.RESET
        );
    }

    public static void warning(Object object) {
        warning(object.toString());
    }
    public static void warning(String message) {
        stream.println(
                ConsoleColor.YELLOW_BRIGHT + "[!]" + prefix
                        + ConsoleColor.YELLOW_BOLD + "[" + ConsoleColor.YELLOW_BRIGHT + formatter.format(new Date()) + ConsoleColor.YELLOW_BOLD + "]"
                        + ConsoleColor.YELLOW + " > " + message+ ConsoleColor.RESET
        );
    }

    public static void error(Object object) {
        error(Objects.toString(object));
    }
    public static void error(Collection<?> collection) {
        error(Arrays.toString(collection.toArray()));
    }
    public static void error(String message) {
        stream.println(
                ConsoleColor.RED_BRIGHT + "[X]" + prefix
                        + ConsoleColor.RED_BOLD + "[" + ConsoleColor.RED_BRIGHT + formatter.format(new Date()) + ConsoleColor.RED_BOLD + "]"
                        + " > " + ConsoleColor.RED + message+ ConsoleColor.RESET
        );
    }

    public static void info(Object object) {
        info(object.toString());
    }
    public static void info(Collection<?> collection) {
        info(Arrays.toString(collection.toArray()));
    }
    public static void info(String message) {
        stream.println(
                ConsoleColor.BLUE + "[?]" + prefix
                        + ConsoleColor.BLUE_BRIGHT + "[" + ConsoleColor.CYAN_BRIGHT + formatter.format(new Date()) + ConsoleColor.BLUE_BRIGHT + "]"
                        + " > " + ConsoleColor.CYAN + message+ ConsoleColor.RESET
        );
    }

    public static void success(Object object) {
        success(object.toString());
    }
    public static void success(Collection<?> collection) {
        success(Arrays.toString(collection.toArray()));
    }
    public static void success(String message) {
        stream.println(
                ConsoleColor.GREEN_BRIGHT + "[~]" + prefix
                        + ConsoleColor.GREEN_BOLD + "[" + ConsoleColor.WHITE_BRIGHT + formatter.format(new Date()) + ConsoleColor.GREEN_BOLD + "]"
                        + " > " + ConsoleColor.GREEN_BRIGHT + message+ ConsoleColor.RESET
        );
    }


    public static void setPrefix(String prefix) { JamLogger.prefix = prefix; }

    public static void setOut(PrintStream out) {
        stream = out;
    }

    public static void resetOut(PrintStream out) {
        stream = System.out;
    }

    public static void silent() {
        setOut(new NullOutput());
    }

    public static void setFormatter(SimpleDateFormat formatter) {
        JamLogger.formatter = formatter;
    }

}

