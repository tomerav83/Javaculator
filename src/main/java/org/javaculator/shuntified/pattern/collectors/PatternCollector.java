package org.javaculator.shuntified.pattern.collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PatternCollector {
    private final Pattern pattern;

    public PatternCollector(Pattern pattern) {
        this.pattern = pattern;
    }

    public List<String> collect(String input) {
        Matcher matcher = pattern.matcher(input);
        List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            matches.add(matcher.group());
        }

        return matches;
    }
}
