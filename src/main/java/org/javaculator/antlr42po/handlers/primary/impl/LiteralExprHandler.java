package org.javaculator.antlr42po.handlers.primary.impl;

import org.javaculator.antlr4.Calc2Parser;
import org.javaculator.antlr42po.exceptions.InvalidCalculationImplException;
import org.javaculator.antlr42po.handlers.primary.IPrimaryExpr;
import org.javaculator.antlr42po.utils.ParserRuleContextUtils;

import java.util.Map;
import java.util.function.Function;

public class LiteralExprHandler implements IPrimaryExpr {
    public static final LiteralExprHandler INSTANCE = new LiteralExprHandler();

    @Override
    public Implementation getImpl(Calc2Parser.PrimaryExprContext ctx) {
        if (ctx.INT() != null) {
            return Implementation.LITERAL;
        }

        return null;
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx) {
        return ParserRuleContextUtils.getLiteral(ctx);
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx, Map<String, Integer> vars) {
        throw new InvalidCalculationImplException(Implementation.LITERAL);
    }

    @Override
    public Integer calculate(Calc2Parser.PrimaryExprContext ctx, Function<Calc2Parser.ExpressionContext, Integer> visitor) {
        throw new InvalidCalculationImplException(Implementation.LITERAL);
    }


}
