package org.javaculator.tokenization.impl.identifiers;

import org.javaculator.tokenization.models.TokenType;
import org.javaculator.tokenization.abs.Tokenizer;

import static org.javaculator.tokenization.Constants.JAVA_KEYWORDS;

public class VariableTokenizer extends Tokenizer {
    public static final VariableTokenizer INSTANCE = new VariableTokenizer();

    private VariableTokenizer() {super("^[a-zA-Z_][a-zA-Z0-9_]*$", TokenType.VARIABLE, JAVA_KEYWORDS);}
}
