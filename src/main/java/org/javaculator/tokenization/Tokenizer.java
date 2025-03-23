package org.javaculator.tokenization;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    public static final Pattern TOKEN_PATTERN = Pattern.compile(
            "(?<NUMBER>\\d+\\.?\\d*|\\.\\d+)"
                    + "|(?<IDENTIFIER>[a-zA-Z_][a-zA-Z0-9_]*)"
                    + "|(?<STRING>\"(?:[^\"\\\\]|\\\\.)*\"|'(?:[^'\\\\]|\\\\.)*')"
                    + "|(?<OPERATOR>==|!=|<=|>=|\\+\\+|--|[=+\\-*/%])"
                    + "|(?<PUNCTUATION>[;(),{}])");


    public static List<Token> tokenize(String input) {
        Matcher matcher = TOKEN_PATTERN.matcher(input);
        List<Token> tokens = new ArrayList<>();
        int pos = 0;

        while (matcher.find()) {
            Token.createPrefix(matcher, input, pos).ifPresent(tokens::add);
            pos = matcher.end();
            tokens.add(Token.create(matcher));
        }

        Token.createSuffix(input, pos).ifPresent(tokens::add);

        return tokens;
    }
}
