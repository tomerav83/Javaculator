package org.javaculator.shuntified.lexer.impl;

import org.javaculator.shuntified.lexer.Relaxer;
import org.javaculator.shuntified.tkn.Token;
import org.javaculator.shuntified.tkn.impl.ConstantToken;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstantsRelaxer implements Relaxer<ConstantToken> {
    private final Pattern numbersRegex = Pattern.compile("^\\d+(\\.\\d+)?");

    @Override
    public ConstantToken readToken(String input, int pos) {
        Matcher matcher = numbersRegex.matcher(input.substring(pos));

        if (matcher.find()) {
            return ConstantToken.create(matcher.group());
        }

        return null;
    }
}
