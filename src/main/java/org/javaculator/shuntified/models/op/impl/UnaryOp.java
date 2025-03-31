package org.javaculator.shuntified.models.op.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.tkn.impl.OperatorTkn;
import org.javaculator.shuntified.models.tkn.impl.UnaryOperatorTkn;

import java.util.function.DoubleUnaryOperator;

@Getter
public class UnaryOp extends Op {
    private final DoubleUnaryOperator action;

    public UnaryOp(String symbol, DoubleUnaryOperator action) {
        super(symbol);

        this.action = action;
    }


    @Override
    public OperatorTkn getToken() {
        return new UnaryOperatorTkn(getSymbol(), this);
    }
}
