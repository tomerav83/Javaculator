package org.javaculator.shuntified.models.operator.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.operator.Operator;

import java.util.regex.Pattern;

@Getter
public class AssignOp extends Operator {
    private String targetVariable;

    private AssignOp(String sign, Pattern pattern, Association association, int precedence) {
        super(sign, pattern, association, precedence);
    }

    public static AssignOp create(String sign, Pattern pattern, Association association, int precedence) {
        return new AssignOp(sign, pattern, association, precedence);
    }

    public AssignOp setTargetVariable(String targetVariable) {
        this.targetVariable = targetVariable;
        return this;
    }
}
