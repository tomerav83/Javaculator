package org.javaculator.tokenization.impl.operators;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;
import org.javaculator.tokenization.abs.Tokenizer;

public class DecOperatorTokenizer extends NonDelimitedTokenizer {
    public static final DecOperatorTokenizer INSTANCE = new DecOperatorTokenizer();

    private DecOperatorTokenizer() {
        super("--", TokenType.DEC);
    }
}
