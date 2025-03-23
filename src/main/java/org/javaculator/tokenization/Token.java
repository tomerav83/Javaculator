package org.javaculator.tokenization;

import org.javaculator.tokenization.utils.ErrorlessMatcher;

import java.util.Optional;
import java.util.regex.Matcher;

public record Token(TokenType type, String value, int pos) {

    public static Token create(Matcher matcher) {
        return new Token(TokenType.getByGroup(matcher),  matcher.group(), matcher.start());
    }

    public static Optional<Token> createPrefix(Matcher matcher, String input, int pos) {
        return Optional.ofNullable( ErrorlessMatcher.errorSafeStart(matcher))
                .filter((Integer start) -> start != pos)
                .map((Integer start) -> input.substring(pos, start))
                .map((String prefixToken) -> new Token(TokenType.UNKNOWN, prefixToken, pos));
    }

    public static Optional<Token> createSuffix(String input, int pos) {
        String suffixToken = pos < input.length() ? input.substring(pos) : null;

        return Optional.ofNullable(suffixToken)
                .map((String unknownToken) -> new Token(TokenType.UNKNOWN, unknownToken, pos));
    }

    @Override
    public String toString() {
        return String.format("Token[type=%s, value='%s', pos=%d]", type, value, pos);
    }
}
