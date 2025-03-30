package org.javaculator.shuntified.lexer;

import org.javaculator.shuntified.tkn.Token;

public interface Relaxer<T extends Token> {

    /**
     * Reads a single token at the specified location within an input string.
     * @param input                         The input string to find a token within.
     * @param pos                           The position of the first character of the token.
     * @return                              The token at the specified location within the input string.
     */
    T readToken(String input, int pos);
}
