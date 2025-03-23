package org.javaculator.tokenization.abs;

import org.javaculator.tokenization.models.Token;
import org.javaculator.tokenization.models.TokenType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NonDelimitedTokenizer extends Tokenizer {

    public NonDelimitedTokenizer(String regex, TokenType type) {
        super(regex, type, new HashSet<>());
    }
}
