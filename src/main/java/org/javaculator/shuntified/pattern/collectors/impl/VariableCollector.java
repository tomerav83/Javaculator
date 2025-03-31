package org.javaculator.shuntified.pattern.collectors.impl;

import org.javaculator.shuntified.pattern.Patterns;
import org.javaculator.shuntified.pattern.collectors.PatternCollector;

import java.util.regex.Pattern;

public class VariableCollector extends PatternCollector {
    public static final VariableCollector INSTANCE = new VariableCollector(Patterns.VARIABLES);

    private VariableCollector(Pattern pattern) {
        super(pattern);
    }
}
