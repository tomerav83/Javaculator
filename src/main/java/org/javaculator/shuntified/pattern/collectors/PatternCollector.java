package org.javaculator.shuntified.pattern.collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PatternCollector<T> {
    protected final Pattern pattern;

    public PatternCollector(Pattern pattern) {
        this.pattern = pattern;
    }

    protected abstract List<T> collectByPattern(String input);
}
