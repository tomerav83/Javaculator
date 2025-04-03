package org.javaculator.shuntified.models.tkn.impl.op;

import org.javaculator.shuntified.models.op.impl.BinaryOp;

public class BinaryOpTkn extends OpTkn {
    private final BinaryOp operation;

    public BinaryOpTkn(String originalRep, BinaryOp operation) {
        super(originalRep);

        this.operation = operation;
    }

    @Override
    public BinaryOp getOperation() {
        return operation;
    }
}
