package org.javaculator.shuntified.pattern.collectors.impl;

import org.javaculator.shuntified.models.Assignment;
import org.javaculator.shuntified.pattern.Patterns;
import org.javaculator.shuntified.pattern.collectors.PatternCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableAssignmentCollector extends PatternCollector<Assignment> {
    public static final VariableAssignmentCollector INSTANCE =
            new VariableAssignmentCollector(Patterns.ASSIGNMENT_PATTERN);

    private VariableAssignmentCollector(Pattern pattern) {
        super(pattern);
    }

    @Override
    public List<Assignment> collect(String input) {
        Matcher matcher = pattern.matcher(input);
        List<Assignment> matches = new ArrayList<>();

        while (matcher.find()) {
            matches.add(Assignment.create(matcher));
        }

        return matches;
    }
}
