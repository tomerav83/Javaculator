package org.javaculator.tokenization.impl.literal.impl;

import org.javaculator.tokenization.impl.Patterns;
import org.javaculator.tokenization.impl.literal.LiteralTokenizer;

import java.util.Optional;
import java.util.regex.Matcher;

public class DecimalLiteralTokenizer extends LiteralTokenizer<Integer> {
    @Override
    public Integer tokenize(String token) {
        return Optional.of(Patterns.DECIMAL_LITERAL.matcher(token))
                .filter(Matcher::find)
                .map(Matcher::group)
                .map(Integer::valueOf)
                .orElse(0);
    }
}
