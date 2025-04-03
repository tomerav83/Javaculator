package org.javaculator.shuntified.models;

public class Symbolizer {
    public static String toSymbol(int value, int repeats) {
       return String.valueOf(value).repeat(repeats);
    }

    public static String toSymbol(char value, int repeats) {
        return String.valueOf(value).repeat(repeats);
    }
}
