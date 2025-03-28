package org.javaculator.antlr4.op.abstracts.impl;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.op.abstracts.VisitorOp;

public abstract class LiteralCalcOp<CTX extends CalcParser.ExpressionContext> extends VisitorOp<CTX> {
    protected LiteralCalcOp(CTX ctx) {
        super(ctx);
    }

    protected abstract Integer calculate();
}
