package org.javaculator.shuntified.models.operator;

import lombok.Getter;
import org.javaculator.shuntified.models.Token;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Getter
public abstract class Operator extends Token {
    private final Pattern pattern;
    private final Association association;
    private final int precedence;

    protected Operator(String sign,
                       Pattern pattern,
                       Association association,
                       int precedence) {
        super(sign);

        this.pattern = pattern;
        this.association = association;
        this.precedence = precedence;
    }

    public boolean isMatching(String input, int start) {
        if (input.startsWith(getSign(), start)) {
            return true;
        }

        Matcher matcher = pattern.matcher(input);

        if (matcher.find() && matcher.toMatchResult().start() == start) {
            setSign(matcher.group());
            return true;
        }

        return false;
    }

    public boolean isLeftAssociative() {
        return Objects.equals(association, Association.LEFT);
    }
}
