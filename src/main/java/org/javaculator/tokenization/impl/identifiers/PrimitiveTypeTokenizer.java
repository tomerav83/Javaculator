package org.javaculator.tokenization.impl.identifiers;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;

public class PrimitiveTypeTokenizer extends NonDelimitedTokenizer {
    public static final PrimitiveTypeTokenizer INSTANCE = new PrimitiveTypeTokenizer();

    private PrimitiveTypeTokenizer() {
        super("\\b(byte|short|int|long|float|double|boolean|char)\\b", TokenType.PRIMITIVE);
    }
}
