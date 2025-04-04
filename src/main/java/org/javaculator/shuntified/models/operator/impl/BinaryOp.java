package org.javaculator.shuntified.models.operator.impl;

import org.javaculator.shuntified.models.operator.Operator;

import java.util.regex.Pattern;

public class BinaryOp extends Operator {
    private BinaryOp(String sign, Pattern pattern, Association association, int precedence) {
        super(sign, pattern, association, precedence);
    }

    public static BinaryOp create(String sign, Pattern pattern, Association association, int precedence) {
        return new BinaryOp(sign, pattern, association, precedence);
    }
}
