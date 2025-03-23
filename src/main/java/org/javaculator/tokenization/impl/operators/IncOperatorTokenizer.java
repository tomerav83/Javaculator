package org.javaculator.tokenization.impl.operators;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;
import org.javaculator.tokenization.abs.Tokenizer;

public class IncOperatorTokenizer extends NonDelimitedTokenizer {
    public static final IncOperatorTokenizer INSTANCE = new IncOperatorTokenizer();

    private IncOperatorTokenizer() {
        super("\\+\\+", TokenType.INC);
    }
}
