package org.javaculator.tokenization.impl.numbers;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;

public class BinaryTokenizer extends NonDelimitedTokenizer {
    public static final BinaryTokenizer INSTANCE = new BinaryTokenizer();

    private BinaryTokenizer() {
        super("(?<![a-zA-Z0-9_])0[bB][01](?:[01_]*[01])?[lL]?(?![a-zA-Z0-9_])", TokenType.BINARY);
    }
}
