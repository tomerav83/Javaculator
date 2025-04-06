package org.javaculator.shuntified.models.operator.impl;

import org.javaculator.shuntified.models.operator.Operator;

import java.util.Objects;

public class BinaryOp extends Operator {
    private BinaryOp(String sign, Association association, int precedence) {
        super(sign, association, precedence);
    }

    public static BinaryOp create(String sign, Association association, int precedence) {
        return new BinaryOp(sign, association, precedence);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BinaryOp binaryOp)) {
            return false;
        }

        return Objects.equals(getSign(), binaryOp.getSign()) &&
                Objects.equals(getAssociation(), binaryOp.getAssociation()) &&
                Objects.equals(getPrecedence(), binaryOp.getPrecedence());

    }
}
