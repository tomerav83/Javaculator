package org.javaculator.tokenization.impl.parenthesis;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;
import org.javaculator.tokenization.abs.Tokenizer;

public class LeftParenTokenizer extends NonDelimitedTokenizer {
    public static final LeftParenTokenizer INSTANCE = new LeftParenTokenizer();

    private LeftParenTokenizer() {
        super("\\(", TokenType.L_PAREN);
    }
}
