package org.javaculator.shuntified.models.operator.impl;

import org.javaculator.shuntified.models.operator.Operator;
import org.javaculator.shuntified.pattern.Patterns;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnaryOp extends Operator {
    private final Pattern pattern;

    private UnaryOp(String sign, Association association, int precedence, Pattern pattern) {
        super(sign, association, precedence);
        this.pattern = pattern;
    }

    public static UnaryOp create(String sign, Association association, int precedence, Pattern pattern) {
        return new UnaryOp(sign, association, precedence, pattern);
    }

    public boolean isNegate() {
        return getSign().equals("−");
    }

    public boolean matchByPattern(String input, int position) {
        Matcher matcher = pattern.matcher(input);

        if (matcher.find() && matcher.start() == position) {
            setSign(matcher.group());
            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UnaryOp unaryOp)) {
            return false;
        }

        return Objects.equals(getSign(), unaryOp.getSign()) &&
                Objects.equals(getAssociation(), unaryOp.getAssociation()) &&
                Objects.equals(getPrecedence(), unaryOp.getPrecedence());

    }
}
