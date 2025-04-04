package org.javaculator.shuntified.models.operator.impl;

import org.javaculator.shuntified.models.operator.Operator;

import java.util.regex.Pattern;

public class UnaryOp extends Operator {
    private UnaryOp(String sign, Pattern pattern, Association association, int precedence) {
        super(sign, pattern, association, precedence);
    }

    public static UnaryOp create(String sign, Pattern pattern, Association association, int precedence) {
        return new UnaryOp(sign, pattern, association, precedence);
    }

    public boolean isNegate() {
        return getSign().equals("−");
    }
}
