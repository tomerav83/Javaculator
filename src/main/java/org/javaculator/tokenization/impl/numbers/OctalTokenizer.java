package org.javaculator.tokenization.impl.numbers;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;

public class OctalTokenizer extends NonDelimitedTokenizer {
    public static final OctalTokenizer INSTANCE = new OctalTokenizer();

    private OctalTokenizer() {
        super("(?<![a-zA-Z0-9_])0(?:[0-7_]*[0-7])?[lL]?(?![a-zA-Z0-9_])", TokenType.OCTAL);
    }
}
