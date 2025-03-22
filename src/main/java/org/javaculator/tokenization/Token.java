package org.javaculator.tokenization;

import java.util.regex.Matcher;

public record Token(TokenType type, String value, int pos) {

    public static Token create(Matcher matcher) {
        TokenType type = TokenType.getByGroup(matcher);
        String value = matcher.group();

        return new Token(type, value, matcher.start());
    }
    @Override
    public String toString() {
        return String.format("Token[type=%s, value='%s', pos=%d]", type, value, pos);
    }
}
