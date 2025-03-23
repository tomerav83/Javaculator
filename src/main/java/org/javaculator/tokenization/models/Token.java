package org.javaculator.tokenization.models;

import java.util.regex.MatchResult;

public record Token(TokenType type, String value, int pos) {

    public static Token create(MatchResult matchResult, TokenType type) {
        return new Token(type,  matchResult.group(), matchResult.start());
    }

    @Override
    public String toString() {
        return String.format("Token[type=%s, value='%s', pos=%d]", type, value, pos);
    }
}
