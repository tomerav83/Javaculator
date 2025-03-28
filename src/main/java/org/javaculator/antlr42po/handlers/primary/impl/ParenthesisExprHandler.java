package org.javaculator.antlr42po.handlers.primary.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.exceptions.InvalidCalculationImplException;
import org.javaculator.antlr42po.handlers.primary.IPrimaryExpr;

import java.util.Map;
import java.util.function.Function;

public class ParenthesisExprHandler implements IPrimaryExpr {
    public static final ParenthesisExprHandler INSTANCE = new ParenthesisExprHandler();

    @Override
    public Implementation getImpl(Calc2Parser.PrimaryExprContext ctx) {
        if (ctx.expression() != null) {
            return Implementation.PARENS;
        }

        return null;
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx) {
        throw new InvalidCalculationImplException(Implementation.PARENS);
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx, Map<String, Integer> vars) {
        throw new InvalidCalculationImplException(Implementation.PARENS);
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx,
                             Function<Calc2Parser.ExpressionContext, Integer> visitor) {
        return visitor.apply(ctx.expression());
    }
}
