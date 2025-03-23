package org.javaculator.tokenization.impl.numbers;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;
import org.javaculator.tokenization.abs.Tokenizer;

public class NumberTokenizer extends NonDelimitedTokenizer {
    public static final NumberTokenizer INSTANCE = new NumberTokenizer();

    private NumberTokenizer() {
        super("(?<![a-zA-Z])(\\d+\\.?\\d*|\\.\\d+)(?![a-zA-Z])", TokenType.NUMBER);
    }
}
