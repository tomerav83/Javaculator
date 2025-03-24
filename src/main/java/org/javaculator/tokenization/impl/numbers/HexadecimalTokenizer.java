package org.javaculator.tokenization.impl.numbers;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;

public class HexadecimalTokenizer extends NonDelimitedTokenizer {
    public static final HexadecimalTokenizer INSTANCE = new HexadecimalTokenizer();

    private HexadecimalTokenizer() {
        super("(?<![a-zA-Z0-9_])0[xX][0-9a-fA-F](?:[0-9a-fA-F_]*[0-9a-fA-F])?[lL]?(?![a-zA-Z0-9_])", TokenType.HEXADECIMAL);
    }
}
