package org.javaculator.shuntified.lexer.stages.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaculator.shuntified.lexer.stages.TokenizationStage;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.variable.VariableToken;
import org.javaculator.shuntified.pattern.collectors.impl.VariableCollector;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class VariableStage implements TokenizationStage {
    private final List<String> variables;

    public static VariableStage create(String input) {
        return new VariableStage(VariableCollector.INSTANCE.collectByPattern(input));
    }

    @Override
    public Token match(String input, int position) {
        for (String variable : variables) {
            if (input.startsWith(variable, position)) {
                return new VariableToken(variable);
            }
        }

        return null;
    }
}
