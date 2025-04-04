package org.javaculator.shuntified.models.bracket;

import java.util.List;

public class Brackets {
    private static final List<BracketToken> BRACKETS = List.of(
            new BracketToken("(", true),
            new BracketToken(")", false)
    );

    public static List<BracketToken> get() {
        return BRACKETS;
    }
}
