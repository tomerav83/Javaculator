package org.javaculator.shuntified.models.op.impl;

import lombok.Getter;
import org.javaculator.shuntified.models.op.Op;
import org.javaculator.shuntified.models.tkn.impl.BinaryOperatorTkn;
import org.javaculator.shuntified.models.tkn.impl.OperatorTkn;

import java.util.function.DoubleBinaryOperator;

@Getter
public class BinaryOp extends Op {
    private final DoubleBinaryOperator action;
    private final boolean leftAssociative;
    private final int precedence;

    public BinaryOp(String symbol, int precedence, boolean leftAssociative, DoubleBinaryOperator action) {
        super(symbol);

        this.precedence = precedence;
        this.leftAssociative = leftAssociative;
        this.action = action;
    }


    @Override
    public OperatorTkn getToken() {
        return new BinaryOperatorTkn(getSymbol(), this);
    }
}
