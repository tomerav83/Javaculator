package org.javaculator.antlr42po.handlers.primary;

import org.javaculator.antlr4.Calc2Parser;

import java.util.Map;
import java.util.function.Function;

public interface IPrimaryExpr {
    Implementation getImpl(Calc2Parser.PrimaryExprContext ctx);
    Integer calculate(Calc2Parser.PrimaryExprContext ctx);
    Integer calculate(Calc2Parser.PrimaryExprContext ctx, Map<String, Integer> vars);
    Integer calculate(Calc2Parser.PrimaryExprContext ctx, Function<Calc2Parser.ExpressionContext, Integer> visitor);

    enum Implementation {
        LITERAL,
        PRE_OP,
        IDENTIFIER,
        PARENS
    }
}
