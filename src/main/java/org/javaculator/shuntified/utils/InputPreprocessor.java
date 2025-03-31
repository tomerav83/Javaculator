package org.javaculator.shuntified.utils;

import java.util.regex.Pattern;

public class InputPreprocessor {
    private static final Pattern NEGATE_PATTARN = Pattern.compile("(?<!(\\d|\\)))-");

    public static String preprocess(String input) {
        return input.replace(" ", "").toLowerCase();
    }
}
