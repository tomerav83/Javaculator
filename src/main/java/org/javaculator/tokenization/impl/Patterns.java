package org.javaculator.tokenization.impl;

import java.util.regex.Pattern;

public class Patterns {
    public static final Pattern DECIMAL_LITERAL =
            Pattern.compile("^[+-]?((\\d+(\\.\\d*)?)|(\\.\\d+))([eE][+-]?\\d+)?[fFdD]?$");
}
