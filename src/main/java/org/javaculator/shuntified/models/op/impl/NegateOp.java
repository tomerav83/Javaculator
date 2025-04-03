package org.javaculator.shuntified.models.op.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.tkn.impl.op.OpTkn;
import org.javaculator.shuntified.models.tkn.impl.op.NegateOpTkn;

@Getter
public class NegateOp extends Op {
    public static final String REPLACED_SYMBOL = "[negate]";

    public NegateOp() {
        super(REPLACED_SYMBOL);
    }


    @Override
    public OpTkn getToken() {
        return new NegateOpTkn(getSymbol(), this);
    }
}
