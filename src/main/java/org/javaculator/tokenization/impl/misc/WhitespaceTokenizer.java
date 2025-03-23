package org.javaculator.tokenization.impl.misc;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;

public class WhitespaceTokenizer extends NonDelimitedTokenizer {
    public static final WhitespaceTokenizer INSTANCE = new WhitespaceTokenizer();

    private WhitespaceTokenizer() {
        super("\\s+", TokenType.WHITESPACE);
    }
}
