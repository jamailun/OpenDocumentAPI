package fr.jamailun.ooapi.utils;

public class Indent {

    private final String pattern;

    private String currentValue;
    private int count;

    public Indent() {
        this("");
    }

    public Indent(String pattern) {
        this(pattern, 0);
    }

    public Indent(String pattern, int count) {
        this.pattern = pattern;
        this.count = count;
        recompute();
    }

    private void recompute() {
        currentValue = pattern.repeat(Math.max(0, count));
    }

    public String add() {
        count++;
        recompute();
        return toString();
    }

    public String remove() {
        count--;
        recompute();
        return toString();
    }

    public String debug() {
        return "Indent{"+count+"x'"+ StringUtils.escape(pattern)+"'}";
    }

    @Override
    public String toString() {
        return currentValue;
    }
}
