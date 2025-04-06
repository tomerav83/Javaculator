package org.javaculator.shuntified.models.operator.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.operator.Operator;

import java.util.Objects;

@Getter
public class AssignOp extends Operator {
    private String targetVariable;

    private AssignOp(String sign) {
        super(sign, Association.RIGHT, 5);
    }

    public static AssignOp create(String sign) {
        return new AssignOp(sign);
    }

    public AssignOp setTargetVariable(String targetVariable) {
        this.targetVariable = targetVariable;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AssignOp assignOp)) {
            return false;
        }

        return Objects.equals(getSign(), assignOp.getSign());
    }

    @Override
    public String toString() {
        return "Assign: op:\"%s\" target=%s".formatted(getSign(), getTargetVariable());
    }
}
