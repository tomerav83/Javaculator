package org.javaculator.antlr42po.handlers.unary.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.exceptions.InvalidCalculationImplException;
import org.javaculator.antlr42po.handlers.unary.IUnaryExpr;

import java.util.Map;
import java.util.function.Function;

public class PrimaryExprHandler implements IUnaryExpr {
    public static final PrimaryExprHandler INSTANCE = new PrimaryExprHandler();

    @Override
    public Implementation getImpl(Calc2Parser.UnaryExprContext ctx) {
        if (ctx.primaryExpr() != null) {
            return Implementation.PRIMARY;
        }

        return null;
    }

    @Override
    public Integer calculate(Calc2Parser.UnaryExprContext ctx, Function<Calc2Parser.PrimaryExprContext, Integer> visitor) {
        return visitor.apply(ctx.primaryExpr());
    }

    @Override
    public Integer calculate(Calc2Parser.UnaryExprContext ctx, Map<String, Integer> vars, Function<Calc2Parser.UnaryExprContext, Integer> visitor) {
        throw new InvalidCalculationImplException(Implementation.PRIMARY.name());
    }
}
