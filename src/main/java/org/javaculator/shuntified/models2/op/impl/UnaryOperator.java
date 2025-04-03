package org.javaculator.shuntified.models2.op.impl;

import org.javaculator.shuntified.models2.Token;
import org.javaculator.shuntified.models2.op.Operator;

import java.util.Map;
import java.util.regex.Pattern;

public class UnaryOperator extends Operator {
    private UnaryOperator(String sign, Pattern pattern, Association association, int precedence) {
        super(sign, pattern, association, precedence);
    }

    public static Map.Entry<String, UnaryOperator> asEntry(String sign, Pattern pattern, Association association, int precedence) {
        return Map.entry(sign, new UnaryOperator(sign, pattern, association, precedence));
    }

    public boolean isNegate() {
        return getSign().equals("−");
    }
}
