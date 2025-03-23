package org.javaculator.tokenization.impl.parenthesis;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;
import org.javaculator.tokenization.abs.Tokenizer;

public class RightParenTokenizer extends NonDelimitedTokenizer {
    public static final RightParenTokenizer INSTANCE = new RightParenTokenizer();

    private RightParenTokenizer() {
        super("\\)", TokenType.R_PAREN);
    }
}
