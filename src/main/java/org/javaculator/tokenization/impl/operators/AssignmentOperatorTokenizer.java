package org.javaculator.tokenization.impl.operators;

import org.javaculator.tokenization.abs.NonDelimitedTokenizer;
import org.javaculator.tokenization.models.TokenType;
import org.javaculator.tokenization.abs.Tokenizer;

public class AssignmentOperatorTokenizer extends NonDelimitedTokenizer {
    public static final AssignmentOperatorTokenizer INSTANCE = new AssignmentOperatorTokenizer();

    private AssignmentOperatorTokenizer() {
        super("=|\\+=|-=|\\*=|/=|%=|&=|\\|=|\\^=|<<=|>>=|>>>=", TokenType.ASSIGN);
    }
}
