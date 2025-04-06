package org.javaculator.shuntified.pattern.collectors.impl;

import org.javaculator.shuntified.pattern.JavaKeywords;
import org.javaculator.shuntified.pattern.Patterns;
import org.javaculator.shuntified.pattern.collectors.PatternCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class VariableCollector extends PatternCollector<String> {
    public static final VariableCollector INSTANCE = new VariableCollector();
    private VariableCollector() {super(Patterns.VARIABLES);}

    @Override
    public List<String> collectByPattern(String input) {
        Matcher matcher = pattern.matcher(input);
        List<String> matches = new ArrayList<>();

        while (matcher.find() && !JavaKeywords.isJavaKeyword(matcher.group())) {
            matches.add(matcher.group());
        }

        return matches;
    }
}
