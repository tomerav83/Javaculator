package org.javaculator.tokenization;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Matcher;

public enum TokenType {
    IDENTIFIER((String value) -> !value.equals("var"), "IDENTIFIER"),
    NUMBER((String ignored) -> true, "NUMBER"),
    OPERATOR((String ignored) -> true, "OPERATOR"),
    KEYWORD((String value) -> value.equals("var"), "IDENTIFIER"),
    STRING((String ignored) -> true, "STRING"),
    UNKNOWN((String ignored) -> true, "UNKNOWN");

    private final Predicate<String> predicate;
    private final String tokenGroup;


    TokenType(Predicate<String> predicate, String tokenGroup) {
        this.predicate = predicate;
        this.tokenGroup = tokenGroup;
    }

    public static TokenType getByGroup(Matcher matcher) {
        return Arrays.stream(TokenType.values()).parallel()
                .filter(Predicate.not(TokenType.UNKNOWN::equals))
                .filter((TokenType type) -> type.matchTokenGroup(matcher))
                .findAny()
                .orElse(TokenType.UNKNOWN);
    }

    private boolean matchTokenGroup(Matcher matcher) {
        return matcher.group(tokenGroup) != null && predicate.test(matcher.group());
    }
}
