package org.javaculator.antlr4.op.abstracts.impl;

import org.javaculator.antlr4.CalcParser;
import org.javaculator.antlr4.op.abstracts.VisitorOp;
import org.javaculator.antlr4.utils.ParserCtxUtils;

import java.util.function.Function;

public abstract class VisitorCalcOp<CTX extends CalcParser.ExpressionContext> extends VisitorOp<CTX> {
    protected VisitorCalcOp(CTX ctx) {
        super(ctx);
    }

    protected abstract Integer calculate(Function<CalcParser.ExpressionContext, Integer> visitor);
}
