package org.javaculator.tokenization.impl.operators;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;
import org.javaculator.tokenization.abs.Tokenizer;

public class ShiftOperatorTokenizer extends NonDelimitedTokenizer {
    public static final ShiftOperatorTokenizer INSTANCE = new ShiftOperatorTokenizer();

    private ShiftOperatorTokenizer() {
        super("<<|>>|>>>", TokenType.SHIFT);
    }
}
