package org.javaculator.shuntified.pattern.collectors.impl.numbers;

import org.javaculator.shuntified.models.literal.LiteralWrapper;
import org.javaculator.shuntified.pattern.Patterns;
import org.javaculator.shuntified.pattern.collectors.PatternCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.javaculator.shuntified.models.literal.LiteralWrapperUtils.handleMatch;

public class DecimalNumbers extends PatternCollector<LiteralWrapper> {
    public static final DecimalNumbers INSTANCE = new DecimalNumbers();

    private DecimalNumbers() {
        super(Patterns.DECIMAL);
    }

    @Override
    public List<LiteralWrapper> collectByPattern(String input) {
        Matcher matcher = pattern.matcher(input);
        List<LiteralWrapper> matches = new ArrayList<>();

        while (matcher.find()) {
            Optional.ofNullable(handleMatch(matcher.toMatchResult(), "[lL]", 10))
                    .ifPresent(matches::add);
        }

        return matches;
    }
}
