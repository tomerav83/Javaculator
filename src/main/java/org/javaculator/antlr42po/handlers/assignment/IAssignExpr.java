package org.javaculator.antlr42po.handlers.assignment;

import org.javaculator.antlr4.Calc2Parser;

import java.util.Map;
import java.util.function.Function;

public interface IAssignExpr {
    Implementation getImpl(Calc2Parser.ExpressionContext ctx);
    Integer calculate(Calc2Parser.ExpressionContext ctx,
                      Function<Calc2Parser.AdditiveExprContext, Integer> visitor);
    Integer calculate(Calc2Parser.ExpressionContext ctx,
                      Map<String, Integer> vars,
                      Function<Calc2Parser.ExpressionContext, Integer> visitor);

    enum Implementation {
        ASSIGNMENT,
        DF
    }
}
