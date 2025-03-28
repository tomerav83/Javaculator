package org.javaculator.antlr42po.handlers.additive;

import org.javaculator.antlr4.Calc2Parser;

import java.util.function.Function;

public interface IAdditiveExpr {
    Implementation getImpl(Calc2Parser.AdditiveExprContext ctx);
    Integer calculate(Calc2Parser.AdditiveExprContext ctx, Function<Calc2Parser.MultiplicativeExprContext, Integer> visitor);

    enum Implementation {
        ADDITIVE
    }
}
