package org.javaculator.tokenization.impl.operators;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;
import org.javaculator.tokenization.abs.Tokenizer;

public class NumericOperatorTokenizer extends NonDelimitedTokenizer {
    public static final NumericOperatorTokenizer INSTANCE = new NumericOperatorTokenizer();

    private NumericOperatorTokenizer() {
        super("[+\\-*/%]", TokenType.NUMERIC);
    }
}
