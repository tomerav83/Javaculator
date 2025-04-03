package org.javaculator.shuntified.pattern.collectors.impl;

import org.javaculator.shuntified.pattern.Patterns;
import org.javaculator.shuntified.pattern.collectors.PatternCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumbersCollector extends PatternCollector<String> {
    public static final NumbersCollector INSTANCE = new NumbersCollector(Patterns.NUMBERS);

    private NumbersCollector(Pattern pattern) {
        super(pattern);
    }

    @Override
    public List<String> collect(String input) {
        Matcher matcher = pattern.matcher(input);
        List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group());
        }

        return matches;
    }
}
