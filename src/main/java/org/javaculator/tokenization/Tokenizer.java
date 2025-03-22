package org.javaculator.tokenization;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
                "(?<NUMBER>\\d+(\\.\\d+)?)"
                    + "|(?<IDENTIFIER>[a-zA-Z_][a-zA-Z0-9_]*)"
                    + "|(?<STRING>\"(?:[^\"\\\\]|\\\\.)*\"|'(?:[^'\\\\]|\\\\.)*')"
                    + "|(?<OPERATOR>==|!=|<=|>=|[=+\\-*/%])"
                    + "|(?<PUNCTUATION>[;(),{}])");


    public static List<Token> tokenize(String input) {
        Matcher matcher = TOKEN_PATTERN.matcher(input);
        List<Token> tokens = new ArrayList<>();
        int pos = 0;

        while (matcher.find()) {
            // Check if there is any unrecognized text between tokens.
            if (matcher.start() != pos) {
                String unknown = input.substring(pos, matcher.start());
                tokens.add(new Token(TokenType.UNKNOWN, unknown, pos));
            }

            pos = matcher.end();

            tokens.add(Token.create(matcher));
        }

        // Capture any trailing unrecognized text.
        if (pos < input.length()) {
            tokens.add(new Token(TokenType.UNKNOWN, input.substring(pos), pos));
        }

        return tokens;
    }
}
