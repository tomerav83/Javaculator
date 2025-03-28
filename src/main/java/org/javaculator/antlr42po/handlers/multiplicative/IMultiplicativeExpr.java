package org.javaculator.antlr42po.handlers.multiplicative;

import org.javaculator.antlr4.Calc2Parser;

import java.util.function.Function;

public interface IMultiplicativeExpr {
    Implementation getImpl(Calc2Parser.MultiplicativeExprContext ctx);
    Integer calculate(Calc2Parser.MultiplicativeExprContext ctx, Function<Calc2Parser.UnaryExprContext, Integer> visitor);

    enum Implementation {
        MULTIPLICATIVE
    }
}
