package org.javaculator.shuntified.models.tkn.impl;

import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.op.impl.BinaryOp;

public class BinaryOperatorTkn extends OperatorTkn {
    private final BinaryOp operation;

    public BinaryOperatorTkn(String originalRep, BinaryOp operation) {
        super(originalRep);

        this.operation = operation;
    }

    @Override
    public Op getOperation() {
        return operation;
    }
}
