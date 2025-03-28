package org.javaculator.antlr42po.handlers.unary;

import org.javaculator.antlr4.Calc2Parser;

import java.util.Map;
import java.util.function.Function;

public interface IUnaryExpr {
    Implementation getImpl(Calc2Parser.UnaryExprContext ctx);
    Integer calculate(Calc2Parser.UnaryExprContext ctx,
                      Function<Calc2Parser.PrimaryExprContext, Integer> visitor);
    Integer calculate(Calc2Parser.UnaryExprContext ctx,
                      Map<String, Integer> vars,
                      Function<Calc2Parser.UnaryExprContext, Integer> visitor);

    enum Implementation {
        UNARY,
        PRIMARY
    }
}
