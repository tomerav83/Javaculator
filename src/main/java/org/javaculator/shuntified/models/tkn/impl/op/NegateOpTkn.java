package org.javaculator.shuntified.models.tkn.impl.op;

import lombok.Getter;
import org.javaculator.shuntified.models.op.impl.NegateOp;

@Getter
public class NegateOpTkn extends OpTkn {
    private final NegateOp operation;

    public NegateOpTkn(String originalRep, NegateOp operation) {
        super(originalRep);
        this.operation = operation;
    }

    @Override
    public NegateOp getOperation() {
        return operation;
    }
}
