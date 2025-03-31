package org.javaculator.shuntified.pattern.collectors.impl;

import org.javaculator.shuntified.pattern.Patterns;
import org.javaculator.shuntified.pattern.collectors.PatternCollector;

import java.util.regex.Pattern;

public class NumbersCollector extends PatternCollector {
    public static final NumbersCollector INSTANCE = new NumbersCollector(Patterns.NUMBERS);

    private NumbersCollector(Pattern pattern) {
        super(pattern);
    }
}
