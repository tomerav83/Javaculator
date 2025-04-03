package org.javaculator.shuntified.models.tkn.impl.op;

import org.javaculator.shuntified.models.op.impl.AssignOp;

public class AssignOpTkn extends OpTkn {
    private final AssignOp operation;

    public AssignOpTkn(String originalRep, AssignOp operation) {
        super(originalRep);

        this.operation = operation;
    }

    @Override
    public AssignOp getOperation() {
        return operation;
    }
}
