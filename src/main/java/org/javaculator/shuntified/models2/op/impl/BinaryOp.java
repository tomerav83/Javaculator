package org.javaculator.shuntified.models2.op.impl;

import org.javaculator.shuntified.models2.op.Operator;

import java.util.Map;
import java.util.regex.Pattern;

public class BinaryOp extends Operator {
    private BinaryOp(String sign, Pattern pattern, Association association, int precedence) {
        super(sign, pattern, association, precedence);
    }

    public static Map.Entry<String, BinaryOp> asEntry(String sign, Pattern pattern, Association association, int precedence) {
        return Map.entry(sign, new BinaryOp(sign, pattern, association, precedence));
    }

    public boolean isAssignment() {
        return switch (getSign()) {
            case "=", "+=", "-=", "*=", "/=", "%=" -> true;
            default -> false;
        };
    }
}
