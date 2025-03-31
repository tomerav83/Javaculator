package org.javaculator.shuntified.models.tkn.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.op.impl.UnaryOp;

@Getter
public class UnaryOperatorTkn extends OperatorTkn {
    private UnaryOp operation;

    public UnaryOperatorTkn(String originalRep, UnaryOp operation) {
        super(originalRep);
        this.operation = operation;
    }

    @Override
    public Op getOperation() {
        return operation;
    }
}
