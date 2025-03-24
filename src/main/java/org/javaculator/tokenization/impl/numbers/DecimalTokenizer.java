package org.javaculator.tokenization.impl.numbers;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;

public class DecimalTokenizer extends NonDelimitedTokenizer {
    public static final DecimalTokenizer INSTANCE = new DecimalTokenizer();

    private DecimalTokenizer() {
        super("(?<![a-zA-Z0-9_])0[bB][01](?:[01_]*[01])?[lL]?(?![a-zA-Z0-9_])", TokenType.BINARY);
    }
}
