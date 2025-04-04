package org.javaculator.shuntified.lexer.stages.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaculator.shuntified.lexer.stages.TokenizationStage;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.literal.LiteralToken;
import org.javaculator.shuntified.pattern.collectors.impl.LiteralCollector;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class LiteralStage implements TokenizationStage {
    private final List<String> literals;

    public static LiteralStage create(String input) {
        return new LiteralStage(LiteralCollector.INSTANCE.collect(input));
    }

    @Override
    public Token match(String input, int position) {
        for (int i = 0; i < literals.size(); i++) {
            if (input.startsWith(literals.get(i), position)) {
                return new LiteralToken(literals.remove(i));
            }
        }

        return null;
    }
}
