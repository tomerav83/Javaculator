package org.javaculator.shuntified.lexer.stages.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.javaculator.shuntified.lexer.stages.TokenizationStage;
import org.javaculator.shuntified.models.Token;
import org.javaculator.shuntified.models.bracket.BracketToken;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BracketStage implements TokenizationStage {
    private final List<BracketToken> brackets = List.of(
            new BracketToken("(", true),
            new BracketToken(")", false)
    );

    public static BracketStage create() {
        return new BracketStage();
    }

    @Override
    public Token match(String input, int position) {
        for (BracketToken bracket : brackets) {
            if (input.startsWith(bracket.getSign(), position)) {
                return bracket;
            }
        }

        return null;
    }
}
