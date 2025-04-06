package org.javaculator.shuntified.pattern.collectors.impl;

import org.javaculator.shuntified.models.literal.LiteralWrapper;
import org.javaculator.shuntified.pattern.Patterns;
import org.javaculator.shuntified.pattern.collectors.PatternCollector;
import org.javaculator.shuntified.pattern.collectors.impl.numbers.BinaryNumbers;
import org.javaculator.shuntified.pattern.collectors.impl.numbers.DecimalFPNumbers;
import org.javaculator.shuntified.pattern.collectors.impl.numbers.DecimalNumbers;
import org.javaculator.shuntified.pattern.collectors.impl.numbers.HexadecimalFPNumbers;
import org.javaculator.shuntified.pattern.collectors.impl.numbers.HexadecimalNumbers;
import org.javaculator.shuntified.pattern.collectors.impl.numbers.OctalNumbers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LiteralCollector extends PatternCollector<LiteralWrapper> {
    public static final LiteralCollector INSTANCE = new LiteralCollector();

    private LiteralCollector() {
        super(Patterns.BINARY);
    }

    @Override
    public List<LiteralWrapper> collectByPattern(String input) {
        Map<Integer, List<LiteralWrapper>> indexedMatches = Stream.of(
                        BinaryNumbers.INSTANCE.collectByPattern(input),
                        OctalNumbers.INSTANCE.collectByPattern(input),
                        DecimalNumbers.INSTANCE.collectByPattern(input),
                        DecimalFPNumbers.INSTANCE.collectByPattern(input),
                        HexadecimalNumbers.INSTANCE.collectByPattern(input),
                        HexadecimalFPNumbers.INSTANCE.collectByPattern(input)
                )
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(LiteralWrapper::position));

        List<LiteralWrapper> matchResults = new ArrayList<>();

        for (List<LiteralWrapper> matches : indexedMatches.values()) {
            matches.stream()
                    .max(Comparator.comparing((LiteralWrapper literal) -> literal.original().length()))
                    .ifPresent(matchResults::add);
        }

        return matchResults;
    }
}
