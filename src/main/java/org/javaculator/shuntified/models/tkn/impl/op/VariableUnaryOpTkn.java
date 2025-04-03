package org.javaculator.shuntified.models.tkn.impl.op;

import lombok.Getter;
import org.javaculator.shuntified.models.op.impl.VariableUnaryOp;

@Getter
public class VariableUnaryOpTkn extends OpTkn {
    private final VariableUnaryOp operation;

    public VariableUnaryOpTkn(String originalRep, VariableUnaryOp operation) {
        super(originalRep);
        this.operation = operation;
    }

    @Override
    public VariableUnaryOp getOperation() {
        return operation;
    }
}
