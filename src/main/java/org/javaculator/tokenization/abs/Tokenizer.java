package org.javaculator.tokenization.abs;

import org.javaculator.tokenization.models.Token;
import org.javaculator.tokenization.models.TokenType;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Tokenizer {
    private final Pattern pattern;
    private final TokenType type;
    private final Set<String> delimiters;

    public Tokenizer(String regex, TokenType type, Set<String> delimiters) {
        this.pattern = Pattern.compile(regex);
        this.type = type;
        this.delimiters = delimiters;
    }

    public List<Token> tokenize(String input) {
        if (input == null) {
            return Collections.emptyList();
        }

        return pattern.matcher(input).results()
                .filter((MatchResult matchResult) -> !delimiters.contains(matchResult.group()))
                .map((MatchResult matchResult) -> Token.create(matchResult, type))
                .collect(Collectors.toList());
    }
}
