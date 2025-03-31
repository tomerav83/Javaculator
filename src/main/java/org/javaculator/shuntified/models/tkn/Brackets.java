package org.javaculator.shuntified.models.tkn;

import org.javaculator.shuntified.models.tkn.impl.BracketTkn;

import java.util.List;

public class Brackets {
    private static final List<BracketTkn> TOKENS = List.of(
            new BracketTkn("(", true),
            new BracketTkn(")", false));

    public static List<BracketTkn> get() {
        return TOKENS;
    }
}
